<?php
    if(isset($_GET["COST"], $_GET["LEVEL"], $_GET["ICON"], $_GET["TITLE"], $_GET["CODE"])) {

        $data = array();

        $cost = $_GET["COST"];
        $level = $_GET["LEVEL"];
        $icon = $_GET["ICON"];
        $title = $_GET["TITLE"];
        $code = $_GET["CODE"];

        $host="localhost";
        $uname="root";
        $pass="";
        $dbname="trash2cash";

        $dbh = mysqli_connect($host,$uname,$pass) or die("cannot connect");
        mysqli_select_db($dbh, $dbname);
        $sql = "UPDATE rewards SET COST=$cost, LEVEL=$level, ICON='$icon', TITLE='$title' WHERE CODE=$code";
        echo $sql;
        mysqli_query($dbh, $sql);
        mysqli_close($dbh);

    }
?>
