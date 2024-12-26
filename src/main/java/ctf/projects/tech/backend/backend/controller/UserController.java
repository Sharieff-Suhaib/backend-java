package ctf.projects.tech.backend.backend.controller;

import ctf.projects.tech.backend.backend.model.Users;
import ctf.projects.tech.backend.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/register")
    public String register(@RequestBody Users users){
        return service.register(users);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users users){
        return service.login(users.getUsername(), users.getPassword());
    }

}
