package org.example;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

public class GameLogic {
    private final Map<WebSocketSession, Character> players = new HashMap<>();
    private String roomCode = generateRoomCode(); // Genera el código de sala al iniciar la clase

    public void addPlayer(WebSocketSession session) {
        players.put(session, new Character(session.getId(), 0, 0));

        // Enviar el código de sala al cliente recién conectado
        try {
            session.sendMessage(new TextMessage("{\"type\":\"roomCode\", \"roomCodeValue\":\"" + roomCode + "\"}"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para generar el código de sala
    private String generateRoomCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * chars.length());
            code.append(chars.charAt(index));
        }
        return code.toString();
    }
}
