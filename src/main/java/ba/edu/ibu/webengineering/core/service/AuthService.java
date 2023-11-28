package ba.edu.ibu.webengineering.core.service;

import ba.edu.ibu.webengineering.core.exceptions.auth.UserAlreadyExistsException;
import ba.edu.ibu.webengineering.core.model.User;
import ba.edu.ibu.webengineering.core.repository.UserRepository;
import ba.edu.ibu.webengineering.rest.dto.LoginResponseDTO;
import ba.edu.ibu.webengineering.rest.dto.RegisterResponseDTO;
import ba.edu.ibu.webengineering.rest.dto.UserDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public RegisterResponseDTO addUser(UserDTO payload) {
        Optional<User> optionalUser = userRepository.findFirstByEmail(payload.getUsername());
        if(optionalUser.isPresent())
            throw new UserAlreadyExistsException("User with provided payload already exists");

        User user = new User();
        user.setUsername(payload.getUsername());
        user.setName(payload.getName());
        user.setEmail(payload.getUsername());
        user.setPassword(passwordEncoder.encode(payload.getPassword()));
        user.setUserType(payload.getUserType());

        user = userRepository.save(user);

        return new RegisterResponseDTO(user.getEmail(), user.getUsername(), user.getUserType());
    }

    public LoginResponseDTO login(String email, String password) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        Optional<User> loggedInUser = userRepository.findFirstByEmail(email);
        if (loggedInUser.isPresent()) return new LoginResponseDTO(jwtService.generateToken(loggedInUser.get()), 200);
        return null;
    }
}
