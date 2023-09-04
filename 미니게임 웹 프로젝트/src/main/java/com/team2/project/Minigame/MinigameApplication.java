package com.team2.project.Minigame;

import com.team2.project.Minigame.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AppConfig.class)
public class MinigameApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinigameApplication.class, args);
	}

}
