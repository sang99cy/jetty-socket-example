package com.dineshsawant.websocketdemo.publisher;

import java.util.EventObject;

public class ServerSocketListener extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ServerSocketListener(Object source) {
        super(source);

    }


}
