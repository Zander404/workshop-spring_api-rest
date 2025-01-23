package com.xandy.spring_rest.repository;

import com.xandy.spring_rest.entities.User;
import com.xandy.spring_rest.entities.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("select u.role from User u where u.username like :username")
    Role findRoleByUsername(String username);
}
