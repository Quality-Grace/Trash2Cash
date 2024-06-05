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
    $request_id = intval($_POST['id']);
    $status = $conn->real_escape_string($_POST['status']);

    // Prepare and bind
    $stmt = $conn->prepare("UPDATE requests SET status = ? WHERE id = ?");
    if ($stmt === false) {
        die("Error preparing statement: " . $conn->error);
    }

    $stmt->bind_param("si", $status, $request_id); // 'i' for integer, 's' for string

    // Execute the statement
    if ($stmt->execute() === TRUE) {
        echo "Request status updated successfully";
    } else {
        echo "Error: " . $stmt->error;
    }

    // Close the statement
    $stmt->close();
}

// Close the connection
$conn->close();
?>
