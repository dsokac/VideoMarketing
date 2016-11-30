<?php
include_once "MyGlobal.class.php";
include_once "Db.class.php";
include_once "AbstractModel.class.php";

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Class used to operate with table of similar name to this class name.
 *
 * @author Danijel
 */
class TelecommOperator extends AbstractModel {
    
    public $list = array();
    
    protected $id;
    protected $name = null;
    protected $userPageUrl = null;
    protected $code = null;


    protected $t = "telecomm_operators";
    protected $tId = "id";
    protected $tName = "name";
    protected $tUserPageUrl = "user_page_url";
    protected $tCode = "code";
            
    
    function getName() {
        return $this->name;
    }

    function getUserPageUrl() {
        return $this->userPageUrl;
    }

    function setName($name) {
        $this->name = $name;
    }

    function setUserPageUrl($userPageUrl) {
        $this->userPageUrl = $userPageUrl;
    }

    function getId() {
        return $this->id;
    }

    public function getCode() {
        return $this->code;
    }

    public function setCode($code) {
        $this->code = $code;
    }

        
    function __construct($id = null, $data = null) {
        $output = $this->initialOperation($id, $data);
        if($output["exit"]){return;}
        $data = is_null($data) ? $output["data"] : $data;
        
    $this->id = intval($data[$this->tId]);
    $this->name = $data[$this->tName];
    $this->userPageUrl = $data[$this->tUserPageUrl];
    $this->createdAt = $data[$this->tCreatedAt];
    $this->updatedAt = $data[$this->tUpdatedAt];
    $this->deleted = intval($data[$this->tDeleted]);
    $this->deletedAt = $data[$this->tDeletedAt];
    $this->code = $data[$this->tCode];
    parent::__construct();    
        
    }
    
    /**
     * Method creates new row in table filled with data in class properties.
     * @return int num of rows affected by query.
     */
    public function create() {
        $columnsArray = array(
            $this->tName,
            $this->tUserPageUrl
        );
        $valuesArray = array(
            $this->name,
            $this->userPageUrl
        );
        
        if($this->code != "" && $this->code != null){
            array_push($columnsArray, $this->tCode);
            array_push($valuesArray, $this->code);
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
            $this->tUserPageUrl,
            $this->tDeleted
        );
        $valuesArray = array(
            $this->name,
            $this->userPageUrl,
            $this->deleted
        );
        
        if($this->deletedAt != "" && $this->deletedAt != null){
            array_push($columnsArray, $this->tDeletedAt);
            array_push($valuesArray, $this->deletedAt);
        }
        
        if($this->code != "" && $this->code != null){
            array_push($columnsArray, $this->tCode);
            array_push($valuesArray, $this->code);
        }
        
        return $this->dbOperation(false, $columnsArray, $valuesArray);
    }

    
}
