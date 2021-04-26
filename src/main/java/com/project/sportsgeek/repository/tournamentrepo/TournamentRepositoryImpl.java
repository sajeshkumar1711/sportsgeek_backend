package com.project.sportsgeek.repository.tournamentrepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.project.sportsgeek.mapper.TournamentRowMapper;
import com.project.sportsgeek.model.Tournament;

@Repository(value = "tournamentRepo")
public class TournamentRepositoryImpl implements TournamentRepository {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Tournament> findAllTournament() {
        String sql = "SELECT * from Tournament";
        return jdbcTemplate.query(sql, new TournamentRowMapper());
    }

    @Override
    public List<Tournament> findTournamentById(int i) throws Exception {
            String sql = "SELECT * FROM Tournament WHERE TournamentId = " +i;
            return jdbcTemplate.query(sql,new TournamentRowMapper());
    }

    @Override
    public int addTournament(Tournament tournament) throws Exception {
        KeyHolder holder = new GeneratedKeyHolder();
               return holder.getKey().intValue();
    }

    @Override
    public boolean updateTournament(int id, Tournament tournament) throws Exception {
        String sql = "UPDATE `" + "Tournament" + "` set "
                + "`Name` = :name where `TournamentId`=:TournamentId";
        tournament.setTournamentId(id);
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(tournament)) > 0;
    }

    @Override
    public boolean updateActiveTournament(int id) throws Exception {
        String deactive_tournament = "UPDATE Tournament SET active=0 WHERE active=1";
        jdbcTemplate.update(deactive_tournament,new BeanPropertySqlParameterSource(id));
        String sql = "UPDATE Tournament SET active=1 WHERE TournamentId="+id;
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(id)) > 0;
    }

    @Override
    public int deleteTournament(int id) throws Exception {
        String sql = "DELETE FROM Tournament WHERE TournamentId = " +id;
        return jdbcTemplate.update(sql,new BeanPropertySqlParameterSource(id));
    }
}
