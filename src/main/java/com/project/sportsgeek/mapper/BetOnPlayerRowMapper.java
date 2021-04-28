package com.project.sportsgeek.mapper;

import com.project.sportsgeek.model.BetOnPlayer;
import com.project.sportsgeek.model.BetOnPlayerResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BetOnPlayerRowMapper implements RowMapper<BetOnPlayerResponse> {
    @Override
    public BetOnPlayerResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        BetOnPlayerResponse player = new BetOnPlayerResponse();
        player.setBetPlayerId(rs.getInt("BetPlayerId"));
        player.setFirstName(rs.getString("FirstName"));
        player.setLastName(rs.getString("LastName"));
        player.setTeam1(rs.getString("Team1"));
        player.setTeam2(rs.getString("Team2"));
        player.setStartDateTime(rs.getTimestamp("StartDateTime"));
        player.setTotalGamePoints(rs.getInt("TotalGamePoints"));
        player.setUserName(rs.getString("UserName"));
        return player;
    }
}
