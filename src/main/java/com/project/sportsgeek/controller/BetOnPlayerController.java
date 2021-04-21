package com.project.sportsgeek.controller;

import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.*;
import com.project.sportsgeek.response.Result;
import com.project.sportsgeek.service.BetOnPlayerService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping(path = "/bet-on-players",produces = MediaType.APPLICATION_JSON_VALUE)
public class BetOnPlayerController {

    @Autowired
    BetOnPlayerService betOnPlayerService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = BetOnPlayer.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    public ResponseEntity<Result<List<BetOnPlayerResponse>>> getAllBetOnPlayer() {
        Result<List<BetOnPlayerResponse>> playerList = betOnPlayerService.findAllBetOnPlayer();
        return new ResponseEntity<>(playerList, HttpStatus.valueOf(playerList.getCode()));
    }
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = BetOnPlayer.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    public ResponseEntity<Result<BetOnPlayerResponse>> getBetPlayerByBetPlayerId(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id) throws Exception {
        Result<BetOnPlayerResponse> playerList = betOnPlayerService.findBetPlayerByBetPlayerId(id);
        return new ResponseEntity<>(playerList, HttpStatus.valueOf(playerList.getCode()));
    }
    @GetMapping(value = "/users/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = BetOnPlayer.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    public ResponseEntity<Result<BetOnPlayerResponse>> getBetPlayerByUserId(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id) throws Exception {
        Result<BetOnPlayerResponse> playerList = betOnPlayerService.findBetPlayerByUserId(id);
        return new ResponseEntity<>(playerList, HttpStatus.valueOf(playerList.getCode()));
    }
    @GetMapping(value = "/matches/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = BetOnPlayer.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    public ResponseEntity<Result<List<BetOnPlayerResponse>>> getBetPlayerByMatchId(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id) throws Exception {
        Result<List<BetOnPlayerResponse>> playerList = betOnPlayerService.findBetPlayerByMatchId(id);
        return new ResponseEntity<>(playerList, HttpStatus.valueOf(playerList.getCode()));
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = BetOnPlayer.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    public ResponseEntity<Result<BetOnPlayer>> addBetPlayer(@RequestBody(required = true) @Valid BetOnPlayer player) throws  Exception {
        Result<BetOnPlayer> playerResult = betOnPlayerService.addBetOnPlayer(player);
        return new ResponseEntity(playerResult,HttpStatus.valueOf(playerResult.getCode()));
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
    public ResponseEntity<Result<BetOnPlayer>> updateBetPlayer(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id, @RequestBody(required = true) @Valid BetOnPlayer player) throws Exception {
        Result<BetOnPlayer> playerResult = betOnPlayerService.updateBetPlayer(id, player);
        return new ResponseEntity(playerResult,HttpStatus.valueOf(playerResult.getCode()));
    }
    @PutMapping(value = "/{id}/game-point/{gamePoints}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Venue.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Result<String>> updateGamePoints(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id, @PathVariable @Valid @Pattern(regexp = "[0-9]*") int gamePoints) throws Exception {
        Result<String> playerResult = betOnPlayerService.updateGamePoints(id, gamePoints);
        return new ResponseEntity(playerResult,HttpStatus.valueOf(playerResult.getCode()));
    }
    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = BetOnPlayer.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Result<BetOnPlayer>> deleteBetPlayerById(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id) throws Exception {
        Result<Integer> playerResult =  betOnPlayerService.deleteBetPlayer(id);
        return new ResponseEntity(playerResult,HttpStatus.valueOf(playerResult.getCode()));
    }
}
