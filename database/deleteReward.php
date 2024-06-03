<?php
    if(isset($_GET["CODE"])) {

        $data = array();

        $code = $_GET["CODE"];

        $host="localhost";
        $uname="root";
        $pass="";
        $dbname="trash2cash";

        $dbh = mysqli_connect($host,$uname,$pass) or die("cannot connect");
        mysqli_select_db($dbh, $dbname);
        $sql = "DELETE FROM rewards WHERE CODE = $code";
        echo $sql;
        mysqli_query($dbh, $sql);
        mysqli_close($dbh);

    }
?>
