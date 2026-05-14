package com.unbosque.paseadores.modules.auth.services;

import com.unbosque.paseadores.modules.users.models.User;

public interface JwtService {
    String generateToken(User user);
    String validateToken(String token);
}
