package tech.getarrays.employeemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.getarrays.employeemanager.model.User;
import tech.getarrays.employeemanager.repo.UserRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;



    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User findUserBySessionId(String sessionId) {
        // Implement logic to retrieve the user based on the session ID
        // This might involve querying the database or some other mechanism
        // For demonstration purposes, let's assume you have a method in UserRepository to find a user by session ID
        return userRepository.findBySessionId(sessionId);
    }
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " was not found"));
    }
}