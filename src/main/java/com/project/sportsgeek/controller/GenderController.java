package com.project.sportsgeek.controller;

import com.project.sportsgeek.model.Gender;
import com.project.sportsgeek.response.Result;
import com.project.sportsgeek.service.GenderService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/genders", produces = MediaType.APPLICATION_JSON_VALUE)
public class GenderController {

    @Autowired
    GenderService genderService;

    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"), @ApiResponse(code = 404, message = "Bad Request")})
    public ResponseEntity<Result<List<Gender>>> getAllGenders() {
        Result<List<Gender>> genderList = genderService.findAllGender();
        return new ResponseEntity<>(genderList, HttpStatus.valueOf(genderList.getCode()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin','User')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"), @ApiResponse(code = 404, message = "Bad Request")})
    public ResponseEntity<Result<Gender>> getGenderById(@PathVariable int id) throws Exception {
        Result<Gender> genderList = genderService.findGenderById(id);
        return new ResponseEntity<>(genderList, HttpStatus.valueOf(genderList.getCode()));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('Admin','User')")
    public ResponseEntity<Result<Gender>> addGender(@RequestBody(required = true) Gender gender) throws Exception {
        Result<Gender> genderResult = genderService.addGender(gender);
        return new ResponseEntity(genderResult, HttpStatus.valueOf(genderResult.getCode()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin','User')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated schema"), @ApiResponse(code = 404, message = "Schema not found"), @ApiResponse(code = 400, message = "Missing or invalid request body"), @ApiResponse(code = 500, message = "Internal error")})
    public ResponseEntity<Result<Gender>> updateGender(@PathVariable int id, @RequestBody(required = true) Gender gender) throws Exception {
        Result<Gender> genderResult = genderService.updateGender(id, gender);
        return new ResponseEntity(genderResult, HttpStatus.valueOf(genderResult.getCode()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully deleted schema"), @ApiResponse(code = 404, message = "Schema not found"), @ApiResponse(code = 409, message = "Schema is in use"), @ApiResponse(code = 500, message = "Error deleting schema")})
    public ResponseEntity<Result<Gender>> deleteGenderById(@PathVariable int id) throws Exception {
        Result<Integer> integerResult = genderService.deleteGender(id);
        return new ResponseEntity(integerResult, HttpStatus.valueOf(integerResult.getCode()));
    }
}
