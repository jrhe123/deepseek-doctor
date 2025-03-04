package com.he.service;

import com.he.bean.ChatRecord;
import com.he.utils.ChatType;

import java.util.List;

public interface ChatRecordService {

    public void saveChatRecord(String userName, String message, ChatType chatType);

    public List<ChatRecord> getChatRecordList(String userName);
}
