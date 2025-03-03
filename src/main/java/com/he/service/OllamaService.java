package com.he.service;

import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;

import java.util.List;

public interface OllamaService {

    Object aiOllamaChat(String message);

    List<String> aiOllamaStream(String message);

    void aiOllamaStreamV2(String userName, String message);
}
