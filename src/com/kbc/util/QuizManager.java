package com.kbc.util;

import com.kbc.model.Question;
import com.kbc.model.Questions;
import com.kbc.model.User;
import java.util.List;
import java.util.Scanner;

public class QuizManager {
    private final User user;
    private final Scanner scanner;
    private int score;

    public QuizManager(User user) {
        this.user = user;
        this.scanner = new Scanner(System.in);
        this.score = 0;
    }

    public void startQuiz() {
        List<Question> questions = Questions.getQuestions();
        int i = 0;
        
        while (i < questions.size()) {
            Question.displayQuestion(questions.get(i));
            String userInput = scanner.nextLine();
            System.out.println();
            
            if (!questions.get(i).checkAnswer(userInput)) {
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