package com.kbc.model;

import java.util.List;

public class Question {
     private final String text;
     private final List<String> options;
     private final int correctIdx;

     public Question(String text, List<String> options, int correctIdx){
          this.text = text;
          this.options = options;
          this.correctIdx = correctIdx;
     }

     public static void displayQuestion(Question q) {
        System.out.println(q.text);
        for (int i = 0; i < q.options.size(); i++) {
            System.out.printf("%d: %s%n", i + 1, q.options.get(i));
        }
    }

    public boolean checkAnswer(String userInput) {
        try {
            int choice = Integer.parseInt(userInput);
            return choice - 1 == correctIdx;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
