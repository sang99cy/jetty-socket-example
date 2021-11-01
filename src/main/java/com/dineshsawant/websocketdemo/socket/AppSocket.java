package com.dineshsawant.websocketdemo.socket;

import com.dineshsawant.websocketdemo.model.ChatMessage;
import com.dineshsawant.websocketdemo.util.DemoBeanUtil;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@WebSocket
public class AppSocket {
    private static final Gson gson = new Gson();
    private Session session;
    private Set<Session> listenerSessions = new CopyOnWriteArraySet<>();
    public static Map<Session,Session> hasMapSessionServerClient = new HashMap<>();
    public static Map<Session,Session> hasMapSessionClientServer = new HashMap<>();

    public static Session sessionClientConnect;

    public void removeSession(Session session) {
        listenerSessions.remove(session);
    }

    public void addSession(Session session) {
        listenerSessions.add(session);
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        listenerSessions.stream()
                .filter(s -> s.isOpen())
                .forEach(s -> this.session = session);
        System.out.println("Connected to new server!");
        session.getPolicy().setIdleTimeout(2000000);
        session.getPolicy().setMaxTextMessageSize(2000000);
        this.session = session;
        System.out.println(session.getRemoteAddress().getHostString() + " connected!");
        if (sessionClientConnect != null) {
            hasMapSessionServerClient.put(session, sessionClientConnect);
            hasMapSessionClientServer.put(sessionClientConnect, session);
        } else {
            session.close();
        }
        System.out.println("BD: " + hasMapSessionServerClient.keySet().size());
    }

    @OnWebSocketClose
    public void onClose(Session session, int _closeCode, String _closeReason) {
        System.out.println("close      " + new Date());
        System.out.println(session.getRemoteAddress().getHostString() + " closed!");
        Session sessionClientRemove = hasMapSessionServerClient.get(session);
        if (hasMapSessionClientServer.containsKey(sessionClientRemove)) {
            hasMapSessionClientServer.remove(sessionClientRemove);
            sessionClientRemove.close();
        }
        if (hasMapSessionServerClient.containsKey(session)) {
            hasMapSessionServerClient.remove(session);
            session.close();
        }
    }


    @OnWebSocketError
    public void onError(Throwable error) {
        error.printStackTrace();
        System.out.println(error);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        System.out.println("message:" + message);
        ChatMessage reChatMessage = deserializeMessage(message);
        //System.out.println("received Chat message: "+reChatMessage);

        /*Message receivedMessage = deserializeMessage(message);
        System.out.println(receivedMessage.op);
        if ("TEST_SOCKET".equals(receivedMessage.op.name())) {
            System.out.println("thuc hien xu ly test socket");
        }
        if ("SAVE_PRODUCT".equals(receivedMessage.op.name())) {
            System.out.println("thuc hien xu li them moi san pham");
        }*/
        Session sessionClient = hasMapSessionServerClient.get(session);
        if (session.isOpen()) {
            this.sendMessage(sessionClient,reChatMessage);
        }
    }

    protected ChatMessage deserializeMessage(String msg) {
        return gson.fromJson(msg, ChatMessage.class);
    }

//    protected Message deserializeMessage(String msg) {
//        return gson.fromJson(msg, Message.class);
//    }

    public void sendMessage(Session session,ChatMessage message) {
        try {
            System.out.println("Message send from Client: " + message);
            session.getRemote().sendString(message.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void sendMessage(Session session, String message) {
        try {
            System.out.println("message:"+message);
            session.getRemote().sendStringByFuture(message);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
