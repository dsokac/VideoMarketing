<?php
include_once "MyGlobal.class.php";
include_once "AbstractModel.class.php";
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Class is used to gather all common properties related to both user and admin classes to ensure simplicity.
 *
 * @author Danijel
 */
abstract class MetaUser extends AbstractModel {    
    public $list = array();
    
    /**
     * protected properties which holds column values
     */
    protected $id;
    protected $name;
    protected $surname;
    protected $email;
    protected $username;
    protected $password;
    protected $passwordChangedAt;
    protected $numWrongLogins;
    protected $blocked = 0;
    protected $deblockingLink;
    protected $activationLink;
    protected $activationSentAt;
    
    /**
     * protected properties which holds column names
     */
    protected $tId = "id";
    protected $tName = "name";
    protected $tSurname = "surname";
    protected $tEmail = "email";
    protected $tUsername = "username";
    protected $tPassword = "password";
    protected $tPasswordChangedAt = "password_changed_at";
    protected $tNumWrongLogins = "num_wrong_logins";
    protected $tBlocked = "blocked";
    protected $tDeactivated = "deactivated";
    protected $tDeblockingLink = "deblocking_link";
    protected $tActivationLink = "activation_link"; 
    protected $tActivationSentAt = "activation_sent_at";
    
    function getId() {
        return $this->id;
    }

    function getName() {
        return $this->name;
    }

    function getSurname() {
        return $this->surname;
    }

    function getEmail() {
        return $this->email;
    }

    function getUsername() {
        return $this->username;
    }

    function getPassword(){
        return $this->password;
    }
    
    function getPasswordChangedAt() {
        return $this->printDate($this->passwordChangedAt);
    }

    function getNumWrongLogins() {
        return $this->numWrongLogins;
    }

    function getDeactivated() {
        return $this->deactivated == 1 ? true : false;
    }

    function getDeblockingLink() {
        return $this->deblockingLink;
    }
    
    function getActivationLink() {
        return $this->activationLink;
    }
    
    function setName($name) {
        $this->name = $name;
    }

    function setSurname($surname) {
        $this->surname = $surname;
    }

    function setEmail($email) {
        $this->email = $email;
    }

    function setUsername($username) {
        $this->username = $username;
    }

    function setPasswordChangedAt($passwordChangedAt) {
        $this->passwordChangedAt = $passwordChangedAt;
    }

    function setNumWrongLogins($numWrongLogins) {
        $this->numWrongLogins = $numWrongLogins;
    }

    function setBlocked($blocked) {
        $this->blocked = $blocked == true ? 1 : 0;
    }

    function setDeactivated($deactivated) {
        $this->deactivated = $deactivated == true ? 1 : 0;
    }

    function setDeleted($deleted) {
        $this->deleted = $deleted == true ? 1 : 0;
    }

    function setDeblockingLink($deblockingLink) {
        $this->deblockingLink = $deblockingLink;
    }

    function setPassword($password){
        $this->password = $password;
    }
    
    function setActivationLink($activationLink) {
        $this->activationLink = $activationLink;
    }
    
    /**
     * Method which changes password of user.
     * @param string $password
     */
    public function changePassword($password){
        $this->setPassword($password);
        $this->setPasswordChangedAt(date(MyGlobal::$timeFormatDB));
        return $this->save();
    }
    
    /**
     * Method which checks if passed password and object password are equal.
     * @param string $password
     * @return boolean true if they are equal, false otherwise.
     */
    public function isPasswordEqualTo($password){
        return $this->getPassword() == $password;
    }
    
    public function createActivationLink(){
        $server = $_SERVER["SERVER_NAME"];
        $port = $_SERVER["SERVER_PORT"];
        $uri = $_SERVER["REQUEST_URI"];
        $uriArray = explode("/",$uri);
        $uriArray[count($uriArray)-1] = "activate_user.php";
        $newUri = implode("/",$uriArray);
        $fullLink = $server.":".$port.$newUri."?time=".  date("YmdHis")."&id={$this->getId()}";
        $this->setActivationLink($fullLink);
        return $this->save();
    }
    
    public function sendEmail(){
        $from = "info@example.com";
        $subject = "User activation email";
        
        $headers = "MIME-Version: 1.0\r\n";
        $headers .= "Content-type: text/html; charset=utf-8\r\n";
        $headers .= "To: {$this->getName()} {$this->getSurname()} <{$this->email}>\r\n";
        $headers .= "From: {$from}\r\n";
        
        $content = "";
        $content .= "<html>";
        $content .= "<head>";
        $content .= "<title>User activation email</title>";
        $content .= "</head>";
        $content .= "<body>";
        $content .= "<h2>Hello, {$this->getName()} {$this->getSurname()}. This is user activation email.</h2>";
        $content .= "<p>Please, click following hyperlink to activate your user account: {$this->activationLink}</p>";
        $content .= "</body>";
        $content .= "</html>";
        
        return mail($this->email, $subject, $content, $headers);
    }
    public function getActivationSentAt() {
        return $this->activationSentAt;
    }

    public function setActivationSentAt($activationSentAt) {
        $this->activationSentAt = $activationSentAt;
    }


}
