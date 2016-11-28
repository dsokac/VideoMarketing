<?php
include_once "../../web_files/classes/MyGlobal.class.php";
include_once "../../web_files/classes/User.class.php";
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$phoneNumber = $_GET["phone_number"];

$u = new User();


if($u->phoneNumberExists($phoneNumber)){
    $success = 1;
    $message = "Phone number exists.";
} else {
    $success = 0;
    $message = "Phone number does not exists.";
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