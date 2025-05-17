package com.neoteric.logs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "logs")
@Getter
@Setter
public class Logs {

    @Id
    private String username;

    private String password;
}
