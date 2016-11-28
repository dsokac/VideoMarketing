<?php
include_once "header.php";
include_once MyGlobal::manageRouting("classes/Video.class.php");

$videos = new Video();
$videos->getAll();

$scriptArray = array(
    "../web_files/js/adminVideoScript.js"
);

$template = $twig->loadTemplate('admin_videos.twig');
echo $template->render(array("videos" => $videos->list, "scripts" => $scriptArray));
