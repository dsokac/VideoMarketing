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
 * Class used to operate with data from table of the same name as this class
 *
 * @author Danijel
 */
class TagType extends AbstractModel {
    
    public static $list = array();
    
    protected $id;
    protected $name;
    protected $description;
    
    protected $t = "tag_types";
    protected $tId = "id";
    protected $tName = "name";
    protected $tDescription = "description";
    
    public function getName() {
        return $this->name;
    }

    public function getDescription() {
        return $this->description;
    }

    public function setName($name) {
        $this->name = $name;
    }

    public function setDescription($description) {
        $this->description = $description;
    }

    public function getId() {
        return $this->id;
    }

    public function __construct($id = null, $data = null) {
        $output = $this->initialOperation($id, $data);
        if($output["exit"]){return;}
        $data = is_null($data) ? $output["data"] : $data;
        
        $this->id = intval($data[$this->tId]);
        $this->name = $data[$this->tName];
        $this->description = $data[$this->tDescription];
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
            $this->tName,
            $this->tDescription
        );
        
        $valuesArray = array(
            $this->name,
            $this->description
        );
        
        return $this->dbOperation(true, $columnsArray, $valuesArray);
    }

    /**
     * Method updates one row related to property id. It updates all properties which are meant to be updated.
     * @return int number of rows affected by query
     */
    public function save() {
        $columnsArray = array(
            $this->tName,
            $this->tDescription,
            $this->tDeleted
        );
        
        $valuesArray = array(
            $this->name,
            $this->description,
            $this->deleted
        );
        
        if($this->deletedAt != "" || $this->deletedAt != null){
            array_push($columnsArray, $this->tDeletedAt);
            array_push($valuesArray, $this->deletedAt);
        }
        
        return $this->dbOperation(false, $columnsArray, $valuesArray);
    }

}