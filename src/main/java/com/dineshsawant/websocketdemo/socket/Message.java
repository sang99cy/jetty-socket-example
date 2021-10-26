package com.dineshsawant.websocketdemo.socket;

import org.slf4j.Logger;
import com.google.gson.Gson;
import java.util.*;

public class Message {
    public enum OP {
        SAVE_PRODUCT,
        EDIT_PRODUCT,
        DELETE_PRODUCT,
        SEARCH_PRODUCT,
        GET_ALL_PRODUCT,
        TEST_SOCKET
    }

    // these messages will be ignored during the sequential run of the note
    private static final Set<OP> disabledForRunningNoteMessages = Collections
            .unmodifiableSet(new HashSet<>(Arrays.asList(
                    OP.SAVE_PRODUCT,
                    OP.EDIT_PRODUCT,
                    OP.DELETE_PRODUCT,
                    OP.SEARCH_PRODUCT,
                    OP.GET_ALL_PRODUCT,
                    OP.TEST_SOCKET)));

    private static final Gson GSON = new Gson();
    public static final Message EMPTY = new Message(null);

    public OP op;
    public Map<String, Object> data = new HashMap<>();
    public String ticket = "anonymous";
    public String principal = "anonymous";
    public String roles = "";

    public String msgId = MSG_ID_NOT_DEFINED;
    public static String MSG_ID_NOT_DEFINED = null;

    public Message(OP op) {
        this.op = op;
    }

    public Message withMsgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    public Message put(String k, Object v) {
        data.put(k, v);
        return this;
    }

    public Object get(String k) {
        return data.get(k);
    }

    public static boolean isDisabledForRunningNotes(OP eventType) {
        return disabledForRunningNoteMessages.contains(eventType);
    }

    public <T> T getType(String key) {
        return (T) data.get(key);
    }

    public <T> T getType(String key, Logger log) {
        try {
            return getType(key);
        } catch (ClassCastException e) {
            log.error("Failed to get {} from message (Invalid type). ", key, e);
            return null;
        }
    }

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

    public static Message fromJson(String json) {
        return GSON.fromJson(json, Message.class);
    }
}
