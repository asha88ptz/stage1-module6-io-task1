package com.epam.mjc.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class FileReader {
    private final static String TEST_FILE = "src/main/resources/Profile.txt";

    public static void main(String[] args) {
        FileReader fr = new FileReader();
        Profile profile = fr.getDataFromFile(new File(TEST_FILE));
        System.out.println(profile);
    }

    public Profile getDataFromFile(File file) {
        Profile profile = new Profile();
        String content = readFile(file);
        String[] lines = content.split("\n");
        for (String line : lines) {
            String[] parts = line.split(": ");
            switch (parts[0]) {
                case "Name":
                    profile.setName(parts[1]);
                    break;
                case "Age":
                    profile.setAge(Integer.parseInt(parts[1]));
                    break;
                case "Email":
                    profile.setEmail(parts[1]);
                    break;
                case "Phone":
                    profile.setPhone(Long.parseLong(parts[1]));
                    break;
            }
        }
        return profile;
    }

    private String readFile(File file) {
        StringBuilder sb = new StringBuilder();
        try (FileInputStream inputStream = new FileInputStream(file)) {
            int c;
            while ((c = inputStream.read()) != -1) {
                sb.append((char) c);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        return sb.toString();
    }


}
