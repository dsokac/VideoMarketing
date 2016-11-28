<?php 
    include_once "header.php";  
    
    include_once "../classes/TelecommUser.class.php";
    $user = 1;
    $code = 21901;
    $tu = TelecommUser::getTelecommUserByTelecommCode($user, $code);
    var_dump($tu);
    
    echo "<br/><br/>";
      
    $template = $twig->loadTemplate('index.twig');
    echo $template->render(array());
?>
