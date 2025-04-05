package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.article.ArticleDto;
import com.openclassrooms.mddapi.dtos.commentaire.CommentaireDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Commentaire;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentaireRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

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
        commentaire.setSubject(dto.getSubject());
        commentaire.setComment(dto.getComment());
        commentaire.setAuthor(user);
        commentaire.setArticle(article);
        commentaireRepository.save(commentaire);
        CommentaireDto commentaireDto = new CommentaireDto();
        commentaireDto.setSubject(commentaire.getSubject());
        commentaireDto.setComment(commentaire.getComment());
        return commentaireDto;
    }

}
