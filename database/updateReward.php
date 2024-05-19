<?php
    if(isset($_GET["POSITION"], $_GET["COST"], $_GET["LEVEL"], $_GET["ICON"], $_GET["TITLE"])) {

        $data = array();

        $position = $_GET["POSITION"];
        $cost = $_GET["COST"];
        $level = $_GET["LEVEL"];
        $icon = $_GET["ICON"];
        $title = $_GET["TITLE"];

        $host="localhost";
        $uname="root";
        $pass="";
        $dbname="rewards";

        $dbh = mysqli_connect($host,$uname,$pass) or die("cannot connect");
        mysqli_select_db($dbh, $dbname);
        $sql = "UPDATE rewards SET COST=$cost, LEVEL=$level, ICON='$icon', TITLE='$title' WHERE POSITION=$position";
        echo $sql;
        mysqli_query($dbh, $sql);
        mysqli_close($dbh);

    }
?>
