package com.project.sportsgeek.repository.betonteamrepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.project.sportsgeek.mapper.BetOnTeamRowMapper;
import com.project.sportsgeek.mapper.BetOnTeamWithMatchRowMapper;
import com.project.sportsgeek.mapper.ContestWithUsersResultRowMapper;
import com.project.sportsgeek.model.BetOnTeam;
import com.project.sportsgeek.model.BetOnTeamWithResult;
import com.project.sportsgeek.model.BetOnTeamWithUser;

@Repository(value = "betOnTeamsRepo")
public class BetOnTeamRepoImpl implements BetOnTeamRepository {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<BetOnTeam> findBetByUserAndMatch(int userid, int matchid) throws Exception {
        String sql = "SELECT BetTeamId, UserId,MatchId,TeamId,BetPoints,WinningPoints from BetOnTeam WHERE UserId = "+userid+" and MatchId="+matchid;
        return jdbcTemplate.query(sql,new BetOnTeamWithMatchRowMapper());
    }

    @Override
    public List<BetOnTeamWithUser> findAllContestByMatchId(int matchId) throws Exception {
        String sql = "SELECT BetTeamId, bot.UserId as UserId, MatchId, bot.TeamId as TeamId, t.ShortName as TeamShortName, FirstName, LastName, Username, ProfilePicture, BetPoints, WinningPoints " +
                "FROM BetOnTeam as bot, User as u, Team as t " +
                "WHERE bot.TeamId=t.TeamId AND bot.UserId=u.UserId AND MatchId="+matchId;

        List<BetOnTeamWithUser> betOnTeamWithUsers = jdbcTemplate.query(sql, new BetOnTeamRowMapper());
        System.out.println(betOnTeamWithUsers);
        return betOnTeamWithUsers;
    }

    @Override
    public List<BetOnTeamWithResult> findContestResultByMatchId(int matchId) throws Exception {
        String sql = "SELECT BetTeamId, t.ShortName as TeamShortName, FirstName, LastName, Username, ProfilePicture, BetPoints, WinningPoints FROM BetOnTeam as bot inner join User as u on bot.UserId=u.UserId inner join Team as t on bot.TeamId=t.TeamId " +
                "WHERE MatchId="+matchId+" ORDER BY WinningPoints desc, BetPoints desc;";
        return jdbcTemplate.query(sql, new ContestWithUsersResultRowMapper());
    }

    @Override
    public int addBetOnTeam(BetOnTeam betOnTeam) throws Exception {
        KeyHolder holder = new GeneratedKeyHolder();
        String sql = "INSERT INTO BetOnTeam (UserId, MatchId, TeamId, BetPoints, WinningPoints) VALUES (:userId, :matchId, :teamId, :betPoints, :winningPoints)";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(betOnTeam), holder);
        sql = "UPDATE User SET AvailablePoints = AvailablePoints - :betPoints WHERE UserId = :userId";
        jdbcTemplate.update(sql,new BeanPropertySqlParameterSource(betOnTeam));
        return holder.getKey().intValue();
    }

    @Override
    public boolean updateBetOnTeam(int id, BetOnTeam betOnTeam) throws Exception {
        betOnTeam.setBetTeamId(id);
        // Get old Bet Points
        String select_betpoints = "SELECT BetTeamId, UserId,MatchId,TeamId,BetPoints,WinningPoints FROM BetOnTeam WHERE BetTeamId="+id;
        int betpoints = jdbcTemplate.query(select_betpoints, new BetOnTeamWithMatchRowMapper()).get(0).getBetPoints();
        // Update User Available Points
        BetOnTeam betOnTeam2 = new BetOnTeam();
        betOnTeam2.setBetPoints(betpoints-betOnTeam.getBetPoints());
        betOnTeam2.setUserId(betOnTeam.getUserId());
        String update_betpoints = "Update User SET AvailablePoints = AvailablePoints + :betPoints WHERE UserId=:userId";
        jdbcTemplate.update(update_betpoints,new BeanPropertySqlParameterSource(betOnTeam2));
        // Update BetOnTeam
        String sql = "Update BetOnTeam SET TeamId = :teamId, BetPoints = :betPoints WHERE BetTeamId = :betTeamId";
        return jdbcTemplate.update(sql,new BeanPropertySqlParameterSource(betOnTeam))>0;
    }

}
