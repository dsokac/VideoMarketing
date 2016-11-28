<?php
include_once "MyGlobal.class.php";
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of VideoMarketingApplication
 *
 * @author Danijel
 */
class VideoMarketingApplication {
    private static $_INSTANCE = null;
    
    private $twig = null;
    
    public function initialize() {
        require MyGlobal::manageRouting("../vendor/autoload.php");
        $loader  = new Twig_Loader_Filesystem(MyGlobal::manageRouting('twig_templates'));
        $this->twig = new Twig_Environment($loader, array(
            'cache' => false
        ));
        //'web_files/twig_cache'
    }
    
    public static function getInstance() {
        if (self::$_INSTANCE == null){
            self::$_INSTANCE = new VideoMarketingApplication();
        }
        return self::$_INSTANCE;
    }
    
    public function getTwig(){
        return $this->twig;
    }
}
