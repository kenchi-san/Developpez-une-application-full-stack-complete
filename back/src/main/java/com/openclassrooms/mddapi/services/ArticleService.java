package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.AuthorDto;
import com.openclassrooms.mddapi.dtos.ArticleDto;
import com.openclassrooms.mddapi.dtos.CreateArticleDto;
import com.openclassrooms.mddapi.dtos.ThemeDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ThemeRepository themeRepository;
    private final UserRepository userRepository;

    public ArticleService(ArticleRepository articleRepository, ThemeRepository themeRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.themeRepository = themeRepository;
        this.userRepository = userRepository;
    }

    public List<ArticleDto> allArticle() {
        List<ArticleDto> result = new ArrayList<>();

        articleRepository.findAll().forEach(article -> {
            ArticleDto dto = new ArticleDto();

            dto.setId(article.getId());
            dto.setTitle(article.getTitle());
            dto.setContent(article.getContent());
            dto.setCreated(article.getCreatedAt());
            dto.setUpdated(article.getUpdatedAt());

            AuthorDto authorDto = new AuthorDto();
            authorDto.setId(article.getAuthor().getId());
            authorDto.setFullName(article.getAuthor().getFullName());
            dto.setAuthor(authorDto);

            ThemeDto themeDto = new ThemeDto();
            themeDto.setId(article.getTheme().getId());
            themeDto.setName(article.getTheme().getName());
            dto.setTheme(themeDto);

            result.add(dto);
        });

        return result;
    }

    public ArticleDto getArticleById(long id) {
        // Vérifier si l'article existe
        Optional<Article> optionalArticle = articleRepository.findById(id);

        // Si l'article n'existe pas, retourner null ou une exception
        if (optionalArticle.isEmpty()) {
            return null; // Ou tu peux lancer une exception personnalisée pour gérer l'erreur
        }

        Article article = optionalArticle.get();

        // Créer le DTO
        ArticleDto dto = new ArticleDto();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());
        dto.setCreated(article.getCreatedAt());
        dto.setUpdated(article.getUpdatedAt());

        // Convertir l'auteur en DTO (si tu veux utiliser un DTO pour l'auteur)
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(article.getAuthor().getId());
        authorDto.setFullName(article.getAuthor().getFullName());
        dto.setAuthor(authorDto);

        ThemeDto themeDto = new ThemeDto();
        themeDto.setId(article.getTheme().getId());
        themeDto.setName(article.getTheme().getName());
        dto.setTheme(themeDto);

        return dto;
    }

    public ArticleDto newArticle(CreateArticleDto input) {

        Article article = new Article();
        article.setTitle(input.getTitle());
        article.setContent(input.getContent());

        Optional<User> infoUser = userRepository.findById(input.getAuthor().getId());
        Optional<Theme> infoTheme = themeRepository.findById(input.getTheme().getId());

        if (infoUser.isPresent()) {
            User user = infoUser.get();
            article.setAuthor(user);
            System.out.println(user.getFullName());
        } else {
            System.out.println("Utilisateur introuvable !");
//mettre la gestion d'erreur ici
        }

        if (infoTheme.isPresent()) {
            Theme theme = infoTheme.get();
            article.setTheme(theme);
            System.out.println(theme.getName());
        } else {
            System.out.println("Thème introuvable !");
//mettre la gestion d'erreur ici
        }

        article = articleRepository.save(article); // important de réassigner pour avoir l'ID généré

        // Créer le DTO à retourner
        ArticleDto dto = new ArticleDto();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());
        dto.setCreated(article.getCreatedAt());
        dto.setUpdated(article.getUpdatedAt());

        // Convertir l'auteur en DTO
        if (article.getAuthor() != null) {
            AuthorDto authorDto = new AuthorDto();
            authorDto.setId(article.getAuthor().getId());
            authorDto.setFullName(article.getAuthor().getFullName());
            dto.setAuthor(authorDto);
        }

        // Convertir le thème en DTO
        if (article.getTheme() != null) {
            ThemeDto themeDto = new ThemeDto();
            themeDto.setId(article.getTheme().getId());
            themeDto.setName(article.getTheme().getName());
            dto.setTheme(themeDto);
        }

        return dto;
    }



}

