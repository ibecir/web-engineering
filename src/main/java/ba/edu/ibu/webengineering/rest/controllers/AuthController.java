package ba.edu.ibu.webengineering.rest.controllers;

import ba.edu.ibu.webengineering.core.model.User;
import ba.edu.ibu.webengineering.core.service.AuthService;
import ba.edu.ibu.webengineering.rest.dto.LoginResponseDTO;
import ba.edu.ibu.webengineering.rest.dto.RegisterResponseDTO;
import ba.edu.ibu.webengineering.rest.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authService.addUser(userDTO));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        return ResponseEntity.ok(authService.login(email, password));
    }
}
