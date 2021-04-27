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
public class BetOnPlayer implements Serializable {

    private int betPlayerId;
    @NotNull(message = "User Id is Required")
    @Pattern(regexp = "[0-9]*")
    private int userId;
    @NotNull(message = "Match Id is Required")
    @Pattern(regexp = "[0-9]*")
    private int matchId;
    @Pattern(regexp = "[0-9]*")
    private int totalGamePoints;
}
