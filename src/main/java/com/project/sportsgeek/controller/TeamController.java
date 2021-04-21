package com.project.sportsgeek.controller;

import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.Team;
import com.project.sportsgeek.model.Venue;
import com.project.sportsgeek.response.Result;
import com.project.sportsgeek.service.TeamService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping(path = "/teams",produces = MediaType.APPLICATION_JSON_VALUE)
public class TeamController {

    @Autowired
    TeamService teamService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Venue.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Result<List<Team>>> getAllTeam() {
        Result<List<Team>> teamList = teamService.findAllTeam();
        return new ResponseEntity<>(teamList, HttpStatus.valueOf(teamList.getCode()));
    }
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Venue.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Result<Team>> getTeamById(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id) throws Exception {
        Result<Team> teamList = teamService.findTeamById(id);
        return new ResponseEntity<>(teamList, HttpStatus.valueOf(teamList.getCode()));
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Venue.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
//    @RequestBody(required = true) @Valid Team team
    public ResponseEntity<Result<Team>> addTeam( @RequestParam("name") String name,@RequestParam("shortName") String shortName,@RequestParam("teamLogo") MultipartFile multipartFile) throws  Exception {
       String filename = multipartFile.getOriginalFilename();
        Team team = Team.builder()
                .name(name)
                .shortName(shortName)
                .teamLogo(filename).build();
        Result<Team> teamResult = teamService.addTeam(team,multipartFile);
        return new ResponseEntity(teamResult,HttpStatus.valueOf(teamResult.getCode()));
    }
    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Venue.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Result<Team>> updateTeam(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id,@RequestBody(required = true) @Valid Team team) throws Exception {
        Result<Team> teamResult = teamService.updateTeam(id,team);
        return new ResponseEntity(teamResult,HttpStatus.valueOf(teamResult.getCode()));
    }

    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Venue.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Result<Team>> deleteTeamById(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id) throws Exception {
       Result<Integer> teamResult =  teamService.deleteTeam(id);
        return new ResponseEntity(teamResult,HttpStatus.valueOf(teamResult.getCode()));
    }
}
