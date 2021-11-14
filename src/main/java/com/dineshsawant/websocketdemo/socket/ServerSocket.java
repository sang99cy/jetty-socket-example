package com.dineshsawant.websocketdemo.socket;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeoutException;


@Component
@Slf4j
public class ServerSocket {

    private final String UPLOAD_CONFIG = "application.properties";
    private Properties properties = new Properties();
    public static final Server jettyServer = null;

    @PostConstruct
    private void postConstruct() throws Exception {
        properties.load(getClass().getClassLoader().getResourceAsStream(UPLOAD_CONFIG));
        String port = properties.getProperty("portSocketServer");
        Server server = new Server(Integer.parseInt(port));
        WebSocketHandler wsHandler = new WebSocketHandler() {
            @Override
            public void configure(WebSocketServletFactory factory) {
                factory.register(AppSocket.class);
            }
        };
        server.setHandler(wsHandler);
        server.start();
        server.setStopTimeout(0);
        log.debug("server socket started!");
    }

}
