package com.project.sportsgeek.repository.genderrepo;

import com.project.sportsgeek.model.Gender;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "genderRepo")
public interface GenderRepository {
    public List<Gender> findAllGender();

    public List<Gender> findGenderById(int id) throws Exception;

    public int addGender(Gender gender) throws Exception;

    public boolean updateGender(int id, Gender gender) throws Exception;

    public int deleteGender(int id) throws Exception;
}
