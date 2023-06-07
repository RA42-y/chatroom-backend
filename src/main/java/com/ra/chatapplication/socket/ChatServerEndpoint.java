package com.ra.chatapplication.socket;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket server endpoint for handling chat connections.
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{chatId}/{email}")
public class ChatServerEndpoint extends TextWebSocketHandler {

    /**
     * A map to store active chats and their corresponding user sessions.
     * The key is the chat ID, and the value is a map of user emails to their WebSocket sessions.
     */
    public static final Map<Long, Map<String, Session>> CHATS = new ConcurrentHashMap<>();

    /**
     * Triggered when a WebSocket connection is established.
     *
     * @param chatId  The ID of the chat
     * @param email   The email of the user
     * @param session The WebSocket session of the client
     */
    @OnOpen
    public synchronized void onOpen(@PathParam("chatId") long chatId, @PathParam("email") String email, Session session) {
        Map<String, Session> chat = CHATS.getOrDefault(chatId, new ConcurrentHashMap<>());
        chat.put(email, session);
        CHATS.put(chatId, chat);
        System.out.println(email);
        System.out.println(session);
        System.out.println(CHATS);
        System.out.println(chat);
        Gson gson = new Gson();

        for (Map.Entry<String, Session> entry : chat.entrySet()) {
            try {
                if (entry.getValue() != session) {
                    System.out.println(entry.getKey());
                    SocketTimestampedMessage msgOnline = new SocketTimestampedMessage(MessageType.ONLINE, entry.getKey());
                    System.out.println(msgOnline);
//                System.out.println(msgOnline.toJson());
                    session.getBasicRemote().sendText(gson.toJson(msgOnline));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        SocketTimestampedMessage msgJoin = new SocketTimestampedMessage(MessageType.JOIN, email);
//        System.out.println(msgJoin);
        for (Map.Entry<String, Session> entry : chat.entrySet()) {
            try {
                System.out.println(msgJoin);
                entry.getValue().getBasicRemote().sendText(gson.toJson(msgJoin));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Triggered when a message is received from a WebSocket client.
     *
     * @param chatId  The ID of the chat
     * @param email   The email of the user
     * @param message The received message
     */
    @OnMessage
    public synchronized void onMessage(@PathParam("chatId") long chatId, @PathParam("email") String email, String message) {
        Map<String, Session> chat = CHATS.get(chatId);

        SocketTimestampedMessage msg = new SocketTimestampedMessage(MessageType.MESSAGE, email, message);
        Gson gson = new Gson();

        for (Map.Entry<String, Session> entry : chat.entrySet()) {
            try {
                System.out.println(msg);
                entry.getValue().getBasicRemote().sendText(gson.toJson(msg));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Triggered when a WebSocket connection is closed.
     *
     * @param chatId the ID of the chat
     * @param email  the email of the user
     */
    @OnClose
    public synchronized void onClose(@PathParam("chatId") long chatId, @PathParam("email") String email) {
        Map<String, Session> chat = CHATS.get(chatId);

        chat.remove(email);

        SocketTimestampedMessage msgLeave = new SocketTimestampedMessage(MessageType.LEAVE, email);
        Gson gson = new Gson();

        for (Map.Entry<String, Session> entry : chat.entrySet()) {
            try {
                System.out.println(msgLeave);
                entry.getValue().getBasicRemote().sendText(gson.toJson(msgLeave));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Triggered when a communication error occurs with a WebSocket connection.
     *
     * @param chatId    The ID of the chat
     * @param session   The WebSocket session of the client
     * @param throwable the thrown error
     */
    @OnError
    public synchronized void onError(@PathParam("chatId") long chatId, Session session, Throwable throwable) {
        try {
            System.out.println("close on error");
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("onError Exception", e);
        }
        log.info("Throwable msg " + throwable.getMessage());
    }
}
