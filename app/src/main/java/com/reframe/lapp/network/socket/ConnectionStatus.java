package com.reframe.lapp.network.socket;

/**
 * Created by Aldo on 02-03-2017.
 */

public class ConnectionStatus {
    public enum Status {
        CONNECTED,
        CONNECTING,
        CONNECTION_ERROR,
        DISCONNECTED

    }
    private Object[] mArgs;
    private Status mStatus;

    public ConnectionStatus(Object[] args, Status status) {
        mArgs = args;
        mStatus = status;
    }

    public ConnectionStatus(Status status) {
        mStatus = status;
    }

    public Object[] getArgs() {
        return mArgs;
    }

    public Status getStatus() {
        return mStatus;
    }
}