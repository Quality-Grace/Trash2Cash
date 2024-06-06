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
    id INT(6) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    level FLOAT NOT NULL,
    rewardPoints FLOAT NOT NULL,
    image TEXT COLLATE utf8_bin NOT NULL,
    rewardList TEXT COLLATE utf8_bin NOT NULL,
    reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP)";

// Execute the SQL statement
if ($conn->query($sql) === TRUE) {
    echo "Table Users created successfully";
} else {
    echo "Error creating table: " . $conn->error;
}

// Create the RecyclableMaterialTypes table
$sql = "CREATE TABLE IF NOT EXISTS RecyclableMaterialTypes (
    TYPE VARCHAR(100) COLLATE utf8_bin PRIMARY KEY,
    EXP INT NOT NULL,
    REWARD_AMOUNT INT NOT NULL,
    RECYCLE_AMOUNT INT NOT NULL,
    IMAGE TEXT COLLATE utf8_bin NOT NULL,
    COLOUR TEXT COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";

if ($conn->query($sql) === TRUE) {
    echo "Table 'RecyclableMaterialTypes' created successfully<br>";
    // Check if the RecyclableMaterialTypes table is empty
    $result = $conn->query("SELECT COUNT(*) AS count FROM RecyclableMaterialTypes");
    $row = $result->fetch_assoc();
    $count = $row['count'];

    if ($count == 0) {
        // Insert values into the RecyclableMaterialTypes table
        $sql = "INSERT INTO RecyclableMaterialTypes (TYPE, EXP, REWARD_AMOUNT, RECYCLE_AMOUNT, IMAGE, COLOUR) VALUES
        ('Paper', 0, 0, 1, '/paper_type.png', '#D1CCBA'),
        ('Glass', 0, 0, 1, '/glass_type.png', '#9FD7CA'),
        ('Metal', 0, 0, 1, '/metal_type.png', '#545454'),
        ('Plastic', 0, 0, 1, '/plastic_type.png', '#376DAE'),
        ('Other', 0, 0, 1, '/other_type.png', '#8BC34A')";

        if ($conn->query($sql) === TRUE) {
            echo "Values inserted successfully";
        } else {
            echo "Error inserting values: " . $conn->error;
        }
    } else {
        echo "RecyclableMaterialTypes table is not empty";
    }
} else {
    echo "Error creating table: " . $conn->error . "<br>";
}

// Create the items table
$sql = "CREATE TABLE IF NOT EXISTS items (
    id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    material_type VARCHAR(100) COLLATE utf8_bin NOT NULL,
    item_type VARCHAR(50) NOT NULL,
    CONSTRAINT fk_material_type FOREIGN KEY (material_type) REFERENCES RecyclableMaterialTypes(TYPE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";

if ($conn->query($sql) === TRUE) {
    echo "Table 'items' created successfully<br>";
} else {
    echo "Error creating table: " . $conn->error . "<br>";
}

$sql = "CREATE TABLE IF NOT EXISTS requests (
    id INT(6) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    user_id INT(6) UNSIGNED NOT NULL,
    item_id INT(6) UNSIGNED NOT NULL,
    status VARCHAR(100) COLLATE utf8_bin NOT NULL,
    FOREIGN KEY (item_id) REFERENCES items(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";

if ($conn->query($sql) === TRUE) {
    echo "Table requests created successfully";
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

// Commit the transaction
$conn->commit();

// Close the connection
$conn->close();
?>