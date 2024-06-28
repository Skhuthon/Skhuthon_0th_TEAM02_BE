package com.skhuthon.sweet_little_kitty.app.repository.user;

import com.skhuthon.sweet_little_kitty.app.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
