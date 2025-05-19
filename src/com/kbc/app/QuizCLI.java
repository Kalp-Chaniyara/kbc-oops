package com.kbc.app;

import com.kbc.FiftyFifty;
import com.kbc.model.Question;
import com.kbc.model.User;
import java.util.List;
import java.util.Scanner;

public class QuizCLI {
    private final User user;
    private Question q;
    private static FiftyFifty fiftyFifty;
    private static Scanner scanner;

    public QuizCLI(User user) {
        this.user = user;
        this.fiftyFifty = FiftyFifty.getInstance();
        this.scanner = new Scanner(System.in);
    }

    public static void rules1() {
        try {
            System.out.println("THE RULES OF THE GAME ARE PRETTY SIMPLE :");
            Thread.sleep(1750);
            System.out.println("~> The game consists of total 15 questions with different difficulty levels");
            // Thread.sleep(1500);
            System.out.println("~> Each question will have 4 options ");
            // Thread.sleep(1750);
            System.out.println("~> The game will start with the amount of 5,000 and will go upto 70,000,000");
            // Thread.sleep(1500);
            System.out.println("~> Checkpoints are 80,000 , 25,60,000 and 10,000,000");
            // Thread.sleep(1500);
            System.out.println("~> If you give wrong answer , then you will get the amount mentioned in the previous checkpoint");
            // Thread.sleep(1500);
            System.out.println("~> At any point of time , you can escape the game and take home the amount that you have won till the moment");
            // Thread.sleep(1500);
            System.out.println("~> The game will have three lifelines : ");
            // Thread.sleep(1500);
            System.out.println(" # Flip the question ");
            System.out.println(" # 50 : 50 ");
            System.out.println(" # Audience poll ");
            //! System.out.println(" # Feed the Greed ");
            // Thread.sleep(1500);
            System.out.println("You can flip any question using 'Flip the Question' ");
            // Thread.sleep(1500);
            System.out.println("You can eliminate two incorrect options using '50:50' ");
            // Thread.sleep(1500);
            System.out.println("You can get a hint using 'Expert's Advice' ");
            // Thread.sleep(1500);
            System.out.println("You can consider audience's opinion using 'Audience Poll'");
            // Thread.sleep(1500);
            // System.out.println("After earning 80,000 , you can use ' Feed the Greed ' to get 1.5 times the money on each question for the next 5 questions but you will get multiple correct choice questions");
            // Thread.sleep(1500);
            // System.out.println("If any of your multiple correct choice question is incorrect , you will receive just 80,000 and the game will end");
            // Thread.sleep(1500);
            // System.out.println("But if it partially correct , your prize money will be reduced by half the amount of that questions and you will be shifted in the normal mode\n");
            // Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void rules2() {
        try {
            System.out.println("~> IMP RULES FOR EVERY QUESTION:");
            Thread.sleep(1500);
            System.out.println("~> Remember , Press A,B,C,D to answer the question ");
            // Thread.sleep(1500);
            System.out.println("~> Type Lifeline to take a Lifeline");
            // Thread.sleep(1500);
            System.out.println("~> Press Quit to escape the game");
            // Thread.sleep(1500);
            System.out.println("~> Press '!' for flipping the question");
            // Thread.sleep(1500);
            System.out.println("~> Press '%' for 50:50");
            // Thread.sleep(1500);
            System.out.println("~> Press '@' for Audience Poll");
            // Thread.sleep(1500);
            System.out.println("~> HERE WE BEGIN");
            // Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void handleLifeline(Question currentQuestion) {
        System.out.println("\nAvailable Lifelines:");
        System.out.println("F - 50:50 (Eliminate two wrong options)");
        System.out.println("A - Audience Poll");
        System.out.println("Q - Flip Question");
        System.out.print("\nEnter your choice (F/A/Q): ");
        
        String choice = scanner.nextLine().toUpperCase();
        
        switch (choice) {
            case "F":
                if (fiftyFifty.isLifelineAvailable()) {
                    // Set current question data
                    fiftyFifty.setCurrentQuestion(
                        currentQuestion.getText(),
                        currentQuestion.getOptions(),
                        currentQuestion.getCorrectIdx()
                    );
                    
                    // Use 50-50
                    List<String> remainingOptions = fiftyFifty.useFiftyFifty();
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
                break;
            case "A":
                // TODO: Implement Audience Poll
                System.out.println("Audience Poll lifeline coming soon!");
                break;
            case "Q":
                // TODO: Implement Flip Question
                System.out.println("Flip Question lifeline coming soon!");
                break;
            default:
                System.out.println("Invalid lifeline choice!");
        }
    }

    public void play() {
        System.out.println("\nLet's play, " + user.getUsername() + "!");
        rules1();
        rules2();
    }
}
