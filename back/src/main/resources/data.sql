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

-- Listage des données de la table mddapi.article : ~5 rows (environ)
INSERT INTO `article` (`id`, `content`, `created_at`, `title`, `updated_at`, `author_id`, `theme_id`) VALUES
	(1, 'Cet article explore les nouvelles tendances technologiques et leur impact sur l\'avenir.', '2025-04-10 02:22:12.000000', 'L\'avenir de la technologie', NULL, 1, 1),
	(2, 'Cet article discute des candidats et de leurs programmes pour les élections présidentielles.', '2025-04-11 02:22:14.000000', 'Les élections présidentielles de 2024', NULL, 2, 2),
	(3, 'Une analyse des nouvelles méthodes pédagogiques et de leur efficacité dans l\'éducation moderne.', '2025-04-12 02:22:15.000000', 'Les nouvelles méthodes d\'enseignement', NULL, 3, 3),
	(4, 'Un guide des destinations de voyage les plus populaires et fascinantes pour l\'année 2024.', '2025-04-13 02:22:16.000000', 'Les meilleures destinations de voyage en 2024', NULL, 4, 4),
	(5, 'Cet article fournit des conseils pour maintenir une bonne santé mentale et gérer le stress au quotidien.', '2025-04-14 02:22:17.000000', 'Comment maintenir une bonne santé mentale', NULL, 5, 5);

-- Listage de la structure de table mddapi. article_seq
CREATE TABLE IF NOT EXISTS `article_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table mddapi.article_seq : ~1 rows (environ)
INSERT INTO `article_seq` (`next_val`) VALUES
	(1);

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

-- Listage des données de la table mddapi.commentaire : ~25 rows (environ)
INSERT INTO `commentaire` (`id`, `comment`, `creationDate`, `article_id`, `author_id`) VALUES
	(1, 'Très bon article sur les technologies du futur!', NULL, 1, 2),
	(2, 'Je ne suis pas d\'accord avec cet article, la politique devrait être abordée différemment.', NULL, 2, 3),
	(3, 'L\'éducation est un sujet crucial, merci pour cet article instructif.', NULL, 3, 4),
	(4, 'Super guide pour planifier des vacances, merci pour les suggestions!', NULL, 4, 1),
	(5, 'L\'article m\'a beaucoup aidé, la santé mentale est essentielle de nos jours.', NULL, 5, 2),
	(6, 'Je suis curieuse de voir comment l\'IA va évoluer.', NULL, 1, 3),
	(7, 'Article très informatif, merci !', NULL, 1, 4),
	(8, 'Je pense que la blockchain va tout changer.', NULL, 1, 5),
	(9, 'Excellent aperçu des tendances tech.', NULL, 1, 1),
	(10, 'Sujet très sensible, mais bien traité.', NULL, 2, 1),
	(11, 'Merci pour ce résumé clair des programmes.', NULL, 2, 4),
	(12, 'Article très équilibré.', NULL, 2, 5),
	(13, 'On aurait aimé plus de détails sur les candidats.', NULL, 2, 2),
	(14, 'J\'utilise déjà certaines de ces méthodes en classe.', NULL, 3, 5),
	(15, 'Super intéressant pour les profs comme moi.', NULL, 3, 1),
	(16, 'Il faudrait que ça soit plus répandu dans les écoles.', NULL, 3, 2),
	(17, 'Très utile, je vais le partager.', NULL, 3, 3),
	(18, 'Je rêve d\'aller en Islande maintenant !', NULL, 4, 2),
	(19, 'Très belles idées de destinations.', NULL, 4, 3),
	(20, 'J\'ai adoré les photos dans l\'article.', NULL, 4, 5),
	(21, 'Merci pour ces recommandations de voyage !', NULL, 4, 4),
	(22, 'Je vais essayer certaines des astuces.', NULL, 5, 3),
	(23, 'Merci de parler de ce sujet important.', NULL, 5, 1),
	(24, 'Très bon contenu, bien structuré.', NULL, 5, 4),
	(25, 'La méditation m\'a aussi beaucoup aidée.', NULL, 5, 5);

-- Listage de la structure de table mddapi. commentaire_seq
CREATE TABLE IF NOT EXISTS `commentaire_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table mddapi.commentaire_seq : ~1 rows (environ)
INSERT INTO `commentaire_seq` (`next_val`) VALUES
	(1);

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table mddapi.suivitheme : ~5 rows (environ)
INSERT INTO `suivitheme` (`id`, `theme`, `user`) VALUES
	(6, 1, 1),
	(7, 2, 2),
	(8, 3, 3),
	(9, 4, 4),
	(10, 5, 5);

-- Listage de la structure de table mddapi. theme
CREATE TABLE IF NOT EXISTS `theme` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table mddapi.theme : ~5 rows (environ)
INSERT INTO `theme` (`id`, `description`, `name`) VALUES
	(1, 'Articles relatifs à la technologie et à l\'innovation.', 'Technologie'),
	(2, 'Articles sur les sujets politiques, les gouvernements et les événements mondiaux.', 'Politique'),
	(3, 'Thème dédié à l\'éducation, les formations et les innovations pédagogiques.', 'Éducation'),
	(4, 'Articles sur les destinations, les voyages et les cultures à travers le monde.', 'Voyages'),
	(5, 'Articles liés à la santé, au bien-être et aux conseils pour une vie saine.', 'Santé');

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

-- Listage des données de la table mddapi.users : ~6 rows (environ)
INSERT INTO `users` (`id`, `created_at`, `email`, `full_name`, `password`, `updated_at`) VALUES
	(1, '2025-04-15 02:23:50.000000', 'alice@example.com', 'Alice_Dupont', 'Valid123!', NULL),
	(2, '2025-04-15 02:23:51.000000', 'bob@example.com', 'Bob_Martin', 'Valid123!', NULL),
	(3, '2025-04-15 02:23:51.000000', 'carol@example.com', 'Carol_Denis', 'Valid123!', NULL),
	(4, '2025-04-15 02:23:52.000000', 'david@example.com', 'David_Lemoine', 'Valid123!', NULL),
	(5, '2025-04-15 02:23:53.000000', 'eve@example.com', 'Eve_Caron', 'Valid123!', NULL),
	(6, '2025-04-15 00:25:41.321487', 'test@test.com', 'jean_toto', '$2a$10$bwu3o4FnPG8r.W7FdXCtv.0ZDDMCDWN2ta9HaN93U74JdUGS6QA4.', '2025-04-15 00:25:41.321487');

-- Listage de la structure de table mddapi. users_seq
CREATE TABLE IF NOT EXISTS `users_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table mddapi.users_seq : ~1 rows (environ)
INSERT INTO `users_seq` (`next_val`) VALUES
	(101);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
