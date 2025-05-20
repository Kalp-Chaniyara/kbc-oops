package com.kbc;

import com.kbc.model.Question;
import com.kbc.util.Questions;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.kbc.model.Lifeline;

public class FlipQuestion implements Lifeline {
    private static FlipQuestion instance;
    private boolean available;
    private List<Question> allQuestions;
    private static List<Question> seenQuestions;
    private Question currentQuestion;
    private Question newQuestion;

    private FlipQuestion() {
        this.available = true;
        this.allQuestions = Questions.getQuestions();
        this.seenQuestions = new ArrayList<>();
    }

    public static FlipQuestion getInstance() {
        if (instance == null) {
            instance = new FlipQuestion();
        }
        return instance;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void setCurrentQuestion(String text, List<String> options, int correctIdx) {
        this.currentQuestion = new Question(text, options, correctIdx);
        if (!seenQuestions.contains(currentQuestion)) {
            seenQuestions.add(currentQuestion);
        }
    }

    @Override
    public List<?> use() {
        if (!available) {
            throw new IllegalStateException("Flip Question lifeline has already been used!");
        }

        newQuestion = getUnseenQuestion();
        if (newQuestion == null) {
            throw new IllegalStateException("No unseen questions available for flipping!");
        }
        
        available = false;
        return newQuestion.getOptions();
    }

    private Question getUnseenQuestion() {
        Random random = new Random();
        List<Question> unseenQuestions = new ArrayList<>(allQuestions);
        unseenQuestions.removeAll(seenQuestions);
        
        if (unseenQuestions.isEmpty()) {
            return null;
        }
        
        int randomIndex = random.nextInt(unseenQuestions.size());
        Question question = unseenQuestions.get(randomIndex);
        seenQuestions.add(question);
        return question;
    }

    public Question getNewQuestion() {
        return newQuestion;
    }

    public void addSeenQuestion(Question question) {
        if (!seenQuestions.contains(question)) {
            seenQuestions.add(question);
        }
    }

    public static boolean  isQuestionAlreadySeen(Question question){
          return seenQuestions.contains(question);
    }

    @Override
    public void reset() {
        available = true;
        currentQuestion = null;
        newQuestion = null;
    }
} 