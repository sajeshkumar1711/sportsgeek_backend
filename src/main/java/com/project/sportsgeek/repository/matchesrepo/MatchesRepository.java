package com.project.sportsgeek.repository.matchesrepo;


import com.project.sportsgeek.model.Matches;
import com.project.sportsgeek.model.MatchesWithVenue;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Repository(value = "matchesRepo")
public interface MatchesRepository {

    public List<MatchesWithVenue> findAllMatches(int tournamentId) throws Exception;
    public List<MatchesWithVenue> findMatchesById(int id, int tournamentId) throws Exception;
    public List<MatchesWithVenue> findAllMatchesByTournament(int id) throws Exception;
    public List<MatchesWithVenue> findAllMatchesByVenue(int id) throws Exception;
    public List<MatchesWithVenue> findAllMatchesByTeam(int id) throws Exception;
    public List<MatchesWithVenue> findAllMatchesByPreviousDateAndResultStatus(int tournamentId) throws Exception;
    public List<MatchesWithVenue> findAllMatchesByMinimumBet(int minBet) throws Exception;
    public int addMatch(Matches matches) throws Exception;
    public boolean updateMatch(int id, Matches matches) throws Exception;
    public boolean updateMatchWinningTeam(int matchId, int ResultStatus, int winningTeamId) throws Exception;
    public int updateMatchVenue(int id,int venueId) throws Exception;
    public int updateResultStatus(int id, boolean status) throws Exception;
    public int updateMinimumBet(int matchId, int minBet) throws Exception;
    public int updateMatchScheduleDate(int id, Timestamp date) throws Exception;
    public int deleteMatches(int id) throws Exception;
}
