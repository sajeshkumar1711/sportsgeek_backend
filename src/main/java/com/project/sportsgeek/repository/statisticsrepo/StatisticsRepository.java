package com.project.sportsgeek.repository.statisticsrepo;

import com.project.sportsgeek.model.BetOnTeam;
import com.project.sportsgeek.model.Statistics;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "statRepo")
public interface StatisticsRepository {

    public List<Statistics> findUserStatistics();

    public List<BetOnTeam> findFutureBetPoints();
}
