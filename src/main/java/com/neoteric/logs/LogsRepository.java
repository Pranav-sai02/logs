package com.neoteric.logs;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LogsRepository extends JpaRepository<Logs, String> {
    boolean existsByUsernameAndPassword(String username, String password);
}
