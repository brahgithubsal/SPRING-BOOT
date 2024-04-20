package tech.getarrays.employeemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.getarrays.employeemanager.model.Client;
import tech.getarrays.employeemanager.model.LoginRequest;
import tech.getarrays.employeemanager.model.RegisterRequest;
import tech.getarrays.employeemanager.repo.ClientRepo;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class ClientService {

    private final ClientRepo clientRepo;

    @Autowired
    public ClientService(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    @Autowired
    private HttpSession httpSession;

    public ResponseEntity<String> login(LoginRequest loginRequest, HttpServletResponse response) {
        try {
            Client client = clientRepo.findByEmail(loginRequest.getEmail());
            if (client != null && client.getPassword().equals(loginRequest.getPassword())) {
                // Set client attribute in session
                httpSession.setAttribute("user", client);

                // Create and set cookie
                Cookie cookie = new Cookie("clientId", String.valueOf(client.getId()));
                cookie.setMaxAge(24 * 60 * 60); // Set cookie expiry time (e.g., 24 hours)
                cookie.setHttpOnly(false); // Set cookie as HttpOnly for security
                cookie.setPath("/"); // Set cookie path
                response.addCookie(cookie);

                return ResponseEntity.ok("Client logged in successfully");
            } else {
                return ResponseEntity.badRequest().body("Authentication failed. Invalid email or password.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Login failed. Please try again later.");
        }
    }

    public ResponseEntity<String> register(RegisterRequest registerRequest) {
        try {
            Client existingClient = clientRepo.findByEmail(registerRequest.getEmail());
            if (existingClient != null) {
                return ResponseEntity.badRequest().body("Email already exists. Please use a different email.");
            }

            Client client = new Client();
            client.setEmail(registerRequest.getEmail());
            client.setPassword(registerRequest.getPassword());
            client.setName(registerRequest.getName());
            clientRepo.save(client);
            httpSession.setAttribute("user", client);
            return ResponseEntity.ok("Client registered successfully");
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace for debugging purposes
            return ResponseEntity.badRequest().body("Client registration failed. Please try again later.");
        }
    }
    public Client findClientById(Long id) {
        return clientRepo.findById(id)
                .orElse(null); // Or throw an exception if you prefer
    }
}

