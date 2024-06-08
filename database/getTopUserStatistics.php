<?php
header('Content-Type: application/json');

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "trash2cash";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die(json_encode(array("error" => "Connection failed: " . $conn->connect_error)));
}

// Check if the request method is POST
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Query to select the top 10 users by rewardPoints
    $query = "SELECT id, level, rewardPoints FROM users ORDER BY rewardPoints DESC LIMIT 10";
    $result = $conn->query($query);

    // Check if there are results
    if ($result->num_rows > 0) {
        $userStatisticsList = array();
        while ($row = $result->fetch_assoc()) {
            $userStatistics = array(
                "id" => $row['id'],
                "level" => (float)$row['level'],
                "rewardPoints" => (float)$row['rewardPoints']
            );
            $userStatisticsList[] = $userStatistics;
        }
        // Output the results as JSON
        echo json_encode($userStatisticsList);
    } else {
        // No data found case
        echo json_encode(array("error" => "No data found"));
    }
} else {
    // Invalid request method case
    echo json_encode(array("error" => "Invalid request method"));
}

// Close the database connection
$conn->close();
?>
