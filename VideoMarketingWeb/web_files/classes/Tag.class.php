<?php

include_once "MyGlobal.class.php";
include_once "Db.class.php";
include_once "AbstractModel.class.php";
include_once "TagType.class.php";
include_once "User.class.php";

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Class used to operate with data from table of the same name as this class
 *
 * @author Danijel
 */
class Tag extends AbstractModel {

    public $list = array();
    
    const TAG_SPOL_M = "muškarci";
    const TAG_SPOL_F = "žene";
    const TAG_GODINE_SINTAKSA = "##-## , ## - age";
    const TAG_GEOUNIT_SINTAKSA = "geounit_## , ## - id of geographic unit";
    
    /**
     * protected properties which holds values of table's columns.
     */
    protected $id = null;
    protected $name = null;
    protected $description = null;
    protected $type = null;
    
    /**
     * protected properties which holds names of table's columns.
     */
    protected $t = "tags";
    protected $tId = "id";
    protected $tName = "name";
    protected $tDescription = "description";
    protected $tType = "type";

    function getId() {
        return $this->id;
    }

    function getName() {
        return $this->name;
    }

    function getDescription() {
        return $this->description;
    }

    function setName($name) {
        $this->name = $name;
    }

    function setDescription($description) {
        $this->description = $description;
    }
    
    public function getType() {
        return $this->type;
    }

    public function setType($type) {
        $this->type = is_int($type) ? new TagType($type) : $type;
    }

        
    public function __construct($id = null, $data = null) {
        $output = $this->initialOperation($id, $data);
        if($output["exit"]){ return; } 
        $data = is_null($data) ? $output["data"] : $data;
        $this->id = intval($data[$this->tId]);
        $this->name = $data[$this->tName];
        $this->description = $data[$this->tDescription];
        $this->type = new TagType(intval($data[$this->tType]));
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
        $columnsArray = array($this->tName, $this->tDescription);
        $valuesArray = array($this->name, $this->description);
        
        if ($this->type != "" || $this->type != null) {
            array_push($columnsArray, $this->tType);
            array_push($valuesArray, $this->type->getId());
        }
        
        return $this->dbOperation(true, $columnsArray, $valuesArray);
    }

    /**
     * Method updates one row related to property id. It updates all properties which are meant to be updated.
     * @return int number of rows affected by query
     */
    public function save() {
        $columnsArray = array($this->tName, $this->tDescription, $this->tDeleted);
        $valuesArray = array($this->name, $this->description, $this->deleted);
        if ($this->deletedAt != "") {
            array_push($columnsArray, $this->tDeletedAt);
            array_push($valuesArray, $this->deletedAt);
        }
        return $this->dbOperation(false, $columnsArray, $valuesArray);
    }
    
    public function getAgeTags($age, $gender, $geo_unit){
        $this->getAll();
        $tagIds = array();
        foreach($this->list as $tag){
            $name = $tag->getName();
            if(preg_match("/^\d+-\d+$/", $name)){
                $stringArray = explode("-",$name);
                $firstNum = intval($stringArray[0]);
                $secondNum = intval($stringArray[1]);
                if($age >= $firstNum && $age <= $secondNum){
                    array_push($tagIds, $tag->getId());
                }
            } else if(preg_match("/^geounit_\d+$/", $name)){
                $stringArray = explode("_",$name);
                $id = intval($stringArray[1]);
                if($id == $geo_unit){
                    array_push($tagIds, $tag->getId());
                }
            } else {
                if(strcmp($gender,User::MALE)==0 && strcmp($name, "muškarci")==0){
                    array_push($tagIds, $tag->getId());
                } else if (strcmp($gender,User::FEMALE)==0 && strcmp($name, "žene")==0){
                    array_push($tagIds, $tag->getId());
                }
            }
            
        }
        return $tagIds;        
    }
   
    public function getTagTable(){
        return $this->t;
    }
    
    public function getTagTableId(){
        return $this->tId;
    }
    
}
