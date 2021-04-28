package com.project.sportsgeek.service;

import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.BetOnPlayer;
import com.project.sportsgeek.model.BetOnPlayerResponse;
import com.project.sportsgeek.model.Matches;
import com.project.sportsgeek.model.MatchesWithVenue;
import com.project.sportsgeek.repository.betonplayerrepo.BetOnPlayerRepository;
import com.project.sportsgeek.repository.matchesrepo.MatchesRepository;
import com.project.sportsgeek.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BetOnPlayerService {

    @Autowired
    @Qualifier("betOnPlayerRepo")
    BetOnPlayerRepository betOnPlayerRepository;

    public Result<List<BetOnPlayerResponse>> findAllBetOnPlayer() {
        List<BetOnPlayerResponse> playerList = betOnPlayerRepository.findAllBetOnPlayer();
        return new Result<>(200,"Bet On Player's Detail Retrieved Successfully",playerList);
    }

    public Result<BetOnPlayerResponse> findBetPlayerByBetPlayerId(int betPlayerId) throws Exception {
        List<BetOnPlayerResponse> playerList = betOnPlayerRepository.findBetOnPlayerByBetPlayerId(betPlayerId);
        if (playerList.size() > 0) {
            return new Result<>(200,"Bet On Player Detail Retrieved Successfully", playerList.get(0));
        }
        else {
            throw new ResultException((new Result<>(404,"No Bet's found,please try again","BetPlayer with id=('"+ betPlayerId +"') not found")));
        }
    }

    public Result<BetOnPlayerResponse> findBetPlayerByUserId(int userId) throws Exception {
        List<BetOnPlayerResponse> playerList = betOnPlayerRepository.findBetOnPlayerByUserId(userId);
        if (playerList.size() > 0) {
            return new Result<>(200,"Bet On Player Detail Retrieved Successfully", playerList.get(0));
        }
        else {
            throw new ResultException((new Result<>(404,"No Bet's found,please try again","User with id=('"+ userId +"') not found")));
        }
    }
    public Result<List<BetOnPlayerResponse>> findBetPlayerByMatchId(int matchId) throws Exception {
        List<BetOnPlayerResponse> playerList = betOnPlayerRepository.findAllBetOnPlayerByMatchId(matchId);
        if (playerList.size() > 0) {
            return new Result<>(200,"Bet On Player Detail Retrieved Successfully",playerList);
        }
        else {
            throw new ResultException((new Result<>(404,"No Bet's found,please try again","Match with id=('"+ matchId +"') not found")));
        }
    }
    public Result<BetOnPlayer> addBetOnPlayer(BetOnPlayer player) throws Exception {
        int id = betOnPlayerRepository.addBetOnPlayer(player);
        if (id > 0) {
            return new Result<>(201,"Bet On Player Added Successfully",player);
        }
        throw new ResultException(new Result<>(400, "Error!, please try again!", new ArrayList<>(Arrays
                .asList(new Result.SportsGeekSystemError(player.hashCode(), "unable to add the given Bet Player")))));
    }
    public Result<BetOnPlayer> updateBetPlayer(int betPlayerId, BetOnPlayer player) throws Exception {
        int result = betOnPlayerRepository.updateBetOnPlayer(betPlayerId,player);
        if (result > 0) {
            return new Result<>(201,"BetPlayer Details Updated Successfully",player);
        }
        throw new ResultException(new Result<>(400, "Unable to update the given BetPlayer details! Please try again!", new ArrayList<>(Arrays
                .asList(new Result.SportsGeekSystemError(player.hashCode(), "given BetPlayerId('"+betPlayerId+"') does not exists")))));
    }
    public Result<String> updateGamePoints(int betPlayerId, int points) throws Exception {
        int result = betOnPlayerRepository.updateTotalGamePoints(betPlayerId, points);
        if (result > 0) {
            return new Result<>(201,"Game Points Updated Successfully");
        }
        throw new ResultException(new Result<>(400, "Unable to update the given Game Points! Please try again!","given BetPlayerId('"+betPlayerId+"') does not exists"));
    }
    public Result<Integer> deleteBetPlayer(int betPlayerId) throws Exception{
        int data = betOnPlayerRepository.deleteBetOnPlayer(betPlayerId);
        if (data > 0) {
            return new Result<>(200,"Bet Player Deleted Successfully",data);
        }
        else {
            throw new ResultException((new Result<>(404,"No BetPlayer found to delete,please try again","BetPlayer with id=('"+ betPlayerId +"') not found")));
        }
    }
}
