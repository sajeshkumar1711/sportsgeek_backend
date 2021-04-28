package com.project.sportsgeek.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.sql.Timestamp;

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
    @NotNull(message = "Match Name Can't Be Blank")
    private String name;
    @NotNull(message = "Match Start Date can't be Blank")
    private Timestamp startDateTime;
    @Pattern(regexp = "[0-9]*")
    private int venueId;
    @Pattern(regexp = "[0-9]*")
    private int team1;
    @Pattern(regexp = "[0-9]*")
    private int team2;
    @Pattern(regexp = "[0-9]*")
    private int winnerTeamId;
    private boolean resultStatus;
    @Pattern(regexp = "[0-9]*")
    private int minimumBet;
    @Pattern(regexp = "[0-9]*")
    private int tournamentId;
}
