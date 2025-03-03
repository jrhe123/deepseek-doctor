package com.he.controller;

import com.he.utils.SSEMsgType;
import com.he.utils.SSEServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequestMapping(value = "sse")
public class SSEController {

    @GetMapping(path = "connect", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter connect(@RequestParam String userId) {
        SseEmitter sse = SSEServer.connect(userId);
        return sse;
    }

    @GetMapping("sendMessage")
    public Object sendMessage(@RequestParam String userId, @RequestParam String message) {
        SSEServer.sendMessage(userId, message, SSEMsgType.MESSAGE);
        return "OK";
    }

    @GetMapping("sendMessageAll")
    public Object sendMessage(@RequestParam String message) {
        SSEServer.sendMessageToAllUsers(message);
        return "OK";
    }

    @GetMapping("stop")
    public Object stopServer(@RequestParam String userId) {
        SSEServer.stopServer(userId);
        return "OK";
    }

    @GetMapping("getOnlineCount")
    public Object getOnlineCount() {
        return SSEServer.getOnlineCount();
    }
}
