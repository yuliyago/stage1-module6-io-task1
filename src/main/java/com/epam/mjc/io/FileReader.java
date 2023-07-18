package com.epam.mjc.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class FileReader {
    static Logger logger = Logger.getLogger(FileReader.class.getName());
    public String getDataFromFileIntoString(File file) {
        String rawDataFromFile="";
        StringBuilder bld = new StringBuilder();
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            int flag;
            while ((flag = fileInputStream.read()) != -1) {
                bld.append ((char) flag);
            }
            rawDataFromFile= bld.toString();
        } catch (FileNotFoundException e) {
            logger.info("Required file hasn't been found.");
//            System.out.println("Required file hasn't been found.");
        } catch (IOException e) {
            logger.info("Input/output exception has been thrown.");
//            System.out.println("Input/output exception has been thrown.");
        }
        return rawDataFromFile;
    }

    public Map<String, String> transformStringIntoMap(String rawDataFromFile) {
        HashMap<String, String> profileData = new HashMap<>();
        String[] lines = rawDataFromFile.split("\\r?\\n|\\r");
        String[] keyValue;
        for (String line : lines) {
            keyValue = line.split(": ");
            profileData.put(keyValue[0], keyValue[1]);
        }
        return profileData;
    }

    public Profile getDataFromFile(File file) {
        String rawDataFromFile = getDataFromFileIntoString(file);
        Map<String, String> profileData = transformStringIntoMap(rawDataFromFile);
        return new Profile(profileData.get("Name"), Integer.parseInt(profileData.get("Age")), profileData.get("Email"), Long.parseLong(profileData.get("Phone")));
    }
}
