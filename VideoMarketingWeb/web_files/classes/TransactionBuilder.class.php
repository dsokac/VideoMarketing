<?php
include_once "MyGlobal.class.php";
include_once "Db.class.php";
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Admin class is used to fill table 'admins' in database and operate with table data.
 *
 * @author Danijel
 */
class TransactionBuilder{
    
   private $result = null;
   private $link = null;
   private $success = true;
   //TODO delete or refactor class
   public function __construct() {
       $this->link = Db::connect();
       $this->link->beginTransaction();
       return $this;
   }
   
   public function selectQuery($query){
       $stmt = $this->link->prepare($query);
       $stmt->execute();
       if($stmt->rowCount() > 0 ){
            $this->result  = $stmt->fetch();
       } else {
           $this->success = false;
       }
       return $this;
   }
   
   public function otherQuery($query, $useResult = null){
       $stmt = $this->link->prepare($query);
       if($useResult == null){
           $output = $stmt->execute();
       } else {
           $stmt->bindValue(1, $this->result[0]);
           $output = $stmt->execute();
       }
       
       if(!$output){
           $this->success = false;
       }
       return $this;
   }
   
   public function build(){
       if($this->success){
           $this->link->commit();
       } else {
           $this->link->rollBack();
       }
   }
   

}
