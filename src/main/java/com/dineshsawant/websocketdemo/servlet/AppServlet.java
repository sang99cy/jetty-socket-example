package com.dineshsawant.websocketdemo.servlet;

import com.dineshsawant.websocketdemo.socket.AppSocket;
import com.dineshsawant.websocketdemo.socket.TopicSocket;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class AppServlet extends WebSocketServlet {
    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.register(AppSocket.class);
    }
}
