<?php
session_start();
include_once "../classes/MyGlobal.class.php";
include_once MyGlobal::manageRouting("classes/VideoMarketingApplication.class.php");
MyGlobal::fetchLangFile(MyGlobal::manageRouting("i18n_langs/eng.xml"));
$app = VideoMarketingApplication::getInstance();
$app->initialize();
$twig = $app->getTwig();