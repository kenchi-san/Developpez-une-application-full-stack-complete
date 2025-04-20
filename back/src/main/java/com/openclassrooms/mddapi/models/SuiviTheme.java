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
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "theme", referencedColumnName = "id")
    private Theme theme;
}
