package com.neoteric.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Authenticate user during login
    public boolean authenticate(String username, String password) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username, password}, Integer.class);

        String status = (count != null && count > 0) ? "SUCCESS" : "FAILURE";

        // Log the login attempt
        String logSql = "INSERT INTO login_logs (username, status) VALUES (?, ?)";
        jdbcTemplate.update(logSql, username, status);

        return "SUCCESS".equals(status);
    }

    // Register new user
    public boolean registerUser(String username, String password) {
        // Check if the user already exists
        String checkSql = "SELECT COUNT(*) FROM users WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, new Object[]{username}, Integer.class);

        if (count != null && count > 0) {
            return false; // Username already exists
        }

        // Insert new user into the database
        String insertSql = "INSERT INTO users (username, password) VALUES (?, ?)";
        jdbcTemplate.update(insertSql, username, password);
        return true; // User successfully registered
    }

    // Get all login logs
    public List<LoginLog> getAllLogs() {
        String sql = "SELECT * FROM login_logs ORDER BY timestamp DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            LoginLog log = new LoginLog();
            log.setId(rs.getInt("id"));
            log.setUsername(rs.getString("username"));
            log.setStatus(rs.getString("status"));
            log.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
            return log;
        });
    }
}
