-- --------------------------------------------------------
-- Hôte:                         127.0.0.1
-- Version du serveur:           8.0.30 - MySQL Community Server - GPL
-- SE du serveur:                Win64
-- HeidiSQL Version:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Listage de la structure de la base pour mddapi
CREATE DATABASE IF NOT EXISTS `mddapi` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mddapi`;

-- Listage de la structure de table mddapi. article
CREATE TABLE IF NOT EXISTS `article` (
  `id` bigint NOT NULL,
  `content` text NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `title` varchar(100) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `author_id` bigint NOT NULL,
  `theme_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmjgtny2i22jf4dqncmd436s0u` (`author_id`),
  KEY `FKgpufqukmfnet3mw61ylcn54xx` (`theme_id`),
  CONSTRAINT `FKgpufqukmfnet3mw61ylcn54xx` FOREIGN KEY (`theme_id`) REFERENCES `theme` (`id`),
  CONSTRAINT `FKmjgtny2i22jf4dqncmd436s0u` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table mddapi.article : ~8 rows (environ)
INSERT INTO `article` (`id`, `content`, `created_at`, `title`, `updated_at`, `author_id`, `theme_id`) VALUES
	(1, 'Cet article explore les nouvelles tendances technologiques et leur impact sur l\'avenir.', '2025-04-10 02:22:12.000000', 'L\'avenir de la technologie', NULL, 1, 1),
	(2, 'Cet article discute des candidats et de leurs programmes pour les élections présidentielles.', '2025-04-11 02:22:14.000000', 'Les élections présidentielles de 2024', NULL, 2, 2),
	(3, 'Une analyse des nouvelles méthodes pédagogiques et de leur efficacité dans l\'éducation moderne.', '2025-04-12 02:22:15.000000', 'Les nouvelles méthodes d\'enseignement', NULL, 3, 3),
	(4, 'Un guide des destinations de voyage les plus populaires et fascinantes pour l\'année 2024.', '2025-04-13 02:22:16.000000', 'Les meilleures destinations de voyage en 2024', NULL, 4, 4),
	(5, 'Cet article fournit des conseils pour maintenir une bonne santé mentale et gérer le stress au quotidien.', '2025-04-14 02:22:17.000000', 'Comment maintenir une bonne santé mentale', NULL, 5, 5),
	(6, 'Spring Boot simplifie le développement Java en permettant de créer des applications avec une configuration minimale.', '2025-04-16 21:33:43.119755', 'Introduction à Spring Boot', '2025-04-16 21:33:43.119755', 1, 2),
	(52, 'Spring Boot simplifie le développement Java en permettant de créer des applications avec une configuration minimale.', '2025-04-16 21:39:10.989935', 'Introduction à Spring Boot', '2025-04-16 21:39:10.989935', 1, 2),
	(53, 'Spring Boot simplifie le développement Java en permettant de créer des applications avec une configuration minimale.', '2025-04-16 21:39:30.408058', 'Introduction à Spring Boot', '2025-04-16 21:39:30.408058', 1, 2);

-- Listage de la structure de table mddapi. article_seq
CREATE TABLE IF NOT EXISTS `article_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table mddapi.article_seq : ~1 rows (environ)
INSERT INTO `article_seq` (`next_val`) VALUES
	(701);

-- Listage de la structure de table mddapi. commentaire
CREATE TABLE IF NOT EXISTS `commentaire` (
  `id` bigint NOT NULL,
  `comment` text NOT NULL,
  `creationDate` datetime(6) DEFAULT NULL,
  `article_id` bigint NOT NULL,
  `author_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4u7we2ohsfn4jbacq0a3uhjuc` (`article_id`),
  KEY `FKcebmmw331mbyql8lp89h5il2h` (`author_id`),
  CONSTRAINT `FK4u7we2ohsfn4jbacq0a3uhjuc` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`),
  CONSTRAINT `FKcebmmw331mbyql8lp89h5il2h` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table mddapi.commentaire : ~0 rows (environ)

-- Listage de la structure de table mddapi. commentaire_seq
CREATE TABLE IF NOT EXISTS `commentaire_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table mddapi.commentaire_seq : ~1 rows (environ)
INSERT INTO `commentaire_seq` (`next_val`) VALUES
	(651);

-- Listage de la structure de table mddapi. suivitheme
CREATE TABLE IF NOT EXISTS `suivitheme` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `theme` bigint NOT NULL,
  `user` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb6my4m4psp78s4p1y1rdcwli9` (`theme`),
  KEY `FK2ojyqgsv1x25hqfyua2tm14wc` (`user`),
  CONSTRAINT `FK2ojyqgsv1x25hqfyua2tm14wc` FOREIGN KEY (`user`) REFERENCES `users` (`id`),
  CONSTRAINT `FKb6my4m4psp78s4p1y1rdcwli9` FOREIGN KEY (`theme`) REFERENCES `theme` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table mddapi.suivitheme : ~0 rows (environ)

-- Listage de la structure de table mddapi. theme
CREATE TABLE IF NOT EXISTS `theme` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(255) NOT NULL,
  `isSubScribed` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table mddapi.theme : ~5 rows (environ)
INSERT INTO `theme` (`id`, `description`, `name`, `isSubScribed`) VALUES
	(1, 'Articles relatifs à la technologie et à l\'innovation, couvrant une vaste gamme de sujets allant des dernières avancées en intelligence artificielle, aux innovations en robotique, en passant par les tendances du numérique, les gadgets de demain, les startups disruptives et les transformations numériques des entreprises dans tous les secteurs d’activité.', 'Technologie', b'0'),
	(2, 'Articles sur les sujets politiques, les gouvernements et les événements mondiaux. Vous trouverez ici des analyses approfondies des politiques publiques, des mouvements sociaux, des enjeux géopolitiques, des élections et réformes, ainsi que des portraits de personnalités influentes qui façonnent notre monde d’aujourd’hui et de demain.\n\n', 'Politique', b'0'),
	(3, 'Thème dédié à l\'éducation, les formations et les innovations pédagogiques, explorant les méthodes d\'apprentissage modernes, l\'impact des technologies dans les salles de classe, les transformations des systèmes éducatifs à travers le monde, ainsi que les enjeux liés à l’accessibilité et à l’équité dans l’enseignement.\n', 'Éducation', b'0'),
	(4, 'Articles sur les destinations, les voyages et les cultures à travers le monde, mettant en lumière des récits d’aventures, des conseils pour les globe-trotteurs, des guides pratiques, des explorations de lieux méconnus, et des immersions culturelles riches en découvertes et en émotions.', 'Voyages', b'0'),
	(5, 'Articles liés à la santé, au bien-être et aux conseils pour une vie saine. Ce thème aborde des sujets variés tels que la nutrition, la santé mentale, les avancées médicales, les maladies chroniques, les bonnes pratiques pour rester en forme, et les approches holistiques pour un équilibre de vie durable.Articles liés à la santé, au bien-être et aux conseils pour une vie saine. Ce thème aborde des sujets variés tels que la nutrition, la santé mentale, les avancées médicales, les maladies chroniques, les bonnes pratiques pour rester en forme, et les approches holistiques pour un équilibre de vie durable.\n                                                                                                                                                                                                                                                                                                                \nArticles liés à la santé, au bien-être et aux conseils pour une vie saine. Ce thème aborde des sujets variés tels que la nutrition, la santé mentale, les avancées médicales, les maladies chroniques, les bonnes pratiques pour rester en forme, et les approches holistiques pour un équilibre de vie durable.\n\n', 'Santé', b'0');

-- Listage de la structure de table mddapi. users
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UKin1xa1v3pcuete8f8lxa47btl` (`full_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table mddapi.users : ~8 rows (environ)
INSERT INTO `users` (`id`, `created_at`, `email`, `full_name`, `password`, `updated_at`) VALUES
	(1, '2025-04-15 02:23:50.000000', 'alice@example.com', 'Alice_Dupont', '$2a$10$bwu3o4FnPG8r.W7FdXCtv.0ZDDMCDWN2ta9HaN93U74JdUGS6QA4.', NULL),
	(2, '2025-04-15 02:23:51.000000', 'bob@example.com', 'Bob_Martin', '$2a$10$bwu3o4FnPG8r.W7FdXCtv.0ZDDMCDWN2ta9HaN93U74JdUGS6QA4.', NULL),
	(3, '2025-04-15 02:23:51.000000', 'carol@example.com', 'Carol_Denis', '$2a$10$bwu3o4FnPG8r.W7FdXCtv.0ZDDMCDWN2ta9HaN93U74JdUGS6QA4.', NULL),
	(4, '2025-04-15 02:23:52.000000', 'david@example.com', 'David_Lemoine', '$2a$10$bwu3o4FnPG8r.W7FdXCtv.0ZDDMCDWN2ta9HaN93U74JdUGS6QA4.', NULL),
	(5, '2025-04-15 02:23:53.000000', 'eve@example.com', 'Eve_Caron', '$2a$10$bwu3o4FnPG8r.W7FdXCtv.0ZDDMCDWN2ta9HaN93U74JdUGS6QA4.', NULL),
	(6, '2025-04-15 00:25:41.321487', 'test@test.com', 'Jean_Dubois', '$2a$10$aYMozx4Gx5/ePynaD36BiOx1MtqYT6hPqPdR1GoHXXv9m7q2QEwbW', '2025-05-09 16:33:22.573605'),
	(54, '2025-04-17 16:41:10.706183', 'test10@test.com', 'toto_toto', '$2a$10$QnKZY8mxW1cakb3r8z8SP.9VMVZIjK2R9XvwKWO4eyuYJ/iH1KfIy', '2025-04-17 16:41:10.706183'),
	(102, '2025-04-20 00:45:31.082439', 'test11@test.com', 'hello', '$2a$10$zswNPW0gD/R4/jXQvtpUEuSufxIjdLFZUvXHo9qtHZY4KOBQO4yXa', '2025-04-20 00:45:31.082439');

-- Listage de la structure de table mddapi. users_seq
CREATE TABLE IF NOT EXISTS `users_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table mddapi.users_seq : ~1 rows (environ)
INSERT INTO `users_seq` (`next_val`) VALUES
	(201);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
