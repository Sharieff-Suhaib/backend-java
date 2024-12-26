package ctf.projects.tech.backend.backend.service;

import ctf.projects.tech.backend.backend.model.Users;
import ctf.projects.tech.backend.backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepo repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Map<String, Object> register(Users users) {
        Optional<Users> existingUser = repo.findByUsername(users.getUsername());
        Map<String, Object> response = new HashMap<>();

        if (existingUser.isPresent()) {
            response.put("message", "Username already exists");
            return response;
        }

        users.setPassword(passwordEncoder.encode(users.getPassword()));
        repo.save(users);
        response.put("message", "User registered successfully");
        return response;
    }

    public Map<String, Object> login(String username, String password) {
        Map<String, Object> response = new HashMap<>();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        if (authentication.isAuthenticated()) {
            response.put("message", "User logged in successfully");
            response.put("token", jwtService.generateToken(username));
        } else {
            response.put("message", "Username or password is incorrect");
        }

        return response;
    }
}
