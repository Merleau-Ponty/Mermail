-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Sam 30 Avril 2016 à 18:04
-- Version du serveur :  5.6.17
-- Version de PHP :  5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `mermail`
--

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

CREATE TABLE IF NOT EXISTS `compte` (
  `id_compte` int(11) NOT NULL AUTO_INCREMENT,
  `titre` varchar(50) DEFAULT NULL,
  `users` varchar(50) DEFAULT NULL,
  `passwords` varchar(50) DEFAULT NULL,
  `serveur` varchar(50) DEFAULT NULL,
  `port` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_compte`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Contenu de la table `compte`
--

INSERT INTO `compte` (`id_compte`, `titre`, `users`, `passwords`, `serveur`, `port`) VALUES
(1, 'Mermail', 'mermail', 'P@ssw0rd', 'pop.lasposte.net', 110),
(2, 'test1', 'nomtest', 'password', 'laposte.net', 110),
(4, NULL, NULL, NULL, NULL, 110),
(5, NULL, NULL, NULL, NULL, 0),
(6, NULL, NULL, NULL, NULL, 0),
(8, 'titre', 'user', 'password', 'host', 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
