package com.dineshsawant.websocketdemo.socket;

import com.dineshsawant.websocketdemo.model.ChatMessage;
import com.dineshsawant.websocketdemo.util.DemoBeanUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.api.CloseException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeoutException;

@WebSocket
@Slf4j
public class AppSocket {
    private static final Gson gson = new Gson();
    private final String UPLOAD_CONFIG = "application.properties";
    private Properties properties = new Properties();
    private Session session;
    private Set<Session> listenerSessions = new CopyOnWriteArraySet<>();
    public static List<Session> listSessionConnect = new ArrayList<>();

    public static Session sessionClientConnect;

    public void removeSession(Session session) {
        listenerSessions.remove(session);
    }

    public void addSession(Session session) {
        listenerSessions.add(session);
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("Connected to new server!");
        session.getPolicy().setIdleTimeout(2000000);
        session.getPolicy().setMaxTextMessageSize(2000000);
        this.session = session;
        System.out.println(session.getRemoteAddress().getHostString() + " connected!");
        listSessionConnect.add(session);
        int x = 0;
    }

    @OnWebSocketClose
    public void onClose(Session session, int closeCode, String closeReason) {
        System.out.println("close      " + new Date());
        System.out.println(session.getRemoteAddress().getHostString() + " closed!");
        if (listSessionConnect.contains(session)) {
            listenerSessions.remove(session);
        }
        session.close();

    }


    @OnWebSocketError
    public void onError(Throwable error) throws Exception {
        error.printStackTrace();
        System.out.println(error);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        System.out.println("message:" + message);
        ChatMessage reChatMessage = deserializeMessage(message);
        System.out.println("received Chat message: "+reChatMessage);

//        ChatMessage receivedMessage = deserializeMessage(message);
//        System.out.println(receivedMessage.op);
//        if ("TEST_SOCKET".equals(receivedMessage.op.name())) {
//            System.out.println("thuc hien xu ly test socket");
//        }
//        if ("SAVE_PRODUCT".equals(receivedMessage.op.name())) {
//            System.out.println("thuc hien xu li them moi san pham");
//        }
        for (Session item : listSessionConnect) {
            this.sendMessage1(item, message);
        }
    }

    protected ChatMessage deserializeMessage(String msg) {
        return gson.fromJson(msg, ChatMessage.class);
    }

//    protected Message deserializeMessage(String msg) {
//        return gson.fromJson(msg, Message.class);
//    }

    public void sendMessage(Session session,String message) {
        try {
            System.out.println("Message send from Client: " + message);
            session.getRemote().sendString(message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void sendMessage1(Session session, String message) {
        try {
            System.out.println("message:"+message);
            session.getRemote().sendStringByFuture(message);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
