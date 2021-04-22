package com.project.sportsgeek.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.sportsgeek.jwtconfig.JwtTokenUtil;
import com.project.sportsgeek.model.profile.User;
import com.project.sportsgeek.model.profile.UserAtLogin;
import com.project.sportsgeek.model.profile.UserForLoginState;
import com.project.sportsgeek.model.profile.UserWinningAndLossingPoints;
import com.project.sportsgeek.model.profile.UserWithNewPassword;
import com.project.sportsgeek.model.profile.UserWithOtp;
import com.project.sportsgeek.model.profile.UserWithPassword;
import com.project.sportsgeek.response.Result;
import com.project.sportsgeek.service.UserService;

import ch.qos.logback.core.subst.Token;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/users",produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"), @ApiResponse(code = 404, message = "Bad Request")})
    public ResponseEntity<Result<List<User>>> getAllUsers() {
        Result<List<User>> userList = userService.findAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.valueOf(userList.getCode()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin','User')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"), @ApiResponse(code = 404, message = "Bad Request")})
    public ResponseEntity<Result<User>> getUserById(@PathVariable int id) throws Exception {
        Result<User> userResult = userService.findUserByUserId(id);
        return new ResponseEntity<>(userResult, HttpStatus.valueOf(userResult.getCode()));
    }
    @GetMapping("/user-name/{username}")
    @PreAuthorize("hasAnyRole('Admin','User')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"), @ApiResponse(code = 404, message = "Bad Request")})
    public ResponseEntity<Result<UserWithPassword>> getUserByUserName(@PathVariable String username) throws Exception {
        Result<UserWithPassword> userResult = userService.findUserByUserName(username);
        return new ResponseEntity<>(userResult, HttpStatus.valueOf(userResult.getCode()));
    }
    @PostMapping("/email")
    @PreAuthorize("hasAnyRole('Admin','User')")
    public ResponseEntity<Result<User>> getUserByEmailId(@RequestBody(required = true) User user) throws Exception {
        Result<User> userResult = userService.findUserByEmailId(user);
        return new ResponseEntity<>(userResult, HttpStatus.valueOf(userResult.getCode()));
    }
    @GetMapping("/{userId}/loosing-point")
    @PreAuthorize("hasAnyRole('Admin','User')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"), @ApiResponse(code = 404, message = "Bad Request")})
    public ResponseEntity<Result<UserWinningAndLossingPoints>> getUserLoosingPoints(@PathVariable int userId) throws Exception {
        Result<UserWinningAndLossingPoints> userResult = userService.findUserLoosingPoints(userId);
        return new ResponseEntity<>(userResult, HttpStatus.valueOf(userResult.getCode()));
    }
    @GetMapping("/{userId}/winning-point")
    @PreAuthorize("hasAnyRole('Admin','User')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"), @ApiResponse(code = 404, message = "Bad Request")})
    public ResponseEntity<Result<UserWinningAndLossingPoints>> getUserWinningPoints(@PathVariable int userId) throws Exception {
        Result<UserWinningAndLossingPoints> userResult = userService.findUserWinningPoints(userId);
        return new ResponseEntity<>(userResult, HttpStatus.valueOf(userResult.getCode()));
    }
    @GetMapping("/user-with-status/{status}")
    @PreAuthorize("hasAnyRole('Admin','User')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"), @ApiResponse(code = 404, message = "Bad Request")})
    public ResponseEntity<Result<List<User>>> getUsersByStatus(@PathVariable boolean status) throws Exception {
        Result<List<User>> userResult = userService.findUsersByStatus(status);
        return new ResponseEntity<>(userResult, HttpStatus.valueOf(userResult.getCode()));
    }
    @PostMapping("/authenticate-status")
    @PreAuthorize("hasAnyRole('Admin','User')")
    public ResponseEntity<Result<UserForLoginState>> authenticateStatus(@RequestBody(required = true) UserAtLogin userAtLogin) throws  Exception {
        Result<UserForLoginState> userResult = userService.authenticateStatus(userAtLogin);
        System.out.println("UserResult "+userResult);
        return new ResponseEntity(userResult, HttpStatus.valueOf(userResult.getCode()));
    }
    @PostMapping("/add-user")
    public ResponseEntity<Result<User>> addUsers(@RequestBody(required = true) UserWithPassword userWithPassword) throws  Exception {
       userWithPassword.setPassword(bCryptPasswordEncoder.encode(userWithPassword.getPassword()));
       Result<User> userResult = userService.addUser(userWithPassword);
        System.out.println(userWithPassword);
        return new ResponseEntity(userResult,HttpStatus.valueOf(userResult.getCode()));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<Result<Token>> authenticate(@RequestBody(required = true) UserAtLogin userAtLogin) throws  Exception {
        System.out.println(" Rest Authenticate");
        authenticate(userAtLogin.getUsername(), userAtLogin.getPassword());
        final UserDetails userDetails = userService.loadUserByUsername(userAtLogin.getUsername());
        System.out.println("User Details"+userDetails);
        final String token = jwtTokenUtil.generateToken(userDetails);
        System.out.println("UserToken"+token);
        return new ResponseEntity("{\"token\":\"" + token + "\"}",HttpStatus.valueOf(200));
    }
    @PutMapping("/update-password")
    @PreAuthorize("hasAnyRole('Admin','User')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated schema"), @ApiResponse(code = 404, message = "Schema not found"), @ApiResponse(code = 400, message = "Missing or invalid request body"), @ApiResponse(code = 500, message = "Internal error")})
    public ResponseEntity<Result<UserWithNewPassword>> updatePassword(@RequestBody(required = true) UserWithNewPassword userWithNewPassword) throws  Exception {
        System.out.println("Try-1:" + bCryptPasswordEncoder.encode("Rushabh@452"));
        System.out.println("Try-2:" + bCryptPasswordEncoder.encode("Rushabh@452"));
        Result<String> userResult = userService.updatePassword(userWithNewPassword);
        return new ResponseEntity(userResult,HttpStatus.valueOf(userResult.getCode()));
    }
    @PutMapping("/forget-password")
    @PreAuthorize("hasAnyRole('Admin','User')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated schema"), @ApiResponse(code = 404, message = "Schema not found"), @ApiResponse(code = 400, message = "Missing or invalid request body"), @ApiResponse(code = 500, message = "Internal error")})
    public ResponseEntity<Result<String>> forgetPassword(@RequestBody(required = true) UserWithOtp userWithOtp) throws  Exception {
        Result<String> userResult = userService.updateForgetPassword(userWithOtp);
        return new ResponseEntity(userResult,HttpStatus.valueOf(userResult.getCode()));
    }
    @PutMapping("/{userId}/update-user-role/{role}")
    @PreAuthorize("hasRole('Admin')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated schema"), @ApiResponse(code = 404, message = "Schema not found"), @ApiResponse(code = 400, message = "Missing or invalid request body"), @ApiResponse(code = 500, message = "Internal error")})
    public ResponseEntity<Result<String>> updateUserRole(@PathVariable int userId, @PathVariable int role) throws  Exception {
        Result<String> userResult = userService.updateUserRole(userId,role);
        return new ResponseEntity(userResult,HttpStatus.valueOf(userResult.getCode()));
    }
    @PutMapping("/{userId}/update-status/{status}")
    @PreAuthorize("hasRole('Admin')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated schema"), @ApiResponse(code = 404, message = "Schema not found"), @ApiResponse(code = 400, message = "Missing or invalid request body"), @ApiResponse(code = 500, message = "Internal error")})
    public ResponseEntity<Result<User>> updateStatus(@PathVariable int userId,@PathVariable boolean status) throws  Exception {
        Result<User> userResult = userService.updateStatus(userId,status);
        return new ResponseEntity(userResult,HttpStatus.valueOf(userResult.getCode()));
    }
    @DeleteMapping("/{userId}/delete-user")
    @PreAuthorize("hasRole('Admin')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully deleted schema"), @ApiResponse(code = 404, message = "Schema not found"), @ApiResponse(code = 409, message = "Schema is in use"), @ApiResponse(code = 500, message = "Error deleting schema")})
    public ResponseEntity<Result<User>> deleteUser(@PathVariable int userId) throws  Exception {
        Result<User> userResult = userService.deleteUser(userId);
        return new ResponseEntity(userResult,HttpStatus.valueOf(userResult.getCode()));
    }
    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyRole('Admin','User')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated schema"), @ApiResponse(code = 404, message = "Schema not found"), @ApiResponse(code = 400, message = "Missing or invalid request body"), @ApiResponse(code = 500, message = "Internal error")})
    public ResponseEntity<Result<User>> updateUser(@PathVariable int userId, @RequestBody(required = true) User user) throws  Exception {
        Result<User> userResult = userService.updateUser(userId,user);
        return new ResponseEntity(userResult,HttpStatus.valueOf(userResult.getCode()));
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
}
