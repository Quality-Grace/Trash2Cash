<?php
// Enable error reporting
error_reporting(E_ALL);
ini_set('display_errors', 1);

// Check if form is submitted
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $email = $_POST['email'];
    $password = $_POST['password']; // Hash the password
    $level = floatval($_POST['level']);
    $rewardPoints = floatval($_POST['rewardPoints']);

    $host = "localhost";
    $uname = "root";
    $pass = "";
    $dbname = "trash2cash";

    // Create connection
    $dbh = new mysqli($host, $uname, $pass, $dbname);

    // Check connection
    if ($dbh->connect_error) {
        die("Connection failed: " . $dbh->connect_error);
    }

    // Prepare and bind
    $stmt = $dbh->prepare("INSERT INTO users (email, password, level, rewardPoints) VALUES (?, ?, ?, ?)");
    $stmt->bind_param("ssdd", $email, $password, $level, $rewardPoints);

    // Execute the statement
    if ($stmt->execute()) {
        echo "New record created successfully";
    } else {
        echo "Error: " . $stmt->error;
    }

    // Close the statement and connection
    $stmt->close();
    $dbh->close();
}
?>
