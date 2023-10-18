package ba.edu.ibu.webengineering.rest.controllers;

import ba.edu.ibu.webengineering.core.model.User;
import ba.edu.ibu.webengineering.core.service.UserService;
import ba.edu.ibu.webengineering.rest.dto.UserDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/register")
    public ResponseEntity<UserDTO> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/filter")
    public ResponseEntity<User> filterUser(@RequestParam("email") String email){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");

        return new ResponseEntity<User>(
                userService.filterByEmail(email),
                headers,
                HttpStatus.ACCEPTED);
    }
}
