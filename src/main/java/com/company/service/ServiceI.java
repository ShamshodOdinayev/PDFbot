package com.company.service;

import com.company.enums.ServiceEnum;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ServiceI implements ServiceEnum {
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream input = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createPDF(String pathName, String PDFName, List<String> pictureName) {
        try {
            PDDocument document = new PDDocument();
            String pictureStoragePath = getMessage("savePdfsImagesPath");
            for (String imagePath : pictureName) {
                BufferedImage bufferedImage = ImageIO.read(new File(pictureStoragePath + imagePath));
                PDPage page = new PDPage(new PDRectangle(bufferedImage.getWidth(), bufferedImage.getHeight()));
                document.addPage(page);
                PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);
                org.apache.pdfbox.pdmodel.PDPageContentStream contentStream = new org.apache.pdfbox.pdmodel.PDPageContentStream(document, page);
                contentStream.drawImage(pdImage, 0, 0);
                contentStream.close();
            }
            document.save(pathName + PDFName);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getPictureName(String directoryPath, Long chatId) {
        List<String> name = new ArrayList<>();
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] filesAndSubdirectories = directory.listFiles();
            if (filesAndSubdirectories != null) {
                for (File fileOrDir : filesAndSubdirectories) {
                    if (!fileOrDir.isDirectory()) {
                        String name1 = fileOrDir.getName();
                        if (name1.startsWith(chatId + "") && name1.endsWith(".jpg")) {
                            name.add(name1);
                        }
                    }
                }
            }
        }
        return name;
    }

    public void deleteJPG(List<String> jpgName) {
        for (String s1 : jpgName) {
            File file = new File(getMessage("savePdfsImagesPath") + s1);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Override
    public void deletePDF(String path) {
        File directory = new File(path);
        File[] filesAndDirs = directory.listFiles();
        assert filesAndDirs != null;
        for (File file : filesAndDirs) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Override
    public String getMessage(String key) {
        return properties.getProperty(key);
    }

    @Override
    public boolean checkAvailabilityImage(List<String> pictureName) {
        return pictureName.size() > 0;
    }
}