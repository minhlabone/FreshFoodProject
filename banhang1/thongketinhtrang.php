<?php
include "connect.php";
$query = "SELECT `trangthai`, COUNT(*) AS soLuong, MONTH(`ngaydat`) AS thang FROM `donhang` WHERE `trangthai` IN (3, 4) GROUP BY YEAR(`ngaydat`), MONTH(`ngaydat`), `trangthai`";
$data = mysqli_query($conn, $query);
$result = array();
while($row = mysqli_fetch_assoc($data)){
	$result[] = ($row);
}
 if(!empty($result)){
    $arr = [
      'success' => true,
      'message' => "thanh cong",
      'result'  => $result
          ];
 }else{
    $arr = [
      'success' => false,
      'message' => "khong thanh cong",
      'result'  => $result
    ];
 }
print_r(json_encode($arr));

?>