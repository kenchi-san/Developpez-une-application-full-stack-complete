package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.SuiviTheme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.models.NomenclatureTheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SuiviThemeRepository extends JpaRepository<SuiviTheme, Long> {
    Optional<SuiviTheme> findByUserAndTheme(User user, NomenclatureTheme nomenclatureTheme);
}