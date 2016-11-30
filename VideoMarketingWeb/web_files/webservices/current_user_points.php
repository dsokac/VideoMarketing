<?php

include_once "../../web_files/classes/MyGlobal.class.php";
include_once "../../web_files/classes/TelecommUser.class.php";

$user = $_GET["user_id"];
$telecommOperator = $_GET["telecomm_operator"];

$tcUser = TelecommUser::getTelecommUserByTelecommCode($user, $telecommOperator);

$json = array(
    "status" => 100,
    "requested_at" => date(MyGlobal::$timeFormat),
    "data" => array(
        "user_id_remote" => $tcUser->getUserIdRemote(),
        "points" => $tcUser->getPoints()
    )
);

header("Content-Type:application/json");
echo json_encode($json);
