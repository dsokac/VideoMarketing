<?php

include_once "../../web_files/classes/MyGlobal.class.php";
include_once "../../web_files/classes/VideoComment.class.php";
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$video = $_GET["video"];

$vc = new VideoComment();
$vc->setVideo(intval($video));
$vc->getAllCommentsOfVideo();
$comments = VideoComment::$list;

$json = array(
    "status" => 100,
    "requested_at" => date(MyGlobal::$timeFormat),
    "data" => array()
);

foreach($comments as $comment){
    $newArray = array(
      "id" => $comment->getId(),
      "author" => array(
          "id" => $comment->getAuthor()->getId(),
          "name" => $comment->getAuthor()->getName(),
          "surname" => $comment->getAuthor()->getSurname()
      ),
      "content" => $comment->getContent(),
      "created_at" => $comment->getCreatedAt()
    );
    array_push($json["data"], $newArray);
}

header("Content-Type:application/json");
echo json_encode($json);