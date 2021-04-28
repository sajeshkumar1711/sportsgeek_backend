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
public class PublicChat {
    private int publicChatId;
    private int userId;
    private String message;
    private boolean status;
    private Timestamp chatTimestamp;
}
