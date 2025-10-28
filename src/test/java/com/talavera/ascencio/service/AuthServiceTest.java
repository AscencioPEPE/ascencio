package com.talavera.ascencio.service;

import com.talavera.ascencio.dto.AuthRequest;
import com.talavera.ascencio.dto.AuthResponse;
import com.talavera.ascencio.model.User;
import com.talavera.ascencio.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_ShouldSaveUserAndReturnToken() {
        AuthRequest request = new AuthRequest();
        request.setEmail("test@mail.com");
        request.setPassword("123456");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(jwtService.generateToken(anyString())).thenReturn("fake-jwt");

        AuthResponse response = authService.register(request);

        // Verifica que el usuario fue guardado
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertEquals("test@mail.com", userCaptor.getValue().getEmail());
        assertTrue(encoder.matches("123456", userCaptor.getValue().getPasswordHash()));

        // Verifica respuesta
        assertEquals("fake-jwt", response.getToken());
    }

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreValid() {
        User user = User.builder()
                .email("user@mail.com")
                .passwordHash(encoder.encode("pass123"))
                .build();

        when(userRepository.findByEmail("user@mail.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(anyString())).thenReturn("jwt-ok");

        AuthRequest request = new AuthRequest();
        request.setEmail("user@mail.com");
        request.setPassword("pass123");

        AuthResponse response = authService.login(request);

        assertEquals("jwt-ok", response.getToken());
    }

    @Test
    void login_ShouldThrowException_WhenPasswordInvalid() {
        User user = User.builder()
                .email("x@mail.com")
                .passwordHash(encoder.encode("realpass"))
                .build();

        when(userRepository.findByEmail("x@mail.com")).thenReturn(Optional.of(user));

        AuthRequest request = new AuthRequest();
        request.setEmail("x@mail.com");
        request.setPassword("wrong");

        assertThrows(RuntimeException.class, () -> authService.login(request));
    }
}
