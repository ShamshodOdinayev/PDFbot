package com.company.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.LinkedList;
import java.util.List;

public class ReplyKeyboardUtil {
    private KeyboardButton button(String text) {
        KeyboardButton button = new KeyboardButton();
        button.setText(text);
        return button;
    }

    public ReplyKeyboardMarkup createPDFButton() {
        KeyboardButton button = button("Create PDF");
        return getReplyKeyboardMarkup(button);
    }

    public ReplyKeyboardMarkup deliveryCompleted() {
        KeyboardButton button = button("Rasm jo'natish yakunlandi");
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
}