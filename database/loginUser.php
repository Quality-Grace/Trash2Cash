<?php
$email = $_POST['email'];
$password = $_POST['password'];

// Database connection details
$host = "localhost";
$uname = "root";
$pass = "";
$dbname = "trash2cash";

$dbh = new mysqli($host, $uname, $pass, $dbname);

// Check connection
if ($dbh->connect_error) {
    die("Connection failed: " . $dbh->connect_error);
}
$dbh->set_charset("utf8");

// Prepare the SQL query
$query = "SELECT * FROM users WHERE email = ?";
$stmt = $dbh->prepare($query);
$stmt->bind_param("s", $email);
$stmt->execute();
$result = $stmt->get_result();

// Check if any rows were returned
if ($result->num_rows > 0) {
    $user_row = $result->fetch_assoc();
    // Verify the password
    if (password_verify($password, $user_row['password'])) {
        // Remove password from the response for security reasons
        unset($user_row['password']);
        // Convert the associative array to a JSON string
        $response = json_encode($user_row);
    } else {
        $response = 0;
    }
} else {
    $response = 0;
}

echo $response;

// Close the statement and database connection
$stmt->close();
$dbh->close();
?>
