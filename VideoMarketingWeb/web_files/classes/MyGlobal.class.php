<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Class which should be available globally.
 *
 * @author Danijel
 */
class MyGlobal {
    
    /**
     * Date format which should be displayed on screen.
     * @var string 
     */
    public static $timeFormat = "d.m.Y. H:i:s";
    public static $dateFormat = "d.m.Y.";
    
    /**
     * Date format which is used by database. 
     * This format is used to save date in database.
     * @var string
     */
    public static $timeFormatDB = "Y-m-d H:i:s";
    public static $dateFormatDB = "Y-m-d";
    
    /**
     * Name for User session.
     * @var string
     */
    public static $userSessionName = "userVideoMarketing";
    
    /**
     * Object which contains content of language file inside i18n_langs folder
     * @var SimpleXML object 
     */
    public static $i18nFile = null;

    /**
     * Method checks if superadmin property is set. If it is the user is either admin or superadmin.
     * @return boolean
     */
    public static function isAdmin(){
        return isset($_SESSION[self::$userSessionName]["superadmin"]);
    }
    
    /**
     * Method gets content of xml language file and saves it inside appropriate property.
     * @param string $filepath a path to xml language file
     */
    public static function fetchLangFile($filepath){
        
        self::$i18nFile = simplexml_load_file($filepath, "SimpleXMLElement", LIBXML_NOCDATA) or die ("Error: cannot create xml object.");
    }
    
    /**
     * Mathod to log errors related to Database.
     * @param Statement $stmt statement where error occured
     * @throws Exception 
     */
    public static function logDatabaseError($stmt){
        var_dump($stmt->errorInfo());
        throw new Exception($stmt->error);
    }
    
    //TODO describe
    public static function manageRouting($basePath){
        $urlArray = explode("/",$_SERVER['REQUEST_URI']);
        $index = 0;   
        foreach(array_reverse($urlArray) as $element){
            if(strcmp($element,"webservice")==0 || strcmp($element,"admin")==0 || strcmp($element,"")==0){
                if(strcmp($element,"")==0){$index++;}
                break;
            }
            $index++;
        }
        $j = $index;
        $string = "";
        while($j != 0){
            $string .= "../";
            $j--;
        }
        return $string.$basePath;
    }
}

