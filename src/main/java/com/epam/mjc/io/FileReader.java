package com.epam.mjc.io;

import java.io.*;


public class FileReader {
    public static final int SET_ERROR = 0;  // 0000
    public static final int SET_NAME = 1;  // 0001
    public static final int SET_AGE = 2;  // 0010
    public static final int SET_EMAIL = 4;  // 0100
    public static final int SET_PHONE = 8;  // 1000
    public static final int CHECK_SUM_OK = 15; // 1111
    private final static String TEST_FILE = "src/main/resources/Profile.txt";

    public static void main(String[] args) {
        FileReader fr = new FileReader();
        Profile profile = fr.getDataFromFile(new File(TEST_FILE));
        System.out.println(profile);
    }

    public Profile getDataFromFile(File file) {
        Profile profile = new Profile();
        String content = getFileContent(file);
        int check_sum = 0;
        for (String line : content.split("\n")) {
            String[] parts = line.split(": ");
            if (parts.length != 2) {
                System.err.format("Parsing Error: Cannot parse line %s%n", line);
            } else {
                check_sum += setAttribute(profile, parts[0], parts[1]);
            }
        }
        if (check_sum != CHECK_SUM_OK) {
            System.err.println("Parsing Error: Missing some attribute");
        }
        return profile;
    }

    private String getFileContent(File file) {

        BufferedReader br = null;
        StringBuilder content = new StringBuilder();
        try {
            br = new BufferedReader(new java.io.FileReader(file));

            String line;
            String new_line_char = "";
            while ((line = br.readLine()) != null) {
                content.append(new_line_char).append(line);
                new_line_char = "\n";
            }
        } catch (IOException ex) {
            System.err.format("IOException: %s%n", ex);
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                System.err.format("IOException: %s%n", ex);
            }
        }
        return content.toString();
    }

    private int setAttribute(Profile profile, String attribute, String value) {
        try {
            if ("Name".equals(attribute)) {
                profile.setName(value);
                return SET_NAME;
            } else if ("Age".equals(attribute)) {
                int age = Integer.parseInt(value);
                profile.setAge(age);
                return SET_AGE;
            } else if ("Email".equals(attribute)) {
                profile.setEmail(value);
                return SET_EMAIL;
            } else if ("Phone".equals(attribute)) {
                long phone = Long.parseLong(value);
                profile.setPhone(phone);
                return SET_PHONE;
            } else {
                System.err.format("Parsing Error: Unmatched token %s%n", attribute);
                return SET_ERROR;
            }
        } catch (NumberFormatException ex) {
            System.err.format("Parsing Error: Cannot parse token %s as number%n", value);
            return SET_ERROR;
        }
    }


}
