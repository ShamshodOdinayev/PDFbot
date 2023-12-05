package com.company;

import com.company.enums.ServiceEnum;
import com.company.service.ServiceI;
import com.company.util.ReplyKeyboardUtil;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PDFBotLongPolling extends TelegramLongPollingBot {
    ServiceEnum service = new ServiceI();
    ReplyKeyboardUtil replyKeyboardUtil = new ReplyKeyboardUtil();

    int additionalName = 1;

    @Override
    public String getBotToken() {
        return service.getMessage("botToken");
    }

    @Override
    public String getBotUsername() {
        return service.getMessage("botUsername");
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                Message message = update.getMessage();
                if (update.getMessage().hasText()) {
                    if (message.getText().equals("/start")) {
                        startMenuMessage(message);
                    } else if (message.getText().equals("Rasm jo'natish yakunlandi")) {
                        pictureSaveMessage(message);
                    } else if (message.getText().equals("Create PDF")) {
                        createPDF(message);
                    }
                } else if (message.hasPhoto()) {
                    pictureSave(update);
                } else elseMessage(message.getChatId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void elseMessage(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(service.getMessage("elseMessage"));
        executeMsg(sendMessage);
    }

    private void createPDF(Message message) {
        List<String> pictureName = service.getPictureName(service.getMessage("savePdfsImagesPath"), message.getChatId());
        if (service.checkAvailabilityImage(pictureName)) {
            service.createPDF(service.getMessage("savePdfsImagesPath"), message.getChatId().toString() + additionalName + "PDF.pdf", pictureName);
            String chatId = message.getChatId().toString();
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(chatId);
            String filePath = service.getMessage("savePdfsImagesPath") + message.getChatId().toString() + additionalName + "PDF.pdf"; // Faylni o'zgartiring
            String fileName = message.getChatId().toString() + additionalName + "PDF.pdf"; // Fayl nomi
            sendDocument.setDocument(new InputFile(new File(filePath), fileName));
            executeMsg(sendDocument);
            service.deleteJPG(pictureName);
            menu(message);
        } else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText(service.getMessage("postPicture"));
            executeMsg(sendMessage);
        }
    }

    private void pictureSaveMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(service.getMessage("imageSaved"));
        ReplyKeyboardMarkup pdfButton = replyKeyboardUtil.createPDFButton();
        sendMessage.setReplyMarkup(pdfButton);
        executeMsg(sendMessage);
    }

    private void pictureSave(Update update) throws MalformedURLException, TelegramApiException {
        URL url = new URL(extractFileUrl(update));
        try {
            InputStream inputStream = url.openStream();
            File imageFile = new File(service.getMessage("savePdfsImagesPath") + update.getMessage().getChatId() + additionalName + ".jpg");
            additionalName++;
            checkName();
            ImageIO.write(ImageIO.read(inputStream), "jpg", imageFile);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkName() {
        if (additionalName >= 99) {
            additionalName = 1;
            service.deletePDF(service.getMessage("savePdfsImagesPath"));
        }
    }

    private String extractFileUrl(Update update) throws TelegramApiException {
        PhotoSize photo = update.getMessage().getPhoto().get(update.getMessage().getPhoto().size() - 1);
        GetFile getFile = new GetFile();
        getFile.setFileId(photo.getFileId());
        org.telegram.telegrambots.meta.api.objects.File file = execute(getFile);
        return getFilePath(file);
    }

    private String getFilePath(org.telegram.telegrambots.meta.api.objects.File file) {
        return "https://api.telegram.org/file/bot" + getBotToken() + "/" + file.getFilePath();
    }

    private void startMenuMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(service.getMessage("startMessage"));
        ReplyKeyboardMarkup pdfButton = replyKeyboardUtil.deliveryCompleted();
        sendMessage.setReplyMarkup(pdfButton);
        executeMsg(sendMessage);
    }

    private void menu(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(service.getMessage("menuMessage"));
        ReplyKeyboardMarkup pdfButton = replyKeyboardUtil.deliveryCompleted();
        sendMessage.setReplyMarkup(pdfButton);
        executeMsg(sendMessage);
    }

    private void executeMsg(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeMsg(SendDocument sendDocument) {
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}