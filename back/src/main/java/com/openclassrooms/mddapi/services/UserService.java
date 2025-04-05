package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.user.UpdateUserDto;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
    public User updateUser(String email, UpdateUserDto updateUserDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (StringUtils.hasText(updateUserDto.getFullName())) {
            user.setFullName(updateUserDto.getFullName());
        }

        if (StringUtils.hasText(updateUserDto.getPassword())) {
            user.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
        }
        if (StringUtils.hasText(updateUserDto.getEmail())) {
            user.setEmail(updateUserDto.getEmail());
        }

        return userRepository.save(user);
    }
}