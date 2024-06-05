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
    // Get the material type from the POST request
    $material_type = $_POST['material_type'];

    // Prepare and bind
    $stmt = $conn->prepare("SELECT * FROM RecyclableMaterialTypes WHERE TYPE = ?");
    if ($stmt === false) {
        die("Error preparing statement: " . $conn->error);
    }

    $stmt->bind_param("s", $material_type); // 's' for string

    // Execute the statement
    if ($stmt->execute() === TRUE) {
        $result = $stmt->get_result();

        // Fetch the row
        $row = $result->fetch_assoc();

        // Return the material details as JSON
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
