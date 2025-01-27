package com.xandy.spring_rest.services;

import com.xandy.spring_rest.entities.User;
import com.xandy.spring_rest.entities.enums.Role;
import com.xandy.spring_rest.exceptions.EntityNotFoundException;
import com.xandy.spring_rest.exceptions.PasswordInvalidException;
import com.xandy.spring_rest.exceptions.UsernameUniqueViolationException;
import com.xandy.spring_rest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;




    public User save(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return repository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UsernameUniqueViolationException(String.format(" Username: {%s} already in use", user.getUsername()));
        }
    }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not Find"));
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User updatePassword(Long id, String password, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new PasswordInvalidException("New Password don't match");
        }

        User user = findById(id);
        System.out.println("password: " + password);
        System.out.println("newPassword: " + newPassword);
        System.out.println("confirmPassword: " + confirmPassword);
        System.out.println("user: " + user.getPassword());
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordInvalidException("Your Actual Password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        return user;
    }

    @Transactional(readOnly = true)
    public User searchUserByName(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(String.format("User with the username '%s' found", username)));
    }

    @Transactional(readOnly = true)
    public Role searchRoleByUsername(String username) {
        return repository.findRoleByUsername(username);

    }
}
