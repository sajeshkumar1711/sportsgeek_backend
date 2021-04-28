package com.project.sportsgeek.repository.betonplayerrepo;

import com.project.sportsgeek.model.BetOnPlayer;
import com.project.sportsgeek.model.BetOnPlayerResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "betOnPlayerRepo")
public interface BetOnPlayerRepository {
    public List<BetOnPlayerResponse> findAllBetOnPlayer();
    public List<BetOnPlayerResponse> findBetOnPlayerByBetPlayerId(int betPlayerId) throws Exception;
    public List<BetOnPlayerResponse> findBetOnPlayerByUserId(int userId) throws Exception;
    public List<BetOnPlayerResponse> findAllBetOnPlayerByMatchId(int matchId) throws Exception;
    public int addBetOnPlayer(BetOnPlayer player) throws Exception;
    public int updateBetOnPlayer(int betPlayerId,BetOnPlayer player) throws Exception;
    public int updateTotalGamePoints(int betPlayerId, int points) throws Exception;
    public int deleteBetOnPlayer(int betPlayerId) throws Exception;
}
