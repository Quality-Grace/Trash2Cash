<?php
// Enable error reporting
error_reporting(E_ALL);
ini_set('display_errors', 1);

// Database configuration
$servername = "localhost";  // Change if using a different host
$username = "root";         // Change to your database username
$password = "";             // Change to your database password
$dbname = "trash2cash";    // Change to your database name

// Create connection
$conn = new mysqli($servername, $username, $password);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Create database
$sql = "CREATE DATABASE IF NOT EXISTS $dbname";
if ($conn->query($sql) === TRUE) {
    echo "Database created successfully or already exists<br>";
} else {
    die("Error creating database: " . $conn->error);
}

// Select the database
$conn->select_db($dbname);

// SQL statement to create a table
$sql = "CREATE TABLE IF NOT EXISTS users (
    id INT(6) UNSIGNED PRIMARY KEY,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP)";

// Execute the SQL statement
if ($conn->query($sql) === TRUE) {
    echo "Table Users created successfully";
} else {
    echo "Error creating table: " . $conn->error;
}

// Create the rewards table
$sql = "CREATE TABLE IF NOT EXISTS rewards (
    COST INT NOT NULL,
    LEVEL INT NOT NULL,
    ICON TEXT COLLATE utf8_bin NOT NULL,
    TITLE VARCHAR(100) COLLATE utf8_bin NOT NULL,
    CODE VARCHAR(100) COLLATE utf8_bin NOT NULL PRIMARY KEY
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
if ($conn->query($sql) === TRUE) {
    echo "Table 'rewards' created successfully<br>";
} else {
    echo "Error creating table: " . $conn->error . "<br>";
}

// Create the RecyclableMaterialTypes table
$sql = "CREATE TABLE IF NOT EXISTS RecyclableMaterialTypes (
    TYPE VARCHAR(100) COLLATE utf8_bin NOT NULL,
    EXP INT NOT NULL,
    REWARD_AMOUNT INT NOT NULL,
    IMAGE TEXT COLLATE utf8_bin NOT NULL,
    COLOUR TEXT COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";

if ($conn->query($sql) === TRUE) {
    echo "Table 'RecyclableMaterialTypes' created successfully<br>";
} else {
    echo "Error creating table: " . $conn->error . "<br>";
}

// Insert values into the RecyclableMaterialTypes table
$sql = "INSERT INTO RecyclableMaterialTypes (TYPE, EXP, REWARD_AMOUNT, IMAGE, COLOUR) VALUES
('Paper', 0, 0, '/paper_type.png', '#D1CCBA'),
('Glass', 0, 0, '/glass_type.png', '#9FD7CA'),
('Metal', 0, 0, '/metal_type.png', '#545454'),
('Plastic', 0, 0, '/plastic_type.png', '#376DAE'),
('Other', 0, 0, '/other_type.png', '#8BC34A')";

if ($conn->query($sql) === TRUE) {
    echo "Initial data inserted into 'RecyclableMaterialTypes' successfully<br>";
} else {
    echo "Error inserting data: " . $conn->error . "<br>";
}

// Commit the transaction
$conn->commit();

// Close the connection
$conn->close();
?>
