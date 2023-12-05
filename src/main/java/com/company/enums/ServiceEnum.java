package com.company.enums;

import java.util.List;

public interface ServiceEnum {
    void createPDF(String pathName, String PDFName, List<String> pictureName);

    List<String> getPictureName(String directoryPath, Long chatId);

    String getMessage(String key);

    boolean checkAvailabilityImage(List<String> pictureName);
    void deleteJPG(List<String> jpgName);
    void deletePDF(String path);
}