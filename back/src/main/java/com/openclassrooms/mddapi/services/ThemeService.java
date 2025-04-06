package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.SuiviTheme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.models.NomenclatureTheme;
import com.openclassrooms.mddapi.repository.SuiviThemeRepository;
import com.openclassrooms.mddapi.repository.NomenclatureThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {

    private final SuiviThemeRepository suiviThemeRepository;
    private final NomenclatureThemeRepository nomenclatureThemeRepository;
    private final UserRepository userRepository;

    public ThemeService(SuiviThemeRepository suiviThemeRepository, NomenclatureThemeRepository nomenclatureThemeRepository, UserRepository userRepository) {
        this.suiviThemeRepository = suiviThemeRepository;
        this.nomenclatureThemeRepository = nomenclatureThemeRepository;
        this.userRepository = userRepository;
    }

    public void followTheme(Long nomenclatureThemeId, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        NomenclatureTheme nomenclatureTheme = nomenclatureThemeRepository.findById(nomenclatureThemeId)
                .orElseThrow(() -> new RuntimeException("Thème non trouvé"));

        // Vérifier si le suivi existe déjà
        boolean alreadyFollowed = suiviThemeRepository.findByUserAndTheme(user, nomenclatureTheme).isPresent();
        if (alreadyFollowed) {
            throw new IllegalStateException("Vous suivez déjà ce thème.");
        }

        SuiviTheme suivi = new SuiviTheme();
        suivi.setUser(user);
        suivi.setTheme(nomenclatureTheme);

        suiviThemeRepository.save(suivi);
    }
    public void unFollowTheme(Long nomenclatureThemeId, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        NomenclatureTheme nomenclatureTheme = nomenclatureThemeRepository.findById(nomenclatureThemeId)
                .orElseThrow(() -> new RuntimeException("Thème non trouvé"));

        SuiviTheme suivi = suiviThemeRepository.findByUserAndTheme(user, nomenclatureTheme)
                .orElseThrow(() -> new IllegalStateException("Vous ne suivez pas ce thème."));

        suiviThemeRepository.delete(suivi);
    }

}
