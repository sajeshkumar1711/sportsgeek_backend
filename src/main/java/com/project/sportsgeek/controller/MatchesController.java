package com.project.sportsgeek.controller;

import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.Matches;
import com.project.sportsgeek.model.MatchesWithVenue;
import com.project.sportsgeek.model.Player;
import com.project.sportsgeek.model.Venue;
import com.project.sportsgeek.response.Result;
import com.project.sportsgeek.service.MatchesService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(path = "/matches",produces = MediaType.APPLICATION_JSON_VALUE)
public class MatchesController {

    @Autowired
    MatchesService matchesService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Venue.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<List<MatchesWithVenue>>> getAllMatches() {
        Result<List<MatchesWithVenue>> matchesList = matchesService.findAllMatches();
        return new ResponseEntity<>(matchesList, HttpStatus.valueOf(matchesList.getCode()));
    }
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Venue.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<MatchesWithVenue>> getMatchesById(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id) throws Exception {
        Result<MatchesWithVenue> matchesList = matchesService.findMatchesById(id);
        return new ResponseEntity<>(matchesList, HttpStatus.valueOf(matchesList.getCode()));
    }
    @GetMapping(value = "/tournament/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Venue.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<List<MatchesWithVenue>>> getMatchesByTournament(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id) throws Exception {
        Result<List<MatchesWithVenue>> matchesList = matchesService.findMatchesByTournament(id);
        return new ResponseEntity<>(matchesList, HttpStatus.valueOf(matchesList.getCode()));
    }
    @GetMapping(value = "/venue/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Venue.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<List<MatchesWithVenue>>> getMatchesByVenue(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id) throws Exception {
        Result<List<MatchesWithVenue>> matchesList = matchesService.findMatchesByVenue(id);
        return new ResponseEntity<>(matchesList, HttpStatus.valueOf(matchesList.getCode()));
    }
    @GetMapping(value = "/minimumBet/{minBet}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Venue.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<List<MatchesWithVenue>>> getMatchesByMinBet(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int minBet) throws Exception {
        Result<List<MatchesWithVenue>> matchesList = matchesService.findMatchesByMinBet(minBet);
        return new ResponseEntity<>(matchesList, HttpStatus.valueOf(matchesList.getCode()));
    }
    @GetMapping(value = "/team/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Venue.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<List<MatchesWithVenue>>> getMatchesByTeam(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id) throws Exception {
        Result<List<MatchesWithVenue>> matchesList = matchesService.findMatchesByTeam(id);
        return new ResponseEntity<>(matchesList, HttpStatus.valueOf(matchesList.getCode()));
    }
    @GetMapping(value = "/oldMatches",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Venue.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<List<MatchesWithVenue>>> findAllMatchesByPreviousDateAndResultStatus() {
        Result<List<MatchesWithVenue>> matchesList = matchesService.findAllMatchesByPreviousDateAndResultStatus();
        return new ResponseEntity<>(matchesList, HttpStatus.valueOf(matchesList.getCode()));
    }
    @PutMapping(value = "/updateMatch/{matchId}/{resultStatus}/{winnerTeamId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Venue.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<Matches>> updateMatchResult(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int matchId,@PathVariable @Valid @Pattern(regexp = "[0-9]*") int resultStatus, @PathVariable @Valid @Pattern(regexp = "[0-9]*") int winnerTeamId) throws Exception {
        Result<String> updateResult = matchesService.updateMatchWinningTeam(matchId,resultStatus,winnerTeamId);
        return new ResponseEntity(updateResult,HttpStatus.valueOf(updateResult.getCode()));
    }
    @PutMapping(value = "/updateMatchDetail/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Venue.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<Matches>> updateMatch(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id, @RequestBody(required = true) @Valid Matches matches) throws Exception {
        Result<Matches> matchResult = matchesService.updateMatch(id, matches);
        return new ResponseEntity(matchResult,HttpStatus.valueOf(matchResult.getCode()));
    }
    @PutMapping(value = "/updateMatchVenue/{id}/{venueId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Venue.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<String>> updateMatchVenue(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id,@PathVariable @Valid @Pattern(regexp = "[0-9]*") int venueId ) throws Exception {
        Result<String> matchResult = matchesService.updateMatchVenue(id, venueId);
        return new ResponseEntity(matchResult,HttpStatus.valueOf(matchResult.getCode()));
    }
    @PutMapping(value = "/updateMatchResultStatus/{id}/{status}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Venue.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<String>> updateMatchResultStatus(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id,@PathVariable @Valid @Pattern(regexp = "[0-9]*") boolean status ) throws Exception {
        Result<String> matchResult = matchesService.updateMatchResultStatus(id, status);
        return new ResponseEntity(matchResult,HttpStatus.valueOf(matchResult.getCode()));
    }
    @PutMapping(value = "/updateMatchStartDate/{id}/{date}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Venue.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<String>> updateMatchStartDateTime(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id, @PathVariable @Valid Timestamp date) throws Exception {
        Result<String> matchResult = matchesService.updateMatchStartDateTime(id, date);
        return new ResponseEntity(matchResult,HttpStatus.valueOf(matchResult.getCode()));
    }
    @PutMapping(value = "/updateMinBet/{matchId}/{minBet}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Venue.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<String>> updateMinimumBet(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int matchId, @PathVariable @Valid @Pattern(regexp = "[0-9]*") int minBet) throws  Exception {
        Result<String> userResult = matchesService.updateMinimumBet(matchId,minBet);
        return new ResponseEntity(userResult,HttpStatus.valueOf(userResult.getCode()));
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Venue.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<Matches>> addMatches(@RequestBody(required = true) @Valid Matches matches) throws  Exception {
        Result<Matches> matchesResult = matchesService.addMatches(matches);
        return new ResponseEntity(matchesResult,HttpStatus.valueOf(matchesResult.getCode()));
    }
    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Venue.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<Matches>> deleteMatchById(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id) throws Exception {
        Result<Integer> integerResult = matchesService.deleteMatch(id);
        return new ResponseEntity(integerResult,HttpStatus.valueOf(integerResult.getCode()));
    }
}
