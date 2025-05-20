package com.kbc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.kbc.model.Lifeline;

/**
 * Singleton class to simulate an Audience Poll lifeline.
 * Distributes realistic audience vote percentages among options.
 */
public class AudiencePoll implements Lifeline {
    private static volatile AudiencePoll instance;
    private boolean used;
    private String question;
    private List<String> options;
    private int correctIndex;
    private final Random random;

    private AudiencePoll() {
        this.used = false;
        this.random = new Random();
    }

    /**
     * Thread-safe singleton retrieval with double-checked locking.
     */
    public static AudiencePoll getInstance() {
        if (instance == null) {
            synchronized (AudiencePoll.class) {
                if (instance == null) {
                    instance = new AudiencePoll();
                }
            }
        }
        return instance;
    }

    /**
     * Set up the current question and its options.
     * @param question      The question text.
     * @param options       List of all answer options.
     * @param correctIdx    Index of the correct option in the list.
     */
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

    /**
     * Check if the lifeline is still available.
     */
    @Override
    public boolean isAvailable() {
        return !used;
    }

    /**
     * Use the Audience Poll lifeline, returning percentages per option.
     * Ensures realistic distribution: correct gets 50-70%, others share the remainder.
     */
    @Override
    public List<Integer> use() {
        if (!isAvailable()) {
            throw new IllegalStateException("Audience Poll lifeline already used.");
        }
        if (options == null) {
            throw new IllegalStateException("No question has been set.");
        }

        // Generate random percentages
        List<Integer> percentages = new ArrayList<>();
        int remainingPercentage = 100;
        
        // Give 40-60% to correct answer
        int correctPercentage = random.nextInt(21) + 40; // Random between 40-60
        percentages.add(correctPercentage);
        remainingPercentage -= correctPercentage;

        // Distribute remaining percentage among other options
        for (int i = 0; i < options.size() - 1; i++) {
            if (i == options.size() - 2) {
                // Last option gets remaining percentage
                percentages.add(remainingPercentage);
            } else {
                // Random percentage for other options
                int percentage = random.nextInt(remainingPercentage / 2);
                percentages.add(percentage);
                remainingPercentage -= percentage;
            }
        }

        // Shuffle percentages except for correct answer
        List<Integer> incorrectPercentages = new ArrayList<>();
        for (int i = 0; i < percentages.size(); i++) {
            if (i != correctIndex) {
                incorrectPercentages.add(percentages.get(i));
            }
        }
        Collections.shuffle(incorrectPercentages);

        // Reconstruct final percentages list
        List<Integer> finalPercentages = new ArrayList<>(Collections.nCopies(options.size(), 0));
        finalPercentages.set(correctIndex, correctPercentage);
        int incorrectIndex = 0;
        for (int i = 0; i < finalPercentages.size(); i++) {
            if (i != correctIndex) {
                finalPercentages.set(i, incorrectPercentages.get(incorrectIndex++));
            }
        }

        used = true;
        return finalPercentages;
    }

    /**
     * Reset lifeline so it can be used again for a new question.
     */
    @Override
    public void reset() {
        used = false;
        question = null;
        options = null;
    }
}
