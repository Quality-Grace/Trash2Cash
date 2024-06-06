<?php
    if(isset($_GET["TYPE"], $_GET["EXP"], $_GET["REWARD_AMOUNT"], $_GET["RECYCLE_AMOUNT"])) {

        $data = array();
        $type = $_GET["TYPE"];
        $exp = $_GET["EXP"];
        $reward_amount = $_GET["REWARD_AMOUNT"];
        $recycle_amount = $_GET["RECYCLE_AMOUNT"];

        $host="localhost";
        $uname="root";
        $pass="";
        $dbname="trash2cash";

        $dbh = mysqli_connect($host,$uname,$pass) or die("cannot connect");
        mysqli_select_db($dbh, $dbname);
        $sql = "UPDATE RecyclableMaterialTypes SET EXP=$exp, REWARD_AMOUNT=$reward_amount, RECYCLE_AMOUNT=$recycle_amount WHERE TYPE=$type";
        echo $sql;
        mysqli_query($dbh, $sql);
        mysqli_close($dbh);

    }
?>
