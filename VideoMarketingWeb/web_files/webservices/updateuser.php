<?php
include_once "../../web_files/classes/MyGlobal.class.php";
include_once "../../web_files/classes/User.class.php";
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$id = $_GET["id"];
$name = $_GET["name"];
$surname = $_GET["surname"];
$birthday = $_GET["birthday"];
$gender = $_GET["gender"];
$email = $_GET["email"];
isset($_GET["password"]) && !empty($_GET["password"]) ? $newPass = $_GET["password"] : $newPass = null;
$username = $_GET["username"];
$geo_unit = $_GET["geo_unit"];
$mobile_number = $_GET["mobile_number"];

$user = new User(intval($id));
$user->setName($name);
$user->setSurname($surname);
$user->setBirthday($birthday);
$user->setGender($gender);
$user->setEmail($email);
$user->setUsername($username);
$user->setGeographicUnit(intval($geo_unit));
$user->setMobileNumber($mobile_number);
$output = $user->save();

$success = $output == 0 ? 0 : 1;
$message = $output == 0 ? "User information can't be updated. Try again." : "User information updated sucessfully.";

if($newPass != null){
  if($user->isPasswordEqualTo($newPass)){
      $success = 0;
      $message = "You can not change password into old one."; //treba složit
  } else {
      $output = $user->changePassword($newPass);
      if($output == 0){
          $success = 0;
          $message = "Password could not be changed. Database error. Try again later."; //treba složit
      }
  }
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