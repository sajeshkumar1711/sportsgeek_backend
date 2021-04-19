package com.project.sportsgeek.repository.tournamentrepo;

import com.project.sportsgeek.model.Tournament;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "tournamentRepo")
public interface TournamentRepository {
    public List<Tournament> findAllTournament();
    public List<Tournament> findTournamentById(int i) throws  Exception;
    public int addTournament(Tournament tournament) throws Exception;
    public boolean updateTournament(int id, Tournament tournament) throws Exception;
    public boolean updateActiveTournament(int id) throws Exception;
    public int deleteTournament(int id) throws Exception;
}
