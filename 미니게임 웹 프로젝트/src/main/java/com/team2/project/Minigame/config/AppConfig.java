package com.team2.project.Minigame.config;

import com.team2.project.Minigame.dao.MemberDAO;
import com.team2.project.Minigame.dao.MemberDAOImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "com.team2.project.Minigame")
public class AppConfig {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.2.124:3306/minigame_db");
        dataSource.setUsername("admin");
        dataSource.setPassword("Green1234@");
        return dataSource;
    }

    @Bean
    public MemberDAO memberDAO(@Qualifier("jdbcTemplate") JdbcTemplate jdbcTemplate) {
        return new MemberDAOImpl(jdbcTemplate);
    }
    
}
