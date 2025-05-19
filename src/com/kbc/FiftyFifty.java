package com.kbc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FiftyFifty {
    private boolean isUsed;
    private static FiftyFifty instance;
    private String currentQuestion;
    private List<String> currentOptions;
    private int correctIndex;

    private FiftyFifty() {
        this.isUsed = false;
    }

    public static FiftyFifty getInstance() {
        if (instance == null) {
            instance = new FiftyFifty();
        }
        return instance;
    }

    public void setCurrentQuestion(String question, List<String> options, int correctIdx) {
        this.currentQuestion = question;
        this.currentOptions = new ArrayList<>(options);
        this.correctIndex = correctIdx;
    }

    public boolean isLifelineAvailable() {
        return !isUsed;
    }

    public List<String> useFiftyFifty() {
        if (!isLifelineAvailable()) {
            System.out.println("50-50 lifeline has already been used!");
            return currentOptions;
        }

        if (currentOptions == null || currentOptions.isEmpty()) {
            System.out.println("No question is currently set!");
            return new ArrayList<>();
        }

        // Create a list to store which options to keep (true) or remove (false)
        List<Boolean> keepOptions = new ArrayList<>(Collections.nCopies(2, false));
        
        // Keep the correct answer
        keepOptions.set(correctIndex, true);
        
        // Get all incorrect options
        List<Integer> incorrectIndices = new ArrayList<>();
        for (int i = 0; i < currentOptions.size(); i++) {
            if (i != correctIndex) {
                incorrectIndices.add(i);
            }
        }
        
        // Randomly select one incorrect option to keep
        Collections.shuffle(incorrectIndices);
        keepOptions.set(incorrectIndices.get(0), true);
        
        // Create the result list maintaining original positions
        List<String> result = new ArrayList<>();
        for (int i = 0; i < currentOptions.size(); i++) {
            if (keepOptions.get(i)) {
                result.add(currentOptions.get(i));
            } else {
                result.add("");  // Empty string for removed options
            }
        }
        
        isUsed = true;
        return result;
    }

    public void reset() {
        isUsed = false;
        currentQuestion = null;
        currentOptions = null;
    }
}