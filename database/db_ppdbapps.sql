-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jun 10, 2020 at 08:33 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.2.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_ppdbapps`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_akunppdb`
--

CREATE TABLE `tbl_akunppdb` (
  `no_un` varchar(14) NOT NULL,
  `name` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `birth_place` varchar(255) NOT NULL,
  `birth_date` varchar(255) NOT NULL,
  `address` varchar(1024) NOT NULL,
  `city` varchar(255) NOT NULL,
  `school_origin` varchar(255) NOT NULL,
  `religion` varchar(255) NOT NULL,
  `exam1` double NOT NULL,
  `exam2` double NOT NULL,
  `exam3` double NOT NULL,
  `exam4` double NOT NULL,
  `exambonus` double NOT NULL,
  `examtotal` double NOT NULL,
  `password` varchar(255) NOT NULL,
  `selection_count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_akunppdb`
--

INSERT INTO `tbl_akunppdb` (`no_un`, `name`, `gender`, `birth_place`, `birth_date`, `address`, `city`, `school_origin`, `religion`, `exam1`, `exam2`, `exam3`, `exam4`, `exambonus`, `examtotal`, `password`, `selection_count`) VALUES
('12345678901234', 'IVAN RIZKYA SUSANTO', 'Laki-laki', 'PEMALANG', '8 April 2004', 'bantarbolang', 'PEMALANG', 'SMP NEGERI 1 BANTARBOLANG', 'ISLAM', 80, 85, 87.5, 82.5, 0, 335, 'password', 0),
('12345678901235', 'ZAHRAN WASKITA GALIH', 'Laki-laki', 'PEMALANG', '7 Juli 1998', 'surajaya', 'PEMALANG', 'SMP NEGERI 3 PEMALANG', 'ISLAM', 78, 80, 75, 77.5, 0.5, 311, 'wordpass', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_pendaftaran`
--

CREATE TABLE `tbl_pendaftaran` (
  `no_pendaftaran` varchar(20) NOT NULL,
  `no_un_pendaftar` varchar(14) NOT NULL,
  `name_pendaftar` varchar(255) NOT NULL,
  `nilai_ppdb` double NOT NULL,
  `time_pendaftaran` varchar(255) NOT NULL,
  `sekolah_pendaftaran` varchar(255) NOT NULL,
  `jurusan_pendaftaran` varchar(255) NOT NULL,
  `is_accepted` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_pendaftaran`
--

INSERT INTO `tbl_pendaftaran` (`no_pendaftaran`, `no_un_pendaftar`, `name_pendaftar`, `nilai_ppdb`, `time_pendaftaran`, `sekolah_pendaftaran`, `jurusan_pendaftaran`, `is_accepted`) VALUES
('200510337', '12345678901235', 'ZAHRAN WASKITA GALIH', 311, 'Wed Jun 10 01:17:13 WIB 2020', 'SMK NEGERI 1 PETARUKAN', 'Teknik Mekanik Industri', 1),
('200510517', '12345678901234', 'IVAN RIZKYA SUSANTO', 335, 'Wed Jun 10 01:13:30 WIB 2020', 'SMK NEGERI 1 PETARUKAN', 'Teknik Mekanik Industri', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_akunppdb`
--
ALTER TABLE `tbl_akunppdb`
  ADD PRIMARY KEY (`no_un`);

--
-- Indexes for table `tbl_pendaftaran`
--
ALTER TABLE `tbl_pendaftaran`
  ADD PRIMARY KEY (`no_pendaftaran`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
