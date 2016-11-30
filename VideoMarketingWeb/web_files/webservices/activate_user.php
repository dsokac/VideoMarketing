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
$message = "Your account is activated successfully.";

$u = new User(intval($id));

$linkTime = date(MyGlobal::$dateFormatDB, strtotime($u->getActivationSentAt()));
$then = new DateTime($linkTime);
$now = new DateTime();
$interval = $then->diff($now);
$seconds = $interval->s;

if($seconds > 7200){
    $success = 0;
    $message = "Activation link expired.";
}else if($u->isUserDeleted()){
    $success = 0;
    $message = "Deleted user cannot activate account.";
} else if($u->isUserBlocked()){
    $success = 0;
    $message = "Blocked user cannot activate account.";
}  else if(!$u->isUserDeactivated()){
    $success = 0;
    $message = "Your account is not deactivated.";
} else {
    $u->setDeactivated(0);
    $output = $u->save();
    if($output == 0){
        $success = 0;
        $message = "User cannot be updated. Database error. Try again later.";
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