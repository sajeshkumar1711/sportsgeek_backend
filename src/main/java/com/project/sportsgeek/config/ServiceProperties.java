package com.project.sportsgeek.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ToString
@Builder
@ConfigurationProperties(prefix = "db")
public class ServiceProperties {
    @NotNull
    public DbQueries dbQueries;
    @Data
    @Component
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DbQueries {
        private String listAllBetOnPlayers;
        private String listAllBetPlayer;
//        private String insertPlan;
//        private String updatePlan;
//        private String changePlanStatus;
    }
}
