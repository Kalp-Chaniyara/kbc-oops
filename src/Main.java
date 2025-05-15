import com.kbc.app.AuthCLI;
import com.kbc.app.QuizCLI;
import com.kbc.model.User;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        AuthCLI authCLI = new AuthCLI();
        authCLI.start();
        User user = authCLI.getUser();

        QuizCLI quizCLI = new QuizCLI(user);
        quizCLI.play();
    }
}
