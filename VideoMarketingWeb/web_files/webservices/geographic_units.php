<?php
include_once "../../web_files/classes/MyGlobal.class.php";
include_once "../../web_files/classes/GeographicUnit.class.php";
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$gu = new GeographicUnit();
$gu->getAll();
$jsonGU = array();
foreach($gu->list as $element){
    array_push($jsonGU, array("id" => $element->getId(), "name" => $element->getName()));
}

$json = array(
    "status" => 100,
    "requested_at" => date(MyGlobal::$timeFormat),
    "data" => $jsonGU
);

header("Content-Type:application/json");
echo json_encode($json);