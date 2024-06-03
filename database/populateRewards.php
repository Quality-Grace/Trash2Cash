<?php
    $data = array();
    $host = "localhost";
    $uname = "root";
    $pass = "";
    $dbname = "trash2cash";
    $dbh = mysqli_connect($host, $uname, $pass) or die("cannot connect");
    mysqli_select_db($dbh, $dbname);
    $sql = "SELECT * FROM rewards";
    $result = mysqli_query($dbh, $sql);
    while ($row = mysqli_fetch_assoc($result)) {
        $data[] = $row;
    }
    header("Content-Type: application/json");
    echo json_encode($data);
    mysqli_close($dbh);
?>
