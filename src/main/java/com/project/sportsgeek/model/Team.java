package com.project.sportsgeek.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.sportsgeek.annotations.QueryHelperColumnName;
import com.project.sportsgeek.annotations.QueryHelperPrimaryKey;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@ApiModel(description = "Team Model")
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Team implements Serializable {
    @QueryHelperColumnName(name = "TeamId")
    @QueryHelperPrimaryKey
    private int teamId;
    @NotNull
    private String name;
    @NotNull
    private String shortName;
    private String teamLogo;
}
