-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Время создания: Июн 10 2023 г., 00:17
-- Версия сервера: 5.7.29-log
-- Версия PHP: 7.2.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `mybus`
--

-- --------------------------------------------------------

--
-- Структура таблицы `bus`
--

CREATE TABLE `bus` (
  `id` int(11) NOT NULL,
  `info` varchar(255) CHARACTER SET utf8 NOT NULL,
  `count` int(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `bus`
--

INSERT INTO `bus` (`id`, `info`, `count`) VALUES
(1, 'Renault 8749 Blue Color', 18),
(2, 'Volkswagen 2345 White Color', 20);

-- --------------------------------------------------------

--
-- Структура таблицы `driver`
--

CREATE TABLE `driver` (
  `id` int(11) NOT NULL,
  `login` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `firstname` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `secondname` varchar(255) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `driver`
--

INSERT INTO `driver` (`id`, `login`, `password`, `firstname`, `secondname`) VALUES
(3, 'alexandr34', 'kukuruza221', 'Alexander', 'Mikulchik'),
(4, 'pro100misha', 'immiha38', 'Mikhail', 'Shurupov');

-- --------------------------------------------------------

--
-- Структура таблицы `route`
--

CREATE TABLE `route` (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `number` int(55) NOT NULL,
  `driver_id` int(55) NOT NULL,
  `bus_id` int(55) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `route`
--

INSERT INTO `route` (`id`, `name`, `number`, `driver_id`, `bus_id`) VALUES
(3, 'Smirnova-Lenina', 14, 3, 1),
(4, 'Agrotrans-Mitskevicha', 42, 4, 2);

-- --------------------------------------------------------

--
-- Структура таблицы `route_stops`
--

CREATE TABLE `route_stops` (
  `id` int(11) NOT NULL,
  `id_route` int(11) NOT NULL,
  `id_stops` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `route_stops`
--

INSERT INTO `route_stops` (`id`, `id_route`, `id_stops`) VALUES
(1, 3, 2),
(2, 3, 3),
(3, 3, 4),
(4, 3, 5),
(5, 3, 6),
(6, 3, 7),
(7, 3, 8),
(8, 4, 1),
(9, 4, 2),
(10, 4, 3),
(11, 4, 4),
(12, 4, 5),
(13, 4, 6),
(14, 4, 7),
(15, 4, 8),
(16, 4, 9);

-- --------------------------------------------------------

--
-- Структура таблицы `stops`
--

CREATE TABLE `stops` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `stops`
--

INSERT INTO `stops` (`id`, `name`) VALUES
(1, 'Agrotrans'),
(2, 'Smirnova'),
(3, 'Moscowskaya'),
(4, 'Adamovskaya'),
(5, 'Yjnaya'),
(6, 'Veselaya'),
(7, 'Bagdanchuka'),
(8, 'Lenina'),
(9, 'Mitskevicha'),
(10, 'Komsomolskaya'),
(11, 'Central-Hospital');

-- --------------------------------------------------------

--
-- Структура таблицы `trip`
--

CREATE TABLE `trip` (
  `id` int(11) NOT NULL,
  `id_route` int(50) NOT NULL,
  `starting_stop` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ending_stop` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_user` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `start_stop` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `end_stop` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `bus`
--
ALTER TABLE `bus`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `driver`
--
ALTER TABLE `driver`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `route`
--
ALTER TABLE `route`
  ADD PRIMARY KEY (`id`),
  ADD KEY `driver_id` (`driver_id`),
  ADD KEY `bus_id` (`bus_id`);

--
-- Индексы таблицы `route_stops`
--
ALTER TABLE `route_stops`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_route` (`id_route`),
  ADD KEY `id_stops` (`id_stops`);

--
-- Индексы таблицы `stops`
--
ALTER TABLE `stops`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `trip`
--
ALTER TABLE `trip`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `trip_ibfk_1` (`id_route`);

--
-- Индексы таблицы `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `bus`
--
ALTER TABLE `bus`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT для таблицы `driver`
--
ALTER TABLE `driver`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `route`
--
ALTER TABLE `route`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `route_stops`
--
ALTER TABLE `route_stops`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT для таблицы `stops`
--
ALTER TABLE `stops`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT для таблицы `trip`
--
ALTER TABLE `trip`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `route`
--
ALTER TABLE `route`
  ADD CONSTRAINT `route_ibfk_1` FOREIGN KEY (`driver_id`) REFERENCES `driver` (`id`),
  ADD CONSTRAINT `route_ibfk_2` FOREIGN KEY (`bus_id`) REFERENCES `bus` (`id`);

--
-- Ограничения внешнего ключа таблицы `route_stops`
--
ALTER TABLE `route_stops`
  ADD CONSTRAINT `route_stops_ibfk_1` FOREIGN KEY (`id_route`) REFERENCES `route` (`id`),
  ADD CONSTRAINT `route_stops_ibfk_2` FOREIGN KEY (`id_stops`) REFERENCES `stops` (`id`);

--
-- Ограничения внешнего ключа таблицы `trip`
--
ALTER TABLE `trip`
  ADD CONSTRAINT `trip_ibfk_1` FOREIGN KEY (`id_route`) REFERENCES `route` (`id`),
  ADD CONSTRAINT `trip_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
