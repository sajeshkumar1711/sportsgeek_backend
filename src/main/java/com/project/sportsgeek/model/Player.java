package com.project.sportsgeek.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.annotation.RegEx;
import javax.validation.Valid;
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
@ApiModel(description = "Player Model")
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Player implements Serializable {

    private int playerId;
    @Pattern(regexp = "[0-9]*")
    private int teamId;
    @NotNull(message = "Player Name can't be Blank")
    private String name;
    @Pattern(regexp = "[0-9]*")
    private int typeId;
    @Valid
    private String profilePicture;
    @Pattern(regexp = "[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)")
    private Double credits;
}
