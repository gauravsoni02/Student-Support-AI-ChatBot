package com.example.bot.state;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConversationStore {
    private final ConcurrentHashMap<String, String> memory = new ConcurrentHashMap<>();

    public void save(String sessionId, String lastMessage) {
        memory.put(sessionId, lastMessage);
    }

    public String recall(String sessionId) {
        return memory.get(sessionId);
    }
}
