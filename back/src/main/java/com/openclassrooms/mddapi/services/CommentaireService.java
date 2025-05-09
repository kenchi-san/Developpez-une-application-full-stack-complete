package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.article.ArticleDto;
import com.openclassrooms.mddapi.dtos.commentaire.CommentaireDto;
import com.openclassrooms.mddapi.dtos.user.UserDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Commentaire;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentaireRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentaireService {

    private final CommentaireRepository commentaireRepository;
    private final ArticleRepository articleRepository;
    public CommentaireService(CommentaireRepository commentaireRepository, ArticleRepository articleRepository) {
        this.commentaireRepository = commentaireRepository;
        this.articleRepository = articleRepository;
    }
    public CommentaireDto createCommentaire(long articleId, CommentaireDto dto, User user) {
        // Récupérer l'article
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with id " + articleId));

        // Créer un nouveau commentaire
        Commentaire commentaire = new Commentaire();
        commentaire.setComment(dto.getComment()); // Le contenu du commentaire
        commentaire.setAuthor(user); // L'auteur est l'utilisateur connecté
        commentaire.setArticle(article); // L'article auquel le commentaire appartient

        // Sauvegarder le commentaire
        commentaireRepository.save(commentaire);

        // Créer un DTO à retourner
        CommentaireDto commentaireDto = new CommentaireDto();
        commentaireDto.setId(commentaire.getId()); // Ajout de l'ID généré du commentaire
        commentaireDto.setComment(commentaire.getComment());
        commentaireDto.setAuthor(new UserDto(user.getId(), user.getFullName().replaceAll("_"," "))); // Assurez-vous que UserDto contient l'ID et le nom de l'utilisateur

        return commentaireDto;
    }
    public List<CommentaireDto> getCommentaireByIdArticle(long articleId) {
        List<Commentaire> commentaires = commentaireRepository.findByArticleId(articleId);

        return commentaires.stream()
                .map(commentaire -> {
                    CommentaireDto dto = new CommentaireDto();
                    dto.setId(commentaire.getId());
                    dto.setComment(commentaire.getComment());

                    // Mapper l'auteur en DTO si besoin
                    UserDto userDto = new UserDto();
                    userDto.setId(commentaire.getAuthor().getId());
                    userDto.setFullName(commentaire.getAuthor().getFullName().replaceAll("_"," ")); // ou autre champ
                    dto.setAuthor(userDto);

                    return dto;
                })
                .collect(Collectors.toList());
    }

}
