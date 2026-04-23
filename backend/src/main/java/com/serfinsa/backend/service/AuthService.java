package com.serfinsa.backend.service;

import com.serfinsa.backend.dto.LoginRequest;
import com.serfinsa.backend.dto.LoginResponse;
import com.serfinsa.backend.entity.Rol;
import com.serfinsa.backend.repository.UsuarioRepository;
import com.serfinsa.backend.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtTokenProvider.generateToken(userDetails);

        return usuarioRepository.findByEmail(request.getEmail())
                .map(u -> LoginResponse.builder()
                        .token(token)
                        .email(u.getEmail())
                        .nombre(u.getNombre())
                        .rol(u.getRoles().stream()
                                .map(Rol::getNombre)
                                .findFirst()
                                .orElse("ROLE_USER"))
                        .build())
                .orElseThrow();
    }
}
