<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "trash2cash";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Check if the form is submitted
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Get the form data and sanitize it
    $user_id = intval($_POST['id']);
    $level = floatval($_POST['level']);
    $reward_points = floatval($_POST['reward_points']);

    // Prepare and bind
    $stmt = $conn->prepare("UPDATE users SET level = ?, rewardPoints = ? WHERE id = ?");
    if ($stmt === false) {
        die("Error preparing statement: " . $conn->error);
    }

    $stmt->bind_param("ddi", $level, $reward_points, $user_id); // 'i' for integer

    // Execute the statement
    if ($stmt->execute() === TRUE) {
        echo "User level and reward points updated successfully";
    } else {
        echo "Error: " . $stmt->error;
    }

    // Close the statement
    $stmt->close();
}

// Close the connection
$conn->close();
?>
