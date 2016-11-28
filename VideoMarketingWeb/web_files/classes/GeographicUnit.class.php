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
 * Class is used to operate with data in table of the similar name as this class.
 *
 * @author Danijel
 */
class GeographicUnit extends AbstractModel{
    
    public $list = array();
    
    /**
     * protected properties which are columns in table related to this class.
     */
    protected $id = null;
    protected $name = null;
    
    /**
     * protected properties which are names of columns in table related to this class.
     */
    protected $t = "geographic_units";
    protected $tId = "id";
    protected $tName = "name";
    
    function getId() {
        return $this->id;
    }

    function getName() {
        return $this->name;
    }

    function setName($name) {
        $this->name = $name;
    }
    
    public function __construct($id = null, $data = null) {
        $output = $this->initialOperation($id, $data);
        if($output["exit"]){return;}
        $data = is_null($data) ? $output["data"] : $data;
        $this->id = intval($data[$this->tId]);
        $this->name = $data[$this->tName];
        $this->deleted = intval($data[$this->tDeleted]);
        $this->deletedAt = $data[$this->tDeletedAt];
        $this->createdAt = $data[$this->tCreatedAt];
        $this->updatedAt = $data[$this->tUpdatedAt];
        parent::__construct();
    }
    
    /**
     * Method creates new row in table filled with data in class properties.
     * @return int num of rows affected by query.
     */
    public function create() {
        $columnsArray = array($this->tName);
        $valuesArray = array($this->name);
        return $this->dbOperation(true, $columnsArray, $valuesArray);
    }

    /**
     * Method updates one row related to property id. It updates all properties which are meant to be updated.
     * @return int number of rows affected by query
     */
    public function save() {
        $columnsArray = array($this->tName);
        $valuesArray = array($this->name);
        
        if($this->deletedAt != null && $this->deletedAt != ""){
            array_push($columnsArray, $this->tDeletedAt);
            array_push($valuesArray, $this->deletedAt);
        }
        return $this->dbOperation(false, $columnsArray, $valuesArray);
    }
}
