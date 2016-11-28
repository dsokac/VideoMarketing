<?php

include_once "../../web_files/classes/MyGlobal.class.php";
include_once "../../web_files/classes/Video.class.php";
include_once "../../web_files/classes/VideoTag.class.php";
include_once "../../web_files/classes/User.class.php";


$userId = $_GET["id"];

$u = new User(intval($userId));

$birthday = $u->getBirthday();
$gender = $u->getGender();
$geo_unit_id = $u->getGeographicUnit()->getId();

$birthdayTime = date(MyGlobal::$dateFormatDB, strtotime($birthday));
$then = new DateTime($birthdayTime);
$now = new DateTime();
$interval = $then->diff($now);
$age = $interval->y;
$t = new Tag();
$tags = $t->getAgeTags($age, $gender, $geo_unit_id);

$tc = new VideoTag();

$videos = $tc->getVideosByTags($tags, $userId);

header("Content-Type:application/json");
echo json_encode($videos);
