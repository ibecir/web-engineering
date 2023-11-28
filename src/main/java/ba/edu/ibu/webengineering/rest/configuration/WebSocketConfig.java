package ba.edu.ibu.webengineering.rest.configuration;

import ba.edu.ibu.webengineering.rest.websockets.MainSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.List;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    MainSocketHandler mainSocketHandler;

    private List<String> handlers;

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(mainSocketHandler, "/websocket").setAllowedOrigins("*");
    }
}
