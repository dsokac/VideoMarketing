<?php
include_once "../../web_files/classes/MyGlobal.class.php";
include_once "../../web_files/classes/User.class.php";
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$name = $_GET["name"];
$surname = $_GET["surname"];
$birthday = $_GET["birthday"];
$gender = $_GET["gender"];
$email = $_GET["email"];
$username = $_GET["username"];
$password = $_GET["password"];
$geo_unit = $_GET["geo_unit"];
$mobile_number = $_GET["mobile_number"];

$success = 1;
$message = "";

$u = new User();

if($u->emailExists($email)){
    $success = 0;
    $message = "E-mail already exists in database, please login or try with different e-mail.";
} else if($u->doesUserExists($username)){
    $success = 0;
    $message = "User already exists in database, please login or try again.";
} else if($u->phoneNumberExists($mobile_number)){
    $success = 0;
    $message = "Phone number already exists in database, please login or try again.";
} else {
    $newUser = new User();
    $newUser->setName($name);
    $newUser->setSurname($surname);
    $newUser->setBirthday($birthday);
    $newUser->setUsername($username);
    $newUser->setPassword($password);
    $newUser->setGeographicUnit(intval($geo_unit));
    $newUser->setGender($gender);
    $newUser->setEmail($email);
    $newUser->setMobileNumber($mobile_number);
    $output = $newUser->create();
    $success = $output == 0 ? 0 : 1;
    $message = $output == 0 ? "User could not be created. Try again." : "User is created successfully";
    
}

$json = array(
    "status" => 100,
    "requested_at" => date(MyGlobal::$timeFormat),
    "data" => array(
        "success" => $success,
        "message" => $message
    )
);

header("Content-Type:application/json");
echo json_encode($json);