package com.project.sportsgeek.mapper;

import com.project.sportsgeek.model.BetOnTeam;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FutureBetsRowMapper implements RowMapper<BetOnTeam> {
    @Override
    public BetOnTeam mapRow(ResultSet rs, int rowNum) throws SQLException {
        BetOnTeam betOnTeam = new BetOnTeam();
        betOnTeam.setBetPoints(rs.getInt("TotalBetPoints"));
        betOnTeam.setUserId(rs.getInt("UserId"));
        return betOnTeam;
    }
}
