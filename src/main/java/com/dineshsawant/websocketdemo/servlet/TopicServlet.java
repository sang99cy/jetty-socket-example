package com.dineshsawant.websocketdemo.servlet;

import com.dineshsawant.websocketdemo.socket.RandomNameSocket;
import com.dineshsawant.websocketdemo.socket.TopicSocket;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class TopicServlet extends WebSocketServlet {
    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.register(TopicSocket.class);
    }
}
