package com.he.utils;

public enum SSEMsgType {

    MESSAGE("message", "single message"),
    ADD("add", "message add for stream"),
    FINISH("finish", "message finished"),
    DONE("done", "message done");

    public final String type;
    public final String value;

    SSEMsgType(String type, String value) {
        this.type = type;
        this.value = value;
    }
}
