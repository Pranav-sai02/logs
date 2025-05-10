package com.neoteric.logs;

import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;


@Getter
@Setter
public class LoginLog {
    private int id;
    private String username;
    private String status;
    private LocalDateTime timestamp;


}

