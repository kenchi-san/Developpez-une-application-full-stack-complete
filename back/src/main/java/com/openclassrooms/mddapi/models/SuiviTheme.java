package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuiviTheme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user", referencedColumnName = "id")  // Spécifie le nom de la colonne dans la DB
    private User user;  // Renamed from user_id to user

    @ManyToOne(optional = false)
    @JoinColumn(name = "theme", referencedColumnName = "id")  // Spécifie le nom de la colonne dans la DB
    private NomenclatureTheme theme;  // Renamed from theme_id to theme
}
