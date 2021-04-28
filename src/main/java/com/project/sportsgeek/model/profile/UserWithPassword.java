package com.project.sportsgeek.model.profile;

import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserWithPassword extends User {

    @NotNull
    String Password;
    String Role;
}
