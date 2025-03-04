package com.he.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.he.bean.ChatRecord;
import com.he.mapper.ChatRecordMapper;
import com.he.service.ChatRecordService;
import com.he.utils.ChatType;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatRecordServiceImpl implements ChatRecordService {

    @Resource
    private ChatRecordMapper chatRecordMapper;

    @Override
    public void saveChatRecord(String userName, String message, ChatType chatType) {
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setFamilyMember(userName);
        chatRecord.setContent(message);
        chatRecord.setChatType(chatType.type);
        chatRecord.setChatTime(LocalDateTime.now());

        chatRecordMapper.insert(chatRecord);
    }

    @Override
    public List<ChatRecord> getChatRecordList(String userName) {
        QueryWrapper<ChatRecord> query = new QueryWrapper<>();
        query.eq("userName", userName);

        return chatRecordMapper.selectList(query);

    }
}
