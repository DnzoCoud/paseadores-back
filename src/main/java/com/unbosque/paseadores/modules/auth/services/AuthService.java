package com.unbosque.paseadores.modules.auth.services;

import com.unbosque.paseadores.core.exceptions.BadAuthenticationException;
import com.unbosque.paseadores.modules.auth.dto.LoginRequest;
import com.unbosque.paseadores.modules.auth.dto.LoginResponse;
import com.unbosque.paseadores.modules.users.dto.request.RegisterUserRequest;
import com.unbosque.paseadores.modules.users.dto.response.UserResponseDto;
import com.unbosque.paseadores.modules.users.mapper.UserMapper;
import com.unbosque.paseadores.modules.users.models.User;
import com.unbosque.paseadores.modules.users.repository.UserRepository;
import com.unbosque.paseadores.modules.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserService userService;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadAuthenticationException("Credenciales Invalidas"));

        boolean valid = passwordEncoder.matches(request.password(), user.getContrasena());
        if (!valid) throw new BadAuthenticationException("Credenciales Invalidas");

        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .type("Bearer")
                .usuario(userMapper.toDto(user)).build();

    }

    public UserResponseDto register(RegisterUserRequest request) {
        return userService.save(request);
    }

}
