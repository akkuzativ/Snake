package com.company;

import java.util.ArrayList;

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
    }

    ArrayList<HighScoreRecord> records = new ArrayList<>();

    HighScore() {
    }

    void readFromFile(String filename) {

    }

    void writeToFile(String filename) {

    }
}
