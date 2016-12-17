<?php
include_once "../../web_files/classes/User.class.php";
include_once "../../web_files/classes/TelecommUser.class.php";

$user = $_GET["user"];

$u = new User(intval($user));
$tu = new TelecommUser();
$tu->setUser($u->getId());
$to = $tu->getActiveTelecommOperator();
$tu = new TelecommUser($u->getId(),$to->getId());
$data = $tu->getPointsStats();

$json = array(
    "status" => 100,
    "requested_at" => date(MyGlobal::$timeFormat),
    "data" => $data
);

header("Content-Type:application/json");
echo json_encode($json);
