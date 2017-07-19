package com.reframe.lapp.network;

import android.util.Log;

import com.google.gson.Gson;
import com.reframe.lapp.constants.Constants;
import com.reframe.lapp.network.socket.ConnectionStatus;
import com.reframe.lapp.network.socket.SocketEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import rx.Observable;
import rx.Subscription;

/**
 * Created by Aldo on 01-03-2017.
 */

public class SocketService {

    /*Here your custom events
    public static final String INIT_CHAT = "chat init";
    public static final String SET_STATUS = "set_status";
    public static final String STATUS_CHANGED = "status_changed";
    public static final String SEND_MESSAGE = "message send";
    public static final String DELIVERED_MESSAGE = "message delivered";
    public static final String RECEIVE_MESSAGE = "message new";
    */
    private static SocketService instance;
    private Socket socket;

    private Subscription profile;

    protected SocketService() {
        tryToConnect();
    }

    public static SocketService getInstance() {
        if(instance == null)
            instance = new SocketService();
        return instance;
    }
    private void tryToConnect() {
        try {
            socket = IO.socket(Constants.SOCKET_SERVER_URL);

        } catch(URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Observable<SocketEvent> socketEventObservable = Observable.create(subscriber -> {
        ArrayList<String> eventNames = new ArrayList<>();

        eventNames.add(Socket.EVENT_CONNECT);
        eventNames.add(Socket.EVENT_CONNECTING);
        eventNames.add(Socket.EVENT_CONNECT_ERROR);
        eventNames.add(Socket.EVENT_DISCONNECT);

        for(final String event: eventNames) {
            socket.on(event, args -> {
                if(!subscriber.isUnsubscribed())
                    subscriber.onNext(new SocketEvent(args, event));
            });
        }
        socket.connect();
    });

    public Observable<ConnectionStatus> connectionStatusObservable = socketEventObservable.filter(socketEvent -> {
        ArrayList connectionEvents = new ArrayList();
        connectionEvents.add(Socket.EVENT_CONNECT);
        connectionEvents.add(Socket.EVENT_CONNECTING);
        connectionEvents.add(Socket.EVENT_CONNECT_ERROR);
        connectionEvents.add(Socket.EVENT_DISCONNECT);

        return connectionEvents.contains(socketEvent.event);
    }).map(socketEvent -> {
        switch (socketEvent.event) {
            case Socket.EVENT_CONNECT:
                return new ConnectionStatus(socketEvent.args, ConnectionStatus.Status.CONNECTED);
            case Socket.EVENT_CONNECTING:
                return new ConnectionStatus(socketEvent.args, ConnectionStatus.Status.CONNECTING);
            case Socket.EVENT_CONNECT_ERROR:
                return new ConnectionStatus(socketEvent.args, ConnectionStatus.Status.CONNECTION_ERROR);
            case Socket.EVENT_DISCONNECT:
                return new ConnectionStatus(socketEvent.args, ConnectionStatus.Status.DISCONNECTED);
            default:
                return null;
        }
    });

    /*
    *Example subscribe to upstream observables
    public Observable<Message> messageObservable = socketEventObservable.filter(socketEvent -> socketEvent.event.equals(SocketService.RECEIVE_MESSAGE) || socketEvent.event.equals(SocketService.DELIVERED_MESSAGE))
            .doOnNext(socketEvent -> Log.d("NEW_MESSAGE", ": ".concat(socketEvent.args[0].toString())))
            .map(socketEvent -> new Gson().fromJson(socketEvent.args[0].toString(), Message.class));
    */
    /*
    * Example filtering observables
    public Observable<Message> getMessagesFrom(String channelId) {
        return messageObservable.filter(message -> message.getChannel().equals(channelId));
    }
    */

    public void sendMessage(Object message) {
        Gson gson = new Gson();
        JSONObject obj;
        try {
            obj = new JSONObject(gson.toJson(message));
            Log.d("SENDING_MESSAGE>", gson.toJson(message));
            //socket.emit(SocketService.SEND_MESSAGE, obj); <-- Here your custom event
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        socket.disconnect();
    }


}
