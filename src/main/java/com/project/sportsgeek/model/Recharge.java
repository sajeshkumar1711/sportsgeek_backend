package com.project.sportsgeek.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Recharge implements Serializable {
    private int RechargeId;
    private int UserId;
    private String UserName;
    @NotNull
    private  int Points;
    private Timestamp RechargeDate;
}
