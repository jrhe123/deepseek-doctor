package com.he.controller;

import com.he.service.OllamaService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.ai.ollama.OllamaChatModel;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

// doc references: https://docs.spring.io/spring-ai/reference/api/chat/ollama-chat.html
@RestController
@RequestMapping("/ollama")
public class OllamaController {

    @Resource
    private OllamaService ollamaService;

    @GetMapping("/ai/chat")
    public Object generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return this.ollamaService.aiOllamaChat(message);
    }

    @GetMapping("/ai/generateStream")
    public List<String> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return this.ollamaService.aiOllamaStream(message);
    }
}