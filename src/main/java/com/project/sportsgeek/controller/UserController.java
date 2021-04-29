package com.project.sportsgeek.controller;

import com.project.sportsgeek.jwtconfig.JwtTokenUtil;
import com.project.sportsgeek.model.profile.*;
import com.project.sportsgeek.response.Result;
import com.project.sportsgeek.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

//	---------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- SELECT CONTROLLER -------------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------

    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Bad Request")})
    public ResponseEntity<Result<List<User>>> getAllUsers() {
        Result<List<User>> userList = userService.findAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.valueOf(userList.getCode()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin','User')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Bad Request")})
    public ResponseEntity<Result<User>> getUserById(@PathVariable int id) throws Exception {
        Result<User> userResult = userService.findUserByUserId(id);
        return new ResponseEntity<>(userResult, HttpStatus.valueOf(userResult.getCode()));
    }

    @GetMapping("/user-name/{username}")
    @PreAuthorize("hasAnyRole('Admin','User')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Bad Request")})
    public ResponseEntity<Result<UserWithPassword>> getUserByUserName(@PathVariable String username) throws Exception {
        Result<UserWithPassword> userResult = userService.findUserByUserName(username);
        return new ResponseEntity<>(userResult, HttpStatus.valueOf(userResult.getCode()));
    }

    @GetMapping("/{userId}/loosing-point")
    @PreAuthorize("hasAnyRole('Admin','User')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Bad Request")})
    public ResponseEntity<Result<UserWinningAndLossingPoints>> getUserLoosingPoints(@PathVariable int userId)
            throws Exception {
        Result<UserWinningAndLossingPoints> userResult = userService.findUserLoosingPoints(userId);
        return new ResponseEntity<>(userResult, HttpStatus.valueOf(userResult.getCode()));
    }

    @GetMapping("/{userId}/winning-point")
    @PreAuthorize("hasAnyRole('Admin','User')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Bad Request")})
    public ResponseEntity<Result<UserWinningAndLossingPoints>> getUserWinningPoints(@PathVariable int userId)
            throws Exception {
        Result<UserWinningAndLossingPoints> userResult = userService.findUserWinningPoints(userId);
        return new ResponseEntity<>(userResult, HttpStatus.valueOf(userResult.getCode()));
    }

    @GetMapping("/user-with-status/{status}")
    @PreAuthorize("hasAnyRole('Admin','User')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Bad Request")})
    public ResponseEntity<Result<List<User>>> getUsersByStatus(@PathVariable boolean status) throws Exception {
        Result<List<User>> userResult = userService.findUsersByStatus(status);
        return new ResponseEntity<>(userResult, HttpStatus.valueOf(userResult.getCode()));
    }

//	---------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- INSERT CONTROLLER --------------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------

    @PostMapping("/add-user")
    public ResponseEntity<Result<User>> addUsers(@RequestBody(required = true) UserWithPassword userWithPassword)
            throws Exception {
        userWithPassword.setPassword(bCryptPasswordEncoder.encode(userWithPassword.getPassword()));
        Result<User> userResult = userService.addUser(userWithPassword);
        System.out.println(userWithPassword);
        return new ResponseEntity(userResult, HttpStatus.valueOf(userResult.getCode()));
    }

//	---------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- UPDATE CONTROLLER --------------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------

    @PutMapping("/update-password")
    @PreAuthorize("hasAnyRole('Admin','User')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated schema"),
            @ApiResponse(code = 404, message = "Schema not found"),
            @ApiResponse(code = 400, message = "Missing or invalid request body"),
            @ApiResponse(code = 500, message = "Internal error")})
    public ResponseEntity<Result<UserWithNewPassword>> updatePassword(
            @RequestBody(required = true) UserWithNewPassword userWithNewPassword) throws Exception {
        System.out.println(userWithNewPassword);
        Result<String> userResult = userService.updatePassword(userWithNewPassword);
        return new ResponseEntity(userResult, HttpStatus.valueOf(userResult.getCode()));
    }

    @PutMapping("/{userId}/update-status/{status}")
    @PreAuthorize("hasRole('Admin')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated schema"),
            @ApiResponse(code = 404, message = "Schema not found"),
            @ApiResponse(code = 400, message = "Missing or invalid request body"),
            @ApiResponse(code = 500, message = "Internal error")})
    public ResponseEntity<Result<User>> updateStatus(@PathVariable int userId, @PathVariable boolean status)
            throws Exception {
        Result<User> userResult = userService.updateStatus(userId, status);
        return new ResponseEntity(userResult, HttpStatus.valueOf(userResult.getCode()));
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyRole('Admin','User')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated schema"),
            @ApiResponse(code = 404, message = "Schema not found"),
            @ApiResponse(code = 400, message = "Missing or invalid request body"),
            @ApiResponse(code = 500, message = "Internal error")})
    public ResponseEntity<Result<User>> updateUser(@PathVariable int userId, @RequestBody(required = true) User user)
            throws Exception {
        Result<User> userResult = userService.updateUser(userId, user);
        return new ResponseEntity(userResult, HttpStatus.valueOf(userResult.getCode()));
    }

    @PutMapping("/{userId}/update-user-role/{role}")
    @PreAuthorize("hasRole('Admin')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated schema"),
            @ApiResponse(code = 404, message = "Schema not found"),
            @ApiResponse(code = 400, message = "Missing or invalid request body"),
            @ApiResponse(code = 500, message = "Internal error")})
    public ResponseEntity<Result<String>> updateUserRole(@PathVariable int userId, @PathVariable int role)
            throws Exception {
        Result<String> userResult = userService.updateUserRole(userId, role);
        return new ResponseEntity(userResult, HttpStatus.valueOf(userResult.getCode()));
    }

//	------------------------------------------------- FORGOT PASSWORD CONTROLLER -----------------------------------------------------------------

    @PostMapping("/forget-password")
    @PreAuthorize("hasAnyRole('Admin','User')")
    public ResponseEntity<Result<User>> getUserByEmailId(@RequestBody(required = true) User user) throws Exception {
        Result<User> userResult = userService.findUserByEmailId(user);
        return new ResponseEntity<>(userResult, HttpStatus.valueOf(userResult.getCode()));
    }

    @PutMapping("/forget-password")
    @PreAuthorize("hasAnyRole('Admin','User')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated schema"),
            @ApiResponse(code = 404, message = "Schema not found"),
            @ApiResponse(code = 400, message = "Missing or invalid request body"),
            @ApiResponse(code = 500, message = "Internal error")})
    public ResponseEntity<Result<String>> forgetPassword(@RequestBody(required = true) UserWithOtp userWithOtp)
            throws Exception {
        Result<String> userResult = userService.updateForgetPassword(userWithOtp);
        return new ResponseEntity(userResult, HttpStatus.valueOf(userResult.getCode()));
    }

//	---------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- DELETE CONTROLLER -------------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------

    @DeleteMapping("/{userId}/delete-user")
    @PreAuthorize("hasRole('Admin')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully deleted schema"),
            @ApiResponse(code = 404, message = "Schema not found"),
            @ApiResponse(code = 409, message = "Schema is in use"),
            @ApiResponse(code = 500, message = "Error deleting schema")})
    public ResponseEntity<Result<User>> deleteUser(@PathVariable int userId) throws Exception {
        Result<User> userResult = userService.deleteUser(userId);
        return new ResponseEntity(userResult, HttpStatus.valueOf(userResult.getCode()));
    }

//	---------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- AUTHENTICATION CONTROLLER -----------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------

    @PostMapping("/authenticate")
    public ResponseEntity<Result<UserForLoginState>> authenticateStatus(
            @RequestBody(required = true) UserAtLogin userAtLogin) throws Exception {
        String token = generateToken(userAtLogin);
        Result<UserForLoginState> userResult = userService.authenticate(userAtLogin);
        UserForLoginState u = userResult.getData();
        u.setToken(token);
        userResult.setData(u);
        System.out.println("UserResult " + userResult);
        return new ResponseEntity(userResult, HttpStatus.valueOf(userResult.getCode()));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            System.out.println("Authenticate method");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            System.out.println("AUthenticate called");
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public String generateToken(@RequestBody(required = true) UserAtLogin userAtLogin) throws Exception {
        System.out.println(" Rest Authenticate");
        authenticate(userAtLogin.getUsername(), userAtLogin.getPassword());
        final UserDetails userDetails = userService.loadUserByUsername(userAtLogin.getUsername());
        System.out.println("User Details" + userDetails);
        final String token = jwtTokenUtil.generateToken(userDetails);
        System.out.println("UserToken" + token);
        return token;
    }
}
