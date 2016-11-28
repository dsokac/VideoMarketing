<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Interface for database models. Most method are implemented in AbstractModel class.
 *
 * @author Danijel
 */
interface iModel {

     public function getAll();
     
     /**
     * Method creates new row in table filled with data in class properties.
     * @return int num of rows affected by query.
     */
     public function create();
     
     /**
     * Method updates one row related to property id. It updates all properties which are meant to be updated.
     * @return int number of rows affected by query
     */
     public function save();
     public function delete();
     public function deleteAll();
     public function hardDelete();
     public function hardDeleteAll();
     public function retrieve();
     public function retrieveAll();
     public function getCreatedAt();
     public function getUpdatedAt();
     public function getDeletedAt();
     public function setDeletedAt();

     
}
