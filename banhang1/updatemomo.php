<?php
include "connect.php";
$token = $_POST['token'];
$iddonhang = $_POST['id'];
$query = 'UPDATE `donhang` SET `momo`="'.$token.'" WHERE `id` ='.$iddonhang;
$data = mysqli_query($conn, $query);

         if($data == true){
            $arr = [
                'success' => true,
                'message' => "Thành Công"
          ];
        }else{
            $arr = [
                'success' => false,
                'message' => "Không Thành Công"
    ];
 }
print_r(json_encode($arr));

?>