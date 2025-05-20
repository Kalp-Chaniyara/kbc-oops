package com.kbc.util;

import com.kbc.AudiencePoll;
import com.kbc.FiftyFifty;
import com.kbc.FlipQuestion;
import com.kbc.model.Question;
import com.kbc.model.User;
import java.util.List;
import java.util.Scanner;

public class QuizManager {
    private final User user;
    private final Scanner scanner;
    private final FiftyFifty fiftyFifty;
    private final AudiencePoll audiencePoll;
    private final FlipQuestion flipQuestion;
    private int score;

    public QuizManager(User user) {
        this.user = user;
        this.scanner = new Scanner(System.in);
        this.fiftyFifty = FiftyFifty.getInstance();
        this.audiencePoll = AudiencePoll.getInstance();
        this.flipQuestion = FlipQuestion.getInstance();
        this.score = 0;
    }

    public void startQuiz() {
        List<Question> questions = Questions.getQuestions();
        int i = 0;
        
        while (i < questions.size()) {
            Question currentQuestion = questions.get(i);
            if(FlipQuestion.isQuestionAlreadySeen(currentQuestion)){
                i++;
                continue;
            }
            // Add current question to seen questions
            flipQuestion.addSeenQuestion(currentQuestion);
            
            Question.displayQuestion(currentQuestion);
            String userInput = scanner.nextLine();
            System.out.println();
            
            if (userInput.equalsIgnoreCase("Lifeline")) {
                currentQuestion = handleLifeline(currentQuestion);
                if (currentQuestion == null) {
                    // If user chose an invalid lifeline, continue with the same question
                    continue;
                }
                // After using lifeline, get user's answer
                String userInput2 = scanner.nextLine();
                if (!currentQuestion.checkAnswer(userInput2)) {
                    System.out.println("Wrong answer, Game is over now !!!");
                    break;
                } else {
                    System.out.println("YES, You are correct. Congratulations !!!");
                    score++;
                }
            } else if (!currentQuestion.checkAnswer(userInput)) {
                System.out.println("Wrong answer, Game is over now !!!");
                break;
            } else {
                System.out.println("YES, You are correct. Congratulations !!!");
                score++;
            }
            i++;
        }
        
        displayFinalScore();
        saveScore();
    }

    private Question handleLifeline(Question currentQuestion) {
        System.out.println("\nAvailable Lifelines:");
        System.out.println("F - 50:50 (Eliminate two wrong options)");
        System.out.println("A - Audience Poll");
        System.out.println("Q - Flip Question");
        System.out.print("\nEnter your choice (F/A/Q): ");
        
        String choice = scanner.nextLine().toUpperCase();
        
        switch (choice) {
            case "F":
                handleFiftyFifty(currentQuestion);
                return currentQuestion;
            case "A":
                handleAudiencePoll(currentQuestion);
                return currentQuestion;
            case "Q":
                return handleFlipQuestion(currentQuestion);
            default:
                System.out.println("Invalid lifeline choice!");
                return null;
        }
    }

    private Question handleFlipQuestion(Question currentQuestion) {
        try {
            if (flipQuestion.isAvailable()) {
                flipQuestion.setCurrentQuestion(
                    currentQuestion.getText(),
                    currentQuestion.getOptions(),
                    currentQuestion.getCorrectIdx()
                );
                
                flipQuestion.use();  // This will set up the new question
                Question newQuestion = flipQuestion.getNewQuestion();
                System.out.println("\nQuestion has been flipped! Here's your new question:");
                Question.displayQuestion(newQuestion);
                return newQuestion;
            } else {
                System.out.println("Flip Question lifeline has already been used!");
                return currentQuestion;
            }
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
            return currentQuestion;
        }
    }

    private void handleFiftyFifty(Question currentQuestion) {
        try {
            if (fiftyFifty.isAvailable()) {
                fiftyFifty.setCurrentQuestion(
                    currentQuestion.getText(),
                    currentQuestion.getOptions(),
                    currentQuestion.getCorrectIdx()
                );
                
                List<String> remainingOptions = fiftyFifty.use();
                System.out.println("\nRemaining options after 50-50:");
                char[] letters = {'A', 'B', 'C', 'D'};
                for (int i = 0; i < remainingOptions.size(); i++) {
                    String option = remainingOptions.get(i);
                    if (!option.isEmpty()) {
                        System.out.printf("%c) %s%n", letters[i], option);
                    }
                }
            } else {
                System.out.println("50-50 lifeline has already been used!");
            }
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleAudiencePoll(Question currentQuestion) {
        try {
            if (audiencePoll.isAvailable()) {
                audiencePoll.setCurrentQuestion(
                    currentQuestion.getText(),
                    currentQuestion.getOptions(),
                    currentQuestion.getCorrectIdx()
                );
                
                List<Integer> percentages = audiencePoll.use();
                System.out.println("\nAudience Poll Results:");
                char[] letters = {'A', 'B', 'C', 'D'};
                for (int i = 0; i < percentages.size(); i++) {
                    System.out.printf("%c) %d%%%n", letters[i], percentages.get(i));
                }
            } else {
                System.out.println("Audience Poll lifeline has already been used!");
            }
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void displayFinalScore() {
        System.out.println("\nGame Over!");
        System.out.println("Final Score: " + score + " out of " + Questions.getQuestions().size());
    }

    private void saveScore() {
        ScoreManager.saveScore(user, score);
        System.out.println("\nScore saved successfully!");
        // System.out.println("\nWould you like to see the high scores? (yes/no)");
        // String response = scanner.nextLine().toLowerCase();
        // if (response.equals("yes") || response.equals("y")) {
        //     ScoreManager.displayHighScores();
        // }
    }

    public int getScore() {
        return score;
    }
} 