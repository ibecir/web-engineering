package ba.edu.ibu.webengineering.core.service;

import ba.edu.ibu.webengineering.core.model.User;
import ba.edu.ibu.webengineering.core.repository.UserRepository;
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

    public User addUser(UserDTO payload) {
        User user = new User();
        user.setUsername(payload.getUsername());
        user.setName(payload.getName());
        user.setEmail(payload.getUsername());
        user.setPassword(passwordEncoder.encode(payload.getName()));
        user.setUserType(payload.getUserType());

        user = userRepository.save(user);
        return user;
    }

    public String login(String email, String password){
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        Optional<User> loggedInUser = userRepository.findFirstByEmail(email);
        if(loggedInUser.isPresent())
            return jwtService.generateToken(loggedInUser.get());
        return null;
    }
}
