package com.he.service.impl;

import com.he.service.OllamaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OllamaServiceImpl implements OllamaService {

    private final OllamaChatModel chatModel;

    @Autowired
    public OllamaServiceImpl(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public Object aiOllamaChat(String message) {
        String callResponse = this.chatModel.call(message);
        return callResponse;
    }

    @Override
    public List<String> aiOllamaStream(String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        Flux<ChatResponse> streamResponse = this.chatModel.stream(prompt);
        List<String> list = streamResponse.toStream().map(chatResponse -> {
            String text = chatResponse.getResult().getOutput().getText();
            // log.info(text);

            return text;
        }).collect(Collectors.toList());
        return list;
    }
}
