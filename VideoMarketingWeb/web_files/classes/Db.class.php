<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Class which is used to communicate with database directly. 
 * Class creates queries for operations with database using PDO.
 *
 * @author Danijel
 */
class Db {
    
    const DBNAME = "u832073829_baza";
    const DBUSER = "u832073829_user";
    const DBPASSWORD = "Ni3SxA3A6PaY";
    const DBSERVER = "localhost";
    
    /**
     * Method creates connection to DB.
     * @return \PDO db link object
     */
    public static function connect(){
        $dsn = "mysql:host=".self::DBSERVER.";dbname=".self::DBNAME.";charset=utf8";
        $link = new PDO($dsn, self::DBUSER, self::DBPASSWORD);
        return $link;
    }
    
    /**
     * Method to query db.
     * @param PDO $link
     * @param string $query
     * @return mixed depends on which query is executed.
     */
    private static function queryDb($link, $query){
        $stmt = $link->prepare($query);
        if($stmt->execute()){
            $numRows = $stmt->rowCount();
             
            $pattern = "/^(insert|update|delete)/i";
            if(preg_match($pattern,$query)){
                return $numRows;
            } 
            else if($numRows == 1){return array($stmt->fetch(PDO::FETCH_ASSOC));}
            else if($numRows > 1){return $stmt->fetchAll(PDO::FETCH_ASSOC);}
            else {return null;}
        } else {
            MyGlobal::logDatabaseError($stmt);
        }
    }
    
    /**
     * Method which executes queryDB method for the simplicity sake.
     * @param string $query
     * @return mixed depends on executed query
     */
    public static function query($query){
        $link = self::connect();
        return self::queryDb($link, $query);
    }
    
    /**
     * Method which is used to generate query.
     * Depending on keyword, method runs more query-specific method.
     * @param string $keyword keywords in SQL - SELECT, INSERT INTO, DELETE, UPDATE
     * @param array $tables array of tables which should be used in query creation
     * @param array $columnsArray array of columns which are used in query creation
     * @param string $constraints contains all contraint which should be used in query
     * @param array $valuesArray array of values, its position matters. first value is mapped to first column in columnsArray
     * @return string query 
     */
    public static function makeQuery($keyword, $tables = array(), $columnsArray = array(), $constraints = null, $valuesArray = array()){
        $query = "";
        switch(strtolower($keyword)){
            case "select": $query = self::selectQuery($tables, $columnsArray, $constraints); break;
            case "insert": $query = self::insertQuery($tables, $columnsArray, $valuesArray); break;
            case "update": $query = self::updateQuery($tables, $columnsArray, $valuesArray, $constraints); break;
            case "delete": $query = self::deleteQuery($tables, $constraints); break;
        }
                
        return $query;
    }
    
    /**
     * Query-specific method for SELECT query. 
     * @param array $tables array of tables to create query
     * @param array $columnsArray array of table columns to create query
     * @param string $constraints contraint to be involved in query
     * @return string complete select query
     */
    private static function selectQuery($tables, $columnsArray = array(), $constraints = null){
        $query = "select ";
        
        if(count($columnsArray) == 0){
            $query .= "*";
        } else {
            $query .= implode(",",$columnsArray);
        }
        $tablesString = implode(",",$tables);
        $query .= " from {$tablesString}";
        
        if($constraints != null){
            $query .= " where {$constraints}";
        }
        
        return $query;
    }
    
    /**
     * Query-specific method for creation of INSERT query.
     * @param array $tables tables that should be used inside query, method use only first table specified
     * @param array $columnsArray table columns that are used inside query
     * @param array $valuesArray values that are used inside query, value position matters, first value is mapped to first column in columnsArray
     * @return string complete insert query
     */
    private static function insertQuery ($tables, $columnsArray = array(), $valuesArray = array()){
        
        $query = "insert into {$tables[0]}";
        
        if(count($columnsArray) != 0){
            $query .= "(";
            $query .= implode(",",$columnsArray);
            $query .= ")";
        }
        
        $query .= " values (";
        
        $numElems = count($valuesArray);
        for( $i = 0; $i < $numElems; $i++){
            $el = $valuesArray[$i];
            $query .= is_string($el) == false ? $el : "'{$el}'";            
            if($i != $numElems-1){
                $query .= ",";
            }
        }
        $query .= ")";
        return $query;
    }
    
    /**
     * Query-specific method for creation of UPDATE query.
     * @param array $tables method use only first table specified
     * @param array $columnsArray columns that shall be used inside query
     * @param array $valuesArray values that shall be used inside query, their positions matters, first value is mapped to first column
     * @param string $constraints constraints as one string which are used inside query
     * @return string complete update query
     */
    private static function updateQuery($tables, $columnsArray, $valuesArray, $constraints){
        $query = "update {$tables[0]} set ";
        $numElems = count($columnsArray);
        
        for($i = 0; $i < $numElems; $i++){
            $el_column = $columnsArray[$i];
            $el_value = $valuesArray[$i];
            if(!is_null($el_value)){
                $el_valueString = is_string($el_value) ? "'{$el_value}'" : $el_value;
            } else {
                $el_valueString = "NULL";
            }
            $query .= "{$el_column} = {$el_valueString}";
            if($i != $numElems - 1){
                $query .= ",";
            }
        }
        
        if($constraints != null){
            $query .= " where {$constraints}";
        }
        return $query;
    }
    
    /**
     * Query-specific method to create DELETE query.
     * @param array $tables method uses only first table specified
     * @param string $constraints contraints that should be used in query
     * @return string complete delete query
     */
    private static function deleteQuery($tables, $constraints){
        $query = "delete from {$tables[0]}";
   
        if($constraints != null){
            $query .= " where {$constraints}";
        }
        
        return $query;
    }
    
    //TODO dokumentirati metodu
    public static function transaction($queryArray){
        $status = true;
        $link = self::connect();
        $link->beginTransaction();
        try{
            foreach($queryArray as $query){
                $stmt = $link->prepare($query);
                if(!$stmt->execute()){
                    throw new Exception("Query error.");
                }
            }
            $link->commit();
        } catch (Exception $e){
            var_dump($e);
            $status = false;
            $link->rollBack();
        }
        return $status;
    }
    
    
}
