package com.dineshsawant.websocketdemo.model;

import com.dineshsawant.websocketdemo.socket.Message;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    
    private static final Gson GSON = new Gson();
    public com.dineshsawant.websocketdemo.socket.Message.OP op;
    public Map<String, Object> data = new HashMap<>();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("data=").append(data);
        sb.append(", op=").append(op);
        sb.append('}');
        return sb.toString();
    }

    public String toJson() {
        return GSON.toJson(this);
    }

    public static ChatMessage fromJson(String json) {
        return GSON.fromJson(json, ChatMessage.class);
    }
}
