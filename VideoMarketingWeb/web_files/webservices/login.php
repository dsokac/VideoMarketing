<?php
include_once "../../web_files/classes/MyGlobal.class.php";
include_once "../../web_files/classes/User.class.php";

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$username = $_GET["username"];
$password = $_GET["password"];

$user = new User();
$success = 1;
$message = "";
if(!$user->doesUserExists($username, $password)){
    $success = 0;
    $message = "User doesn't exist in database or incorrect credentials were provided.";
} else{
    $ctlUser = new User($user->getUserId($username, $password));
    if($ctlUser->isUserDeleted()){
        $success = 0;
        $message = "Requested user is already deleted.";
    } else if($ctlUser->isUserDeactivated()){
        $success = 0;
        $message = "Requested user is deactivated.";
    } else if($ctlUser->isUserBlocked()){
        $success = 0;
        $message = "Requested user is blocked.";
    } else {
        $message = "You logged in successfully.";
        $userJSON = array(
            "id" => $ctlUser->getId(),
            "name" => $ctlUser->getName(),
            "surname" => $ctlUser->getSurname(),
            "birthday" => $ctlUser->getBirthday(),
            "gender" => $ctlUser->getGender(),
            "email" => $ctlUser->getEmail(),
            "username" => $ctlUser->getUsername(),
            "password" => $ctlUser->getPassword(),
            "password_changed_at" => $ctlUser->getPasswordChangedAt(),
            "mobile_number" => $ctlUser->getMobileNumber(),
            "geographic_unit" => array(
                "id" => $ctlUser->getGeographicUnit()->getId(),
                "name" => $ctlUser->getGeographicUnit()->getName()
            )
                
        );
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
if($success == 1){
    $json["data"]["user"] = $userJSON;
}

header("Content-Type:application/json");
echo json_encode($json);