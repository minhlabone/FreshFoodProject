<?php
include "connect.php";
$id = $_POST['id'];
//check data
$query = 'DELETE FROM `sanphammoi` WHERE `id` ='.$id;
$data = mysqli_query($conn, $query);

         if($data == true){
            $arr = [
                'success' => true,
                'message' => "Thành Công"
          ];
        }else{
            $arr = [
                'success' => false,
                'message' => "Xóa Không Thành Công"
    ];
 }


print_r(json_encode($arr));

?>