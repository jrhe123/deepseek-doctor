package com.he.bean;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class ChatRecord {

    private String id;
    private String content;
    private String chatType;
    private LocalDateTime chatTime;
    private String familyMember;

}
