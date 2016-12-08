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
 * Class is used for operations with the table of the similar name as this class.
 *
 * @author Danijel
 */
class Video extends AbstractModel {
    
    public $list = array();
    
    protected $id;
    protected $title;
    protected $link;
    protected $points;
    protected $likes;
    protected $dislikes;
    protected $sponsored;
    protected $thumbnailUrl;
 
    protected $t = "videos";
    protected $tId = "id";
    protected $tTitle = "title";
    protected $tLink = "link";
    protected $tPoints = "points";
    protected $tLikes = "likes";
    protected $tDislikes = "dislikes";
    protected $tSponsored = "sponsored";
    protected $tThumbnailUrl = "thumbnail_url";
    
    public function getTitle() {
        return str_replace("''","'",$this->title);
    }

    public function getLink() {
        return $this->link;
    }

    public function getPoints() {
        return $this->points;
    }

    public function getLikes() {
        return $this->likes;
    }

    public function getDislikes() {
        return $this->dislikes;
    }

    public function setTitle($title) {
        $this->title = str_replace("'","''",$this->title);;
    }

    public function setLink($link) {
        $this->link = $link;
    }

    public function setPoints($points) {
        $this->points = $points;
    }

    public function setLikes($likes) {
        $this->likes = $likes;
    }

    public function setDislikes($dislikes) {
        $this->dislikes = $dislikes;
    }
    
    public function getSponsored() {
        return $this->sponsored;
    }

    public function setSponsored($sponsored) {
        $this->sponsored = $sponsored;
    }

    
    public function getId() {
        return $this->id;
    }
    
    function getThumbnailUrl() {
      return $this->thumbnailUrl;
    }

    function setThumbnailUrl($thumbnailUrl) {
      $this->thumbnailUrl = $thumbnailUrl;
    }

    
    /**
     * Method creates new row in table filled with data in class properties.
     * @return int num of rows affected by query.
     */
    public function create() {
        $columnsArray = array(
            $this->tTitle,
            $this->tLink,
            $this->tThumbnailUrl
        );
        
        $valuesArray = array(
            str_replace("'","''",$this->title),
            $this->link,
            $this->thumbnailUrl
        );
        
        return $this->dbOperation(true, $columnsArray, $valuesArray);
    }
    
    public function __construct($id = null, $data = null) {
        $output = $this->initialOperation($id, $data);
        if($output["exit"]){return;}
        $data = is_null($data) ? $output["data"] : $data;
        $this->id = intval($data[$this->tId]);
        $this->title = str_replace("''","'",$data[$this->tTitle]);
        $this->link = $data[$this->tLink];
        $this->createdAt = $data[$this->tCreatedAt];
        $this->updatedAt = $data[$this->tUpdatedAt];
        $this->deletedAt = $data[$this->tDeletedAt];
        $this->deleted = intval($data[$this->tDeleted]);
        $this->points = intval($data[$this->tPoints]);
        $this->likes = intval($data[$this->tLikes]);
        $this->dislikes = intval($data[$this->tDislikes]);
        $this->sponsored = intval($data[$this->tSponsored]);
        $this->thumbnailUrl = $data[$this->tThumbnailUrl];
        parent::__construct();
       
    }

    /**
     * Method updates one row related to property id. It updates all properties which are meant to be updated.
     * @return int number of rows affected by query
     */
    public function save() {
        $columnsArray = array(
            $this->tTitle,
            $this->tLink,
            $this->tDeleted,
            $this->tPoints,
            $this->tLikes,
            $this->tDislikes,
            $this->tSponsored,
            $this->tThumbnailUrl
        );
        
        $valuesArray = array(
            str_replace("'", "''", $this->title),
            $this->link,
            $this->deleted,
            $this->points,
            $this->likes,
            $this->dislikes,
            $this->sponsored,
            $this->thumbnailUrl
        );
        
        if($this->deletedAt != null && $this->deletedAt != ""){
            array_push($columnsArray, $this->tDeletedAt);
            array_push($valuesArray, $this->deletedAt);
        }
        
        
        return $this->dbOperation(false, $columnsArray, $valuesArray);
    }

    public function addLike(){
        $this->likes += 1;
    }
    
    public function addDislike(){
        $this->dislikes += 1;
    }
    
    public function removeLike(){
        $this->likes -= 1;
    }
    
    public function removeDislike(){
        $this->dislikes -= 1;
    }
    
    public function getVideoTable(){
        return $this->t;
    }
    
    public function getVideoTableId(){
        return $this->tId;
    }
}

