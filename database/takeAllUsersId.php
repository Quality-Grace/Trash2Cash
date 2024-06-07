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

// Prepare and execute query to select all user IDs
$sql = "SELECT id FROM users";
$result = $conn->query($sql);

// Check if there are any results
if ($result->num_rows > 0) {
    // Fetch all rows and store them in an array
    $user_ids = array();
    while ($row = $result->fetch_assoc()) {
        $user_ids[] = $row['id'];
    }

    // Return the user IDs as JSON
    echo json_encode($user_ids);
} else {
    // No user IDs found
    echo json_encode(array("error" => "No user IDs found"));
}

// Close the connection
$conn->close();
?>
