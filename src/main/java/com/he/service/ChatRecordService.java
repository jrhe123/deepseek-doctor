package com.he.service;

import com.he.bean.ChatRecord;
import com.he.utils.ChatType;

public interface ChatRecordService {

    public void saveChatRecord(String userName, String message, ChatType chatType);
}
