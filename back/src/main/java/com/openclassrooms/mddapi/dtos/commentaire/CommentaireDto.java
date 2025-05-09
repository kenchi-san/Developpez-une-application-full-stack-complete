package com.openclassrooms.mddapi.dtos.commentaire;
import com.openclassrooms.mddapi.dtos.user.UserDto;
import lombok.Data;

@Data
public class CommentaireDto {
    private Long id; // ID du commentaire
    private UserDto author; // L'auteur
    private String comment; // Le texte du commentaire
}
