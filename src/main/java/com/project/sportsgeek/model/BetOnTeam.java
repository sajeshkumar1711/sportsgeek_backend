package com.project.sportsgeek.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotNull;
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
    @NotNull
    private int userId;
    @NotNull
    private int matchId;
    @NotNull
    private int teamId;
    private int betPoints;
    private int winningPoints;
}
