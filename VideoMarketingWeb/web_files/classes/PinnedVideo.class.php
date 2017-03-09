<?php
include_once "MyGlobal.class.php";
include_once "Db.class.php";
include_once "AbstractModel.class.php";
include_once "Video.class.php";
include_once "PinOffer.class.php";
include_once "VideoView.class.php";
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of PinnedVideo
 *
 * @author Danijel
 */
class PinnedVideo extends AbstractModel {
    protected $video;
    protected $pinOffer;
    
    protected $t = "pinned_videos";
    protected $tVideo = "video";
    protected $tPinOffer = "pin_offer";
    
    public function getVideo() {
        return $this->video;
    }

    public function getPinOffer() {
        return $this->pinOffer;
    }

    public function setVideo($video) {
        $this->video = is_int($video)? new Video($video) : $video;
    }

    public function setPinOffer($pinOffer) {
        $this->pinOffer = is_int($pinOffer) ? new PinOffer($pinOffer) : $pinOffer;
    }

    public function __construct($video = null, $pinOffer = null, $data = null) {
        $numArgs = func_num_args();
        if($numArgs == 0){
            return;
        }        
        if(!is_null($video) && !is_null($pinOffer) && is_null($data)){
            $query = Db::makeQuery("select", array($this->t), array(), "{$this->tVideo} = {$video} and {$this->tPinOffer} = $pinOffer");
            $data = Db::query($query);
        }
        
        $this->video = new Video(intval($data[$this->tVideo]));
        $this->pinOffer = new PinOffer(intval($data[$this->tPinOffer]));
        $this->createdAt = $data[$this->tCreatedAt];
        $this->updatedAt = $data[$this->tUpdatedAt];
        $this->deletedAt = $data[$this->tDeletedAt];
        $this->deleted = intval($data[$this->tDeleted]);
        $this->classConstraint = "{$this->tVideo} = {$this->video->getId()} and {$this->tPinOffer} = {$this->pinOffer}";
    }

    //TODO dokumentacija
    public function isSponsored(){
        $query = Db::makeQuery("select", array($this->t), array($this->tPinOffer), "{$this->tVideo} = {$this->video->getId()} and {$this->tDeleted} = 0 order by {$this->tCreatedAt} desc");
        $rows = Db::query($query);
        foreach($rows as $row){
            $pinOfferID = intval($row[$this->tPinOffer]);
            $newPinOffer = new PinOffer($pinOfferID);
            if($newPinOffer->getActive() == 1){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Method creates new row in table filled with data in class properties.
     * @return int num of rows affected by query.
     */
    public function create() {
       $columnsArray = array(
           $this->tPinOffer,
           $this->tVideo
       );
       $valuesArray = array(
           $this->pinOffer->getId(),
           $this->video->getId()
       );
       
       $vw = new VideoView();
       $vw->setVideo($this->video->getId());
       $this->pinOffer->setViewsCurrent($vw->getSumViews());
       $this->pinOffer->save();
       
       return $this->dbOperation(true, $columnsArray, $valuesArray);
    }

    /**
     * Method updates one row related to property id. It updates all properties which are meant to be updated.
     * @return int number of rows affected by query
     */
    public function save() {
        $columnsArray = array(
           $this->tPinOffer,
           $this->tVideo,
           $this->tDeleted
       );
       $valuesArray = array(
           $this->pinOffer->getId(),
           $this->video->getId(),
           $this->deleted
       );
       
       if($this->deletedAt != "" && $this->deletedAt != null){
           array_push($columnsArray, $this->tDeletedAt);
           array_push($valuesArray, $this->deletedAt);
       }
       
       return $this->dbOperation(false, $columnsArray, $valuesArray);
    }
    

    //TODO dokumentacija
    public function checkIfVideoIsSponsored(){
        //TODO zamijeniti nekom funkcijom u SQL-u
        $query = Db::makeQuery("select", array($this->t), array($this->tPinOffer), "{$this->tVideo} = {$this->video->getId()} and deleted = 0");
        $rows = Db::query($query);
        if($rows != null){
            foreach($rows as $row){
                $pinOfferChanged = false;
                $pinOfferID = intval($row[$this->tPinOffer]);
                $currentPinOffer = new PinOffer($pinOfferID);
                $today = new DateTime();
                $thenStart = date_create_from_format(MyGlobal::$timeFormat, $currentPinOffer->getStartTime());
                $thenEnd = date_create_from_format(MyGlobal::$timeFormat, $currentPinOffer->getEndTime());
                $vw = new VideoView();
                $vw->setVideo($this->video->getId());

                if(($thenStart > $today || $today > $thenEnd || ($vw->getSumViews() >= $currentPinOffer->getViews())) && $currentPinOffer->getActive()==1 && $currentPinOffer->getCancelled() == 0 ){
                    $currentPinOffer->deactivate();
                    $pinOfferChanged = true;
                } else if($thenStart <= $today && $today <= $thenEnd && $vw->getSumViews() < $currentPinOffer->getViews() && $currentPinOffer->getCancelled() == 0 && $currentPinOffer->getActive() == 0 ){
                    $currentPinOffer->activate();   
                    $pinOfferChanged = true;
                }
            }
            if($pinOfferChanged){
                $sponsored = $this->isSponsored();
                $this->video->setSponsored($sponsored ? 1 : 0);
                $this->video->save();
            }
        }
    }
    
    public function getPinOfferForVideo(){
      $query = Db::makeQuery("select", array($this->t), array($this->tPinOffer), "{$this->tVideo} = {$this->video->getId()} and deleted = 0 order by {$this->tCreatedAt} desc");
      $rows = Db::query($query);
      if($rows != null){
        foreach($rows as $row){
           $pinOfferID = intval($row[$this->tPinOffer]);
           $pinOffer = new PinOffer($pinOfferID);
           if($pinOffer->getActive() == 1){
             return $pinOffer->getId();
           }
        }
      }
      return -1;
    
    }
    
    
}
