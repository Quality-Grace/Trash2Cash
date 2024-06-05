<?php
    $email = $_POST['email'];
    $password = $_POST['password'];

    // Database connection details
    $host = "localhost";
    $uname = "root";
    $pass = "";
    $dbname = "trash2cash";

    $dbh = mysqli_connect($host, $uname, $pass) or die("Cannot connect");
    $dbh->set_charset("utf8");

    mysqli_select_db($dbh, $dbname);

    // Prepare the SQL query
    $query = "SELECT * FROM users WHERE email='$email' AND password='$password'";

    $result = mysqli_query($dbh, $query);

    // Check if any rows were returned
    if (mysqli_num_rows($result) > 0) {
        $response = 1;
    } else {
        $response = 0;
    }

    echo $response;

    // Close the database connection
    mysqli_close($dbh);
?>