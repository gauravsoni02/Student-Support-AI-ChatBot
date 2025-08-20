package com.example.bot.knowledge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class KnowledgeService {
    private Map<String, Object> data;

    public KnowledgeService() {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            data = mapper.readValue(new File("src/main/resources/knowledge.yml"), Map.class);
        } catch (IOException e) {
            data = Map.of();
        }
    }

    public String lookup(String intent) {
        if (intent == null) return null;
        Object info = data.get(intent);
        return info != null ? info.toString() : null;
    }
}
