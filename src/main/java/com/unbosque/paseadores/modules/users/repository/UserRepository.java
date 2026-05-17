package com.unbosque.paseadores.modules.users.repository;

import com.unbosque.paseadores.core.database.relational.service.RelationalDatabaseService;
import com.unbosque.paseadores.modules.users.mapper.RolRowMapper;
import com.unbosque.paseadores.modules.users.mapper.UserRowMapper;
import com.unbosque.paseadores.modules.users.models.Rol;
import com.unbosque.paseadores.modules.users.models.User;
import com.unbosque.paseadores.modules.users.queries.UserQueries;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final RelationalDatabaseService dbService;
    private final UserRowMapper mapper;
    private final RolRowMapper rolMapper;

    public Optional<User> findByEmail(String email) {
        User user = dbService.queryOne(UserQueries.FIND_BY_EMAIL, mapper, email);
        if (user == null) {
            return Optional.empty();
        }

        List<Rol> roles = dbService.query(UserQueries.FIND_ROLES_BY_USER_ID, rolMapper, user.getIdUsuario());
        user.setRoles(roles);

        return Optional.of(user);
    }

    public Optional<User> findById(Long idUsuario) {
        User user = dbService.queryOne(UserQueries.FIND_BY_ID, mapper, idUsuario);
        if (user == null) {
            return Optional.empty();
        }

        List<Rol> roles = dbService.query(UserQueries.FIND_ROLES_BY_USER_ID, rolMapper, user.getIdUsuario());
        user.setRoles(roles);

        return Optional.of(user);
    }

    public User save(User user) {
        Long[] roles = user.getRoles() == null
                ? null
                : user.getRoles()
                .stream()
                .map(Rol::getIdRol)
                .toArray(Long[]::new);

        User savedUser = dbService.queryOneWithArray(
                UserQueries.SAVE_USER,
                mapper,
                new Object[] {
                        user.getCorreo(),
                        user.getContrasena(),
                        user.getTelefono(),
                        user.getPrimerNombre(),
                        user.getSegundoNombre(),
                        user.getPrimerApellido(),
                        user.getSegundoApellido(),
                        user.getFotoPerfil()
                },
                "bigint",
                roles
        );

        savedUser.setRoles(user.getRoles());
        return savedUser;
    }

    public boolean existsByEmail(String email) {
        return dbService.exists(UserQueries.EXISTS_BY_EMAIL, email);
    }

    public boolean existsById(Long id) {
        return dbService.exists(UserQueries.EXISTS_BY_ID, id);
    }
}
