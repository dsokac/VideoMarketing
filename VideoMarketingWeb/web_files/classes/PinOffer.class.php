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
 * Description of PinOffers
 *
 * @author Danijel
 */
class PinOffer extends AbstractModel {
    
    protected $id;
    protected $viewsGoal;
    protected $viewsCurrent;
    protected $timeInterval;
    protected $sponsor;
    protected $active;
    protected $startTime;
    protected $endTime;
    protected $cancelled;
    
    protected $t = "pin_offers";
    protected $tId = "id";
    protected $tViewsGoal = "views_goal";
    protected $tViewsCurrent = "views_current";
    protected $tTimeInterval = "time_interval";
    protected $tSponsor = "sponsor";
    protected $tActive = "active";
    protected $tStartTime = "start_time";
    protected $tEndTime = "end_time";
    protected $tCancelled = "cancelled";
    
    public function getViewsCurrent() {
        return $this->viewsCurrent;
    }

    public function setViewsCurrent($viewsCurrent) {
        $this->viewsCurrent = $viewsCurrent;
    }

        
    public function getViewsGoal() {
        return $this->viewsGoal;
    }

    public function getTimeInterval() {
        return $this->timeInterval;
    }

    public function getSponsor() {
        return $this->sponsor;
    }

    public function getActive() {
        return $this->active;
    }

    public function getStartTime() {
        return $this->printDate($this->startTime);
    }

    public function getEndTime() {
        return $this->printDate($this->endTime);
    }

    public function getCancelled() {
        return $this->cancelled;
    }

    public function setViewsGoal($viewsGoal) {
        $this->viewsGoal = $viewsGoal;
    }

    public function setTimeInterval($timeInterval) {
        $this->timeInterval = $timeInterval;
    }

    public function setSponsor($sponsor) {
        $this->sponsor = addslashes($sponsor);
    }

    public function setActive($active) {
        $this->active = $active;
    }

    public function setStartTime($startTime) {
        $this->startTime = $startTime;
    }

    public function setEndTime($endTime) {
        $this->endTime = $endTime;
    }

    public function setCancelled($cancelled) {
        $this->cancelled = $cancelled;
    }

    public function getId() {
        return $this->id;
    }

    public function __construct($id = null, $data = null) {
        $output = $this->initialOperation($id, $data);
        if($output["exit"]){return;}
        $data = is_null($data) ? $output["data"] : $data;
        $this->id = intval($data[$this->tId]);
        $this->sponsor = addslashes($data[$this->tSponsor]);
        $this->active = intval($data[$this->tActive]);
        $this->cancelled = intval($data[$this->tCancelled]);
        $this->endTime = $data[$this->tEndTime];
        $this->startTime = $data[$this->tStartTime];
        $this->viewsGoal = intval($data[$this->tViewsGoal]);
        $this->viewsCurrent = intval($data[$this->tViewsCurrent]);
        $this->timeInterval = intval($data[$this->tTimeInterval]);
        $this->createdAt = $data[$this->tCreatedAt];
        $this->updatedAt = $data[$this->tUpdatedAt];
        $this->deletedAt =$data[$this->tDeletedAt];
        $this->deleted = intval($data[$this->tDeleted]);
        parent::__construct();
    }

    /**
     * Method creates new row in table filled with data in class properties.
     * @return int num of rows affected by query.
     */
    public function create() {
        $columnsArray = array(
            $this->tViewsGoal,
            $this->tTimeInterval
        );
        
        $valuesArray = array(
            $this->viewsGoal,
            $this->timeInterval
        );
        
        if($this->sponsor != "" && $this->sponsor != null){
            array_push($columnsArray, $this->tSponsor);
            array_push($valuesArray, $this->sponsor);
        }
        
        if($this->active != "" && $this->active != null){
            array_push($columnsArray, $this->tActive);
            array_push($valuesArray, $this->active);
        }
        
        if($this->startTime != "" && $this->startTime != null){
            array_push($columnsArray, $this->tStartTime);
            array_push($valuesArray, $this->startTime);
        }
        
        if($this->endTime != "" && $this->endTime != null){
            array_push($columnsArray, $this->tEndTime);
            array_push($valuesArray, $this->endTime);
        }
        
        return $this->dbOperation(true, $columnsArray, $valuesArray);
    }

    /**
     * Method updates one row related to property id. It updates all properties which are meant to be updated.
     * @return int number of rows affected by query
     */
    public function save() {
        
        $columnsArray = array(
            $this->tViewsGoal,
            $this->tTimeInterval,
            $this->tSponsor,
            $this->tCancelled,
            $this->tActive,
            $this->tDeleted,
            $this->tViewsCurrent
        );
        
        $valuesArray = array(
            $this->viewsGoal,
            $this->timeInterval,
            $this->sponsor,
            $this->cancelled,
            $this->active,
            $this->deleted,
            $this->viewsCurrent
        );
        
        if($this->startTime != "" && $this->startTime != null){
            array_push($columnsArray, $this->tStartTime);
            array_push($valuesArray, $this->startTime);
        }
        
        if($this->endTime != "" && $this->endTime != null){
            array_push($columnsArray, $this->tEndTime);
            array_push($valuesArray, $this->endTime);
        }
        
        if($this->deletedAt != "" && $this->deletedAt != null){
            array_push($columnsArray, $this->tDeletedAt);
            array_push($valuesArray, $this->deletedAt);
        }
        
        return $this->dbOperation(false, $columnsArray, $valuesArray);
        
    }

    //TODO dokumentacija
    public function deactivate(){
        $this->setActive(0);
        $this->save();
    }
    
    //TODO dokumentacija
    public function cancel(){
        $this->setActive(0);
        $this->setCancelled(1);
        $this->save();
    }
    
    //TODO dokumentacija
    public function activate(){
        $this->setActive(1);
        $this->setCancelled(0);
        $this->save();
    }
    
    //TODO dokumentacija
    public function getViews(){
        return $this->getViewsCurrent() + $this->getViewsGoal();
    }
}
