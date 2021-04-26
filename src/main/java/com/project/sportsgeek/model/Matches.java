package com.project.sportsgeek.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(description = "Matches Model")
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Matches implements Serializable {

    private int matchId;
    private int tournamentId;
    private String name;
    private Timestamp startDateTime;
    private int venueId;
    private int team1;
    private int team2;
    private int winnerTeamId;
    private int resultStatus;
    private int minimumBet;
}
