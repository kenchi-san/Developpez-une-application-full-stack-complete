package com.openclassrooms.mddapi.dtos.commentaire;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Data
public class CommentaireDto {

    private String subject;

    private String comment;

}
