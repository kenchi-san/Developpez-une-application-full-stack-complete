package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.article.ThemeDto;
import com.openclassrooms.mddapi.dtos.article.ThemeListDto;
import com.openclassrooms.mddapi.models.SuiviTheme;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.SuiviThemeRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public void manageSubscribe(Long themeId, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new RuntimeException("Thème non trouvé"));

        Optional<SuiviTheme> suiviOptional = suiviThemeRepository.findByUserAndTheme(user, theme);

        if (suiviOptional.isPresent()) {
            unFollowTheme(suiviOptional.get());
        } else {
            followTheme(theme, user);
        }
    }

    public void followTheme(Theme theme, User user) {
        SuiviTheme suivi = new SuiviTheme();
        suivi.setUser(user);
        suivi.setTheme(theme);

        suiviThemeRepository.save(suivi);
    }

    public void unFollowTheme(SuiviTheme suivi) {
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        List<SuiviTheme> suivis = suiviThemeRepository.findByUser(user);

        Set<Long> followedThemeIds = suivis.stream()
                .map(suivi -> suivi.getTheme().getId())
                .collect(Collectors.toSet());

        List<Theme> themes = (List<Theme>) themeRepository.findAll();

        List<ThemeListDto> themeListDtos = new ArrayList<>();
        for (Theme theme : themes) {
            ThemeListDto dto = new ThemeListDto();
            dto.setId(theme.getId());
            dto.setName(theme.getName());
            dto.setDescription(theme.getDescription());
            followedThemeIds.contains(theme.getId());
            themeListDtos.add(dto);
        }

        return themeListDtos;
    }

}
