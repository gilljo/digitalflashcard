-- phpMyAdmin SQL Dump
-- version 3.4.10.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 08, 2012 at 01:07 PM
-- Server version: 5.1.63
-- PHP Version: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `angelabi_FLASHCARD`
--

-- --------------------------------------------------------

--
-- Table structure for table `Categories`
--

DROP TABLE IF EXISTS `Categories`;
CREATE TABLE IF NOT EXISTS `Categories` (
  `C_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` text CHARACTER SET hp8 NOT NULL,
  `DESCRIPTION` text CHARACTER SET hp8,
  PRIMARY KEY (`C_ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `Categories`
--

INSERT INTO `Categories` (`C_ID`, `NAME`, `DESCRIPTION`) VALUES
(2, 'Physics', 'This is a test.'),
(3, 'Math', 'Test Category'),
(4, 'English', 'Test Category');

-- --------------------------------------------------------

--
-- Table structure for table `Modules`
--

DROP TABLE IF EXISTS `Modules`;
CREATE TABLE IF NOT EXISTS `Modules` (
  `M_ID` int(11) NOT NULL AUTO_INCREMENT,
  `C_ID` int(11) NOT NULL,
  `NAME` text NOT NULL,
  `PUBLIC` int(11) NOT NULL,
  `CREATED_BY` text CHARACTER SET hp8 NOT NULL,
  `DESCRIPTION` text CHARACTER SET hp8,
  `NUM_CARDS` int(11) NOT NULL,
  `DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`M_ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `Modules`
--

INSERT INTO `Modules` (`M_ID`, `C_ID`, `NAME`, `PUBLIC`, `CREATED_BY`, `DESCRIPTION`, `NUM_CARDS`, `DATE`) VALUES
(1, 2, 'Super Physics', 1, 'Angela', 'Test Module', 1, '2012-03-17 20:12:31'),
(2, 3, 'Addition', 1, 'Angela', 'basic addition', 10, '2012-04-25 17:58:58'),
(3, 4, 'Test For Long Questions and Module Names', 1, 'Angela', 'Test Module', 3, '2012-05-09 19:19:30');

-- --------------------------------------------------------

--
-- Table structure for table `Questions`
--

DROP TABLE IF EXISTS `Questions`;
CREATE TABLE IF NOT EXISTS `Questions` (
  `Q_ID` int(11) NOT NULL AUTO_INCREMENT,
  `M_ID` int(11) NOT NULL,
  `QUE` text NOT NULL,
  `ANS` text NOT NULL,
  PRIMARY KEY (`Q_ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

--
-- Dumping data for table `Questions`
--

INSERT INTO `Questions` (`Q_ID`, `M_ID`, `QUE`, `ANS`) VALUES
(1, 1, 'What is momentum?', 'A measure of the motion of a body equal to the product of its mass and velocity'),
(3, 2, '2 + 4', '6'),
(4, 2, '3 + 6', '9'),
(5, 2, '8 + 2', '10'),
(6, 2, '5 + 5', '10'),
(7, 2, '3 + 1 + 4', '8'),
(8, 2, '9 + 4', '13'),
(9, 2, '6 + 5', '11'),
(10, 2, '8 + 7', '15'),
(11, 2, '9 + 10', '19'),
(12, 2, '6 + 7', '13'),
(13, 3, 'What is the definition of grammar?', 'The rules for speaking or writing a particular language, or an analysis of the rules of a particular aspect of language.'),
(14, 3, 'Give a full definition of an onomatopoeia.', 'imitation of sound in words: the formation or use of words that imitate the sound associated with something, e.g. "hiss" and "buzz"'),
(15, 3, 'This is a really long question. This is a really long question. This is a really long question. This is a really long question. This is a really long question. This is a really long question. This is a really long question. This is a really long question. This is a really long question. This is a really long question. This is a really long question.  This is a really long question.  This is a really long question.  ', 'This is a really long answer. This is a really long answer. This is a really long answer. This is a really long answer. This is a really long answer. This is a really long answer. This is a really long answer. This is a really long answer. This is a really long answer. This is a really long answer. This is a really long answer. This is a really long answer. This is a really long answer. This is a really long answer. This is a really long answer. This is a really long answer. This is a really long answer. ');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
