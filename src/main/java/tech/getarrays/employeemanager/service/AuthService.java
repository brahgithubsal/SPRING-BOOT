package tech.getarrays.employeemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.getarrays.employeemanager.model.LoginRequest;
import tech.getarrays.employeemanager.model.RegisterRequest;
import tech.getarrays.employeemanager.model.User;
import tech.getarrays.employeemanager.repo.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private HttpSession httpSession;

    public ResponseEntity<String> login(LoginRequest loginRequest, HttpServletResponse response) {
        try {
            User user = userRepository.findByEmail(loginRequest.getEmail());
            if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
                // Set user attribute in session
                httpSession.setAttribute("user", user);

                // Create and set cookie
                Cookie cookie = new Cookie("sessionId", httpSession.getId());
                cookie.setMaxAge(24 * 60 * 60); // Set cookie expiry time (e.g., 24 hours)
                response.addCookie(cookie);

                return ResponseEntity.ok("User logged in successfully");
            } else {
                return ResponseEntity.badRequest().body("Authentication failed. Invalid email or password.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Login failed. Please try again later.");
        }
    }

    // Other methods (register, logout) remain unchanged}


    public ResponseEntity<String> register(RegisterRequest registerRequest) {
        try {
            User user = new User();
            user.setEmail(registerRequest.getEmail());
            user.setPassword(registerRequest.getPassword());
            user.setName(registerRequest.getName());
            userRepository.save(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed. Please try again later.");
        }
    }

    public ResponseEntity<String> logout() {
        // Invalidate session
        httpSession.invalidate();
        return ResponseEntity.ok("User logged out successfully");
    }
}
