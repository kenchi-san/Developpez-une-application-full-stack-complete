package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.user.MeDto;
import com.openclassrooms.mddapi.dtos.user.UpdateUserDto;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<MeDto> allUsers() {
        Iterable<User> usersIterable = userRepository.findAll();

        List<User> users = new ArrayList<>();
        usersIterable.forEach(users::add);

        return users.stream().map(user -> {
            MeDto dto = new MeDto();
            dto.setEmail(user.getEmail());
            dto.setFullName(user.getFullName().replaceAll("_"," "));
            return dto;
        }).collect(Collectors.toList());
    }
    public User updateUser(String email, UpdateUserDto updateUserDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (StringUtils.hasText(updateUserDto.getFullName().replaceAll("_"," "))) {
            user.setFullName(updateUserDto.getFullName().replaceAll("_"," "));
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