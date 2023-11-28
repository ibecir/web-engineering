package ba.edu.ibu.webengineering.rest.controllers;

import ba.edu.ibu.webengineering.core.service.NotificationService;
import ba.edu.ibu.webengineering.rest.dto.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "/notification")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RequestMapping(path = "/broadcast", method = RequestMethod.POST)
    public ResponseEntity<Void> sendBroadcastMessage(@RequestBody MessageDTO message) throws IOException {
        System.out.println("THE MESSAGE IS " + message.message());
        notificationService.broadcastMessage(message.message());
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(path = "/chat/message/{userId}", method = RequestMethod.POST)
    public ResponseEntity<Void> sendChatMessage(@PathVariable String userId, @RequestBody MessageDTO message) throws IOException {
        notificationService.sendMessage(userId, message.message());
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }
}
