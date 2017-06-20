<?php
  include_once "../../web_files/classes/MyGlobal.class.php";
  include_once "../../web_files/classes/Db.class.php";

  if(isset($_GET["setmaintenance"])){
    $query = Db::makeQuery("update", array("application_settings"), array("maintenance"), "id = 1", array($_GET["setmaintenance"]));
    $data = Db::query($query);
    if($data > 0){ echo "success";}
    else{echo "fail";}
  } else {
      $data = Db::query(Db::makeQuery("select",array("application_settings"),array("maintenance"),"id = 1"));
      header("Content-Type:application/json");
      echo json_encode($data[0]);
  }

