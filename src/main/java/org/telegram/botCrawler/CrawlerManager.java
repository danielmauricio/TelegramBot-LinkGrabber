package org.telegram.botCrawler;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;

import updatesHandler.CrawlerHandler;

public class CrawlerManager {
	
	public static void main(String[] args) {
		TelegramBotsApi telegramBotApi =  new TelegramBotsApi();
		try {
            telegramBotApi.registerBot(new CrawlerHandler());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
	}
}
