package com.pony.data.repositories;

import java.util.Optional;

import com.pony.entities.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
     
    public Optional<User> findById(Long id);

    public User findByUserName(String userName);

    public User findByNormalizedUserName(String normalizedUserName);

    public User findByMail(String mail);

    public User findByNormalizedMail(String normalizedMail);
}