package com.he.utils;

public enum ChatType {

    USER("user", "user message"),
    BOT("bot", "ai generated message");

    public final String type;
    public final String value;

    ChatType(String type, String value) {
        this.type = type;
        this.value = value;
    }
}
