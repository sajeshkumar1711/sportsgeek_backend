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
@ApiModel(description = "BetOnPlayer Model")
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BetOnPlayerDetails implements Serializable {

    private int betPlayerId;
    @NotNull(message = "Player No is Required")
    @Pattern(regexp = "[0-9]*")
    private int playerNo;
    @NotNull(message = "Player Id is Required")
    @Pattern(regexp = "[0-9]*")
    private int playerId;
    @Pattern(regexp = "[0-9]*")
    private int playerPoints;
}
