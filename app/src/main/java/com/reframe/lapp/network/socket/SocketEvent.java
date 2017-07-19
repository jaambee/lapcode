package com.reframe.lapp.network.socket;

/**
 * Created by Aldo on 02-03-2017.
 */

public class SocketEvent {
    public Object[] args;
    public String event;

    public SocketEvent(Object[] args, String event) {
        this.args = args;
        this.event = event;
    }
}