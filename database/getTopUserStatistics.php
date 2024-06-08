<?php
header('Content-Type: application/json');

$servername = "your_servername";
$username = "your_username";
$password = "your_password";
$dbname = "your_database";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die(json_encode(array("error" => "Connection failed: " . $conn->connect_error)));
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $query = "SELECT username, totalRecycledItems, userLevel, rewardPoints FROM users ORDER BY rewardPoints DESC LIMIT 10";
    $result = $conn->query($query);

    if ($result->num_rows > 0) {
        $userStatisticsList = array();
        while ($row = $result->fetch_assoc()) {
            $userStatistics = array(
                "userName" => $row['username'],
                "totalRecycledItems" => $row['totalRecycledItems'],
                "userLevel" => (float)$row['userLevel'],
                "rewardPoints" => (float)$row['rewardPoints']
            );
            $userStatisticsList[] = $userStatistics;
        }
        echo json_encode($userStatisticsList);
    } else {
        echo json_encode(array("error" => "No data found"));
    }
} else {
    echo json_encode(array("error" => "Invalid request method"));
}

$conn->close();
?>
