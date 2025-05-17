package com.neoteric.logs;


import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginLogRepository extends JpaRepository<com.neoteric.logs.model.LoginLog, Integer> {
}
