package com.project.sportsgeek.repository.matchesrepo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.project.sportsgeek.mapper.MatchesRowMapper;
import com.project.sportsgeek.mapper.TournamentRowMapper;
import com.project.sportsgeek.model.Matches;
import com.project.sportsgeek.model.MatchesWithVenue;

@Repository(value = "matchesRepo")
public class MatchesRepoImpl implements MatchesRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbcCall;
    @Override
    public List<MatchesWithVenue> findAllMatches() {
        String tournament_sql = "SELECT * from Tournament WHERE active = true";
        int tournamentid = jdbcTemplate.query(tournament_sql,new TournamentRowMapper()).get(0).getTournamentId();
       String sql = "SELECT MatchId , StartDatetime, Team1,Team2, t1.Name as team1long, t1.ShortName as team1short, " +
               "t1.TeamLogo as team1logo, t2.Name as team2long, t2.ShortName as team2short, t2.TeamLogo as team2logo, v.Name as venue, MinimumBet, WinnerTeamId, ResultStatus, TournamentId  " +
               "FROM Matches as m INNER JOIN Venue as v on m.VenueId=v.VenueId left JOIN Team as t1 on m.Team1=t1.TeamId left JOIN Team as t2 on m.Team2=t2.TeamId " +
               "where TournamentId=" + tournamentid + " and StartDatetime > CURRENT_TIMESTAMP order by StartDatetime";
       return jdbcTemplate.query(sql,new MatchesRowMapper());
    }

    @Override
    public List<MatchesWithVenue> findAllMatchesByTournament(int id) throws Exception {
        String sql = "SELECT MatchId , StartDatetime, Team1,Team2, t1.Name as team1long, t1.ShortName as team1short, " +
                "t1.TeamLogo as team1logo, t2.Name as team2long, t2.ShortName as team2short, t2.TeamLogo as team2logo, v.Name as venue, MinimumBet, WinnerTeamId, ResultStatus, TournamentId  " +
                "FROM Matches as m INNER JOIN Venue as v on m.VenueId=v.VenueId left JOIN Team as t1 on m.Team1=t1.TeamId left JOIN Team as t2 on m.Team2=t2.TeamId " +
                "where TournamentId="+id;
        return jdbcTemplate.query(sql,new MatchesRowMapper());
    }

    @Override
    public List<MatchesWithVenue> findAllMatchesByVenue(int id) throws Exception {
        String sql = "SELECT MatchId , StartDatetime, Team1,Team2, t1.Name as team1long, t1.ShortName as team1short, " +
                "t1.TeamLogo as team1logo, t2.Name as team2long, t2.ShortName as team2short, t2.TeamLogo as team2logo, v.Name as venue, MinimumBet, WinnerTeamId, ResultStatus, TournamentId  " +
                "FROM Matches as m INNER JOIN Venue as v on m.VenueId=v.VenueId left JOIN Team as t1 on m.Team1=t1.TeamId left JOIN Team as t2 on m.Team2=t2.TeamId " +
                "where m.VenueId="+id;
        return jdbcTemplate.query(sql,new MatchesRowMapper());
    }

    @Override
    public List<MatchesWithVenue> findAllMatchesByTeam(int id) throws Exception {
        String sql = "SELECT MatchId , StartDatetime, Team1,Team2, t1.Name as team1long, t1.ShortName as team1short, " +
                "t1.TeamLogo as team1logo, t2.Name as team2long, t2.ShortName as team2short, t2.TeamLogo as team2logo, v.Name as venue, MinimumBet, WinnerTeamId, ResultStatus, TournamentId  " +
                "FROM Matches as m INNER JOIN Venue as v on m.VenueId=v.VenueId left JOIN Team as t1 on m.Team1=t1.TeamId left JOIN Team as t2 on m.Team2=t2.TeamId " +
                "where m.Team1="+id+" or m.Team2="+id;
        return jdbcTemplate.query(sql,new MatchesRowMapper());
    }

    @Override
    public List<MatchesWithVenue> findAllMatchesByPreviousDateAndResultStatus() {
        String tournament_sql = "SELECT * from Tournament WHERE active = true";
        int tournamentid = jdbcTemplate.query(tournament_sql,new TournamentRowMapper()).get(0).getTournamentId();
        String sql = "SELECT MatchId , StartDatetime, Team1,Team2, t1.Name as team1long, t1.ShortName as team1short, " +
                "t1.TeamLogo as team1logo, t2.Name as team2long, t2.ShortName as team2short, t2.TeamLogo as team2logo, v.Name as venue, MinimumBet, WinnerTeamId, ResultStatus, TournamentId  " +
                "FROM Matches as m INNER JOIN Venue as v on m.VenueId=v.VenueId left JOIN Team as t1 on m.Team1=t1.TeamId left JOIN Team as t2 on m.Team2=t2.TeamId " +
                "where TournamentId=" + tournamentid + " and StartDatetime < CURRENT_TIMESTAMP and m.ResultStatus IS NULL order by StartDatetime";
        return jdbcTemplate.query(sql,new MatchesRowMapper());
    }

    @Override
    public List<MatchesWithVenue> findAllMatchesByMinimumBet(int minBet) throws Exception {
        String sql = "SELECT MatchId , StartDatetime, Team1,Team2, t1.Name as team1long, t1.ShortName as team1short, " +
                "t1.TeamLogo as team1logo, t2.Name as team2long, t2.ShortName as team2short, t2.TeamLogo as team2logo, v.Name as venue, MinimumBet, WinnerTeamId, ResultStatus, TournamentId  " +
                "FROM Matches as m INNER JOIN Venue as v on m.VenueId=v.VenueId left JOIN Team as t1 on m.Team1=t1.TeamId left JOIN Team as t2 on m.Team2=t2.TeamId " +
                "where m.MinimumBet="+minBet;
        return jdbcTemplate.query(sql,new MatchesRowMapper());
    }
//Pending
    @Override
    public int addMatch(Matches matches) throws Exception {
//        KeyHolder holder = new GeneratedKeyHolder();
//        jdbcTemplate.update(queryGenerator.generatePreparedStatementInsertQuery("Matches", matches),
//                new BeanPropertySqlParameterSource(matches), holder);
//        return holder.getKey().intValue();
        String sql = "INSERT INTO Matches (MatchId,TournamentId,Name,StartDateTime,VenueId,Team1,Team2,MinimumBet) VALUES("+matches.getMatchId()+","+matches.getTournamentId()+",'"+matches.getName()+"'" +
                ",'"+matches.getStartDateTime()+"',"+matches.getVenueId()+","+matches.getTeam1()+","+matches.getTeam2()+","+matches.getMinimumBet()+")";
        return jdbcTemplate.update(sql);
    }

    //Pending
    @Override
    public boolean updateMatch(int id, Matches matches) throws Exception {
//        String sql = "UPDATE `" + "Matches" + "` set "
//                + "`TournamentId` = :tournamentId, `Name` = :name, `StartDatetime` = :startDateTime, `VenueId`= :venueId, `Team1` :team1, `Team2` :team2, `WinnerTeamId` :winnerTeamId, `ResultStatus` :resultStatus, `MinimumBet` :minimumBet where `MatchId`="+id;
        String sql = "UPDATE Matches SET TournamentId="+matches.getTournamentId()+",Name='"+matches.getName()+"',StartDatetime='"+matches.getStartDateTime()+"',VenueId="+matches.getVenueId()+",Team1="+matches.getTeam1()+",Team2="+matches.getTeam2()+",WinnerTeamId="+matches.getWinnerTeamId()+",ResultStatus="+matches.getResultStatus()+",MinimumBet="+matches.getMinimumBet()+" WHERE MatchId="+id;
        matches.setMatchId(id);
        return jdbcTemplate.update(sql) > 0;
    }

    @Override
    public boolean updateMatchWinningTeam(int matchId, int ResultStatus, int winningTeamId) throws Exception {
        try
        {
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("vMatchId", matchId)
                    .addValue("vResultStatus",ResultStatus)
                    .addValue("vWinnerTeamId",winningTeamId);

            simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("setMatchResult");
            simpleJdbcCall.execute(in);
            return true;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public int updateMatchVenue(int id, int venueId) throws Exception {
        String sql = "UPDATE Matches SET VenueId="+venueId +" WHERE MatchId="+id;
        return jdbcTemplate.update(sql);
    }

    @Override
    public int updateResultStatus(int id, boolean status) throws Exception {
        String sql = "UPDATE Matches SET ResultStatus="+status +" WHERE MatchId="+id;
        return jdbcTemplate.update(sql);
    }

    @Override
    public int updateMinimumBet(int matchId, int minBet) throws Exception {
       String sql = "UPDATE Matches SET MinimumBet="+minBet+" WHERE MatchId="+matchId;
       return jdbcTemplate.update(sql);
    }

    @Override
    public int updateMatchScheduleDate(int id, Timestamp date) throws Exception {
        String sql = "UPDATE Matches SET StartDatetime='"+date+"' WHERE MatchId="+id;
        return jdbcTemplate.update(sql);
    }

    @Override
    public int deleteMatches(int id) throws Exception {
       String sql = "DELETE FROM Matches WHERE MatchId="+id;
       return jdbcTemplate.update(sql);
    }

	@Override
	public List<MatchesWithVenue> findMatchesById(int id) throws Exception {
		String tournament_sql = "SELECT * from Tournament WHERE active = true";
        int tournamentid = jdbcTemplate.query(tournament_sql,new TournamentRowMapper()).get(0).getTournamentId();
       String sql = "SELECT MatchId , StartDatetime, Team1,Team2, t1.Name as team1long, t1.ShortName as team1short, " +
               "t1.TeamLogo as team1logo, t2.Name as team2long, t2.ShortName as team2short, t2.TeamLogo as team2logo, v.Name as venue, MinimumBet, WinnerTeamId, ResultStatus, TournamentId  " +
               "FROM Matches as m INNER JOIN Venue as v on m.VenueId=v.VenueId left JOIN Team as t1 on m.Team1=t1.TeamId left JOIN Team as t2 on m.Team2=t2.TeamId " +
               "where TournamentId="+ tournamentid + " AND MatchId=" + id;
       return jdbcTemplate.query(sql,new MatchesRowMapper());
	}
}
