package com.example.bot.service;

import com.example.bot.ai.AIService;
import com.example.bot.knowledge.KnowledgeService;
import com.example.bot.opinions.OpinionService;
import com.example.bot.state.ConversationStore;
import com.example.bot.nlp.IntentClassifier;
import org.springframework.stereotype.Service;

@Service
public class PipelineService {
    private final KnowledgeService knowledge;
    private final OpinionService opinions;
    private final AIService aiService;
    private final ConversationStore store;
    private final IntentClassifier classifier;

    public PipelineService(
            KnowledgeService knowledge,
            OpinionService opinions,
            AIService aiService,
            ConversationStore store,
            IntentClassifier classifier
    ) {
        this.knowledge = knowledge;
        this.opinions = opinions;
        this.aiService = aiService;
        this.store = store;
        this.classifier = classifier;
    }

    public String handleMessage(String sessionId, String userMessage) {
        String lower = userMessage.toLowerCase();
        String intent = classifier.classify(lower);

        // 1. Knowledge base check
        String kbResponse = knowledge.lookup(intent);
        if (kbResponse != null) {
            return formatResponse(kbResponse);
        }

        // 2. AI fallback
        String aiResponse = aiService.askAI(userMessage);
        if (aiResponse != null && !aiResponse.isEmpty()) {
            return formatResponse(aiResponse);
        }

        // 3. Default
        return "Sorry, I couldn’t find anything on that right now.";
    }

    private String formatResponse(String response) {
        if (response == null) return "";

        String formatted = response
                // Remove markdown symbols
                .replaceAll("\\*\\*", "")
                .replaceAll("_", "")
                .replaceAll("`", "")

                // Add line breaks before numbered lists (handles 1), 2., 3- right after colon too)
                .replaceAll("(\\d+[).-])", "\n\n$1 ")

                // Add line breaks before bullets (- or •)
                .replaceAll("[-•]\\s*", "\n\n• ")

                // Split sentences for readability
                .replaceAll("([.!?])\\s+([A-Z])", "$1\n\n$2")

                // Remove extra blank lines
                .replaceAll("\\n{3,}", "\n\n")
                .trim();

        return formatted;
    }

}
