<?php
include_once "../../web_files/classes/MyGlobal.class.php";
include_once "../../web_files/classes/VideoView.class.php";
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$video = $_GET["video"];
$user = $_GET["user"];
$like = $_GET["like"];

$vv = new VideoView(intval($user), intval($video));
$oldLike = $vv->getVideolike();
$vv->setVideolike($like);
$output = $vv->save();
if($output != 0){
    if($oldLike == 1 && $like == 0){
        $vv->getVideo()->removeLike();
        $output = $vv->getVideo()->save();
    } else if ($oldLike == 0 && $like == 1) {
        $vv->getVideo()->addLike();
        $output = $vv->getVideo()->save();
    } else if ($oldLike == 1 && $like == -1){
        $vv->getVideo()->removeLike();
        $vv->getVideo()->addDislike();
        $output = $vv->getVideo()->save();
    } else if($oldLike == -1 && $like == 1){
        $vv->getVideo()->removeDislike();
        $vv->getVideo()->addLike();
        $output = $vv->getVideo()->save();
    } else if($oldLike == -1 && $like == 0){
        $vv->getVideo()->removeDislike();
        $output = $vv->getVideo()->save();
    } else if($oldLike == 0 && $like == -1){
        $vv->getVideo()->addDislike();
        $output = $vv->getVideo()->save();
    }
}

$success = $output == 0 ? 0 : 1;
$message = $output == 0 ? "Your like could not be written." : "Your like is written successfully.";
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