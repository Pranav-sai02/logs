package com.neoteric.logs;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    private LogsRepository LogsRepository;

    @Autowired
    private LoginLogRepository loginLogRepository;

    public boolean authenticate(String username, String password) {
        boolean isAuthenticated = LogsRepository.existsByUsernameAndPassword(username, password);
        String status = isAuthenticated ? "SUCCESS" : "FAILURE";

        com.neoteric.logs.model.LoginLog log = new com.neoteric.logs.model.LoginLog();
        log.setUsername(username);
        log.setStatus(status);
        log.setTimestamp(LocalDateTime.now());

        loginLogRepository.save(log);

        return isAuthenticated;
    }

    public List<com.neoteric.logs.model.LoginLog> getAllLogs() {
        return loginLogRepository.findAll()
                .stream()
                .sorted((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()))
                .toList();
    }
}
