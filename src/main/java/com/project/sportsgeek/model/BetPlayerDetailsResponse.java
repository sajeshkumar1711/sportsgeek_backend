package com.project.sportsgeek.model;

import java.io.Serializable;

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
