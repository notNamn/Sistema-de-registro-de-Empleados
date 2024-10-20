-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 06-08-2024 a las 21:37:16
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `login_system`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `employee`
--

CREATE TABLE `employee` (
  `idEmployee` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `numberPhone` varchar(15) NOT NULL,
  `gender` varchar(20) NOT NULL,
  `position` varchar(100) NOT NULL,
  `date` date NOT NULL,
  `salary` double(10,2) NOT NULL,
  `image` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `employee`
--

INSERT INTO `employee` (`idEmployee`, `name`, `numberPhone`, `gender`, `position`, `date`, `salary`, `image`) VALUES
(1, 'Juanes', '123456789', 'female', 'jefe', '2024-08-14', 23.10, 'file:/C:/Users/HP/Pictures/imagen19.jpg'),
(2, 'Maria', '12345', 'Male', 'Developer', '2024-08-12', 0.00, 'file:/C:/Users/HP/Pictures/PROYECTO1/1.jpg'),
(3, 'fefe', '122334', 'Male', 'Developer', '2024-08-05', 0.00, 'file:/C:/Users/HP/Pictures/PROYECTO1/2.jpeg'),
(4, 'fefe', '122334', 'Male', 'Developer', '2024-08-05', 0.00, 'file:/C:/Users/HP/Pictures/PROYECTO1/2.jpeg'),
(5, 'fefe', '122334', 'Male', 'Developer', '2024-08-05', 0.00, 'file:/C:/Users/HP/Pictures/PROYECTO1/2.jpeg'),
(6, 'fifi', '122334', 'Male', 'Developer', '2024-08-05', 0.00, 'file:/C:/Users/HP/Pictures/PROYECTO1/2.jpeg'),
(7, 'fefe', '122334', 'Male', 'Developer', '2024-08-05', 23.60, 'file:/C:/Users/HP/Pictures/PROYECTO1/2.jpeg'),
(10, 'fefe', '122334', 'Male', 'Developer', '2024-08-05', 0.00, 'file:/C:/Users/HP/Pictures/PROYECTO1/2.jpeg'),
(12, 'def', '21123', 'Male', 'Manager', '2024-08-05', 0.00, 'file:/C:/Users/HP/Pictures/Fondo/fondo1.jpg'),
(13, 'afef', '1332', 'Female', 'Developer', '2024-08-05', 0.00, 'file:/C:/Users/HP/Pictures/PROYECTO1/7.jpg'),
(14, 'antes', '1332', 'Male', 'Manager', '2024-08-05', 45.50, 'file:/C:/Users/HP/Pictures/PROYECTO1/7.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `idUser` int(11) NOT NULL,
  `nameUser` varchar(100) NOT NULL,
  `passUser` varchar(100) NOT NULL,
  `emailUser` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`idUser`, `nameUser`, `passUser`, `emailUser`) VALUES
(1, 'admin', 'admin', 'admin@gmail.com');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`idEmployee`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`idUser`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `employee`
--
ALTER TABLE `employee`
  MODIFY `idEmployee` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `idUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
