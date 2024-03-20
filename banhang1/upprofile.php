<?php 
include "connect.php";

$target_dir = "images/";
$id = $_POST['id']; 
$name = $id. ".jpg"; 
$query = 'UPDATE `user` SET `imageprofile`="'.$name.'" WHERE `id`='.$id;
$data = mysqli_query($conn, $query);

$target_file_name = $target_dir .$name;  

if (isset($_FILES["file"]))  
   {  
   if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file_name) )  
      {  
        $arr = [
      'success' => true,
      'message' => "Thành công",
      "name"  => $name
            ];
      }  
   else  
      {  
        $arr = [
      'success' => false,
      'message' => " Khong Thành công"
            ];
      }  
   }  
else  
   {  
     $arr = [
      'success' => false,
      'message' => "loi"
            ];
   }  
   
   echo json_encode($arr);  
?>  