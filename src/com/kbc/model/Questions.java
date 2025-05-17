package com.kbc.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;

public class Questions {
    private static final List<Question> questions = new ArrayList<>();
    private static final String QUESTIONS_FILE = "com/kbc/data/questions.csv";

    static {
        loadQuestions();
    }

    private static void loadQuestions() {
        try (BufferedReader reader = new BufferedReader(new FileReader(QUESTIONS_FILE))) {
            reader.readLine(); //skip the top level line

            String line;
            while((line=reader.readLine())!=null){
                String[] parts = line.split(",");
                if(parts.length>=7){
                    String text = parts[0];
                    List<String> options = Arrays.asList(parts[1],parts[2],parts[3],parts[4]);
                    int correctIdx = Integer.parseInt(parts[5]);
                    questions.add(new Question(text,options,correctIdx));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading questions: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static List<Question> getQuestions() {
        return new ArrayList<>(questions);
    }

    public static Question getQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            return questions.get(index);
        }
        return null;
    }
} 