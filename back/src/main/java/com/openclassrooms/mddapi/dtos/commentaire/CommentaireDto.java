package com.openclassrooms.mddapi.dtos.commentaire;
import com.openclassrooms.mddapi.dtos.user.UserDto;
import lombok.Data;

@Data
public class CommentaireDto {

    private Long id;

    private UserDto author;

    private String comment;

}
