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
    // Get the user_id from the POST request
    $user_id = intval($_POST['user_id']);

    // Prepare and bind
    $stmt = $conn->prepare("SELECT * FROM requests WHERE user_id = ?");
    if ($stmt === false) {
        die("Error preparing statement: " . $conn->error);
    }

    $stmt->bind_param("i", $user_id); // 'i' for integer

    // Execute the statement
    if ($stmt->execute() === TRUE) {
        $result = $stmt->get_result();

        // Fetch all rows
        $requests = array();
        while ($row = $result->fetch_assoc()) {
            $requests[] = $row;
        }

        // Return the requests as JSON
        echo json_encode($requests);
    } else {
        echo json_encode(array("error" => $stmt->error));
    }

    // Close the statement
    $stmt->close();
}

// Close the connection
$conn->close();
?>
