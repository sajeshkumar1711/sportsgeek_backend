package com.project.sportsgeek.repository.genderrepo;

import com.project.sportsgeek.mapper.GenderRowMapper;
import com.project.sportsgeek.model.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "genderRepo")
public class GenderRepositoryImpl implements GenderRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Gender> findAllGender() {
        String sql = "SELECT * FROM Gender";
        return jdbcTemplate.query(sql, new GenderRowMapper());
    }

    @Override
    public List<Gender> findGenderById(int id) throws Exception {
        String sql = "SELECT * FROM Gender WHERE GenderId=" + id;
        return jdbcTemplate.query(sql, new GenderRowMapper());
    }

    @Override
    public int addGender(Gender gender) throws Exception {
        String insert_query = "INSERT INTO Gender (Name) values('" + gender.getName() + ")";
        jdbcTemplate.update(insert_query, new BeanPropertySqlParameterSource(gender));
        return 1;
    }

    @Override
    public boolean updateGender(int id, Gender gender) throws Exception {
        String update_sql = "UPDATE Gender SET Name = '" + gender.getName() + "' WHERE GenderId =" + id;
        gender.setGenderId(id);
        return jdbcTemplate.update(update_sql, new BeanPropertySqlParameterSource(gender)) > 0;
    }

    @Override
    public int deleteGender(int id) throws Exception {
        String sql = "DELETE FROM Gender WHERE GenderId =" + id;
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(id));
    }

}
