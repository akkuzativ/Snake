package com.company;

import java.util.ArrayList;
import java.io.*;
import java.util.Comparator;

/***
 * Acts as a wrapper for a list of records
 */
public class HighScore {
    /***
     * Represents a high score record combined of a player's name and their score
     */
    public static class HighScoreRecord {
        private final String name;
        private final int score;

        /***
         * HighScoreRecord constructor
         * @param name player's name
         * @param score player's score
         */
        HighScoreRecord(String name, int score) {
            this.name = name;
            this.score = score;
        }

        /***
         * Gets score
         * @return score inside the record
         */
        public int getScore() {
            return score;
        }

        /***
         * Represents the record as a formatted string
         * @return formatted string representing the record
         */
        @Override
        public String toString() {
            return (name + " " + score);
        }
    }

    private ArrayList<HighScoreRecord> records = new ArrayList<>();
    private final String saveFileName;

    /***
     * Highscore constructor
     * @param saveFileName file associated with the record for the IO operations
     */
    HighScore(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    /***
     * Gets the ArrayList of records
     * @return ArrayList of records
     */
    public ArrayList<HighScoreRecord> getRecords() {
        return records;
    }

    /***
     * Adds a specified record to the list of records
     * @param record record to add
     */
    public void addRecord(HighScoreRecord record) {
        records.add(record);
        records.sort(Comparator.comparingInt(HighScoreRecord::getScore).reversed());
    }

    /***
     * Tries to read records from the associated file
     */
    public void readFromFile() {
        File inputFile = new File(saveFileName).getAbsoluteFile();
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(inputFile);
        }
        catch (Exception e) {
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

    /***
     * Tries to write the records to the associated file
     */
    public void writeToFile() {
        File outputFile = new File(saveFileName);
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
