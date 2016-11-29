<?php

use \User;

$user = $_GET["user"];

$u = new User(intval($user));
