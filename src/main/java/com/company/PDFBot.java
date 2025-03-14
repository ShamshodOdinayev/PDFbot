package com.company;

import com.company.db.DataBase;
import com.company.db.InitDataBase;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class PDFBot {
    public static void main(String[] args) {
        try {
            DataBase.initTable();
            InitDataBase.adminInit();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new PDFBotLongPolling());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}