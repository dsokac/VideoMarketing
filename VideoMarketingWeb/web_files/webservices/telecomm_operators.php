<?php
include_once "../../web_files/classes/MyGlobal.class.php";
include_once "../../web_files/classes/TelecommOperator.class.php";
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$to = new TelecommOperator();
$to->getAll();

$toArray = array();
foreach($to->list as $element){
    array_push($toArray, array("id" => $element->getId(), "name" => $element->getName()));
}

$json = array(
    "status" => 100,
    "requested_at" => date(MyGlobal::$timeFormat),
    "data" => $toArray
);

header("Content-Type:application/json");
echo json_encode($json);