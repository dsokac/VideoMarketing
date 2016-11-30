<?php
include_once "web_files/classes/MyGlobal.class.php";
include_once "web_files/classes/Db.class.php";
include_once "web_files/classes/MetaUser.class.php";
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Admin class is used to fill table 'admins' in database and operate with table data.
 *
 * @author Danijel
 */
class Admin extends MetaUser{
    
    /**
     * protected properties specific for this table admins
     */
    protected $superadmin = 0;
    protected $numDeblocking;
    protected $activated = 0;
    
    /**
     * protected properties which hold table name and its column names.
     */
    protected $t = "admins";
    protected $tSuperAdmin = "superadmin";    
    protected $tNumDeblocking = "num_deblocking";
    protected $tActivated = "activated";

    
    function getSuperadmin() {
        return $this->superadmin == 1 ? true : false;
    }

    function getNumDeblocking() {
        return $this->numDeblocking;
    }

    function getActivated() {
        return $this->activated == 1 ? true : false;
    }

    function setSuperadmin($superadmin) {
        $this->superadmin = $superadmin == true ? 1 : 0;
    }

    function setNumDeblocking($numDeblocking) {
        $this->numDeblocking = $numDeblocking;
    }

    function setActivated($activated) {
        $this->activated = $activated == true ? 1 : 0;
    }

        
    public function __construct($id = null, $data = null) {
        $output = $this->initialOperation($id, $data);
        if($output["exit"]){return;}
        $data = is_null($data) ? $output["data"] : $data;
        
        $this->id = intval($data[$this->tId]);
        $this->name = $data[$this->tName];
        $this->surname = $data[$this->tSurname];
        $this->email = $data[$this->tEmail];
        $this->username = $data[$this->tUsername];
        $this->password = $data[$this->tPassword];
        $this->passwordChangedAt = $data[$this->tPasswordChangedAt];
        $this->numWrongLogins = intval($data[$this->tNumWrongLogins]);
        $this->blocked = intval($data[$this->tBlocked]);
        $this->createdAt = $data[$this->tCreatedAt];
        $this->updatedAt = $data[$this->tUpdatedAt];
        $this->deletedAt = $data[$this->tDeletedAt];
        $this->deleted = intval($data[$this->tDeleted]);
        $this->deblockingLink = $data[$this->tDeblockingLink];
        $this->superadmin = intval($data[$this->tSuperAdmin]);
        $this->activationLink = $data[$this->tActivationLink];
        $this->numDeblocking = intval($data[$this->tNumDeblocking]);
        $this->activated = intval($data[$this->tActivated]);
        $this->activationSentAt = $data[$this->tActivationSentAt];
        parent::__construct();
    }
   
    /**
     * Method creates new row in table filled with data in class properties.
     * @return int num of rows affected by query.
     */
    public function create() {
        $columnsArray = array(
            $this->tName,
            $this->tSurname,
            $this->tEmail,
            $this->tUsername,
            $this->tSuperAdmin,
            $this->tActivationLink,
            $this->tPassword
        );
        $valuesArray = array(
            $this->name,
            $this->surname,
            $this->email,
            $this->username,
            $this->superadmin,
            $this->activationLink,
            $this->password
        );
                
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
            $this->tEmail,
            $this->tUsername,
            $this->tNumWrongLogins,
            $this->tBlocked,
            $this->tDeleted,
            $this->tActivated,
            $this->tNumDeblocking
        );
        $valuesArray = array(
            $this->name,
            $this->surname,
            $this->email,
            $this->username,
            $this->numWrongLogins,
            $this->blocked,
            $this->deleted,
            $this->activated,
            $this->numDeblocking
        );

        if($this->passwordChangedAt != null && $this->passwordChangedAt != ""){
            array_push($columnsArray,$this->tPasswordChangedAt);
            array_push($valuesArray,$this->passwordChangedAt);
        }
       
        if($this->activationLink != null && $this->activationLink != ""){
            array_push($columnsArray,$this->tActivationLink);
            array_push($valuesArray,$this->activationLink);
        }
        
        if($this->deblockingLink != null && $this->deblockingLink != ""){
            array_push($columnsArray,$this->tDeblockingLink);
            array_push($valuesArray,$this->deblockingLink);
        }
        
        if($this->deletedAt != null && $this->deletedAt != ""){
            array_push($columnsArray,$this->tDeletedAt);
            array_push($valuesArray,$this->deletedAt);
        }
        
       return $this->dbOperation(false, $columnsArray, $valuesArray);
    }

}
