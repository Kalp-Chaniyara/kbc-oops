package com.kbc.app;

import com.kbc.model.User;

public class QuizCLI {
    private final User user;

    public QuizCLI(User user) {
        this.user = user;
    }

    public static void rules1() {
        try {
            System.out.println("THE RULES OF THE GAME ARE PRETTY SIMPLE :");
            Thread.sleep(1750);
            System.out.println("~> The game consists of total 15 questions with different difficulty levels");
            System.out.println("~> Each question will have 4 options ");
            System.out.println("~> The game will start with the amount of 5,000 and will go upto 70,000,000");
            System.out.println("~> Checkpoints are 80,000 , 25,60,000 and 10,000,000");
            System.out.println("~> If you give wrong answer , then you will get the amount mentioned in the previous checkpoint");
            System.out.println("~> At any point of time , you can escape the game and take home the amount that you have won till the moment");
            System.out.println("~> The game will have three lifelines : ");
            System.out.println(" # Flip the question ");
            System.out.println(" # 50 : 50 ");
            System.out.println(" # Audience poll ");
            System.out.println("You can flip any question using 'Flip the Question' ");
            System.out.println("You can eliminate two incorrect options using '50:50' ");
            System.out.println("You can get a hint using 'Expert's Advice' ");
            System.out.println("You can consider audience's opinion using 'Audience Poll'");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void rules2() {
        try {
            System.out.println("~> IMP RULES FOR EVERY QUESTION:");
            Thread.sleep(1500);
            System.out.println("~> Remember , Press A,B,C,D to answer the question ");
            System.out.println("~> Type Lifeline to take a Lifeline");
            System.out.println("~> Press Quit to escape the game");
            System.out.println("~> Press '!' for flipping the question");
            System.out.println("~> Press '%' for 50:50");
            System.out.println("~> Press '@' for Audience Poll");
            System.out.println("~> HERE WE BEGIN");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        System.out.println("\nLet's play, " + user.getUsername() + "!");
        rules1();
        rules2();
    }
}
