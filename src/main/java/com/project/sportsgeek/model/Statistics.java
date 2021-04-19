package com.project.sportsgeek.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.sportsgeek.annotations.QueryHelperColumnName;
import com.project.sportsgeek.annotations.QueryHelperPrimaryKey;
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
@ApiModel(description = "Statistics Model")
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Statistics implements Serializable {
    @QueryHelperColumnName(name = "UserId")
    @QueryHelperPrimaryKey
    private int userId;
    private String firstName;
    private String lastName;
    private String userName;
    private int totalWinningPoints;
}
