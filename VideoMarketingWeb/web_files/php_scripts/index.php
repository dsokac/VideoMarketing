<?php 
    include_once "header.php";  
    
    include_once "../classes/PinnedVideo.class.php";
    include_once "../classes/Video.class.php";
    include_once "../classes/VideoTag.class.php";
    include_once "../classes/Tag.class.php";
    
    $v = new Video(1);
    
    
    
      
    $template = $twig->loadTemplate('index.twig');
    echo $template->render(array());
?>
