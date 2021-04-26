package com.project.sportsgeek.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(description = "BetOnPlayerDetails Response Model")
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BetPlayerDetailsResponse implements Serializable {


    private int betPlayerId;
    private int playerNo;
    private String name;
    private String team;
    private String type;
    private String profilePicture;
    private String userName;
    private String firstName;
    private String lastName;
    private int playerPoints;
}
