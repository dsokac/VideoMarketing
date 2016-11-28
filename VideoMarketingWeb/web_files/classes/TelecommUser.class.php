<?php
include_once "MyGlobal.class.php";
include_once "Db.class.php";
include_once "AbstractModel.class.php";
include_once "User.class.php";
include_once "TelecommOperator.class.php";

/* /web_files/classes/models/
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Class is used to operate with the table of the similar name as this class.
 *
 * @author Danijel
 */
class TelecommUser extends AbstractModel {
    
    public static $list = array();
    
    protected $user;
    protected $telecommOperator;
    protected $points;
    protected $activated;
    protected $activatedAt;
    protected $spentPoints;
    protected $userIdRemote;
    
    protected $t = "telecomm_users";
    protected $tUser = "user";
    protected $tTelecommOperator = "telecomm_operator";
    protected $tPoints = "points";
    protected $tActivated = "activated";
    protected $tActivatedAt = "activated_at";
    protected $tSpentPoints = "spent_points";
    protected $tUserIdRemote = "user_id_remote";
    
    function getUser() {
        return $this->user;
    }

    function getTelecommOperator() {
        return $this->telecommOperator;
    }

    function getPoints() {
        return $this->points;
    }
    
    public function getUserIdRemote() {
        return $this->userIdRemote;
    }

    public function setUserIdRemote($userIdRemote) {
        $this->userIdRemote = $userIdRemote;
    }

    
    /**
     * Gets value of activated as bool.
     * @return bool
     */
    function getActivated() {
        return $this->activated == 1 ? true : false;
    }

    function getActivatedAt() {
        return $this->printDate($this->activatedAt);
    }

    function getSpentPoints() {
        return $this->spentPoints;
    }

    function setUser($user) {
        $this->user = is_int($user) ? new User($user) : $user;
    }

    function setTelecommOperator($telecommOperator) {
        $this->telecommOperator = is_int($telecommOperator) ? new TelecommOperator($telecommOperator) : $telecommOperator;
    }

    function setPoints($points) {
        $this->points = $points;
    }

    /**
     * Sets property activated.
     * @param bool $activated
     */
    function setActivated($activated) {
        $this->activated = $activated ? 1 : 0;
    }

    function setActivatedAt() {
        $this->activatedAt = date(MyGlobal::$timeFormatDB);  
    }

    function setSpentPoints($spentPoints) {
        $this->spentPoints = $spentPoints;
    }

    function __construct($user = null, $telecommOperator = null, $data = null) {
        $numArgs = func_num_args();
        if($numArgs == 0){
            return;
        }
        if($user != null && $telecommOperator != null && $data == null){
            $query = Db::makeQuery("SELECT",array($this->t),array(),"{$this->tUser} = {$user} and {$this->tTelecommOperator} = {$telecommOperator}");
            $data = Db::query($query)[0];
        }
        $this->user = new User(intval($data[$this->tUser]));
        $this->telecommOperator = new TelecommOperator(intval($data[$this->tTelecommOperator]));
        $this->activated = intval($data[$this->tActivated]);
        $this->activatedAt = $data[$this->tActivatedAt];
        $this->createdAt = $data[$this->tCreatedAt];
        $this->updatedAt = $data[$this->tUpdatedAt];
        $this->deleted = intval($data[$this->tDeleted]);
        $this->deletedAt = $data[$this->tDeletedAt];
        $this->points = intval($data[$this->tPoints]);
        $this->spentPoints = intval($data[$this->tSpentPoints]);
        $this->userIdRemote = preg_match("/^[^0-9]+/", $data[$this->tUserIdRemote]) ? $data[$this->tUserIdRemote] : intval($data[$this->tUserIdRemote]);
        $this->classConstraint = "{$this->tUser} = {$this->user->getId()} and {$this->tTelecommOperator} = {$this->telecommOperator->getId()}";
    }

    /**
     * Method creates new row in table filled with data in class properties.
     * @return int num of rows affected by query.
     */
    public function create() {
        if($this->hasTelecommConnection()){
            $this->setActivated(false);
            $this->activatedAt = null;
        } else {
            $this->setActivated(true);
            $this->setActivatedAt();
        }

        $columnsArray = array(
            $this->tUser,
            $this->tTelecommOperator,
            $this->tActivated
        );
               
        $valuesArray = array(
            $this->user->getId(),
            $this->telecommOperator->getId(),
            $this->activated
        );
       
        if($this->userIdRemote != null && $this->userIdRemote != ""){
            array_push($columnsArray, $this->tUserIdRemote);
            array_push($valuesArray, $this->userIdRemote);
        }
        
        if($this->activatedAt != null && $this->activatedAt != ""){
            array_push($columnsArray, $this->tActivatedAt);
            array_push($valuesArray, $this->activatedAt);
        }
        
        return $this->dbOperation(true, $columnsArray, $valuesArray);
    }

    /**
     * Method updates one row related to property id. It updates all properties which are meant to be updated.
     * @return int number of rows affected by query
     */
    public function save() {
        $columnsArray = array(
            $this->tPoints,
            $this->tActivated,
            $this->tDeleted,
            $this->tSpentPoints
        );
        $valuesArray = array(
            $this->points,
            $this->activated,
            $this->deleted,
            $this->spentPoints
        );
        
        if($this->userIdRemote != null && $this->userIdRemote != ""){
            array_push($columnsArray, $this->tUserIdRemote);
            array_push($valuesArray, $this->userIdRemote);
        }
        
        if($this->activatedAt != null && $this->activatedAt != ""){
            array_push($columnsArray, $this->tActivatedAt);
            array_push($valuesArray, $this->activatedAt);
        }
        
        if($this->deletedAt != null && $this->deletedAt != ""){
            array_push($columnsArray, $this->tDeletedAt);
            array_push($valuesArray, $this->deletedAt);
        }

        return $this->dbOperation(false, $columnsArray, $valuesArray);      
    }
    
    /**
     * Method counts number of telecomm operator's connection to the user specified in property.
     * It returns true only if user has no other telecomm operators.
     * @return bool
     */
    public function hasTelecommConnection(){
        $query = Db::makeQuery("select", array($this->t), array("count(*)"),"{$this->tUser} = {$this->user->getId()}");
        $data = Db::query($query)[0];
        return intval($data["count(*)"]) > 0 ? true : false;
    }
    
    /**
     * Method returns active Telecomm operator for user specified in user property of class TelecommUser
     * @return \TelecommOperator
     */
    public function getActiveTelecommOperator(){
        if(!$this->hasTelecommConnection()){
            return MyGlobal::$i18nFile["no_telecomm_conn"];
        }
        $query = Db::makeQuery("select", array($this->t),array($this->tTelecommOperator),"{$this->tUser} = {$this->user->getId()} and {$this->tActivated} = 1 ");
        $data = Db::query($query)[0];
        return new TelecommOperator($data[$this->tTelecommOperator]);
    }
  
    /**
     * Method fetches all telecomm operator connections related to user specified.
     * @param \User $user object of class User
     */
    public function getAllPerUser($user){
        $query = Db::makeQuery("select", array($this->t),array(),"{$this->tUser} = {$user->getId()}");
        $rows = Db::query($query);
        foreach ($rows as $row){
            array_push(self::$list,new TelecommUser($user->getId(), new TelecommOperator(null, $row)));
        }
    }
    
    /**
     * Method to add points to User and logging.
     * @param int $point points that shall be added
     */
    public function addPoints($point){
        $this->points += $point;
        return $this->save();
    }
    
    //TODO dokumentacija
    public function switchTelecommOperator($newTelecommOperator){
        $link = Db::connect();
        $link->beginTransaction();
        $query = Db::makeQuery("select", array($this->t), array($this->tTelecommOperator), "{$this->tUser} = {$this->user->getId()} and {$this->tActivated} = 1");
        
        $stmt = $link->prepare($query);
        $result = $stmt->execute();
        if($stmt->rowCount() == 0){
            $link->rollBack();
            return false;
        } 
        $aTelecomm =$stmt->fetch()[$this->tTelecommOperator];
        $query = Db::makeQuery("update", array($this->t), array($this->tActivated), "{$this->tUser} = {$this->user->getId()} and {$this->tTelecommOperator} = {$aTelecomm}", array(0));
        $stmt = $link->prepare($query);
        $result = $stmt->execute();
        if($stmt->rowCount() == 0){
            $link->rollBack();
            return false;
        }
        $query = Db::makeQuery("select", array("telecomm_operators"),array("id"),"code = '$newTelecommOperator' and deleted = 0");
        $stmt = $link->prepare($query);
        $result = $stmt->execute();
        if($stmt->rowCount() < 1){
          $link->rollBack();
          return false;
        }
        $newTelecommOperator = $stmt->fetch()["id"];
        $query = Db::makeQuery("update", array($this->t), array($this->tActivated, $this->tActivatedAt), "{$this->tUser} = {$this->user->getId()} and {$this->tTelecommOperator} = {$newTelecommOperator}", array(1, date(MyGlobal::$timeFormatDB)));
        $stmt = $link->prepare($query);
        $result = $stmt->execute();
        if($stmt->rowCount() == 0){
            $link->rollBack();
            return false;
        }
        $link->commit();
        return true;
    }
    
    private function doesTelecommUserExists($telecommOperator){
        $query = Db::makeQuery("select", array($this->t), array(), "{$this->tUser} = {$this->user->getId()} and {$this->tTelecommOperator} = {$telecommOperator}");
        $result = Db::query($query);
        return count($result) == 1;
    }
    
    //TODO documentation
    public static function getTelecommUserByTelecommCode($user, $code){
       $query = Db::makeQuery("select", array("telecomm_operators"), array("id"), "code = '{$code}'");
       $result = Db::query($query);
       $id = $result[0]["id"];
       return new TelecommUser($user, $id);
     }
     
     //TODO documentation
     public function setTelecommOperatorUsingCode($code){
       $query = Db::makeQuery("select", array("telecomm_operators"), array("id"), "code = '{$code}'");
       $result = Db::query($query);
       $id = $result[0]["id"];
       $this->setTelecommOperator(intval($id));
     }
    
}
