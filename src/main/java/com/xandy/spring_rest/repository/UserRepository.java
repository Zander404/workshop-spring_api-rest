package com.xandy.spring_rest.repository;

import com.xandy.spring_rest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {

}
