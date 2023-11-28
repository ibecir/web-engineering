package ba.edu.ibu.webengineering.rest.websockets;

import ba.edu.ibu.webengineering.core.exceptions.GeneralException;
import ba.edu.ibu.webengineering.core.model.User;
import ba.edu.ibu.webengineering.core.service.JwtService;
import ba.edu.ibu.webengineering.core.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MainSocketHandler implements WebSocketHandler {
    public static Integer counter = 0;
    private final JwtService jwtService;
    private final UserService userService;
    public Map<String, WebSocketSession> sessions = new HashMap<>();

    public MainSocketHandler(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        User user = getUser(session);
        if(user == null)
            return;

        sessions.put(user.getUserId(), session);
        System.out.println("Session created for the user " + user.getUserId() + " where the session id is " + session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        int userId = 2;
        WebSocketSession userSession = sessions.get(userId);
        String messageReceived = (String) message.getPayload();
        if (userSession == null) {
            userSession = session;
        }

        System.out.println(new TextMessage("Message sent from session " + session.getId() + " to sessions " + userSession.getId() + " with payload " + messageReceived));
        userSession.sendMessage(new TextMessage("Message sent from session " + session.getId() + " to sessions " + userSession.getId() + " with payload " + messageReceived));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("Error happened " + session.getId() + " with reason ### " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("Connection closed for session " + session.getId() + " with status ### " + closeStatus.getReason());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void broadcastMessage(String message) throws IOException {
        sessions.forEach((key, session) -> {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(message));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void sendMessage(String userId, String message) {
        WebSocketSession session = sessions.get(userId);
        if (session == null) {
            return;
        }

        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            throw new GeneralException(e);
        }
    }

    private User getUser(WebSocketSession session) throws IOException {
        List<String> headers = session.getHandshakeHeaders().getOrEmpty("authorization");
        if (headers.size() == 0) {
            session.close();
            return null;
        }

        String jwt = headers.get(0).substring(7);
        String userEmail = jwtService.extractUserName(jwt);

        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
        User user = (User) userDetails;
        return user;
    }
}
