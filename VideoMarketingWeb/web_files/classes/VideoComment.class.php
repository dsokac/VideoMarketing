<?php
include_once "MyGlobal.class.php";
include_once "Db.class.php";
include_once "AbstractModel.class.php";
include_once "User.class.php";
include_once "Video.class.php";
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Class is used to operate with the table of the similar name as this class.
 *
 * @author Danijel
 */
class VideoComment extends AbstractModel {
    
    public static $list = array();
    
    protected $id;
    protected $author;
    protected $video;
    protected $content;
    
    protected $t = "video_comments";
    protected $tId = "id";
    protected $tAuthor = "author";
    protected $tVideo = "video";
    protected $tContent = "content";
    
    public function getAuthor() {
        return $this->author;
    }

    public function getVideo() {
        return $this->video;
    }

    public function getContent() {
        return $this->content;
    }

    public function setAuthor($author) {
        $this->author = is_int($author) ? new User($author) : $author;
    }

    public function setVideo($video) {
        $this->video = is_int($video) ? new Video($video) : $video;
    }

    public function setContent($content) {
        $this->content = $content;
    }

    public function getId() {
        return $this->id;
    }

    public function __construct($id = null, $data = null) {
        $output = $this->initialOperation($id, $data);
        if($output["exit"]){return;}
        $data = is_null($data) ? $output["data"] : $data;
        $this->id = intval($data[$this->tId]);
        $this->author = new User(intval($data[$this->tAuthor]));
        $this->video = new Video(intval($data[$this->tVideo]));
        $this->content = $data[$this->tContent];
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
            $this->tAuthor,
            $this->tVideo,
            $this->tContent
        );
        $valuesArray = array(
            $this->author->getId(),
            $this->video->getId(),
            $this->content
        );
        return $this->dbOperation(true, $columnsArray, $valuesArray);
    }

    /**
     * Method updates one row related to property id. It updates all properties which are meant to be updated.
     * @return int number of rows affected by query
     */
    public function save() {
        $columnsArray = array(
            $this->tContent,
            $this->tDeleted
        );
        
        $valuesArray = array(
            $this->content,
            $this->deleted
        );
        
        if($this->deletedAt != null && $this->deletedAt != ""){
            array_push($columnsArray, $this->tDeletedAt);
            array_push($valuesArray, $this->deletedAt);
        }
        
        return $this->dbOperation(false, $columnsArray, $valuesArray);        
    }
    
    /**
     * Method that gets all comments of video specified in object.
     */
    public function getAllCommentsOfVideo(){
        $query = Db::makeQuery("select", array($this->t), array(),"{$this->tVideo} = {$this->video->getId()} and {$this->tDeleted} = 0");
        $rows = Db::query($query);
        if(!is_null($rows)){
            foreach($rows as $row){
                array_push(self::$list, new VideoComment(null, $row));
            }
        }
    } 

}
