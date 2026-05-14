package com.unbosque.paseadores.modules.auth.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.unbosque.paseadores.config.EnvConfig;
import com.unbosque.paseadores.modules.users.models.Rol;
import com.unbosque.paseadores.modules.users.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        List<String> roles =
                user.getRoles()
                        .stream()
                        .map(Rol::getNombre)
                        .toList();

        return JWT.create()
                .withIssuer("paseadores-app")
                .withSubject(user.getIdUsuario().toString())
                .withClaim("email", user.getCorreo())
                .withClaim("roles", roles)
                .withIssuedAt(new Date())
                .withExpiresAt(
                        new Date(
                                System.currentTimeMillis()
                                        + 86400000
                        )
                )
                .sign(algorithm);
    }

    @Override
    public String validateToken(String token) {
        Algorithm algorithm =
                Algorithm.HMAC256(secret);

        JWTVerifier verifier =
                JWT.require(algorithm)
                        .withIssuer("paseadores-app")
                        .build();

        DecodedJWT decoded =
                verifier.verify(token);

        return decoded.getSubject();
    }
}
