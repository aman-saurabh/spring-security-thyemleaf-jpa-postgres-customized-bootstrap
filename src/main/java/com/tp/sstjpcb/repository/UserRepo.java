package com.tp.sstjpcb.repository;

import com.tp.sstjpcb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    @Query("from User where email=?1")
    Optional<User> findByEmail(String email);
}
