package com.project.sportsgeek.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrivateChat {
    private int privateChatId;
    private int fromUserId;
    private int toUserId;
    private String message;
    private Timestamp chatTimestamp;
}
