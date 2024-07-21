<?php
// Enable error reporting
error_reporting(E_ALL);
ini_set('display_errors', 1);

// Check if form is submitted
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $email = $_POST['email'];
    $username = $_POST['username'];
    $password = $_POST['password']; // Hash the password
    $level = floatval($_POST['level']);
    $rewardPoints = floatval($_POST['rewardPoints']);
    $image = $_POST['image'];
    $reward_list = $_POST['rewardList'];

    $host = "localhost";
    $uname = "root";
    $pass = "";
    $dbname = "trash2cash";

    // Create connection
    $dbh = new mysqli($host, $uname, $pass, $dbname);

    // Check connection
    if ($dbh->connect_error) {
        die("Connection failed: " . $dbh->connect_error);
    }

    // Hash the password
    $hashed_password = password_hash($password, PASSWORD_DEFAULT);

    // Check if user already exists
    $query = "SELECT * FROM users WHERE email=?";
    $stmt = $dbh->prepare($query);
    $stmt->bind_param("s", $email);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows < 1) {
        // Prepare and bind
        $stmt = $dbh->prepare("INSERT INTO users (email, username, password, level, rewardPoints, image, rewardList) VALUES (?, ?, ?, ?, ?, ?, ?)");
        $stmt->bind_param("sssddss", $email, $username, $hashed_password, $level, $rewardPoints, $image, $reward_list);

        // Execute the statement
        if ($stmt->execute()) {
            echo "New record created successfully";
        } else {
            echo "Error: " . $stmt->error;
        }
    } else {
        echo "User already exists!";
    }

    // Close the statement and connection
    $stmt->close();
    $dbh->close();
}
?>
