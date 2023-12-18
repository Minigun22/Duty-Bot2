package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.example.demo.bot.DutyBot;

@SpringBootApplication
@EnableScheduling
public class DutyBotApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DutyBotApplication.class, args);
		try {
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
			telegramBotsApi.registerBot(new DutyBot());
			System.out.println("Bot has been registered successfully!");
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
