package com.company.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.LinkedList;
import java.util.List;

public class ReplyKeyboardUtil {
    private KeyboardButton getButton(String text) {
        KeyboardButton button = new KeyboardButton();
        button.setText(text);
        return button;
    }

    public ReplyKeyboardMarkup createPDFButton() {
        KeyboardButton button = getButton("Create PDF");
        return getReplyKeyboardMarkup(button);
    }

    public ReplyKeyboardMarkup deliveryCompleted() {
        KeyboardButton button = getButton("Rasm jo'natish yakunlandi");
        return getReplyKeyboardMarkup(button);
    }

    private ReplyKeyboardMarkup getReplyKeyboardMarkup(KeyboardButton button) {
        KeyboardRow row = new KeyboardRow();
        row.add(button);
        List<KeyboardRow> rowList = new LinkedList<>();
        rowList.add(row);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(rowList);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    private ReplyKeyboardMarkup getReplyKeyboardMarkup(KeyboardButton button1, KeyboardButton button2) {
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        row1.add(button1);
        row2.add(button2);
        List<KeyboardRow> rowList = new LinkedList<>();
        rowList.add(row1);
        rowList.add(row2);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(rowList);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup adminButton(String profileList, String sendMessagesToUsers) {
        KeyboardButton button1 = getButton(profileList);
        KeyboardButton button2 = getButton(sendMessagesToUsers);
        return getReplyKeyboardMarkup(button1, button2);
    }
}