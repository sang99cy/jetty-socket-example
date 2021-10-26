//package com.dineshsawant.websocketdemo.socket;
//
//import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
//import org.eclipse.jetty.websocket.client.WebSocketClient;
//
//import java.net.URI;
//
//public class TestMainSocket {
//    public static void main(String[] args) {
//        String host = "ws://localhost:9441/ws/random";
//        WebSocketClient client = new WebSocketClient();
//        try {
//            Message message = new Message(Message.OP.TEST_SOCKET).put("hello", "hi");
//            RandomNameSocket socket = new RandomNameSocket();
//            client.start();
//            URI echoUri = new URI(host);
//            ClientUpgradeRequest request = new ClientUpgradeRequest();
//
//            client.connect(socket, echoUri, request);
//            socket.sendMessage(message);
//            System.out.println("send message successfully!");
//            int x = 0;
//        } catch (java.nio.channels.AsynchronousCloseException aex) {
//            aex.printStackTrace();
//        } catch (Exception t) {
//            System.out.println("send messsage fail!" + t.getCause());
//            t.printStackTrace();
//        } finally {
//
//        }
//    }
//}
