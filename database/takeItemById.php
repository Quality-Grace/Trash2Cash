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
    // Get the item_id from the POST request
    $item_id = intval($_POST['item_id']);

    // Prepare and bind
    $stmt = $conn->prepare("SELECT * FROM items WHERE id = ?");
    if ($stmt === false) {
        die("Error preparing statement: " . $conn->error);
    }

    $stmt->bind_param("i", $item_id); // 'i' for integer

    // Execute the statement
    if ($stmt->execute() === TRUE) {
        $result = $stmt->get_result();

        // Fetch the row
        $row = $result->fetch_assoc();

        // Return the item details as JSON
        echo json_encode($row);
    } else {
        echo json_encode(array("error" => $stmt->error));
    }

    // Close the statement
    $stmt->close();
}

// Close the connection
$conn->close();
?>
