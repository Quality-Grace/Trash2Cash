<?php
// Enable error reporting
error_reporting(E_ALL);
ini_set('display_errors', 1);



// Database configuration
$servername = "localhost";  // Change if using a different host
$username = "root";         // Change to your database username
$password = "";             // Change to your database password
$dbname = "trash2cash";    // Change to your database name

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Check if form is submitted
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $email = $_POST['email'];
    $password = $_POST['password'];
    $level = $_POST['level'];
    $rewardPoints = $_POST['rewardPoints'];

    // Prepare and bind
    $stmt = $conn->prepare("INSERT INTO users (id, email, password, level, rewardPoints) VALUES (?, ?, ?, ?)");
    $stmt->bind_param("ssss", $email, $password, $level, $rewardPoints);

    // Execute the statement
    if ($stmt->execute()) {
        echo "1";
    } else {
        echo "Error: " . $stmt->error;
    }

    // Close the statement
    $stmt->close();
}

// Close the connection
$conn->close();
?>
