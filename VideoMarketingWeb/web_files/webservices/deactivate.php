<?php
include_once "../../web_files/classes/MyGlobal.class.php";
include_once "../../web_files/classes/User.class.php";
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$id = $_GET["id"];

$success = 1;
$message = "Your account is deactivated successfully";

$u = new User(intval($id));

if($u->isUserDeleted()){
    $success = 0;
    $message = "Deleted user cannot deactivate account.";
} else if($u->isUserBlocked()){
    $success = 0;
    $message = "Blocked user cannot deactivate account.";
}  else if($u->isUserDeactivated()){
    $success = 0;
    $message = "Your account is already deactivated.";
} else {
    $u->setDeactivated(1);
    $output = $u->save();
    if($output == 0){
        $success = 0;
        $message = "Your account cannot be deactivated. Database error. Try again later.";
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