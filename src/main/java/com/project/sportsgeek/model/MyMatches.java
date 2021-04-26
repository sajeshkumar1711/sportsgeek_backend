package com.project.sportsgeek.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

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
@ApiModel(description = "MyMatches Model")
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyMatches implements Serializable {

	private int MatchId;
	@NotNull
	private String TeamName;
	@NotNull
	private int BetPoints;
	@NotNull
	private String Team1Short;
	private String Team1Logo;
	private String Team2Short;
	private String Team2Logo;
	@NotNull
	private String Venue;
	private Timestamp StartDatetime;
	private String WinnerTeamName;
	private int WinningPoints;
}
