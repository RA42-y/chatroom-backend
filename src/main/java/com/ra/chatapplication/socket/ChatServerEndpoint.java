package com.ra.chatapplication.socket;

import com.google.gson.Gson;
import com.ra.chatapplication.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint("/websocket/{chatId}/{email}")
public class ChatServerEndpoint extends TextWebSocketHandler {

    @Autowired
    private UserService userService;

    public static final Map<Long, Map<String, Session>> CHATS = new ConcurrentHashMap<>();

    /**
     * Triggered when connection is established
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
     * Triggered when the client receives data from the server
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
     * Triggered when connection is closed
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
     * Triggered when a communication error occurs
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
