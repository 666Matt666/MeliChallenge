package com.example.tc.service;

import com.example.tc.dto.UserDTO;
//import com.example.tc.event.UserCreatedEvent;
import com.example.tc.model.User;
import com.example.tc.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO createUser(User user) {
        // simple unique username check
        if (repo.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        // Encriptar la contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved = repo.save(user);
        UserDTO dto = toDto(saved);
        return dto;
    }

    public UserDTO getById(String id) {
        return repo.findById(id).map(this::toDto).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public java.util.List<UserDTO> listAll() {
        return repo.findAll().stream().map(user -> toDto(user)).collect(Collectors.toList());
    }

    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    private UserDTO toDto(User u) { return new UserDTO(u.getId().toString(), u.getUsername(), u.getEmail()); }
}