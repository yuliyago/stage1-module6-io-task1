package com.epam.mjc.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;


public class FileReader {
    public String getDataFromFileIntoString(File file) {
        String rawDataFromFile = "";
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            int flag;
            while ((flag = fileInputStream.read()) != -1) {
                rawDataFromFile += (char) flag;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Required file hasn't been found.");
        } catch (IOException e) {
            System.out.println("Input/output exception has been thrown.");
        }
        return rawDataFromFile;
    }

    public HashMap<String,String> transformStringIntoMap(String rawDataFromFile) {
        HashMap<String,String> profileData=new HashMap<>();
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
        HashMap<String,String> profileData = transformStringIntoMap(rawDataFromFile);
        return new Profile(profileData.get("Name"),Integer.parseInt(profileData.get("Age")),profileData.get("Email"),Long.parseLong(profileData.get("Phone")));
    }
}
