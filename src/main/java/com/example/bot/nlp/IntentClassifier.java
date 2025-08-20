package com.example.bot.nlp;

import org.springframework.stereotype.Service;

@Service
public class IntentClassifier {
    public String classify(String msg) {
        if (msg.contains("exam")) return "exams";
        if (msg.contains("library")) return "library";
        if (msg.contains("admission")) return "admissions";
        return null;
    }
}
