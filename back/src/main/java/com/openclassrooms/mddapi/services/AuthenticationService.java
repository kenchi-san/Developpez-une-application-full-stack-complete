package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.user.LoginUserDto;
import com.openclassrooms.mddapi.dtos.user.RegisterUserDto;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Déclarer la constante en dehors de la méthode, au niveau de la classe
    private static final String PASSWORD_REGEX =
            "^(?=.*[0-9])" +                    // au moins un chiffre
                    "(?=.*[a-z])" +                     // au moins une minuscule
                    "(?=.*[A-Z])" +                     // au moins une majuscule
                    "(?=.*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'`~<>.,?/\\\\|])" + // au moins un caractère spécial
                    "(?=\\S+$).{8,}$";                  // pas d'espaces, min 8 caractères

    public User signup(RegisterUserDto input) {
        // Vérifier si le mot de passe correspond à l'expression régulière
        if (input.getPassword() == null || !input.getPassword().matches(PASSWORD_REGEX)) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial.");
        }

        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(input.getEmail())) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà.");
        }

        // Affichage des données reçues pour le débogage
        System.out.println("Données reçues : " + input.toString());

        // Création et enregistrement de l'utilisateur
        User user = new User()
                .setFullName(input.getFullName())
                .setEmail(input.getEmail())
                .setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        // Récupérer l'utilisateur ou lancer une exception avec un message d'erreur
        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email ou mot de passe incorrect"));
    }
}