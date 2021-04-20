package com.project.sportsgeek.repository.playerrepo;

import com.project.sportsgeek.model.Player;
import com.project.sportsgeek.model.PlayerResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "playerRepo")
public interface PlayerRepository {

    public List<PlayerResponse> findAllPlayers();
    public List<PlayerResponse> findPlayerByPlayerId(int id) throws Exception;
    public List<PlayerResponse> findPlayerByPlayerType(int id) throws Exception;
    public List<PlayerResponse> findPlayerByTeamId(int id) throws Exception;
    public int addPlayer(Player player) throws Exception;
    public boolean updatePlayer(int id, Player player) throws Exception;
    public boolean updatePlayerType(int id,int playerTypeId) throws Exception;
    public int deletePlayer(int id) throws Exception;
}
