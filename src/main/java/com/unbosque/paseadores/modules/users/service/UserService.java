package com.unbosque.paseadores.modules.users.service;

import com.unbosque.paseadores.core.exceptions.AlreadyExistsException;
import com.unbosque.paseadores.modules.users.dto.request.RegisterUserRequest;
import com.unbosque.paseadores.modules.users.dto.response.UserResponseDto;
import com.unbosque.paseadores.modules.users.mapper.UserMapper;
import com.unbosque.paseadores.modules.users.models.User;
import com.unbosque.paseadores.modules.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserResponseDto save(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.correo())) throw new AlreadyExistsException("El email ya existe");

        String hashedPassword = passwordEncoder.encode(request.contrasena());
        User user = userMapper.registerToModel(request);
        user.setContrasena(hashedPassword);
        return userMapper.toDto(userRepository.save(user));
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
