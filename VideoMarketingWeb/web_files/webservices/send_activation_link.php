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
$message = "The activation link is sent to your e-mail address.";

$u = new User(intval($id));

if($u->isUserDeleted()){
    $success = 0;
    $message = "Deleted user cannot activate account.";
} else if($u->isUserBlocked()){
    $success = 0;
    $message = "Blocked user cannot activate account.";
}  else if(!$u->isUserDeactivated()){
    $success = 0;
    $message = "Your account is not deactivated.";
} else {
    $output = $u->createActivationLink();
    if($output == 0){
        $success = 0;
        $message = "Activation link cannot be created. Database error. Try again later.";
    } else {
        $u->setActivationSentAt(date(MyGlobal::$timeFormatDB));
        $output = $u->save();
        if($output == 0){
            $success = 0;
            $message = "Activation link sent date cannot be set. Try again later.";
        } else {
            $output = $u->sendEmail();
            if(!$output){
                $success = 0;
                $message = "Activation link cannot be sent. Try again later.";
            } 
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