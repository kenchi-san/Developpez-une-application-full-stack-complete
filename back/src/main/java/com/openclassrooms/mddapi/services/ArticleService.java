package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.article.*;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
            authorDto.setFullName(article.getAuthor().getFullName().replaceAll("_", " "));
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

        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(article.getAuthor().getId());
        authorDto.setFullName(article.getAuthor().getFullName().replaceAll("_", " "));
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        Optional<Theme> infoTheme = themeRepository.findById(input.getTheme().getId());
        Optional<User> infoUser = userRepository.findByEmail(currentUser);

        if (infoUser.isPresent()) {
            User user = infoUser.get();
            article.setAuthor(user);  // Assignation de l'utilisateur comme auteur
        } else {
            throw new RuntimeException("Utilisateur introuvable !");
        }
        if (infoTheme.isPresent()) {
            Theme theme = infoTheme.get();
            article.setTheme(theme);
        } else {
            throw new RuntimeException("Thème introuvable !");
        }

        article = articleRepository.save(article);

        // Créer le DTO à retourner
        ArticleDto dto = new ArticleDto();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());
        dto.setCreated(article.getCreatedAt());
        dto.setUpdated(article.getUpdatedAt());

        if (article.getAuthor() != null) {
            AuthorDto authorDto = new AuthorDto();
            authorDto.setId(article.getAuthor().getId());
            authorDto.setFullName(article.getAuthor().getFullName());  // Optionnel : remplacement des underscores par des espaces
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

    public ArticleDto updateArticle(Long id, UpdateArticleDto input) {
        Optional<Article> articleToUpdate = articleRepository.findById(id);

        if (articleToUpdate.isPresent()) {

            Article article = articleToUpdate.get();
            if (!input.getTitle().equals(articleToUpdate.get().getTitle()) && !input.getTitle().trim().isEmpty()) {
                article.setTitle(input.getTitle());
            }
            if (!input.getContent().equals(articleToUpdate.get().getContent()) && !input.getContent().trim().isEmpty()) {
                article.setContent(input.getContent());
            }
            if (input.getTheme().getId() != article.getTheme().getId()) {
                Theme theme = themeRepository.findById(input.getTheme().getId()).orElseThrow(() -> new RuntimeException("Thème introuvable"));
                article.setTheme(theme);
            }
            article = articleRepository.save(article);
            ArticleDto dto = new ArticleDto();
            dto.setId(article.getId());
            dto.setTitle(article.getTitle());
            dto.setContent(article.getContent());
            dto.setCreated(article.getCreatedAt());
            dto.setUpdated(article.getUpdatedAt());

            if (article.getAuthor() != null) {
                AuthorDto authorDto = new AuthorDto();
                authorDto.setId(article.getAuthor().getId());
                authorDto.setFullName(article.getAuthor().getFullName().replaceAll("_", " "));
                dto.setAuthor(authorDto);
            }

            if (article.getTheme() != null) {
                ThemeDto themeDto = new ThemeDto();
                themeDto.setId(article.getTheme().getId());
                themeDto.setName(article.getTheme().getName());
                dto.setTheme(themeDto);
            }
            return dto;
        } else {
            throw new RuntimeException("Article non trouvé");
        }
    }

    public void deleteArticle(long id) {
        Optional<Article> articleToDelete = articleRepository.findById(id);

        if (articleToDelete.isPresent()) {
            articleRepository.deleteById(id);  // Utilise deleteById pour supprimer l'article
        } else {
            throw new RuntimeException("Article non trouvé");
        }
    }
}

