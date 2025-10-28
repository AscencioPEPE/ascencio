package com.talavera.ascencio.controller;

import com.talavera.ascencio.dto.AuthRequest;
import com.talavera.ascencio.dto.AuthResponse;
import com.talavera.ascencio.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody AuthRequest request) {
        return authService.register(request);
    }

    @PostMapping("/signin")
    public AuthResponse signin(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
}
