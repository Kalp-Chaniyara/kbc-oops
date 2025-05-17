package com.kbc.util;

import com.kbc.model.User;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScoreManager {
    private static final String SCORES_FILE = "com/kbc/data/scores.csv";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void saveScore(User user, int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORES_FILE,true))) {
            writer.newLine();
            String add = user.getUsername();
            add+=',';
            add+=Integer.toString(score);
            System.out.println("ADD: "+add);
            writer.write(add);
        } catch (IOException e) {
            System.err.println("Error saving score: " + e.getMessage());
            e.printStackTrace();
        }
    }

} 