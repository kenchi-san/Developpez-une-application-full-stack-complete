package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.article.ThemeDto;
import com.openclassrooms.mddapi.dtos.article.ThemeListDto;
import com.openclassrooms.mddapi.models.SuiviTheme;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.SuiviThemeRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThemeService {

    private final SuiviThemeRepository suiviThemeRepository;
    private final ThemeRepository themeRepository;
    private final UserRepository userRepository;

    public ThemeService(SuiviThemeRepository suiviThemeRepository, ThemeRepository themeRepository, UserRepository userRepository) {
        this.suiviThemeRepository = suiviThemeRepository;
        this.themeRepository = themeRepository;
        this.userRepository = userRepository;
    }

    public void followTheme(Long themeId, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new RuntimeException("Thème non trouvé"));

        // Vérifier si le suivi existe déjà
        boolean alreadyFollowed = suiviThemeRepository.findByUserAndTheme(user, theme).isPresent();
        if (alreadyFollowed) {
            throw new IllegalStateException("Vous suivez déjà ce thème.");
        }

        SuiviTheme suivi = new SuiviTheme();
        suivi.setUser(user);
        suivi.setTheme(theme);

        suiviThemeRepository.save(suivi);
    }
    public void unFollowTheme(Long themeId, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new RuntimeException("Thème non trouvé"));

        SuiviTheme suivi = suiviThemeRepository.findByUserAndTheme(user, theme)
                .orElseThrow(() -> new IllegalStateException("Vous ne suivez pas ce thème."));

        suiviThemeRepository.delete(suivi);
    }

    public List<ThemeDto> getAllThemes() {
        List<Theme> themes = (List<Theme>) themeRepository.findAll();

        List<ThemeDto> themeDtos = new ArrayList<>();
        for (Theme theme : themes) {
            ThemeDto themeDto = new ThemeDto();
            themeDto.setId(theme.getId());
            themeDto.setName(theme.getName());
            themeDtos.add(themeDto);
        }

        return themeDtos;
    }
    public List<ThemeListDto> getListThemes() {
        List<Theme> themes = (List<Theme>) themeRepository.findAll();

        List<ThemeListDto> themeListDtos = new ArrayList<>();
        for (Theme theme : themes) {
            ThemeListDto themeListDto = new ThemeListDto();
            themeListDto.setId(theme.getId());
            themeListDto.setName(theme.getName());
            themeListDto.setDescription(theme.getDescription());
            themeListDtos.add(themeListDto);
        }

        return themeListDtos;
    }

}
