<?php
include_once '../classes/Video.class.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    echo 'POSTed';
  }
print_r($_POST);
print_r($_GET);
$title = $_GET["title"];
$url = $_GET["url"];

$video = new Video();
$video->setTitle($title);
$video->setLink($url);
$output = $video->create();

$json = array("success" => $output);

header("Content-Type:application/json");
echo json_encode($json);

