package com.epam.mjc.io;

import java.io.*;


public class FileReader {
    private final static String TEST_FILE = "src/main/resources/Profile.txt";

    private String readFile(File file) {

        BufferedReader br = null;
        java.io.FileReader fr = null;
        String content = "";

        try {

            fr = new java.io.FileReader(file);
            br = new BufferedReader(fr);

            String line;
            String delim = "";
            while ((line = br.readLine()) != null) {
                content += delim;
                content += line;
                delim = "\n";
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        } finally {
            try {
                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                System.err.format("IOException: %s%n", ex);
            }
        }
        return content;

    }
    public Profile getDataFromFile(File file) {
        String content = readFile(file);
        Profile profile = new Profile();
        for (String line: content.split("\n")) {
            String[] parts = line.split(": ");
            switch (parts[0]) {
                case "Name":
                    profile.setName(parts[1]);
                    break;
                case "Age":
                    try {
                        int age = Integer.parseInt(parts[1]);
                        profile.setAge(age);
                    } catch (NumberFormatException ex) {
                        System.err.format("Parsing Error: Cannot parse as integer %s%n", parts[1]);
                    }
                    break;
                case "Email":
                    profile.setEmail(parts[1]);
                    break;
                case "Phone":
                    try {
                        long phone = Long.parseLong(parts[1]);
                        profile.setPhone(phone);
                    } catch (NumberFormatException ex) {
                        System.err.format("Parsing Error: Cannot parse as long %s%n", parts[1]);
                    }
                    break;
                default:
                    System.err.format("Parsing Error: Unmatched token %s%n", parts[0]);
            }
        }

        return profile;
    }

    public static void main(String[] args) {
        FileReader fr = new FileReader();
        Profile profile = fr.getDataFromFile(new File(TEST_FILE));
        System.out.println(profile);
    }
}
