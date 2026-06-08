package com.github.Ashirios.bookshop_api.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.Ashirios.bookshop_api.dto.AuthRequest;
import com.github.Ashirios.bookshop_api.dto.AuthResponse;
import com.github.Ashirios.bookshop_api.dto.UserRegisterDto;
import com.github.Ashirios.bookshop_api.entity.User;
import com.github.Ashirios.bookshop_api.entity.enums.Role;
import com.github.Ashirios.bookshop_api.repository.UserRepository;
import com.github.Ashirios.bookshop_api.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(UserRegisterDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.USER)
                .balance(0L)
                .build();
        userRepository.save(user);
        return new AuthResponse(jwtService.generateToken(user));
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        return new AuthResponse(jwtService.generateToken(user));
    }
}
