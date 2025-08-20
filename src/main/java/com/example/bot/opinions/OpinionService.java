package com.example.bot.opinions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class OpinionService {
    private Map<String, Object> data;

    public OpinionService() {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            data = mapper.readValue(new File("src/main/resources/opinions.yml"), Map.class);
        } catch (IOException e) {
            data = Map.of();
        }
    }

    public Map<String, Object> getData() {
        return data;
    }
}
