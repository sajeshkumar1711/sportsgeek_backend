package com.project.sportsgeek.repository.playertype;

import com.project.sportsgeek.mapper.PlayerTypeRowMapper;
import com.project.sportsgeek.model.PlayerType;
import com.project.sportsgeek.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "playerTypeRepo")
public class PlayerTypeRepoImpl implements PlayerTypeRepository{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    private QueryGenerator<PlayerType> queryGenerator = new QueryGenerator<PlayerType>();
    @Override
    public List<PlayerType> findAllPlayerType() {
        String sql = "SELECT * FROM PlayerType";
        return jdbcTemplate.query(sql,new PlayerTypeRowMapper());
    }

    @Override
    public List<PlayerType> findPlayerTypeById(int i) throws Exception {
        String sql = "SELECT * FROM PlayerType WHERE PlayerTypeId="+i;
        return jdbcTemplate.query(sql,new PlayerTypeRowMapper());
    }

    @Override
    public int addPlayerType(PlayerType playerType) throws Exception {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(queryGenerator.generatePreparedStatementInsertQuery("PlayerType", playerType),
                new BeanPropertySqlParameterSource(playerType), holder);
        return holder.getKey().intValue();
    }

    @Override
    public boolean updatePlayerType(int id, PlayerType playerType) throws Exception {
        String sql = "UPDATE `" + "PlayerType" + "` set "
                + "`TypeName` = :typeName where `PlayerTypeId`=:PlayerTypeId";
        playerType.setPlayerTypeId(id);
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(playerType)) > 0;
    }

    @Override
    public int deletePlayerType(int id) throws Exception {
        String sql = "DELETE FROM PlayerType WHERE PlayerTypeId =" + id;
        return  jdbcTemplate.update(sql,new BeanPropertySqlParameterSource(id));
    }
}
