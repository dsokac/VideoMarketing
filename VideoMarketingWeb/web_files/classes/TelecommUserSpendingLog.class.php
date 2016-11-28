<?php
include_once "web_files/classes/MyGlobal.class.php";
include_once "web_files/classes/Db.class.php";
include_once "web_files/classes/models/AbstractModel.class.php";
include_once "web_files/classes/models/User.class.php";
include_once "web_files/classes/models/TelecommOperator.class.php";
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of TelecommUserSpendingLog
 *
 * @author Danijel
 */
class TelecommUserSpendingLog extends AbstractModel {
    
    public static $list = array();
    public static $sumSpentPoins = null;

    
    protected $id;
    protected $user;
    protected $telecommOperator;
    protected $pointsSpent;
    
    protected $t = "telecomm_users_spending_logs";
    protected $tId = "id";
    protected $tUser = "user";
    protected $tTelecommOperator = "telecomm_operator";
    protected $tPointsSpent = "points_spent";
       
    public function getUser() {
        return $this->user;
    }

    public function getTelecommOperator() {
        return $this->telecommOperator;
    }

    public function getPointsSpent() {
        return $this->pointsSpent;
    }

    public function setUser($user) {
        $this->user = is_int($user) ? new User($user) : $user;
    }

    public function setTelecommOperator($telecommOperator) {
        $this->telecommOperator = is_int($telecommOperator) ? new TelecommOperator($telecommOperator) : $telecommOperator;
    }

    public function setPointsSpent($pointsSpent) {
        $this->pointsSpent = $pointsSpent;
    }

    public function getId() {
        return $this->id;
    }

    public function __construct($id = null, $data = null) {
        $output = $this->initialOperation($id, $data);
        if($output["exit"]){return;}
        $data = is_null($data) ? $output["data"] : $data;
        $this->id = intval($data[$this->tId]);
        $this->user = new User(intval($data[$this->tUser]));
        $this->telecommOperator = new TelecommOperator(intval($data[$this->tTelecommOperator]));
        $this->pointsSpent = intval($data[$this->tPointsSpent]);
        $this->createdAt = $data[$this->tCreatedAt];
        $this->updatedAt = $data[$this->tUpdatedAt];
        $this->deletedAt = $data[$this->tDeletedAt];
        $this->deleted = intval($data[$this->tDeleted]);
        parent::__construct();
    }
    
    /**
     * Method creates new row in table filled with data in class properties.
     * @return int num of rows affected by query.
     */
    public function create() {
        $columnsArray = array(
            $this->tUser,
            $this->tTelecommOperator
        );
        $valuesArray = array(
            $this->user->getId(),
            $this->telecommOperator->getId()
        );
        if($this->pointsSpent != null && $this->pointsSpent != ""){
            array_push($columnsArray, $this->tPointsSpent);
            array_push($valuesArray, $this->pointsSpent);
        }
        
        return $this->dbOperation(true, $columnsArray, $valuesArray);
    }

    /**
     * Method updates one row related to property id. It updates all properties which are meant to be updated.
     * @return int number of rows affected by query
     */
    public function save() {
        $columnsArray = array(
            $this->tUser,
            $this->tTelecommOperator,
            $this->tPointsSpent,
            $this->tDeleted
        );
        $valuesArray = array(
            $this->user->getId(),
            $this->telecommOperator->getId(),
            $this->pointsSpent,
            $this->deleted
        );
        if($this->deletedAt != null && $this->deletedAt != ""){
            array_push($columnsArray, $this->tDeletedAt);
            array_push($valuesArray, $this->deletedAt);
        }
        
        return $this->dbOperation(false, $columnsArray, $valuesArray);
    }

    /**
     * Method returns sum of spent points per user and telecomm operator whuch has to be specified.
     * @return int
     */
    public function getSumPoints(){
        $query = Db::makeQuery("select",array($this->t),array("sum({$this->tPointsSpent})"),"{$this->tUser} = {$this->user->getId()} and {$this->tTelecommOperator} =  {$this->telecommOperator->getId()}");
        $data = Db::query($query);
        return intval($data[0]);
    }
 
    /**
     * General method for getting all rows depending on sent object.
     * If object is User, method gets all rows for specified user and calculates sum of its spent points.
     * @param mixed $param can be object of type User or TelecommOperator
     * @throws \Exception wrong type of object is sent to method.
     */
    public function getAllSpec($param){
        $class = get_class($param);
        switch($class){
            case "User": $this->getAllByUser(); break;
            case "TelecommOperator": $this->getAllByTelecommOperator(); break;
            default: throw Exception("Nepoznati parametar");
        }
                
    }
    
    /**
     * Specific method for fetching all rows of user specified in parent method.
     * It also calculates its sum of spent points.
     */
    private function getAllByUser(){
        $query = Db::makeQuery("select", array($this->t), array(), "{$this->tUser} = {$this->user->getId()}");
        $rows = Db::query($query);
        foreach ($rows as $row){
            $newEl = new TelecommUserSpendingLog($row);
            if(self::$sumSpentPoins == null){
                self::$sumSpentPoins = 0;
            }
            self::$sumSpentPoins += $newEl->getPointsSpent();
            array_push(self::$list, $newEl );
        }
    }
    
    /**
     * Specific method that gets all rows of telecomm operator specified in parent method
     */
    private function getAllByTelecommOperator(){
        $query = Db::makeQuery("select", array($this->t), array(), "{$this->tTelecommOperator} = {$this->telecommOperator->getId()}");
        $rows = Db::query($query);
        foreach ($rows as $row){
            $newEl = new TelecommUserSpendingLog($row);
            array_push(self::$list, $newEl );
        }
    }
}
