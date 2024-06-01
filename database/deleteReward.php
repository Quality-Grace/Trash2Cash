<?php
    if(isset($_GET["POSITION"])) {

        $data = array();

        $position = $_GET["POSITION"];

        $host="localhost";
        $uname="root";
        $pass="";
        $dbname="rewards";

        $dbh = mysqli_connect($host,$uname,$pass) or die("cannot connect");
        mysqli_select_db($dbh, $dbname);
        $sql = "DELETE FROM rewards WHERE POSITION = $position";
        echo $sql;
        mysqli_query($dbh, $sql);
        mysqli_close($dbh);

    }
?>
