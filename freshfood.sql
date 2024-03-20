-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 12, 2023 lúc 08:49 AM
-- Phiên bản máy phục vụ: 10.4.24-MariaDB
-- Phiên bản PHP: 8.0.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `freshfood`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietdonhang`
--

CREATE TABLE `chitietdonhang` (
  `iddonhang` int(11) NOT NULL,
  `idsp` int(11) NOT NULL,
  `soluong` int(11) NOT NULL,
  `gia` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `chitietdonhang`
--

INSERT INTO `chitietdonhang` (`iddonhang`, `idsp`, `soluong`, `gia`) VALUES
(6, 18, 2, '70000'),
(6, 10, 1, '99000'),
(7, 18, 2, '70000'),
(7, 10, 1, '99000'),
(8, 18, 1, '35000'),
(8, 17, 1, '45000'),
(9, 18, 2, '70000'),
(10, 18, 1, '35000'),
(10, 17, 1, '45000'),
(11, 13, 1, '35000'),
(12, 17, 1, '45000'),
(13, 17, 1, '45000'),
(13, 1, 2, '118000'),
(13, 2, 1, '150000'),
(14, 10, 1, '99000'),
(14, 9, 1, '50000'),
(15, 18, 1, '35000'),
(16, 18, 1, '35000'),
(16, 3, 2, '135000'),
(16, 1, 1, '59000'),
(16, 10, 1, '99000'),
(17, 18, 1, '35000'),
(17, 3, 1, '135000'),
(18, 18, 2, '140000'),
(18, 1, 1, '59000'),
(19, 18, 4, '140000'),
(20, 18, 1, '35000'),
(21, 18, 1, '35000'),
(22, 18, 1, '35000'),
(23, 18, 3, '35000'),
(24, 17, 1, '45000'),
(25, 16, 2, '50000'),
(26, 18, 1, '35000'),
(27, 24, 1, '60000'),
(28, 18, 1, '35000'),
(29, 11, 1, '35000'),
(29, 8, 1, '25000'),
(30, 12, 1, '99000'),
(30, 5, 1, '50000'),
(31, 31, 1, '30000'),
(32, 45, 1, '160000'),
(33, 41, 1, '160000'),
(34, 41, 1, '160000'),
(35, 45, 1, '160000'),
(36, 45, 1, '160000'),
(37, 31, 1, '30000'),
(38, 45, 1, '160000'),
(39, 45, 1, '160000'),
(40, 45, 1, '160000'),
(41, 45, 1, '160000'),
(43, 45, 1, '160000'),
(44, 40, 1, '50000'),
(46, 40, 1, '50000'),
(47, 40, 1, '50000'),
(48, 45, 1, '160000'),
(49, 45, 1, '160000'),
(50, 41, 1, '160000'),
(50, 33, 1, '100000'),
(51, 41, 1, '160000'),
(52, 41, 2, '160000'),
(53, 45, 1, '160000'),
(54, 41, 1, '160000'),
(54, 40, 1, '50000'),
(56, 45, 1, '160000'),
(59, 45, 1, '160000'),
(61, 41, 1, '160000'),
(61, 31, 1, '30000'),
(62, 41, 1, '160000');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `donhang`
--

CREATE TABLE `donhang` (
  `id` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  `diachi` text COLLATE utf8_unicode_ci NOT NULL,
  `sodienthoai` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `soluong` int(11) NOT NULL,
  `tongtien` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `trangthai` int(2) NOT NULL DEFAULT 0,
  `momo` text COLLATE utf8_unicode_ci NOT NULL,
  `ngaydat` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `donhang`
--

INSERT INTO `donhang` (`id`, `iduser`, `diachi`, `sodienthoai`, `email`, `soluong`, `tongtien`, `trangthai`, `momo`, `ngaydat`) VALUES
(6, 2, 'tien du', '123456789', 'diep@gmail.com', 5, '150000', 0, '', '2023-12-05'),
(7, 2, 'tien du', '123456789', 'diep@gmail.com', 5, '150000', 0, '', '2023-12-05'),
(8, 21, 'Bac Ninh', '123456789', 'diep@gmail.com', 2, '80000', 0, '', '2023-12-05'),
(9, 21, 'Bac Ninh', '123456789', 'diep@gmail.com', 2, '140000', 0, '', '2023-12-05'),
(10, 21, 'Bac Ninh', '123456789', 'diep@gmail.com', 2, '80000', 2, '', '2023-12-05'),
(11, 21, 'Bac nInh', '123456789', 'diep@gmail.com', 1, '35000', 0, '', '2023-12-05'),
(12, 21, 'Bac Ninh', '123456789', 'diep@gmail.com', 1, '45000', 0, '', '2023-12-05'),
(13, 21, 'Tien Du', '123456789', 'diep@gmail.com', 4, '431000', 0, '', '2023-12-05'),
(14, 22, 'Bac Ninh', '123456789', 'diep1@gmail.com', 2, '149000', 0, '', '2023-12-05'),
(16, 24, 'Bac Ninh', '12345', 'diep123@gmail.com', 5, '463000', 0, '', '2023-12-05'),
(17, 24, 'Berlin', '12345', 'diep123@gmail.com', 2, '170000', 0, '', '2023-12-05'),
(18, 33, 'Berlin', '0962501346', 'shin23ra9@gmail.com', 3, '339000', 0, '', '2023-12-05'),
(20, 22, 'Bac ninh', '123456789', 'diep1@gmail.com', 1, '35000', 0, '', '2023-12-05'),
(21, 22, 'bac ninh', '123456789', 'diep1@gmail.com', 1, '35000', 0, '', '2023-12-05'),
(22, 22, 'bac ninh', '123456789', 'diep1@gmail.com', 1, '35000', 0, '', '2023-12-05'),
(23, 22, 'bac ninh', '123456789', 'diep1@gmail.com', 3, '105000', 0, '', '2023-12-05'),
(29, 36, 'Don hang moi', '12345678', 'diep1@gmail.com', 2, '60000', 1, '', '2023-12-05'),
(30, 36, 'Tien Du', '12345678', 'diep1@gmail.com', 2, '149000', 3, '', '2023-12-05'),
(31, 36, 'lac ve', '12345678', 'diep1@gmail.com', 1, '30000', 0, '', '2023-12-05'),
(32, 36, 'Tien du', '12345678', 'diep1@gmail.com', 1, '160000', 4, '', '2023-12-05'),
(33, 36, 'Tien Du', '12345678', 'diep1@gmail.com', 1, '160000', 3, '', '2023-12-05'),
(38, 36, 'bac ninh', '12345678', 'diep1@gmail.com', 1, '160000', 0, '', '2023-12-05'),
(49, 36, 'bac ninh', '12345678', 'diep1@gmail.com', 1, '160000', 0, '', '2023-12-05'),
(50, 36, 'Bac Ninh', '12345678', 'diep1@gmail.com', 2, '260000', 1, '', '2023-12-05'),
(51, 36, 'Tien Du', '12345678', 'diep1@gmail.com', 1, '160000', 1, '', '2023-12-05'),
(53, 36, 'Tien Du', '12345678', 'diep1@gmail.com', 1, '160000', 0, '', '2023-12-05'),
(54, 36, 'Bac Ninh', '12345678', 'diep1@gmail.com', 2, '210000', 3, '', '2023-12-05'),
(56, 36, 'bac ninh', '12345678', 'diep1@gmail.com', 1, '160000', 0, '', '2023-12-07'),
(59, 36, 'tien du', '12345678', 'diep1@gmail.com', 1, '160000', 2, '', '2023-12-07'),
(61, 36, 'Bac Ninh', '12345678', 'diep1@gmail.com', 2, '190000', 3, '', '2023-12-08'),
(62, 0, 'hhhji', '123456789', 'diep2@gmail.com', 1, '160000', 0, '', '2023-12-12');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanpham`
--

CREATE TABLE `sanpham` (
  `id` int(11) NOT NULL,
  `tensanpham` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `hinhanh` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `sanpham`
--

INSERT INTO `sanpham` (`id`, `tensanpham`, `hinhanh`) VALUES
(1, 'Trang Chủ', 'https://www.heartlandfoods.com/wp-content/uploads/2020/01/icon@500w.png'),
(2, 'Rau Củ', 'https://64.media.tumblr.com/e0a59fcf9ae79a26ef6d98c1f7c8f2c0/2680751f3d735a8d-25/s540x810/33b8d0c05c5c7d1d7135b17428e05777e22c68b9.jpg'),
(3, 'Thịt,Cá,Trứng', 'https://i.pinimg.com/originals/bf/f6/20/bff62066ade1a0957e54e512f32f220b.jpg'),
(4, 'Trái Cây', 'https://thumbs.dreamstime.com/b/big-pile-bright-healthy-fruits-isolated-white-background-big-pile-bright-healthy-fruits-isolated-white-240683222.jpg'),
(5, 'Đơn Hàng', 'https://cdn.iconscout.com/icon/premium/png-256-thumb/check-list-15-1093938.png'),
(6, 'Đơn ', 'https://cdn.iconscout.com/icon/premium/png-256-thumb/check-list-15-1093938.png');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanphammoi`
--

CREATE TABLE `sanphammoi` (
  `id` int(11) NOT NULL,
  `tensp` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `giasp` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `hinhanh` text COLLATE utf8_unicode_ci NOT NULL,
  `mota` text COLLATE utf8_unicode_ci NOT NULL,
  `loai` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `sanphammoi`
--

INSERT INTO `sanphammoi` (`id`, `tensp`, `giasp`, `hinhanh`, `mota`, `loai`) VALUES
(1, 'Cá bạc má sạch vỉ 300g (1 Vỉ)', '59000', 'https://thucphamsachgiatot.vn/image/cache/catalog/ca-bac-ma-700x700.png', 'Với công nghệ cấp đông siêu tốc IQF từ nhà máy đạt chuẩn xuất khẩu châu Âu, Cá Bạc Má đông lạnh G hoàn toàn không sử dụng chất bảo quản mà vẫn giữ được độ ngọt thịt, săn chắc như cá tươi. Cá Bạc Má G đã làm sạch sẵn, chỉ cần cho vào nồi hấp ăn kèm rau là có ngay món ngon. Ngoài ra, thịt cá đàn hồi tốt nên còn có thể chiên, kho,… cho món hấp dẫn khó cưỡng.', 2),
(2, 'Sầu Riêng Ri (Kg)', '150000', 'https://thucphamsachgiatot.vn/image/cache/catalog/X-SAU-RIENG-RI-6-VIETGAP-700x700.png', 'Sầu riêng Ri 6 hạt lép chín tự nhiên Cai Lậy – Tiền Giang. Được trồng trên vùng đất phù sa màu mỡ nên có hương vị thơm khác với các dòng sầu riêng khác. Cơm bùi, dẻo. Đạt chuẩn VietGap.\r\n\r\nSầu riêng Ri 6 là loại quả giàu giá trị dinh dưỡng, trong 100g cơm quả sầu riêng có 37% nước, 6% protit, 2% lipit, 16% gluxit và nhiều loại chất khoáng, vitamin.\r\n\r\n Sầu riêng Ri 6 có tác dụng giúp tăng cường và phục hồi sức khỏe cho người ốm yếu, có ích cho cơ bắp, duy trì sự chắc khỏe của xương, giúp duy trì và điều hòa hoạt động của tuyến giáp, bảo vệ sự khỏe mạnh cho răng và lợi.\r\n\r\nSầu riêng Ri 6 được trồng theo tiêu chuẩn , không chứa dư lượng hóa chất bảo vệ thực vật và kích thích tăng trưởng độc hại. Qua quá trình thu hoạch, vận chuyển và bảo quản cẩn thận, sản phẩm đảm bảo vẫn giữ được hương vị và độ tươi ngon khi tới tay người tiêu dùng.', 3),
(3, 'CẢI XOĂN KALE ĐÀ LẠT (350G)', '45000', 'https://thucphamsachgiatot.vn/image/cache/catalog/ro-cai-xoan-700x700.jpg', 'Cải kale là một loại cây thuộc họ thân thảo, sống lâu năm với thân cao từ 1 - 1,5 mét. Nó có vị hơi đắng và được xem là có họ hàng gần với các loại rau như bắp cải, súp lơ, cải bruxen hay rau xanh collard.\r\n\r\nKhông phải ngẫu nhiên mà loại rau xanh này lại được mệnh danh là loại thực phẩm giàu dinh dưỡng nhất hành tinh, là \"nữ hoàng rau xanh\" và được lòng nhiều chị em đến vậy. Tất cả đều nằm ở những giá trị dinh dưỡng mà nó mang lại cho người dùng.\r\n\r\nKale chứa các hoạt chất có tính chống oxy hóa cao, nhờ đó góp phần tăng cường sức khỏe và phòng ngừa các khối u ác tính hình thành.', 1),
(4, 'RAU MẦM CẢI NGỌT 200G', '20000', 'https://thucphamsachgiatot.vn/image/cache/catalog/V-RAU-MAM-CN-700x700.jpg', 'Được trồng từ những hạt giống rau mầm cải ngọt tuyển chọn, chuyên dùng cho sản xuất rau ăn mầm, Rau Mầm cải ngọt có giá trị dinh dưỡng cao.\r\n\r\nRau mầm cải ngọt là cây thân nhỏ, khi thu hoạch cao chừng 3-4cm, thường dùng săn sống hoặc nấu canh, trộn gỏi, hoặc dùng trang trí các món ăn. Rau mầm cải ngọt là cây có  vị ngọt, giòn, giàu vitamin và các chất dinh dưỡng thiết yếu. Không những thế rau mầm cải ngọt còn cung cấp nhiều chất xơ giúp quá trình bài tiết dễ dàng hơn.\r\n\r\nRau mầm cải ngọt rất dễ ăn, đảm bảo sẽ chinh phục vị giác của tất cả mọi người, bao gồm cả người già yếu và trẻ nhỏ. Tuy nhiên do mầm cải ngọt có nhiều chất xơ (cao gấp hơn 5 lần so với rau lớn) nên khi sử dụng lưu ý tăng dần số lượng rau ăn, ngày đầu chỉ nên ăn khoảng từ 100g/người/ngày (trẻ nhỏ thì khoảng từ 25g/bé/ngày). Không nên ăn quá 200g/người/ngày – lượng rau này tương đương với 1kg rau cải ngọt lớn/người/ngày.', 1),
(5, 'TRỨNG GÀ BA HUÂN (HỘP 10 QUẢ)', '50000', 'https://thucphamsachgiatot.vn/image/cache/catalog/trung-ga-hop-10q-700x700.jpg', 'Trứng gà là một loại thực phẩm có giá trị dinh dưỡng cao giàu protein, trong lòng đỏ trứng có chứa một hàm lượng phong phú lecithin và các vitamin A, D, B2. Protein của trứng gà là loại dinh dưỡng cần thiết cho cơ thể, tỷ lệ phù hợp nên dễ hấp thu, có thể hấp thu trên 94%.\r\nSản phẩm trứng gia cầm Ba Huân  được xử lý trên thiết bị hiện đại nhất hiện nay trên thế giới, trong đó có 2 công đoạn quan trọng là chiếu UV diệt khuẩn và làm se khít các lỗ thông khí, tránh sự xâm nhập của vi khuẩn và tạp chất vào bên trong trứng.', 2),
(6, 'Rau Húng Cây (Húng Đứng) 100g', '10000', 'https://thucphamsachgiatot.vn/image/cache/catalog/XRAU-HUNG-CAY-700x700.jpg', 'Tên sản phẩm: Rau Húng Cây 100G\r\nKhối lượng tịnh / Thể tích thực / Kích thước: 100G\r\nMàu sắc:\r\nXuất xứ: Việt Nam\r\nThành phần: 100% Rau húng cây tươi\r\nHướng dẫn sử dụng: Dùng để chế biến, trang trí các món ăn.\r\nHướng dẫn bảo quản: Nơi khô ráo, thoáng mát, bọc kín để trong ngăn mát tủ lạnh, nhiệt độ tốt nhất 10-15˚C.', 1),
(7, 'RAU MỒNG TƠI (500G)', '33000', 'https://thucphamsachgiatot.vn/image/cache/catalog/rau-mong-toi-700x700.jpg', 'Rau mồng tơi còn gọi là mùng tơi, lạc quỳ, có tên khoa học là Basella alba L, là loại dây leo, lá to, dày, giòn, màu xanh  và nhiều chất nhầy. Quả nhỏ khi chín có nước với màu tím than. Theo Đông y, mồng tơi có vị chua ngọt, không độc, tính lạnh có tác dụng giải độc, thanh nhiệt, nhuận tràng… Ăn mồng tơi chữa táo bón, đái dắt, kiết lỵ, tốt cho người tiểu đường, thải chất béo nên rất tốt cho người có mỡ và đường máu cao.', 1),
(8, 'CÀ RỐT AN TOÀN (500G)', '25000', 'https://thucphamsachgiatot.vn/image/cache/catalog/ca-rot-da-lat-700x700.jpg', 'Cây cà rốt không chỉ là loại rau quen thuộc mà còn là cây thuốc quý. Cà rốt là loại cây thảo sống 2 năm. Lá cắt thành bản hẹp. Hoa tập hợp thành tán kép; trong mỗi tán, hoa ở chính giữa thì không sinh sản và màu tía, còn các hoa sinh sản ở chung quanh thì màu trắng hay hồng. Hạt Cà rốt có vỏ gỗ và lớp lông cứng che phủ.\r\n\r\nCà rốt là một trong những loại rau trồng rộng rãi nhất và lâu đời nhất trên thế giới. Người Lã Mã gọi Cà rốt là nữ hoàng của các loại rau. Cà rốt cũng được trồng nhiều ở nước ta. Hiện nay, các vùng rau của ta đang trồng phổ biến hai loại Cà rốt: một loại có củ màu đỏ tươi, một loại có củ màu đỏ ngả sang màu da cam', 1),
(9, 'Phi Lê Cá Basa Đóng Vỉ (300g)', '50000', 'https://thucphamsachgiatot.vn/image/cache/catalog/X-PHI-LE-CA-BASA-700x700.jpg', 'Tên sản phẩm: Phi Lê Cá Basa Đóng Vỉ (Kg)\r\nKhối lượng tịnh/ Thể tích thực/ Kích thước: Kg\r\nMàu sắc:\r\nXuất xứ: Việt Nam\r\nThành phần: 100% fillet cá basa\r\nHướng dẫn sử dụng: Chế biến món ăn tùy thích: chiên, nướng, hấp, kho, nấu canh hoặc nấu lẩu,...\r\nHướng dẫn bảo quản: Bảo quản ở nhiệt độ 0-5 độ C\r\nHạn sử dụng: 3 ngày kể từ NSX', 2),
(10, 'THỊT BA RỌI HEO (500G)', '99000', 'https://thucphamsachgiatot.vn/image/cache/catalog/thit-ba-roi-700x700.png', 'Thịt ba rọi hay còn gọi là thịt ba chỉ có lớp thịt và lớp mỡ xen kẽ đẹp mắt. Với hương vị thịt béo hài hòa đặc trưng, thịt ba rọi đặc bệt được ưa chuộng để chế biến nhiều món ăn khác nhau như luộc, chiên, nướng...đều phù hợp\r\n\r\nBạn muốn có những miếng thịt ba rọi vừa ngon vừa sạch, hãy chọn G!', 2),
(11, 'Thanh Long Ruột Đỏ (Kg)', '35000', 'https://thucphamsachgiatot.vn/image/cache/catalog/XTHANH-LONG-DO-700x700.jpg', 'Thanh long đỏ là một loại trái cây có tác dụng sức khỏe tuyệt vời. Với chức năng độc đáo, ít sâu bệnh, cây có thể phát triển bình thường mà không cần sử dụng bất kỳ loại thuốc bảo vệ thực vật nào. Vì vậy, thanh long đỏ là loại quả xanh, thân thiện với môi trường và là thực phẩm tốt cho sức khỏe, có tác dụng chữa bệnh nhất định.\r\n\r\nMỗi 100g thanh long chứa 2,83g fructose và 7,83g glucose. Ngoài vitamin C, nó còn chứa 23,3mg  malic, 2,8mg  shikimic, 20mg  oxalic,  succinic,  fumaric,… Do chứa một lượng lớn  hữu cơ nên độ pH trong thanh long đỏ đạt 5,8 ~ 6,4.\r\n\r\nThịt của quả thanh long đỏ giàu chất dinh dưỡng, giá trị chữa bệnh cao, có tác dụng hạ huyết áp, hạ lipid máu, giải độc , dưỡng phổi, dưỡng da, cải thiện thị lực, đồng thời cũng có tác dụng nhất định đối với bệnh táo bón, tiểu đường. Ngoài ra, thanh long đỏ còn tác dụng phòng chống nhiễm độc kim loại nặng và tăng cường sức đề kháng, chống oxy hóa, chống lão hóa và các tác dụng khác.', 3),
(12, 'Tủy Bò Tươi (300G)', '99000', 'https://thucphamsachgiatot.vn/image/cache/catalog/xtuy-bo.jpg-700x700.jpg', 'Tủy là bộ phận quan trọng của động vật, chạy dọc bên trong xương sống, chứa các dây thần kinh tạo liên hệ từ não đến toàn bộ cơ thể. Tủy có màu hồng nhạt, trải qua quá trình chế biến thì có màu trắng đục, mềm, dai , ăn béo . Không những thế tủy bò có giá trị dinh dưỡng vô cùng cao, cực tốt cho sức khỏe.\r\n\r\nTủy bò tuy không phổ biến trong các bữa cơm của gia đình người Việt nhưng lại thường xuyên có mặt trên bàn ăn của những người sành ăn.\r\n\r\nTủy bò có tác dụng bổ thận, lợi tủy tăng cường trí thông minh, thích hợp cho trẻ em không đủ khiếu bẩm sinh, dậy thì muộn, vóc dáng thấp bé…', 2),
(13, 'ỚT CHUÔNG ĐÀ LẠT (300G)', '35000', 'https://thucphamsachgiatot.vn/image/catalog/ot-chuong-vang.webp', 'Ớt chuông vàng (hay còn gọi là ớt ngọt, ớt Đà Lạt) được mệnh danh là “siêu thực phẩm” với nhiều lợi ích tuyệt vời đối với sức khỏe. Chúng có lượng calo thấp, đặc biệt giàu vitamin C và các chất chống oxy hóa khác.\r\n\r\nỚt Đà Lạt có nhiều màu sắc khác nhau như đỏ, vàng, cam và xanh lục (ớt chưa chín có vị hơi đắng và không ngọt như những quả chín hoàn toàn).', 1),
(14, 'RAU MẦM CẢI ĐỎ 200G', '30000', 'https://thucphamsachgiatot.vn/image/catalog/V-RAU-MAM-CAI-DO.webp', 'Rau Mầm Cải Đỏ có thân to, màu hồng rất đẹp. Ăn giòn, cay nồng.\r\nRau Mầm Cải Đỏ giàu carotene, vitamin B2, vitamin E và nhiều chất dinh dưỡng rất tốt cho sức khỏe, đặc biệt trong việc phòng ngừa lão hóa tế bào và làm đẹp cho phụ nữ. Rau Mầm Cải Đỏ ngoài giàu hàm lượng vitamin C, sắt và can xi rất tốt cho cơ thể thì vị cay ngọt nhẹ của loại rau mầm từ hạt cải có tác dụng kỳ diệu trong việc kích thích vị giác, tăng cường tiêu hóa và giảm mệt mỏi.', 1),
(15, 'SUSU AN TOÀN VIETGAP (300G)', '15000', 'https://thucphamsachgiatot.vn/image/cache/catalog/qua-su-su-700x700.jpg', 'Susu là món ăn quen thuộc, loại quả này ăn rất mát, lành tính. Cây leo sống dai có rễ phình thành củ. Lá to, bóng, hình chân vịt, có 5 thu , tua cuốn chia 3-5 nhánh. Hoa nhỏ, đơn tính, cùng gốc, màu trắng vàng. Quả thịt hình quả lê có cạnh lồi dọc và sần sùi, to bằng nắm tay, chứa một hạt lớn hình cầu.', 1),
(16, 'KHOAI TÂY VÀNG ĐÀ LẠT (500G)', '25000', 'https://thucphamsachgiatot.vn/image/cache/catalog/V-KHOAI-TAY-VANG-700x700.jpg', 'Khoai tây vàng Đà Lạt được trồng theo phương pháp hữu cơ, không chứa các hóa chất bảo quản độc hại, đảm bảo giữ nguyên các giá trị dinh dưỡng vốn có và đảm bảo không ảnh hưởng đến sức khỏe người tiêu dùng. Sản phẩm có vị ngọt, bở bùi, bạn có thể dùng chế biến nhiều món ăn thơm ngon: sườn om khoai tây, thịt gà kho khoai tây, mứt khoai tây… tốt cho sức khỏe.', 1),
(17, 'SÚP LƠ VIETGAP (500G)', '45000', 'https://thucphamsachgiatot.vn/image/catalog/bong-cai-trang.webp', 'Bông cải trắng là thành phần của họ cải bao gồm bông cải xanh, cải bắp, cải xoăn, cải Brussel... Tuy nhiên, bông cải trắng cũng là một nguồn dinh dưỡng quý giá trên thế giới. Giàu dưỡng chất thực vật, giàu chất chống viêm, chống ung thư, tốt cho bệnh tim mạch, ngăn ngừa bệnh ở não và thậm chí là giúp tăng cân, dường như là quá nhiều cho một loại thực vật', 1),
(18, 'Bí Đỏ Tròn Cắt Miếng (950g)', '35000', 'https://thucphamsachgiatot.vn/image/cache/catalog/X-BI-DO-TRON-700x700.jpg', 'Bí đỏ có ruột màu vàng cam, vị ngọt béo, dẻo, thơm ngon và dễ chế biến thành nhiều món ăn hấp dẫn như: luộc, hấp, xào, nấu canh,...\r\nSử dụng bí đỏ trong khẩu phần ăn hàng ngày giúp cung cấp vitamin A, C, acid folic, magnesium, kali, chất đạm, acid glutamic rất cần thiết cho hoạt động của não bộ…\r\nSản phẩm có nguồn gốc xuất xứ rõ ràng, đảm bảo vệ sinh an toàn chất lượng, được Emart chọn lọc kỹ lưỡng giúp mang đến cho khách hàng sự an tâm trong việc lựa chọn và tin dùng.', 1),
(22, 'Chuối Dole', '25000', 'https://thucphamsachgiatot.vn/image/cache/catalog/V-chuoi-dole-700x700.jpg', 'Chuối DOLE, thương hiệu chuối nổi tiếng thế giới được người tiêu dùng trong và ngoài nước tin dùng.\r\n\r\nChuối Dole là một giống chuối ngoại nhập, có mùi thơm và có vị ngọt đặc trưng. Đây là loại quả cung cấp kali và axit folic tuyệt vời cho sức khỏe. Bên cạnh đó, chuối còn giàu vitamin và khoáng chất tốt cho não bộ, hỗ trợ trí nhớ, bổ sung dưỡng chất, hỗ trợ hệ tiêu hóa và có tác dụng trong việc làm đẹp hiệu quả.', 3),
(24, 'Lê Nam Phi (500g)', '60000', 'https://thucphamsachgiatot.vn/image/cache/catalog/V-LE-NAM-PHI-700x700.jpg', 'Lê Nam Phi là một trong số ít các loại trái cây nhập khẩu  vượt qua nhiều tiêu chuẩn khắt khe để xuất khẩu ra toàn thế giới. Với vẻ ngoài lạ, bắt mắt cùng hương vị thơm ngon, lê Nam Phi sẽ là thứ quả không thể thiếu trong list trái cây của gia đình bạn mỗi khi đến mùa.\r\n\r\nLê Nam Phi có nguồn gốc từ Châu Âu, được xem là một trong những loại trái cây nhập khẩu có từ thời xa xưa. Hiện nay tại Nam Phi lê được trồng nhiều ở vùng Western Cape, tỉnh nằm phía Tây Nam. Nơi đây có khí hậu đa dạng, tuy nhiên đa phần là mát mẻ, khô và ấm áp mùa hè nên thích hợp trồng loại trái cây này. Mùa vụ lê Nam Phi bắt đầu từ tháng 2 đến tháng 8 hàng năm.\r\n\r\nQuả lê có hình chuông nhỏ, một đầu to và đầu nhỏ, nhưng tròn và thon đều. Khi chín, vỏ lê chuyển từ màu xanh đen sang màu vàng đỏ rực. Khi bổ quả lê ra mới thực sự hấp dẫn: Thịt trắng ngần cùng hương vị thơm mát, giòn và rất mọng nước mà không một loại lên nào sánh được.', 3),
(29, 'test', '2222', '42.jpg', 'teet', 3),
(30, 'Nho xanh không hạt', '150000', '31.jpg', 'Nho xanh không hạt Mỹ là loại hoa quả nhập khẩu rất giàu dinh dưỡng, có tác dụng bổ tì, ích khí, giúp cơ thể khỏe mạnh, miễn dịch tốt và làm chậm quá trình lão hóa\r\nCanxi, kali, photpho, sắt, vitamin B1, B2, B6, C và các loại axit amin cần thiết tốt cho người suy nhược thần kinh và có lợi cho tiêu hóa\r\nHợp chất Resveratrol trong nho xanh không hạt mỹ đặc biệt là phần vỏ giúp giải độc tốt, giảm máu nhiễm mỡ, chống tụ huyết, phòng chống xơ vỡ động mạch và tăng cường hệ thống miễn dịch của cơ thể.', 3),
(31, 'Dưa hấu ngọt (1Kg)', '30000', '32.jpg', 'Sở dĩ dưa hấu Long An nổi tiếng như thế là do, quả dưa hấu ở đây được trồng theo tiêu chuẩn quốc tế về độ sạch và vệ sinh an toàn thực phẩm. Ăn dưa hấu Long An ngoài việc đảm bảo được sức khỏe, người dùng còn thưởng thức được độ ngon của dưa hấu một cách trọn vẹn, vì dưa hấu Long An là giống dưa không hạt, vỏ mỏng, đặc ruột, hạt li ti mịn, thơm lừng, ngọt thanh, ít xốp, mọng nước nhưng cũng rất giòn. Một quả dưa hấu Long An đạt trọng lượng nhẹ nhất là 2 kg. Với trọng lượng này, một quả dưa hấu cũng đã đủ trở thành món ngon tráng miệng cho một gia đình.\r\n\r\nKhông chỉ trở thành đặc sản nổi tiếng, dưa hấu Long An còn góp phần giúp người nông dân nơi đây kiếm thêm thu nhập trái mùa, thậm chí làm giàu từ loại quả thanh mát này. Được biết, nông dân Long An đã chọn cách trồng dưa hấu thay cho cả vụ lúa Hè thu. Thông thường, vụ lúa Đông xuân cho năng suất rất cao, nhưng đến vụ Hè thu thường hay rớt giá, không trúng mùa. Nên dưa hấu đã trở thành “cứu tinh” cho bà con. Dù nặng công chăm sóc nhưng lợi nhuận từ trái dưa hấu mang lại cao gấp 5 lần cây lúa. Những nhà trồng dưa hấu giỏi, diện tích lớn có thể mang về cho mình lợi nhuận trên 40 triệu đồng một mùa là chuyện bình thường.\r\n\r\nSong, không chạy theo lợi nhuận mà làm mất giá trị dưa hấu Long An, người nông dân Long An luôn tuân thủ theo quy tắc trồng dưa hấu chuẩn, gieo trồng trong môi trường sạch, cung cấp chất dinh dưỡng an toàn cho đất đai, quy trình thu hoạch đảm bảo chất lượng để tăng thời gian bảo quản. Từ đó, quả dưa hấu Long An luôn giữ nguyên được độ ngon ngọt, an toàn và ngày càng nhân rộng vị thế của loại trái cây đặc sản này trên thị trường trong và ngoài nước.', 3),
(33, 'Thịt Heo xay (500g)', '100000', 'https://thucphamsachgiatot.vn/image/cache/catalog/thit-xay-700x700.jpg', 'Thịt heo xay (gọi tắt là thịt xay) là một trong những thực phẩm được nhiều bà nội trợ yêu thích chế biến trong các bữa ăn nhờ giá thành rẻ, dễ dàng chế biến đa dạng các món ăn ngon mà không tốn quá nhiều thời gian.\r\n\r\nThịt heo xay đảm bảo an toàn vệ sinh không chỉ đòi hỏi thịt phải sạch và ngon, mà các dụng cụ sơ chế, cối xay cũng phải cực kỳ sạch mới có thể tạo ra được thịt xay chất lượng.', 2),
(40, 'Phi Lê Ức Gà (500g)', '50000', '40.jpg', 'Thịt ức gà Leboucher được chế biến từ nguồn gà sạch, được nuôi trong hệ thống trang trại đạt tiêu chuẩn khắt khe của Pháp. Quá trình sơ chế và bảo quản được thực hiện trong quy trình khép kín, đảm bảo vệ sinh an toàn thực phẩm. Thịt gà rất giàu vitamin A, B1, B2, C, E, axit, canxi, photpho, sắt mà cơ thể con người dễ hấp thu và tiêu hóa. Do đó, ăn thịt gà giúp giảm nguy cơ mắc ung thư ruột, giúp kéo dài tuổi thọ, tốt cho tim, chống trầm cảm, hỗ trợ răng và xương, thúc đẩy sức khỏe cho mắt.', 2),
(41, 'Thịt Bò VietGap (500G)', '160000', '41.jpg', 'Thịt bò phi lê hay thịt thăn vai hay lóc thăn vai là những miếng thịt được xẻ từ phần lưng bò. Là phần thịt được cắt nằm ở phần nạc vai bò. Toàn bộ xương và sụn cũng như phần gân ngoài đã được loại bỏ. Đây là nơi những miếng thịt ngon nhất, gồm thịt thăn phi lê có hình chữ T hay là những miếng thịt Porterhouse steak hoặc cũng như thịt thăn viền mỡ.\r\nThịt phile thường mềm nhất và ngọt thịt, thịt thường có rất ít, cứ 1 tạ thịt bò thì có khoản 3kg thịt phile, đầu vai cuộn có nhiều gân và mỡ, rất thích hợp cho những người thích ăn giòn', 2),
(45, 'Quả Bòn Bon (1Kg)', '160000', '45.jpg', 'Bòn bon thái lan hay còn gọi dâu da đất, chắc chắn sẽ không mấy ai còn xa lạ. Đây là loại quả nhiệt đới, họ Xoan. Quả bòn bon tròn hoặc hơi thuôn, khi chín cho vỏ màu vàng tươi và ruột màu trắng đục. Bòn bon có vị ngọt hơi chua. Thịt quả chia làm nhiều múi (từ 5 – 6 múi), mỗi múi có 1 hạt. Đây là loại quả chứa nhiều nước và vitamin, khoáng nên rất được các chị em ưa chuộng.', 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `pass` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `mobile` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `uid` text COLLATE utf8_unicode_ci NOT NULL,
  `token` text COLLATE utf8_unicode_ci NOT NULL,
  `status` int(2) NOT NULL DEFAULT 0,
  `imageprofile` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`id`, `email`, `pass`, `username`, `mobile`, `uid`, `token`, `status`, `imageprofile`) VALUES
(21, 'diep@gmail.com', '1234', 'diep', '123456789', '', '', 0, ''),
(27, 'diep0@gmail.com', '123', 'Dang', '123', '', '', 0, ''),
(35, 'shin23ra9@gmail.com', '123456789', 'Ngoc Diep', '0962501346', '8zybZ8Ip0DRfpAGJvB4puktRTC02', 'eN64vcIUT5GLYjnnemK1jI:APA91bEqf0VE7Nxz9DSnI7HwltGhwmhldmq-NiUCYw04li-Z0WDccT9tKzL7_Jh90OKTqPD6B54xxDnOSKgC7lSJKun18RskW10Oas4RI-mdISuqEyw9spshppLT-NUHiUxS8Qw-cRnL', 1, ''),
(36, 'diep1@gmail.com', '123456', 'Ngoc', '12345678', 'LpltndxJDKOFJ8VAk1pMqYtpvef1', 'eXS_AbVISaGamU6vFQr_6S:APA91bHkYtCqQMQHCRTujuog2fwPnNa1Jk5dFV9emfuS104DWHhlA3N9ucFEAZ1sdDeCqCUd9lHNNK-TnJrSHoDP1ZacGXLeS4p-bZZcPNKAk7riBbAOmsMUam3Fpx62YaYFWLU0yk_o', 0, ''),
(38, 'diep2@gmail.com', 'onfibase', 'Ngoc Diep jjhfgg', '123456789', 'FHGxLbr34mQoy8wtiOz1gmI51dE3', 'cv0bACPZRq-zqgHPD9mhSY:APA91bEfm6PUYyAA_xcurYWi43tbK4vZAOUcBvRJsvSd-BOxoh1Xj7bpZyPVX3rAzTE1_8LHxdEXR01poBzrHmzbfJ_wzqC3pqnkhulsIVAqNDQfpTyXi7V4P9hlIdGb_XT63JAmKhlX', 0, '38.jpg'),
(39, 'mrdiep15052002@gmail.com', 'onfibase', 'Ngoc Diep', '1234567', '4IVaATsdX7gT8GlhFytZDc6hRXx2', 'c5hzdJR7TpW2vmXtUsHsys:APA91bFWg0s8eiq0pX7mZYcju-zgQGln7Otx_NvBrW_hMtbGctG0zaegGgcDaQ3BcgawI4S6-j07P2otmFjxxzhmmqmP_DxJAYbb_FzMahW4bLfP1UOAXONeAu0Tyo3w8HtZ8NITikDR', 0, '');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `donhang`
--
ALTER TABLE `donhang`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `sanphammoi`
--
ALTER TABLE `sanphammoi`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `donhang`
--
ALTER TABLE `donhang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;

--
-- AUTO_INCREMENT cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `sanphammoi`
--
ALTER TABLE `sanphammoi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
