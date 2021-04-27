package com.project.sportsgeek.model.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWinningAndLossingPoints implements Serializable {

    private int UserId;
    private int WinningPoints;
    private int LoosingPoints;
}
