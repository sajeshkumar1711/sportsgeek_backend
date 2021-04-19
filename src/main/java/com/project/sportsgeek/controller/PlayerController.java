package com.project.sportsgeek.controller;

import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.Player;
import com.project.sportsgeek.model.PlayerResponse;
import com.project.sportsgeek.model.Venue;
import com.project.sportsgeek.response.Result;
import com.project.sportsgeek.service.PlayerService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping(path = "/player",produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Venue.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<List<PlayerResponse>>> getAllPlayer() {
        Result<List<PlayerResponse>> playerList = playerService.findAllPlayer();
        return new ResponseEntity<>(playerList, HttpStatus.valueOf(playerList.getCode()));
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Venue.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<PlayerResponse>> getPlayerById(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id) throws Exception {
        Result<PlayerResponse> playerResult = playerService.findPlayerById(id);
        return new ResponseEntity<>(playerResult, HttpStatus.valueOf(playerResult.getCode()));
    }

    @GetMapping(value = "/playerType/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Venue.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<List<PlayerResponse>>> getPlayerByPlayerType(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id) throws Exception {
        Result<List<PlayerResponse>> playerResult = playerService.findPlayerByPlayerType(id);
        return new ResponseEntity<>(playerResult, HttpStatus.valueOf(playerResult.getCode()));
    }
    @GetMapping(value = "/team/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Venue.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<List<PlayerResponse>>> getPlayerByTeam(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id) throws Exception {
        Result<List<PlayerResponse>> playerResult = playerService.findPlayerByTeamId(id);
        return new ResponseEntity<>(playerResult, HttpStatus.valueOf(playerResult.getCode()));
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Venue.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
//    @RequestBody(required = true) @Valid Player player
    public ResponseEntity<Result<Player>> addPlayer(@RequestParam("playerId") int playerId,@RequestParam("teamId") int teamId,@RequestParam("name") String name,@RequestParam("typeId") int typeId,@RequestParam("profilePicture") MultipartFile multipartFile ) throws Exception {
        String filename = multipartFile.getOriginalFilename();
       Player player = Player.builder()
               .playerId(playerId)
               .teamId(teamId)
               .name(name)
               .typeId(typeId)
               .profilePicture(filename).build();
        Result<Player> playerResult = playerService.addPlayer(player,multipartFile);
        return new ResponseEntity(playerResult,HttpStatus.valueOf(playerResult.getCode()));
    }
    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Venue.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<Player>> updatePlayer(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id,@RequestBody(required = true) @Valid Player player) throws Exception {
        Result<Player> playerResult = playerService.updatePlayer(id, player);
        return new ResponseEntity(playerResult,HttpStatus.valueOf(playerResult.getCode()));
    }
    @PutMapping(value = "/updatePlayerType/{id}/{typeId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Venue.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<String>> updatePlayerType(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id, @PathVariable @Valid @Pattern(regexp = "[0-9]*") int typeId) throws Exception {
        Result<String> playerResult = playerService.updatePlayerType(id, typeId);
        return new ResponseEntity(playerResult,HttpStatus.valueOf(playerResult.getCode()));
    }
    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Venue.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    public ResponseEntity<Result<Player>> deletePlayerById(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int id) throws Exception {
        Result<Integer> integerResult =  playerService.deletePlayer(id);
        return new ResponseEntity(integerResult,HttpStatus.valueOf(integerResult.getCode()));
    }
}
