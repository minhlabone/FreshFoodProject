<?php
include "connect.php";
$username = $_POST['username'];
$id = $_POST['id'];

$query = 'UPDATE `user` SET `username`="'.$username.'" WHERE `id`='.$id;
$data = mysqli_query($conn, $query);
if ($data == true) {
		$arr = [
		'success' => true,
		'message' => "Thành công"
			   ];
			}else{
		$arr = [
		'success' => false,
		'message' => "Không thành công"
				];
	}


print_r(json_encode($arr));

?>