package com.openclassrooms.mddapi.dtos.commentaire;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentaireDto {
    @NotBlank(message = "Le sujet est requis")
    private String subject;
    @NotBlank(message = "Un commentaire est requis")
    private String comment;

}
