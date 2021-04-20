package com.project.sportsgeek.repository.teamrepo;

import com.project.sportsgeek.model.Team;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "teamRepo")
public interface TeamRepository {
    public List<Team> findAllTeam();
    public List<Team> findTeamById(int i) throws Exception;
    public int addTeam(Team team) throws Exception;
    public boolean updateTeam(int id, Team team) throws Exception;
    public int deleteTeam(int id) throws Exception;
}
