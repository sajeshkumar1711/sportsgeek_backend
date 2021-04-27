package com.project.sportsgeek.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.Valid;
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

    private int teamId;
    @NotNull(message = "Team name can't be blank")
    private String name;
    @NotNull(message = "Team short name can't be blank")
    private String shortName;
    @Valid
    private String teamLogo;
}
