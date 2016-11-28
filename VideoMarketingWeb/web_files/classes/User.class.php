<?php

include_once "Db.class.php";
include_once "GeographicUnit.class.php";
include_once "MetaUser.class.php";
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Class is used to operate with data from table of the same name as this class.
 *
 * @author Danijel
 */
class User extends MetaUser {

    /**
     * Constants to insert as data. Ensuring easier management.
     */
    const MALE = "M";
    const FEMALE = "F";
    
    /**
     * protected properties which holds values of the table's columns.
     */
    protected $birthday;
    protected $gender;
    protected $deactivated = false;
    protected $geographicUnit;
    protected $mobileNumber;


    /**
     * protected properties which holds names of the table's columns.
     */
    protected $t = "users";
    protected $tBirthday = "birthday";
    protected $tGender = "gender";
    protected $tDeactivated = "deactivated";
    protected $tGeographicUnit = "geographic_unit";
    protected $tMobileNumber = "mobile_number";
    
    
    public function getBirthday() {
        return $this->birthday;
    }

    public function getGender() {
        return $this->gender;
    }

    public function getDeactivated() {
        return $this->deactivated;
    }

    public function getGeographicUnit() {
        return $this->geographicUnit;
    }

    public function setBirthday($birthday) {
        $this->birthday = $birthday;
    }

    public function setGender($gender) {
        $this->gender = $gender;
    }

    public function setDeactivated($deactivated) {
        $this->deactivated = $deactivated;
    }

    public function setGeographicUnit($geographicUnit) {
        $this->geographicUnit = is_int($geographicUnit) ? new GeographicUnit($geographicUnit) : $geographicUnit;
    }

    public function getMobileNumber() {
        return $this->mobileNumber;
    }

    public function setMobileNumber($mobileNumber) {
        $mobileNumber = preg_replace("/0/", "+", $mobileNumber, 1);
        $this->mobileNumber = $mobileNumber;
    }

        
    function __construct($id = null, $data = null) {
        $output = $this->initialOperation($id, $data);
        if($output["exit"]){return;}
        $data = is_null($data) ? $output["data"] : $data;
        $this->id = intval($data[$this->tId]);
        $this->name = $data[$this->tName];
        $this->surname = $data[$this->tSurname];
        $this->birthday = $data[$this->tBirthday];
        $this->gender = $data[$this->tGender];
        $this->email = $data[$this->tEmail];
        $this->username = $data[$this->tUsername];
        $this->password = $data[$this->tPassword];
        $this->passwordChangedAt = $data[$this->tPasswordChangedAt];
        $this->numWrongLogins = intval($data[$this->tNumWrongLogins]);
        $this->blocked = intval($data[$this->tBlocked]);
        $this->deactivated = intval($data[$this->tDeactivated]);
        $this->geographicUnit = new GeographicUnit($data[$this->tGeographicUnit]);
        $this->createdAt = $data[$this->tCreatedAt];
        $this->updatedAt = $data[$this->tUpdatedAt];
        $this->deletedAt = $data[$this->tDeletedAt];
        $this->deleted = intval($data[$this->tDeleted]);
        $this->deblockingLink = $data[$this->tDeblockingLink];
        $this->activationLink = $data[$this->tActivationLink];
        $this->activationSentAt = $data[$this->tActivationSentAt];
        $this->mobileNumber = $data[$this->tMobileNumber];
        parent::__construct();
    }

    /**
     * Method creates new row in table filled with data in class properties.
     * @return int num of rows affected by query.
     */
    public function create() {
        $columnsArray = array($this->tName, $this->tSurname, $this->tBirthday, $this->tGender, $this->tEmail, $this->tUsername, $this->tPassword, $this->tGeographicUnit);
        $valuesArray = array($this->name, $this->surname, $this->birthday, $this->gender, $this->email, $this->username, $this->password, $this->geographicUnit->getId());
        
        if($this->mobileNumber != "" && $this->mobileNumber != null){
            array_push($columnsArray, $this->tMobileNumber);
            array_push($valuesArray, $this->mobileNumber);
        }
        
        return $this->dbOperation(true, $columnsArray, $valuesArray);
    }

    /**
     * Method updates one row related to property id. It updates all properties which are meant to be updated.
     * @return int number of rows affected by query
     */
    public function save() {
        $columnsArray = array(
            $this->tName,
            $this->tSurname,
            $this->tBirthday,
            $this->tGender,
            $this->tEmail,
            $this->tUsername,
            $this->tGeographicUnit,
            $this->tNumWrongLogins,
            $this->tBlocked,
            $this->tDeactivated,
            $this->tDeleted,
            $this->tPassword
        );
        $valuesArray = array(
            $this->name,
            $this->surname,
            $this->birthday,
            $this->gender,
            $this->email,
            $this->username,
            $this->geographicUnit->getId(),
            $this->numWrongLogins,
            $this->blocked,
            $this->deactivated,
            $this->deleted,
            $this->password
        );

       
        if($this->mobileNumber != "" && $this->mobileNumber != null){
            array_push($columnsArray, $this->tMobileNumber);
            array_push($valuesArray, $this->mobileNumber);
        }
        
        if ($this->deletedAt != null && $this->deletedAt != "") {
            array_push($columnsArray, $this->tDeletedAt);
            array_push($valuesArray, $this->deletedAt);
        }

        if ($this->deblockingLink != null && $this->deblockingLink != "") {
            array_push($columnsArray, $this->tDeblockingLink);
            array_push($valuesArray, $this->deblockingLink);
        }
        
        if ($this->passwordChangedAt != null && $this->passwordChangedAt != "") {
            array_push($columnsArray, $this->tPasswordChangedAt);
            array_push($valuesArray, $this->passwordChangedAt);
        }
        
        if ($this->activationLink != null && $this->activationLink != "") {
            array_push($columnsArray, $this->tActivationLink);
            array_push($valuesArray, $this->activationLink);
        }
        
        if ($this->activationSentAt != null && $this->activationSentAt != "") {
            array_push($columnsArray, $this->tActivationSentAt);
            array_push($valuesArray, $this->activationSentAt);
        }
        
        
        return $this->dbOperation(false, $columnsArray, $valuesArray);
    }

    public function doesUserExists($username, $password = null){
        $constraint = is_null($password) ? "{$this->tUsername} = '{$username}'" : "{$this->tUsername} = '{$username}' and {$this->tPassword} = '{$password}'";
        $query = Db::makeQuery("select", array($this->t), array(), $constraint);
        $result = Db::query($query);
        return count($result) >= 1;
    }
    
    public function isUserBlocked(){
        return $this->blocked == 1;
    }
    
    public function isUserDeactivated(){
        return $this->deactivated == 1;
    }
    
    public function isUserDeleted(){
        return $this->deleted == 1;
    }
    
    public function getUserId($username, $password){
        $query = Db::makeQuery("select", array($this->t), array(), "{$this->tUsername} = '{$username}' and {$this->tPassword} = '{$password}'");
        $result = Db::query($query)[0];
        return intval($result[$this->tId]);
    }
    
    public function emailExists($email){
         $query = Db::makeQuery("select", array($this->t), array(), "{$this->tEmail} = '{$email}'");
        $result = Db::query($query);
        return count($result) >= 1;
    }
    
    public function phoneNumberExists($phoneNumber){
        $phoneNumber = preg_replace("/0/", "+", $phoneNumber, 1); 
        $query = Db::makeQuery("select", array($this->t), array(), "{$this->tMobileNumber} = '{$phoneNumber}'");
        $result = Db::query($query);
        return count($result) >= 1;
    }
    
    
}
