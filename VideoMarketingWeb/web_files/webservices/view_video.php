<?php
include_once "../../web_files/classes/MyGlobal.class.php";
include_once "../../web_files/classes/Video.class.php";
include_once "../../web_files/classes/VideoView.class.php";
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$video = $_GET["video"];
$user = $_GET["user"];

$vv = new VideoView();
$vv->setVideo(intval($video));
$vv->setUser(intval($user));

$output = $vv->addView();

$success = $output == 0 ? 0 : 1;
$message = $output == 0 ? "Video view is not written in database." : "Video view is successfully written.";

$json = array(
    "status" => 100,
    "requested_at" => date(MyGlobal::$timeFormat),
    "data" => array(
        "success" => $success,
        "message" => $message
    )
);

header("Content-Type: application/json");
echo json_encode($json);