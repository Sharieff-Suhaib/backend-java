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

    public String register(Users users) {
        Optional<Users> existingUser = repo.findByUsername(users.getUsername());
        if(existingUser.isPresent()){
            return "Username already exists";
        }
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        repo.save(users);
        return "User registered successfully";
    }

    public String login(String username,String password){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        if(authentication.isAuthenticated()){

            return "User logged in successfully " + jwtService.generateToken(username);
        }else{
            return "Username or password is incorrect";
        }
    }
}
