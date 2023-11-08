package ba.edu.ibu.webengineering.rest.controllers;

import ba.edu.ibu.webengineering.core.service.AuthService;
import ba.edu.ibu.webengineering.rest.dto.LoginDTO;
import ba.edu.ibu.webengineering.rest.dto.LoginResponseDTO;
import ba.edu.ibu.webengineering.rest.dto.RegisterResponseDTO;
import ba.edu.ibu.webengineering.rest.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO request) {
        return ResponseEntity.ok(authService.login(request.username(), request.password()));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/get")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("GOOD");
    }
}
