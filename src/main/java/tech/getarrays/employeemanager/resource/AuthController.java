package tech.getarrays.employeemanager.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.getarrays.employeemanager.model.LoginRequest;
import tech.getarrays.employeemanager.model.RegisterRequest;
import tech.getarrays.employeemanager.service.AuthService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private HttpSession httpSession;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        ResponseEntity<String> loginResponse = authService.login(loginRequest, response);
        return ResponseEntity.ok(loginResponse.getBody());
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        ResponseEntity<String> response = authService.register(registerRequest);
        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Invalidate session
        httpSession.invalidate();
        return ResponseEntity.ok("User logged out successfully");
    }
}