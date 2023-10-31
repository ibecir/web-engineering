package ba.edu.ibu.webengineering.core.service;

import ba.edu.ibu.webengineering.core.api.mailsender.MailSender;
import ba.edu.ibu.webengineering.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.webengineering.core.model.User;
import ba.edu.ibu.webengineering.core.repository.UserRepository;
import ba.edu.ibu.webengineering.rest.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final MailSender mailSender;

    public UserService(UserRepository userRepository, MailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public UserDTO updateUser(String id, User payload) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("The user with provided id does not exist!");
        }
        payload.setUserId(user.get().getUserId());
        User updatedUser = userRepository.save(payload);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(updatedUser.getUserId());
        userDTO.setUserType(updatedUser.getUserType());
        userDTO.setName(updatedUser.getName());
        userDTO.setUsername(updatedUser.getUsername());
        return userDTO;
    }

    public void deleteUser(String userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            userRepository.delete(user.get());
        }
    }


    public User filterByEmail(String email){
        Optional<User> user = userRepository.findUserByEmailCustom(email);
        if(user.isPresent())
            return user.get();

        return null;
    }

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findFirstByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

}
