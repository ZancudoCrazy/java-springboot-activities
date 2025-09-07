package com.adrian.practice.app.repositories;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.adrian.practice.app.entities.User;

@Repository
public class UserRepositoryImp implements IUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "select username, password, c.name as role"
            + " from user a inner join user_has_granted b on a.user_id = b.user_id"
            + " inner join granted c on b.granted_id = c.granted_id where username=?;";
        
        return jdbcTemplate.query(sql, rse -> {
            if (rse.next()) {
                User user = User.builder()
                    .username(rse.getString("username"))
                    .password(rse.getString("password"))
                    .role(rse.getString("role"))
                    .build();
                return Optional.of(user);
            }
            return Optional.empty();
        }, username);
        
    }

}
