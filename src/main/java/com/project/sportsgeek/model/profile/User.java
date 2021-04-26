package com.project.sportsgeek.model.profile;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
	
    private int UserId;
    @NotNull
    private String FirstName;
    @NotNull
    private String LastName;
    @NotNull
    private int GenderId;
    @NotNull
    private String Username;
    private String Email;
    private String MobileNumber;
    private String ProfilePicture;
    @NotNull
    private int RoleId;
    private  int AvailablePoints;
    private boolean status;
    
    public User(UserWithPassword userWithPassword) {
        super();
            UserId = userWithPassword.getUserId();
            FirstName = userWithPassword.getFirstName();
            LastName = userWithPassword.getLastName();
            GenderId = userWithPassword.getGenderId();
            Username = userWithPassword.getUsername();
            ProfilePicture = userWithPassword.getProfilePicture();
            RoleId = userWithPassword.getRoleId();
            AvailablePoints = userWithPassword.getAvailablePoints();
            status = userWithPassword.isStatus();
        }

}
