package com.pony.repositories;

import com.pony.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
     
    public User findByUserName(String userName);

    public User findByNormalizedUserName(String normalizedUserName);

    public User findByMail(String mail);

    public User findByNormalizedMail(String normalizedMail);
}