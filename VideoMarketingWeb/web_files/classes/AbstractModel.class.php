<?php
include_once "MyGlobal.class.php";
include_once "Db.class.php";
include_once "iModel.interface.php";
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * AbstractModel class is abstract class used to implement common actions to avoid repeating same code segments.
 * 
 * @author Danijel Sokač
 */
abstract class AbstractModel implements iModel {
    
    protected $classConstraint = null;
    
    protected $createdAt;
    protected $updatedAt;
    protected $deletedAt;
    protected $deleted;
    
    protected $tCreatedAt = "created_at";
    protected $tUpdatedAt = "updated_at";
    protected $tDeletedAt = "deleted_at";
    protected $tDeleted = "deleted";
    
    public function getDeleted() {
        return $this->deleted == 1 ? true : false;
    }

    public function setDeleted($deleted) {
        $this->deleted = $deleted ? 1 : 0;
    }

        
    public function __construct() {
        $this->classConstraint = "{$this->tId} = {$this->id}";
    }
    /**
     * Method getAll() fetches all record of class. If you are an admin, you can see all records.
     * If you are an user, you can see only available records.
     */
    public function getAll() {
        $constraint = null;
        if(!MyGlobal::isAdmin()){
            $constraint = "{$this->tDeleted} = 0";
        }
        $rows = Db::query(Db::makeQuery("select", array($this->t), array(),$constraint));
        $class = get_called_class();
        foreach ($rows as $row) {
            array_push($this->list, new $class(null, $row));
        }
                
    }
    
    /**
     * Method delete() sets flag deleted of current objects data.
     * Flag deleted indicated that data should not be displayed in application.
     */
    public function delete(){
        $this->setDeleted(true);
        $this->setDeletedAt();
        $this->save();
    }
    
    
    /**
     * Method deleteAll() sets deleted flags to all records of current table (e.g. Class).
     * Deleted rows will not be displayed in application.
     * 
     * @return int number of rows affected by update query
     */
    public function deleteAll() {
        $data = Db::query(Db::makeQuery("update", array($this->t), array($this->tDeleted, $this->tDeletedAt), null, array(1, date(MyGlobal::$timeFormatDB))));
        return $data;
    }
    
    
    /**
     * Method hardDelete() deletes record from database. After method is executed the row in database is gone.
     * This method should be secured checking if admin wants to run this method.
     * 
     * @return int number of rows affected by delete query
     */
    public function hardDelete() {
        $data = Db::query(Db::makeQuery("delete",array($this->t), array(),$this->classConstraint));
        return $data;
    }

    /**
     * Method hardDeleteAll() deletes all records of current class (table).
     * After this method, table is empty.
     * 
     * @return int number of rows affected by delete query
     */
    public function hardDeleteAll(){
         $data = Db::query(Db::makeQuery("delete",array($this->t), array()));
        return $data;
    }
    
    /**
     * Method retrieve() reverts flag deleted to its default state which is 0. (i.e. not deleted)
     * Method cannot return data that were hard-deleted.
     * 
     * @return int number of rows affected by update query
     */
    public function retrieve(){
        $this->setDeleted(false);
        $this->setDeletedAt(null);
        $this->save();
//        $query = Db::makeQuery("update", array($this->t), array($this->tDeleted, $this->tDeletedAt), $this->classConstraint, array(0, null));
//        $data = Db::query($query);
//        return $data;
    }
    
    /**
     * Method retrieveAll() reverts flag deleted in all rows to its default state which is 0. (i.e. not deleted)
     * Method cannot return data that were hard-deleted.
     * 
     * @return int number of rows affected by update query
     */
    public function retrieveAll(){
        $query = Db::makeQuery("update", array($this->t), array($this->tDeleted, $this->tDeletedAt), null, array(0, null));
        $data = Db::query($query);
        return $data;
    }
 
    /**
     * Method printDate() does initial check if value is existent.
     * If value is existent, method returns date and time in format that is used.
     * Else, method returns error text.
     * 
     * @param Datetime $date
     * @return string error or date and time string
     */
    protected function printDate($date){
        if($date == null || $date == ""){
            return MyGlobal::$i18nFile["property_value_unknown"];
        }
        return date(MyGlobal::$timeFormat, strtotime($date));
    }
    
    /**
     * Method getCreatedAt() returns value of property 'createdAt' if this property exists.
     * @return string date and time string or error that property doesn't exist.
     */
    public function getCreatedAt(){
         return $this->printDate($this->createdAt);
    }
    
    /**
     * Method getUpdatedAt() returns value of property 'updatedat' if this property exists.
     * @return string data and time string or error that property doesn't exist.
     */
    public function getUpdatedAt(){
        return $this->printDate($this->updatedAt);
    }
    
    /**
     * Method getDeletedAt() returns value of property 'deletedAt' if this property exists.
     * @return string date and time string or error that property doesn't exist.
     */
    public function getDeletedAt(){
       return $this->printDate($this->deletedAt);
    }
    
    /**
     * Method setDeletedAt() sets current time as value to property 'deletedAt' if property exists.
     * @return mixed nothing or error text that property doesn't exist.
     */
    public function setDeletedAt(){
       $this->deletedAt = date(MyGlobal::$timeFormatDB);
    }
    
    /**
     * Method to generate insert query and update query for create and save action. 
     * This method is used to reduce redundacy and to make work easier.
     * 
     * @param boolean $insertion
     * @param array $columnsArray
     * @param array $valuesArray
     * @return int number of rows affected by query
     */
    protected function dbOperation($insertion, $columnsArray, $valuesArray){
        if($insertion == true){
            $keyword = "insert";
            $constraint = null;
        } else {
            $keyword = "update";
            $constraint = $this->classConstraint;
        }
        $query = Db::makeQuery($keyword,array($this->t), $columnsArray, $constraint, $valuesArray);
        $result = Db::query($query);
        return $result;
    }
    
    /**
     * Method is used to reduce redundancy and replace couple lines with single one. 
     * This method checks if id is passed and then it executes select query and fetches row from database.
     * If all params are null, this method does nothing.
     * 
     * @param int $id
     * @param sqlrows $data
     * @return array exit -> contains flag if function should exit and do nothing, data -> contains data returned by select query.
     */
    protected function initialOperation($id = null, $data = null){
        if ($id != null && $data == null) {
            $data = Db::query(Db::makeQuery("select",array($this->t),array(),"{$this->tId} = {$id}"));
        }
        
        return array("exit" => is_null($id) && is_null($data), "data" => !is_null($id) ? $data[0] : $data);
    }
    
}
