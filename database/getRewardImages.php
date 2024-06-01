<?php
    $dir = './rewardImages/';

    $images = array();

    if (is_dir($dir)) {
        if ($dh = opendir($dir)) {
            while (($file = readdir($dh)) !== false) {
                $fileExtension = pathinfo($file, PATHINFO_EXTENSION);

                if (in_array(strtolower($fileExtension), array('jpg', 'jpeg', 'png', 'gif', 'bmp'))) {
                    $images[] = $file;
                }
            }
            closedir($dh);
        }
    } else {
        echo "Directory does not exist.";
    }

    echo json_encode($images);
?>

