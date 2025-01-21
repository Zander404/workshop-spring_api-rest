package com.xandy.spring_rest.services;

import com.xandy.spring_rest.entities.User;
import com.xandy.spring_rest.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository repository;


    public User save(User user) {

        return repository.save(user);
    }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User not Find"));
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User updatePassword(Long id, String newPassword) {
        User user = findById(id);
        user.setPassword(newPassword);
        return repository.save(user);
    }
}
