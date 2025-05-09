package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.dtos.commentaire.CommentaireDto;
import com.openclassrooms.mddapi.models.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaireRepository extends CrudRepository<Commentaire, Long> {
    List<Commentaire> findByArticleId(long articleId);
}
