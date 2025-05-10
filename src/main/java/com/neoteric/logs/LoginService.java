package com.neoteric.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public boolean authenticate(String username, String password) {
        String sql = "SELECT COUNT(*) FROM logs WHERE username = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username, password}, Integer.class);

        String status = (count != null && count > 0) ? "SUCCESS" : "FAILURE";


        String logSql = "INSERT INTO login_logs (username, status) VALUES (?, ?)";
        jdbcTemplate.update(logSql, username, status);

        return "SUCCESS".equals(status);
    }


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
