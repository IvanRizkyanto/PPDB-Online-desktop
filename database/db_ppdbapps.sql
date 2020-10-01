-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 01, 2020 at 08:13 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.2.33

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
