<?php
include_once "../../web_files/classes/MyGlobal.class.php";
include_once "../../web_files/classes/VideoComment.class.php";
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$video = $_GET["video"];
$author = $_GET["author"];
$content = $_GET["content"];

$vc = new VideoComment();
$vc->setAuthor(intval($author));
$vc->setVideo(intval($video));
$vc->setContent($content);
$output = $vc->create();
$success = $output == 0 ? 0 : 1;
$message = $output == 0 ? "Comment couldn't be added to video." : "Your comment is written successfully.";
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