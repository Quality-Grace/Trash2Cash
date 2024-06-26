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
    $user_id = intval($_POST['user_id']);
    $item_id = intval($_POST['item_id']);
    $status = $conn->real_escape_string($_POST['status']);

    // Prepare and bind
    $stmt = $conn->prepare("INSERT INTO requests (user_id, item_id, status) VALUES (?, ?, ?)");
    if ($stmt === false) {
        die("Error preparing statement: " . $conn->error);
    }

    $stmt->bind_param("iis", $user_id, $item_id, $status);

     // Execute the statement
        if ($stmt->execute() === TRUE) {
            // Get the ID of the last inserted row
            $last_id = $conn->insert_id;
            // Return the ID as the response
            echo $last_id;
        } else {
            echo "Error: " . $stmt->error;
        }

        // Close the statement
        $stmt->close();
}

// Close the connection
$conn->close();
?>