package com.he.service.impl;

import com.he.service.ChatRecordService;
import com.he.service.OllamaService;
import com.he.utils.ChatType;
import com.he.utils.SSEMsgType;
import com.he.utils.SSEServer;
import jakarta.annotation.Resource;
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

    @Resource
    private ChatRecordService chatRecordService;

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

    @Override
    public void aiOllamaStreamV2(String userName, String message) {
        // save user message
        chatRecordService.saveChatRecord(userName, message, ChatType.USER);

        Prompt prompt = new Prompt(new UserMessage(message));
        Flux<ChatResponse> streamResponse = this.chatModel.stream(prompt);
        List<String> list = streamResponse.toStream().map(chatResponse -> {
            String text = chatResponse.getResult().getOutput().getText();

            SSEServer.sendMessage(userName, text, SSEMsgType.ADD);

            return text;
        }).collect(Collectors.toList());

        SSEServer.sendMessage(userName, "DONE", SSEMsgType.FINISH);

        // save ai message
        String htmlResult = "";
        for (String s : list) {
            htmlResult += s;
        }
        chatRecordService.saveChatRecord(userName, htmlResult, ChatType.BOT);
    }
}
