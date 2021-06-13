package com.company;

import java.util.ArrayList;
import java.io.*;
import java.util.Comparator;

public class HighScore {
    public static class HighScoreRecord {
        private String name;
        private int score;

        HighScoreRecord(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        @Override
        public String toString() {
            return (name + " " + score);
        }
    }

    private ArrayList<HighScoreRecord> records = new ArrayList<>();
    private String saveFileName;

    HighScore(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    public ArrayList<HighScoreRecord> getRecords() {
        return records;
    }

    public void addRecord(HighScoreRecord record) {
        records.add(record);
        records.sort(Comparator.comparingInt(HighScoreRecord::getScore).reversed());
    }

    public void readFromFile() {
        String filename = saveFileName;
        File inputFile = new File(filename).getAbsoluteFile();
        FileInputStream inputStream;
;        try {
            inputStream = new FileInputStream(inputFile);
        }
        catch (Exception e) {
            System.out.println("asdasd");
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line;

        ArrayList<HighScoreRecord> readRecords = new ArrayList<>();

        do {
            try {
                line = bufferedReader.readLine();
            }
            catch (IOException e) {
                break;
            }
            if (line == null) {
                break;
            }
            String[] possibleRecord = line.split(" ");
            if (possibleRecord.length != 2) {
                continue;
            }
            String name = possibleRecord[0];
            int score = Integer.parseInt(possibleRecord[1]);
            HighScoreRecord highScoreRecord = new HighScoreRecord(name, score);
            readRecords.add(highScoreRecord);

        } while (line != null);

        records = readRecords;

        if (!records.isEmpty()) {
            records.sort(Comparator.comparingInt(HighScoreRecord::getScore).reversed());
        }

        try {
            bufferedReader.close();
            inputStream.close();
        }
        catch (IOException e) {}
    }

    public void writeToFile() {
        String filename = saveFileName;
        File outputFile = new File(filename);
        FileOutputStream outputStream;
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            }
            catch (Exception e) {
                System.out.println("Failed to save score");
                return;
            }
        }
        try {
            outputStream = new FileOutputStream(outputFile, false);
        }
        catch (Exception e) {
            System.out.println("Failed to save score");
            return;
        }

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

        for (HighScoreRecord record: records) {
            try {
                bufferedWriter.write(record.toString());
                bufferedWriter.newLine();
            }
            catch (IOException e) {
                break;
            }
        }


        try {
            bufferedWriter.flush();
            bufferedWriter.close();
        }
        catch (IOException e) {}
    }
}
