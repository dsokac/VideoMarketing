<?php
include_once "../../web_files/classes/MyGlobal.class.php";
include_once "../../web_files/classes/User.class.php";
include_once "../../web_files/classes/TelecommUser.class.php";
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$user = $_GET["user"];
$telecommOperator = $_GET["telecomm"];

$success = 1;
$message = "Telecommunication and user were connected successfully";
$u = new User(intval($id));
if($u->isUserDeleted()){
    $success = 0;
    $message = "Deleted user cannot connect to telecomm operator.";
} else if($u->isUserBlocked()){
    $success = 0;
    $message = "Blocked user cannot connect to telecomm operator.";
}  else if(!$u->isUserDeactivated()){
    $success = 0;
    $message = "Deactivated user cannot connect to telecomm operator.";
} else {
    $tu = new TelecommUser();
    $tu->setUser($u->getId());
    $tu->setTelecommOperator($telecommOperator);
    $output = $tu->create();
    
    if($output == false){
        $success = 0;
        $message = "Telecomm operator cannot be connected to user. Database error. Try again later.";
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