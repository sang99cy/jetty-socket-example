//package com.dineshsawant.websocketdemo.socket;
//
//import com.dineshsawant.websocketdemo.util.DemoBeanUtil;
//import com.google.gson.Gson;
//import org.eclipse.jetty.websocket.api.Session;
//import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
//import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
//import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
//import org.eclipse.jetty.websocket.api.annotations.WebSocket;
//
//import java.io.IOException;
//import java.util.Set;
//import java.util.concurrent.CopyOnWriteArraySet;
//
///**
// * Created by dnsh on 25/12/17.
// */
//@WebSocket
//public class RandomNameSocket {
//
//    private static final Gson gson = new Gson();
//    private Session session;
//    private Set<Session> listenerSessions = new CopyOnWriteArraySet<>();
//
//    public void removeSession(Session session) {
//        listenerSessions.remove(session);
//    }
//
//    public void addSession(Session session) {
//        listenerSessions.add(session);
//    }
//
//    @OnWebSocketConnect
//    public void onConnect(Session session) {
//        listenerSessions.stream()
//                .filter(s -> s.isOpen())
//                .forEach(s -> this.session = session);
//        System.out.println("Connected to new server!");
//        session.getPolicy().setIdleTimeout(2000000);
//        session.getPolicy().setMaxTextMessageSize(2000000);
//        System.out.println(session.getRemoteAddress().getHostString() + " connected!");
//        DemoBeanUtil.getRandomNameService().addSession(session);
//    }
//
//    @OnWebSocketClose
//    public void onClose(Session session, int _closeCode, String _closeReason) {
//        DemoBeanUtil.getRandomNameService().removeSession(session);
//    }
//
//    @OnWebSocketMessage
//    public void onMessage(Session session, String message) throws IOException {
//        Message receivedMessage = deserializeMessage(message);
//        System.out.println(receivedMessage.op);
//        if ("TEST_SOCKET".equals(receivedMessage.op.name())) {
//            System.out.println("thuc hien xu ly test socket");
//        }
//        if ("SAVE_PRODUCT".equals(receivedMessage.op.name())) {
//            System.out.println("thuc hien xu li them moi san pham");
//        }
//
//    }
//
//
//    protected Message deserializeMessage(String msg) {
//        return gson.fromJson(msg, Message.class);
//    }
//
//    public void sendMessage(Message message) {
//        try {
//            System.out.println("Message send from Client: " + message);
//            session.getRemote().sendString(message.toString());
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//}
