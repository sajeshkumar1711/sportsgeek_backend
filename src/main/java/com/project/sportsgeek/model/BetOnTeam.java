package com.project.sportsgeek.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(description = "BetOnTeam Model")
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BetOnTeam implements Serializable {

    private int betTeamId;
    @NotNull(message = "User Id can't be Null")
    @Pattern(regexp = "[0-9]*")
    private int userId;
    @NotNull(message = "Match Id can't be Null")
    @Pattern(regexp = "[0-9]*")
    private int matchId;
    @NotNull(message = "Team Id can't be Null")
    @Pattern(regexp = "[0-9]*")
    private int teamId;
    @NotNull(message = "Bet Points can't be Null")
    @Pattern(regexp = "[0-9]*")
    private int betPoints;
    @Pattern(regexp = "[0-9]*")
    private int winningPoints;
}
