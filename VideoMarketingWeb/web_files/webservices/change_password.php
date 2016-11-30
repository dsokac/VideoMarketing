<?php
include_once "../../web_files/classes/MyGlobal.class.php";
include_once "../../web_files/classes/User.class.php";
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$id = $_GET["id"];
$newPass = $_GET["password"];

$success = 1;
$message = "Your password has been changed successfully.";

$u = new User($id);
if($u->isPasswordEqualTo($newPass)){
    $success = 0;
    $message = "You can not change password into old one.";
} else {
    $output = $u->changePassword($newPass);
    if($output == 0){
        $success = 0;
        $message = "Password could not be changed. Database error. Try again later.";
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