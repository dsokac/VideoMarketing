<?php
include_once "MyGlobal.class.php";
include_once "Db.class.php";
include_once "AbstractModel.class.php";
include_once "Video.class.php";
include_once "Tag.class.php";
include_once "VideoView.class.php";
include_once "PinnedVideo.class.php";
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Class is used for operations with the table of the similar name as this class.
 *
 * @author Danijel
 */
class VideoTag extends AbstractModel {
    
    public static $list = array();
    protected $classConstraint;
    
    protected $video;
    protected $tag;
 
    protected $t = "videotags";
    protected $tVideo = "video";
    protected $tTag = "tag";
    
    public function getVideo() {
        return $this->video;
    }

    public function getTag() {
        return $this->tag;
    }

    public function setVideo($video) {
        $this->video = !is_int($video) ? $video : new Video($video);
    }

    public function setTag($tag) {
        $this->tag = !is_int($tag) ? $tag : new Tag($tag);
    }
    
    public function __construct($video = null, $tag = null, $data = null) {
        $numArgs = func_num_args();
        if($numArgs == 0){
            return;
        }
        if(!is_null($video) && !is_null($tag) && is_null($data)){
            $query = Db::makeQuery("select", array($this->t), array(), "{$this->tVideo} = {$this->video->getId()} and {$this->tTag} = {$this->tag->getId()}");
            $data = Db::query($query)[0];
        } 
        $this->video = new Video(intval($data[$this->tVideo]));
        $this->tag = new Tag(intval($data[$this->tTag]));
        $this->createdAt = $data[$this->tCreatedAt];
        $this->updatedAt = $data[$this->tUpdatedAt];
        $this->deletedAt = $data[$this->tDeletedAt];
        $this->deleted = intval($data[$this->tDeleted]);
         $this->classConstraint = "{$this->tVideo} = {$this->video->getId()} and {$this->tTag} = {$this->tag->getId()}";
        
        }

    
    /**
     * Method creates new row in table filled with data in class properties.
     * @return int num of rows affected by query.
     */
    public function create() {
       $columnsArray = array(
           $this->tVideo,
           $this->tTag
       );
       $valuesArray = array(
           $this->video->getId(),
           $this->tag->getId()
       );
       
       return $this->dbOperation(true, $columnsArray, $valuesArray);
    }

    /**
     * Method updates one row related to property id. It updates all properties which are meant to be updated.
     * @return int number of rows affected by query
     */
    public function save() {
        $columnsArray = array(
           $this->tDeleted
       );
       $valuesArray = array(
           $this->deleted
       );
       
       if($this->deletedAt != null && $this->deletedAt != ""){
           array_push($columnsArray, $this->tDeletedAt);
           array_push($valuesArray, $this->deletedAt);
       }
       
       return $this->dbOperation(false, $columnsArray, $valuesArray);
    }

    /**
     * Method fetches all videos which are labeled by tag.
     * @param int $arrayOfTags array of tag ids from table
     * @return array array of Video objects
     */
    public function getVideosByTags($arrayOfTags, $userId){
        //TODO složiti transakciju koja će vratiti set videa koji bi trebali biti u jsonu
        //"select videos.id, videos.title from videos where videos.id not in (select videotags.video from videotags where videotags.tag in (select tags.id from tags where tags.id not in(2,4,5)))";
        $stringTags = implode(",",$arrayOfTags);
        $tags = new Tag();
        $query = Db::makeQuery("select",array($tags->getTagTable()), array($tags->getTagTableId()), "{$tags->getTagTableId()} not in ({$stringTags})");

        $rows = Db::query($query);
        $notInTags = array();
        foreach($rows as $row){
            array_push($notInTags, $row[$tags->getTagTableId()]);  
        }
        $stringNotInTags = implode(",",$notInTags);
        $query2 = Db::makeQuery("select", array($this->t), array($this->tVideo), "{$this->tTag} in ({$stringNotInTags})");
        $rows2 = Db::query($query2);
        
        $notWantedVideos = array();
        if($rows2  != null){
            foreach($rows2 as $row){
                array_push($notWantedVideos, $row[$this->tVideo]);
            }
        }
        $stringNotWantedVideos = implode(",",$notWantedVideos);
        $video = new Video();
        $query3 = Db::makeQuery("select",array($video->getVideoTable()),array(),count($notWantedVideos) != 0 ? "{$video->getVideoTableId()} not in ({$stringNotWantedVideos}) and {$this->tDeleted} = 0" :  null);
        $rows3 = Db::query($query3);
        $list = array();
        if($rows3 != null){
            foreach($rows3 as $row){
                $newVideo = new Video(null, $row);
                $newPinnedVideo = new PinnedVideo();
                $newPinnedVideo->setVideo($newVideo->getId());
                $newPinnedVideo->checkIfVideoIsSponsored();
                array_push($list, $newPinnedVideo->getVideo());
            }
        }       
        
        $newJSON = array(
            "status" => 100,
            "requested_at" => date(MyGlobal::$timeFormat),
            "data" => array()
        );
        $videoView = new VideoView();
        $videoView->setUser(intval($userId));
        foreach($list as $element){
            $videoView->setVideo($element->getId());
            if($videoView->doesViewExists()){
              $videoView = new VideoView(intval($userId), intval($element->getId()));
            }
            $newArray = array(
                "id" => $element->getId(),
                "title" => $element->getTitle(),
                "link" => $element->getLink(),
                "thumbnail_url" => $element->getThumbnailUrl(),
                "likes" => $element->getLikes(),
                "sponsored" => $element->getSponsored(),
                "dislikes" => $element->getDislikes(),
                "points" => $element->getPoints(),
                "seen" => $videoView->doesViewExists() ? 1 : 0,
                "user_like" => $videoView->doesViewExists() ? $videoView->getVideolike() : 0,
                "created_at" => $element->getCreatedAt()
            );
            array_push($newJSON["data"], $newArray);
        }
        return $newJSON;
    }
}
