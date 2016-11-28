<?php
include_once "web_files/classes/MyGlobal.class.php";
include_once "web_files/classes/Db.class.php";
include_once "web_files/classes/models/AbstractModel.class.php";
include_once "web_files/classes/models/User.class.php";
include_once "web_files/classes/models/Admin.class.php";
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Class is used to operate with database table "application_logs". Its properties are columns in the table.
 *
 * @author Danijel
 */
class ApplicationLog extends AbstractModel {

    /**
     * Constants used for easier data management.
     */
    const STATUS_FAILURE = -1;
    const STATUS_NEUTRAL = 0;
    const STATUS_SUCCESS = 1;
    const TYPE_DB_OPERATION = 1;
    const TYPE_LOGIN_LOGOUT = 2;
    const TYPE_OTHER = 3;
    
    public static $list = array();
    
    /**
     * protected properties which are columns in the table.
     */
    protected $id;
    protected $type = 3;
    protected $success = 0;
    protected $user;
    protected $admin = 0;
    protected $ipAddress;
    protected $script;
    protected $text;
    
    /**
     * protected properties which are name of table and its column for easier maintenance.
     */
    protected $t = "application_logs";
    protected $tId = "id";
    protected $tType = "type";
    protected $tSuccess = "success";
    protected $tUser = "user";
    protected $tAdmin = "admin";
    protected $tIpAddress = "ip_address";
    protected $tScript = "script";
    protected $tText = "text";
    
    function getType() {
        return $this->type;
    }

    function getSuccess() {
        return $this->success;
    }

    function getUser() {
        return $this->user;
    }

    function getAdmin() {
        return $this->admin == 1 ? true : false;
    }

    function getIpAddress() {
        return $this->ipAddress;
    }

    function getScript() {
        return $this->script;
    }

    function getText() {
        return $this->text;
    }

    function setType($type) {
        $this->type = $type;
    }

    function setSuccess($success) {
        $this->success = $success;
    }

    function setUser($user) {
        if(is_int($user)){
            $this->user = $this->admin == 0 ? new User($user) : new Admin($user);
        } else {
             $this->user = $user;
        }
    }

    function setAdmin($admin) {
        $this->admin = $admin;
    }

    function setIpAddress() {
        $this->ipAddress = $_SERVER["REMOTE_ADDR"];
    }

    function setScript($script) {
        $this->script = $script;
    }

    function setText($text) {
        $this->text = $text;
    }

    function getId() {
        return $this->id;
    }



    public function __construct($id = null, $data = null) {
        $output = $this->initialOperation($id, $data);
        if($output["exit"]){return;}
        $data = $data == null ? $output["data"] : $data;
        $this->id = intval($data[$this->tId]);
        $this->type = intval($data[$this->tType]);
        $this->success = intval($data[$this->tSuccess]);
        $this->admin = intval($data[$this->tAdmin]);
        
        $this->user = $this->admin == 0 ? new User(intval($data[$this->tUser])) : new Admin(intval($data[$this->tAdmin]));
        $this->ipAddress = $data[$this->tIpAddress];
        $this->script = $data[$this->tScript];
        $this->text = $data[$this->tText];
        $this->createdAt = $data[$this->tCreatedAt];
        $this->updatedAt = $data[$this->tUpdatedAt];
        $this->deleted = intval($data[$this->deleted]);
        $this->deletedAt = $data[$this->tDeletedAt];
        parent::__construct();
    }

    /**
     * Method creates new row in table filled with data in class properties.
     * @return int num of rows affected by query.
     */
    public function create() {
        $columnsArray = array($this->tType, $this->tSuccess, $this->tUser, $this->tAdmin, $this->tIpAddress, $this->tText);
        $valuesArray = array($this->type, $this->success, $this->user->getId(), $this->admin, $this->ipAddress, $this->text);
     
        if($this->script != null && $this->script != ""){
            array_push($columnsArray, $this->tScript);
            array_push($valuesArray, $this->script);
        }
        
        return $this->dbOperation(true, $columnsArray, $valuesArray);
    }

    /**
     * Method updates one row related to property id. It updates all properties which are meant to be updated.
     * @return int number of rows affected by query
     */
    public function save() {
        $columnsArray = array($this->tType, $this->tSuccess, $this->tUser, $this->tAdmin, $this->tIpAddress, $this->tText, $this->tDeleted);
        $valuesArray = array($this->type, $this->success, $this->user->getId(), $this->admin, $this->ipAddress, $this->text, $this->deleted);
     
        if($this->script != null && $this->script != ""){
            array_push($columnsArray, $this->tScript);
            array_push($valuesArray, $this->script);
        }
    
        if($this->deletedAt != null && $this->deletedAt != ""){
            array_push($columnsArray, $this->deletedAt);
            array_push($valuesArray, $this->deletedAt);
        }
        
       return $this->dbOperation(false, $columnsArray, $valuesArray);
    }
    
    

}
