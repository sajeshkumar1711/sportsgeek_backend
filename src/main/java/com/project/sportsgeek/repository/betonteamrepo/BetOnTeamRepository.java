package com.project.sportsgeek.repository.betonteamrepo;

import com.project.sportsgeek.model.BetOnTeam;
import com.project.sportsgeek.model.BetOnTeamWithResult;
import com.project.sportsgeek.model.BetOnTeamWithUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "betOnTeamsRepo")
public interface BetOnTeamRepository {
    List<BetOnTeam> findBetByUserAndMatch(int userid, int matchid) throws Exception;
    List<BetOnTeamWithUser> findAllContestByMatchId(int matchId) throws Exception;
    List<BetOnTeamWithResult> findContestResultByMatchId(int matchId) throws Exception;
    int addBetOnTeam(BetOnTeam betOnTeam) throws Exception;
    boolean updateBetOnTeam(int id, BetOnTeam betOnTeam) throws Exception;
    int getBetPoints(int betTeamId) throws Exception;
}
