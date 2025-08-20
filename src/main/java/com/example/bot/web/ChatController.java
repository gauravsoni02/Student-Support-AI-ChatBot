package com.example.bot.web;

import com.example.bot.service.PipelineService;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final PipelineService pipelineService;

    // Hardcoded knowledge base (can be expanded or removed later)
    private static final Map<String, String> knowledgeBase = Map.ofEntries(
            Map.entry("library timings", "The library is open from 9 AM to 8 PM."),
            Map.entry("exam timetable", "The exam timetable will be posted on the notice board."),
            Map.entry("sports facilities", "The sports complex is open from 6 AM to 9 PM."),
            Map.entry("canteen timings", "The canteen operates from 8 AM to 7 PM."),
            Map.entry("hostel office", "The hostel office is located near the main gate.")
    );

    public ChatController(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    @PostMapping
    public Map<String, String> chat(@RequestBody Map<String, String> payload) {
        String sessionId = payload.getOrDefault("sessionId", "default-session");
        String userMessage = payload.get("message");  // <-- fixed (was "userMessage")

        if (userMessage == null || userMessage.isBlank()) {
            return Map.of("botMessage", "I didn’t understand your message.");
        }

        // 1. Check hardcoded knowledge base
        String response = knowledgeBase.get(userMessage.toLowerCase());
        if (response != null) {
            return Map.of("botMessage", response);
        }

        // 2. Otherwise go through the pipeline (KnowledgeService → AI → fallback)
        String botMessage = pipelineService.handleMessage(sessionId, userMessage);

        // 3. Always return a safe non-null reply
        if (botMessage == null || botMessage.isEmpty()) {
            botMessage = "Sorry, I couldn’t find anything on that right now.";
        }

        return Map.of("botMessage", botMessage);
    }
}
