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

     public String getText() {
         return text;
     }

     public List<String> getOptions() {
         return options;
     }

     public int getCorrectIdx() {
         return correctIdx;
     }

     public static void displayQuestion(Question q) {
        System.out.println(q.text);
        char[] letters = {'A', 'B', 'C', 'D'};
        for (int i = 0; i < q.options.size(); i++) {
            System.out.printf("%c) %s%n", letters[i], q.options.get(i));
        }
    }

    public boolean checkAnswer(String userInput) {
        if (userInput == null || userInput.isEmpty()) {
            return false;
        }
        
        // Convert letter input to index (A->0, B->1, etc.)
        char choice = userInput.toUpperCase().charAt(0);
        int index = choice - 'A';
        
        // Check if the input is valid (A-D)
        if (index < 0 || index >= options.size()) {
            return false;
        }
        
        return index == correctIdx;
    }
}
