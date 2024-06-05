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
    // Get the form data
    $material_type = $_POST['material_type'];
    $item_type = $_POST['item_type'];

    // Prepare and bind
    $stmt = $conn->prepare("INSERT INTO items (material_type, item_type) VALUES (?, ?)");
    if ($stmt === false) {
        die("Error preparing statement: " . $conn->error);
    }

    $stmt->bind_param("ss", $material_type, $item_type);

    // Execute the statement
    if ($stmt->execute() === TRUE) {
        $last_id = $conn->insert_id; // Get the ID of the last inserted row
        echo json_encode(array("id" => $last_id));
    } else {
        echo json_encode(array("error" => $stmt->error));
    }

    // Close the statement
    $stmt->close();
}

// Close the connection
$conn->close();
?>
