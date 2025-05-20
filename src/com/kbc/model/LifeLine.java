package com.kbc.model;

import java.util.List;

public interface Lifeline {
    /**
     * Check if the lifeline is available for use
     * @return true if lifeline can be used, false otherwise
     */
    boolean isAvailable();

    /**
     * Set up the current question and its options
     * @param question The question text
     * @param options List of all answer options
     * @param correctIdx Index of the correct option
     */
    void setCurrentQuestion(String question, List<String> options, int correctIdx);

    /**
     * Use the lifeline
     * @return Result of using the lifeline (can be List<String> for 50-50 or List<Integer> for Audience Poll)
     */
    List<?> use();

    /**
     * Reset the lifeline for reuse
     */
    void reset();
} 