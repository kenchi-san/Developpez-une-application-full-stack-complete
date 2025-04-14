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
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with id " + articleId));

        Commentaire commentaire = new Commentaire();
        commentaire.setComment(dto.getComment());
        commentaire.setAuthor(user);
        commentaire.setArticle(article);
        commentaireRepository.save(commentaire);
        CommentaireDto commentaireDto = new CommentaireDto();
        commentaireDto.setComment(commentaire.getComment());
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
