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

// Prepare and execute query to select all user data
$sql = "SELECT * FROM users";
$result = $conn->query($sql);

// Check if there are any results
if ($result->num_rows > 0) {
    // Fetch all rows and store them in an array
    $users = array();
    while ($row = $result->fetch_assoc()) {
        $users[] = $row;
    }

    // Return the user data as JSON
    echo json_encode($users);
} else {
    // No user data found
    echo json_encode(array("error" => "No users found"));
}

// Close the connection
$conn->close();
?>
