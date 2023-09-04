package com.team2.project.Minigame.email;

public interface EmailService {
    String sendSimpleMessage(String to) throws Exception;
    String getServerCode();
}
