package com.project.sportsgeek.repository.teamrepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.project.sportsgeek.mapper.TeamRowMapper;
import com.project.sportsgeek.model.Team;

@Repository(value = "teamRepo")
public class TeamRepositoryImpl implements TeamRepository {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Team> findAllTeam() {
        String sql = "SELECT * from Team";
        return jdbcTemplate.query(sql, new TeamRowMapper());
    }

    @Override
    public List<Team> findTeamById(int i) throws Exception{
        String sql = "SELECT * FROM Team WHERE TeamId = " +i;
        return jdbcTemplate.query(sql,new TeamRowMapper());
    }

    @Override
    public int addTeam(Team team) throws Exception {
        KeyHolder holder = new GeneratedKeyHolder();
               return holder.getKey().intValue();
    }

    @Override
    public boolean updateTeam(int id, Team team) throws Exception {
        String sql = "UPDATE `" + "Team" + "` set "
                + "`Name` = :name,`ShortName` = :shortName where `TeamId`= :TeamId";
        team.setTeamId(id);
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(team)) > 0;
    }

    @Override
    public int deleteTeam(int id) throws Exception {
        String sql = "DELETE FROM Team WHERE TeamId = " +id;
        return jdbcTemplate.update(sql,new BeanPropertySqlParameterSource(id));
    }
}
