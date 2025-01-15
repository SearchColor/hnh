package com.example.hnh.global.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(force = true)
public class ChatRoom {

    private final String roomId;
    private final String name;
    private final Set<WebSocketSession> sessions = new HashSet<>();


    @Builder
    public ChatRoom (String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        if(chatMessage.getType().equals(ChatMessage.MessageType.ENTER)){
            if (!sessions.contains(session)){
                sessions.add(session);
                chatMessage.setMessage(chatMessage.getSender() + "님이 입장하셨습니다.");
            }
        }
        if (chatMessage.getType().equals(ChatMessage.MessageType.EXIT)){
            sessions.remove(session);
            chatService.sendMessage(session , chatMessage.getSender() +"님이 퇴장하셨습니다.");
        }
        sendMessage(chatMessage.getMessage(), chatService);
    }

    public <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream().forEach(session ->chatService.sendMessage(session, message));
    }

}
