package com.kbc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.kbc.model.Lifeline;

public class FiftyFifty implements Lifeline {
    private boolean used;
    private static FiftyFifty instance;
    private String question;
    private List<String> options;
    private int correctIndex;

    private FiftyFifty() {
        this.used = false;
    }

    public static FiftyFifty getInstance() {
        if (instance == null) {
            instance = new FiftyFifty();
        }
        return instance;
    }

    @Override
    public void setCurrentQuestion(String question, List<String> options, int correctIdx) {
        if (options == null || options.isEmpty()) {
            throw new IllegalArgumentException("Options list cannot be null or empty.");
        }
        if (correctIdx < 0 || correctIdx >= options.size()) {
            throw new IndexOutOfBoundsException("Correct index is out of range.");
        }
        this.question = question;
        this.options = new ArrayList<>(options);
        this.correctIndex = correctIdx;
        this.used = false;
    }

    @Override
    public boolean isAvailable() {
        return !used;
    }

    @Override
    public List<String> use() {
        if (!isAvailable()) {
            throw new IllegalStateException("50-50 lifeline already used.");
        }
        if (options == null) {
            throw new IllegalStateException("No question has been set.");
        }

        // Create a list to store which options to keep (true) or remove (false)
        List<Boolean> keepOptions = new ArrayList<>(Collections.nCopies(options.size(), false));
        
        // Keep the correct answer
        keepOptions.set(correctIndex, true);
        
        // Get all incorrect options
        List<Integer> incorrectIndices = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            if (i != correctIndex) {
                incorrectIndices.add(i);
            }
        }
        
        // Randomly select one incorrect option to keep
        Collections.shuffle(incorrectIndices);
        keepOptions.set(incorrectIndices.get(0), true);
        
        // Create the result list maintaining original positions
        List<String> result = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            if (keepOptions.get(i)) {
                result.add(options.get(i));
            } else {
                result.add("");  // Empty string for removed options
            }
        }
        
        used = true;
        return result;
    }

    @Override
    public void reset() {
        used = false;
        question = null;
        options = null;
    }
}