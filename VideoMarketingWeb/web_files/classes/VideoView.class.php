<?php
include_once "MyGlobal.class.php";
include_once "Db.class.php";
include_once "AbstractModel.class.php";
include_once "User.class.php";
include_once "Video.class.php";
include_once "TelecommUser.class.php";
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Class used to operate with table of the similar name as this class
 *
 * @author Danijel
 */
class VideoView extends AbstractModel {
    
    public static $list = array();
    
    protected $user;
    protected $video;
    protected $count = 0;
    protected $videolike = 0;
    
    protected $t = "video_views";
    protected $tUser = "user";
    protected $tVideo = "video";
    protected $tCount = "count";
    protected $tVideolike = "videolike";
    
    public function getUser() {
        return $this->user;
    }

    public function getVideo() {
        return $this->video;
    }

    public function getCount() {
        return $this->count;
    }

    public function getVideolike() {
        return $this->videolike;
    }

    public function setUser($user) {
        $this->user = is_int($user) ? new User($user) : $user;
    }

    public function setVideo($video) {
        $this->video = is_int($video) ? new Video($video) : $video;
    }

    public function setCount($count) {
        $this->count = $count;
    }

    public function setVideolike($videolike) {
        $this->videolike = $videolike;
    }

    public function __construct($user = null, $video = null, $data = null) {
        $numArgs = func_num_args();
        if($numArgs == 0){
            return;
        }
        if(!is_null($user) && !is_null($video) && is_null($data)){
            $query = Db::makeQuery("select", array($this->t),array(),"{$this->tUser} = {$user} and {$this->tVideo} = {$video}");
            $data = Db::query($query)[0];
        }
        $this->video = new Video(intval($data[$this->tVideo]));
        $this->user = new User(intval($data[$this->tUser]));
        $this->videolike = intval($data[$this->tVideolike]);
        $this->count = intval($data[$this->tCount]);
        $this->createdAt = $data[$this->tCreatedAt];
        $this->updatedAt = $data[$this->tUpdatedAt];
        $this->deletedAt = $data[$this->tDeletedAt];
        $this->deleted = $data[$this->tDeleted];
        $this->classConstraint = "{$this->tUser} = {$this->user->getId()} and {$this->tVideo} = {$this->video->getId()}";
    }

    /**
     * Method creates new row in table filled with data in class properties.
     * @return int num of rows affected by query.
     */
    public function create() {
        $columnsArray = array(
            $this->tVideo,
            $this->tUser
        );
        $valuesArray = array(
            $this->video->getId(),
            $this->user->getId()
        );
        
        if($this->videolike != "" && $this->videolike != null){
            array_push($columnsArray, $this->tVideolike);
            array_push($valuesArray, $this->videolike);
        }
        return $this->dbOperation(true, $columnsArray, $valuesArray);
    }

    /**
     * Method updates one row related to property id. It updates all properties which are meant to be updated.
     * @return int number of rows affected by query
     */
    public function save() {
        $columnsArray = array(
            $this->tCount,
            $this->tVideolike,
            $this->tDeleted
        );
        $valuesArray = array(
            $this->count,
            $this->videolike,
            $this->deleted
        );
        
        if($this->deletedAt != "" && $this->deletedAt != null){
            array_push($columnsArray, $this->tDeletedAt);
            array_push($valuesArray, $this->deletedAt);
        }
        
        return $this->dbOperation(false, $columnsArray, $valuesArray);
    }

    /**
     * Method adds view and adds points if the user should get points for view.
     * If user has more than 1 view, it does not get any points.
     */
    public function addView(){
        if($this->doesViewExists()){
            $addView = new VideoView($this->user->getId(),$this->video->getId());
            $addView->count += 1;
            return $addView->save();
        } else {
            $result = $this->create();
            if($result != 0){
                $result = $this->addPointsToUser();
            }
            return $result;
        }        
    }
    
    /**
     * Method adds points to user by using Telecomm Users class and only if the user viewed video once.
     */
    public function addPointsToUser(){

        $points = $this->video->getPoints();

        $tcUser = new TelecommUser();
        $tcUser->setUser($this->user->getId());
        $tc = $tcUser->getActiveTelecommOperator();
        $tcEdit = new TelecommUser($this->user->getId(),$tc->getId());
        return $tcEdit->addPoints($points);                   
    }
    
    //TODO dokumentacija
    public function doesViewExists(){
        $query = Db::makeQuery("select", array($this->t),array(),"{$this->tUser} = {$this->user->getId()} and {$this->tVideo} =  {$this->video->getId()}");
        $result = count(Db::query($query));
        return $result == 1;
    }
    
    public function hasUserViews(){
        $query = Db::makeQuery("select", array($this->t),array(),"{$this->tUser} = {$this->user->getId()}");
        $result = count(Db::query($query));
        return $result >= 1;
    }
    
    //TODO dokumentacija
    public function getSumViews(){
        $query = Db::makeQuery("select", array($this->t), array("sum({$this->tCount})"),"{$this->tVideo} = {$this->video->getId()} and deleted = 0 group by {$this->tVideo}");
        $result = Db::query($query);
        return intval($result[0]["sum({$this->tCount})"]);
    }
}
