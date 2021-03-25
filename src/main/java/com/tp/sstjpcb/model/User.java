package com.tp.sstjpcb.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

@NoArgsConstructor
@ToString
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(name = "is_active")
    @Size(max = 1)
    private boolean active;

    private String role;
}
