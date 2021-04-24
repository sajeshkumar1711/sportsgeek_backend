package com.project.sportsgeek.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(description = "BetOnPlayer Response Model")
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BetOnPlayerResponse implements Serializable {

    private int betPlayerId;
    private String userName;
    private String firstName;
    private String lastName;
    private String team1;
    private String team2;
    private Timestamp startDateTime;
    private int totalGamePoints;
}
