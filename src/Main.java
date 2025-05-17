import com.kbc.app.AuthCLI;
import com.kbc.app.QuizCLI;
import com.kbc.model.User;
import com.kbc.util.QuizManager;

public class Main {
     public static void main(String[] args) throws Exception {
        System.out.println("Welcome to KBC Quiz Game!");

        // Initialize authentication
        AuthCLI authCLI = new AuthCLI();
        authCLI.start();
        User user = authCLI.getUser();

        // Initialize quiz
        QuizCLI quizCLI = new QuizCLI(user);
        quizCLI.play();

        // Start the quiz
        QuizManager quizManager = new QuizManager(user);
        quizManager.startQuiz();
    }
}
