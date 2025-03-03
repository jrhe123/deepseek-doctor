package com.he.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Slf4j
public class SSEServer {

    // 关联用户id 和 sseEmitter
    // 问题：sseEmitter 能不能放在redis中和userId进行关联
    // 问题：sseEmitter 如何在集群Springboot中存在
    private static Map<String, SseEmitter> sseClients = new ConcurrentHashMap<>();

    public static void sendMessage(String userId, String message, SSEMsgType msgType) {
        if (CollectionUtils.isEmpty(sseClients)) {
            return;
        }

        if (sseClients.containsKey(userId)) {
            SseEmitter sseEmitter = sseClients.get(userId);
            sendEmitterMessage(sseEmitter, userId, message, msgType);
        }
    }

    public static void sendMessageToAllUsers(String message) {
        if (CollectionUtils.isEmpty(sseClients)) {
            return;
        }

        sseClients.forEach((key, value) -> {
            sendEmitterMessage(value, key, message, SSEMsgType.MESSAGE);
        });
    }

    public static void sendEmitterMessage(SseEmitter sseEmitter,
                                    String userId,
                                    String message,
                                    SSEMsgType msgType) {
        try {
            SseEmitter.SseEventBuilder msg = SseEmitter.event()
                    .id(userId)
                    .name(msgType.type)
                    .data(message);
            sseEmitter.send(msg);
        } catch (IOException e) {
            removeConnection(userId);
            log.error("sse连接异常，用户id: {}", userId);
        }
    }

    public static SseEmitter connect(String userId) {
        // 0L: 永不过期
        SseEmitter sseEmitter = new SseEmitter(0L);

        sseEmitter.onCompletion(completionCallback(userId));
        sseEmitter.onError(errorCallback(userId));
        sseEmitter.onTimeout(timeoutCallback(userId));

        sseClients.put(userId, sseEmitter);
        log.info("建立新的sse连接，用户id: {}", userId);

        return sseEmitter;
    }

    public static void removeConnection(String userId) {
        sseClients.remove(userId);
        log.info("移除sse连接，用户id: {}", userId);
    }

    private static Runnable completionCallback(String userId) {
        return () -> {
            log.info("完成sse连接，用户id: {}", userId);
            removeConnection(userId);
        };
    }

    private static Consumer<Throwable> errorCallback(String userId) {
        return Throwable -> {
            log.info("错误sse连接，用户id: {}", userId);
            removeConnection(userId);
        };
    }


    private static Runnable timeoutCallback(String userId) {
        return () -> {
            log.info("超时sse连接，用户id: {}", userId);
            removeConnection(userId);
        };
    }

}
