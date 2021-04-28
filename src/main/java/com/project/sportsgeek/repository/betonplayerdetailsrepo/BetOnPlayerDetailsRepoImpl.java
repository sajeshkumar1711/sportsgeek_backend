package com.project.sportsgeek.repository.betonplayerdetailsrepo;

import com.project.sportsgeek.mapper.BetPlayerDetailRowMapper;
import com.project.sportsgeek.model.BetOnPlayer;
import com.project.sportsgeek.model.BetOnPlayerDetails;
import com.project.sportsgeek.model.BetPlayerDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "betOnPlayerDetailsRepo")
public class BetOnPlayerDetailsRepoImpl implements BetOnPlayerDetailsRepo {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<BetPlayerDetailsResponse> findAllPlayerDetails() {
        String sql = "SELECT b.playerPoints as PlayerPoints,b.BetPlayerId as BetPlayerId, b.PlayerNo as PlayerNo, t.ShortName as TeamName, p.Name as Name, pt.TypeName as TypeName, p.ProfilePicture as ProfilePicture, u.Username as UserName, u.FirstName as FirstName, u.LastName as LastName " +
                "FROM BetOnPlayerDetails as b INNER JOIN Player as p on b.PlayerId=p.PlayerId INNER JOIN Team as t on p.TeamId=t.TeamId INNER JOIN PlayerType as pt on p.TypeId=pt.PlayerTypeId INNER JOIN BetOnPlayer as bp on b.BetPlayerId=bp.BetPlayerId INNER JOIN User as u on bp.UserId=u.UserId";
        return jdbcTemplate.query(sql, new BetPlayerDetailRowMapper());
    }
    @Override
    public List<BetPlayerDetailsResponse> findBetPlayerDetailsByBetPlayerId(int betPlayerId) throws Exception {
        String sql = "SELECT b.playerPoints as PlayerPoints,b.BetPlayerId as BetPlayerId, b.PlayerNo as PlayerNo, t.ShortName as TeamName, p.Name as Name, pt.TypeName as TypeName, p.ProfilePicture as ProfilePicture, u.Username as UserName, u.FirstName as FirstName, u.LastName as LastName " +
                "FROM BetOnPlayerDetails as b INNER JOIN Player as p on b.PlayerId=p.PlayerId INNER JOIN Team as t on p.TeamId=t.TeamId INNER JOIN PlayerType as pt on p.TypeId=pt.PlayerTypeId INNER JOIN BetOnPlayer as bp on b.BetPlayerId=bp.BetPlayerId INNER JOIN User as u on bp.UserId=u.UserId WHERE b.BetPlayerId=:betPlayerId";
        BetOnPlayerDetails betOnPlayerDetails = new BetOnPlayerDetails();
        betOnPlayerDetails.setBetPlayerId(betPlayerId);
        return jdbcTemplate.query(sql,new BeanPropertySqlParameterSource(betOnPlayerDetails), new BetPlayerDetailRowMapper());
    }

    @Override
    public List<BetPlayerDetailsResponse> findAllBetPlayerDetailsByUserId(int userId) throws Exception {
        String sql = "SELECT b.playerPoints as PlayerPoints, b.BetPlayerId as BetPlayerId, b.PlayerNo as PlayerNo, t.ShortName as TeamName, p.Name as Name, pt.TypeName as TypeName, p.ProfilePicture as ProfilePicture, u.Username as UserName, u.FirstName as FirstName, u.LastName as LastName " +
                "FROM BetOnPlayerDetails as b INNER JOIN Player as p on b.PlayerId=p.PlayerId INNER JOIN Team as t on p.TeamId=t.TeamId INNER JOIN PlayerType as pt on p.TypeId=pt.PlayerTypeId INNER JOIN BetOnPlayer as bp on b.BetPlayerId=bp.BetPlayerId INNER JOIN User as u on bp.UserId=u.UserId WHERE bp.UserId=:userId";
        BetOnPlayer betOnPlayer = new BetOnPlayer();
        betOnPlayer.setUserId(userId);
        return jdbcTemplate.query(sql, new BeanPropertySqlParameterSource(betOnPlayer), new BetPlayerDetailRowMapper());
    }

    @Override
    public int addBetPlayerDetails(BetOnPlayerDetails betOnPlayerDetails) throws Exception {
        String sql = "INSERT INTO BetOnPlayerDetails (BetPlayerId,PlayerNo, PlayerId, PlayerPoints) VALUES(:betPlayerId,:playerNo, :playerId,:playerPoints)";
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(betOnPlayerDetails));
    }

    @Override
    public int updateBetPlayerDetails(int betPlayerId,int playerNo, BetOnPlayerDetails betOnPlayerDetails) throws Exception {
        String sql = "UPDATE betonplayerdetails SET PlayerId=:playerId,PlayerPoints=:playerPoints WHERE BetPlayerId=:betPlayerId AND PlayerNo=:playerNo";
        betOnPlayerDetails.setBetPlayerId(betPlayerId);
        betOnPlayerDetails.setPlayerNo(playerNo);
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(betOnPlayerDetails));
    }

    @Override
    public int updatePlayerPoints(int betPlayerId, int playerNo, int playerPoints) throws Exception {
        String sql = "UPDATE betonplayerdetails SET PlayerPoints=:playerPoints WHERE BetPlayerId=:betPlayerId AND PlayerNo=:playerNo";
        BetOnPlayerDetails betOnPlayerDetails = new BetOnPlayerDetails();
        betOnPlayerDetails.setPlayerPoints(playerPoints);
        betOnPlayerDetails.setBetPlayerId(betPlayerId);
        betOnPlayerDetails.setPlayerNo(playerNo);
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(betOnPlayerDetails));
    }

    @Override
    public int deleteBetPlayerDetails(int betPlayerId) throws Exception {
        String sql = "DELETE FROM betonplayerdetails WHERE BetPlayerId=:betPlayerId";
        BetOnPlayerDetails betOnPlayerDetails = new BetOnPlayerDetails();
        betOnPlayerDetails.setBetPlayerId(betPlayerId);
        return  jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(betOnPlayerDetails));
    }
}
