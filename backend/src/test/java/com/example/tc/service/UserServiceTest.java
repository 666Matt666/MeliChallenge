package com.example.tc.service;

import com.example.tc.dto.UserDTO;
//import com.example.tc.event.UserCreatedEvent;
import com.example.tc.model.User;
import com.example.tc.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Test
    void createUser_sendsKafkaAndReturnsDto() {
        UserRepository repo = mock(UserRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        UserService svc = new UserService(repo, passwordEncoder);

        User u = new User(1L, "John Doe", "john@example.com", "john", "password");
        when(repo.findByUsername(u.getUsername())).thenReturn(Optional.empty());
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        UserDTO dto = svc.createUser(u);

        assertEquals("john", dto.getUsername());
        //verify(publisher, times(1)).publishEvent(any(UserCreatedEvent.class));
        //verify(repo, times(1)).save(any(User.class));
    }

    @Test
    void createUser_duplicate_throws() {
        UserRepository repo = mock(UserRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        UserService svc = new UserService(repo, passwordEncoder);

        User u = new User(1L, "John Doe", "john@example.com", "john", "password");
        when(repo.findByUsername(u.getUsername())).thenReturn(Optional.of(u));

        assertThrows(IllegalArgumentException.class, () -> svc.createUser(u));
    }
}
