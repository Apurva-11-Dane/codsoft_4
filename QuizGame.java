import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class QuizGame {
    private static final int QUESTION_TIME_LIMIT = 10; // seconds
    private static int score = 0;
    private static int currentQuestionIndex = 0;
    private static boolean answerSubmitted = false;
    private static String[] questions = {
        "What is the capital of India",
        "What is (a+b)^2?",
        "What is the largest planet in our solar system?"
    };
    private static String[][] options = {
        {"1. Mumbai", "2. New Delhi", "3. Punjab", "4. Pune"},
        {"1. a^2+2ab+b^2", "2. a+b", "3. a^2+b^2", "4. a+b^2"},
        {"1. Earth", "2. Mars", "3. Jupiter", "4. Saturn"}
    };
    private static int[] correctAnswers = {2, 1, 3}; // correct options (1-indexed)
    private static boolean[] correctAnswersFlag = new boolean[questions.length]; // stores correct/incorrect answers
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        for (currentQuestionIndex = 0; currentQuestionIndex < questions.length; currentQuestionIndex++) {
            answerSubmitted = false;
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (!answerSubmitted) {
                        System.out.println("\nTime's up!");
                        correctAnswersFlag[currentQuestionIndex] = false;
                        currentQuestionIndex++;
                        askNextQuestion(scanner, timer);
                    }
                }
            };
            timer.schedule(task, QUESTION_TIME_LIMIT * 1000);
            askNextQuestion(scanner, timer);
        }
        scanner.close();
        displayResult();
    }

    private static void askNextQuestion(Scanner scanner, Timer timer) {
        if (currentQuestionIndex >= questions.length) {
            timer.cancel();
            displayResult();
            return;
        }

        System.out.println("\n" + questions[currentQuestionIndex]);
        for (String option : options[currentQuestionIndex]) {
            System.out.println(option);
        }

        System.out.print("Your answer: ");
        int userAnswer = scanner.nextInt();
        answerSubmitted = true;
        timer.cancel();

        if (userAnswer == correctAnswers[currentQuestionIndex]) {
            System.out.println("Correct!");
            score++;
            correctAnswersFlag[currentQuestionIndex] = true;
        } else {
            System.out.println("Incorrect!");
            correctAnswersFlag[currentQuestionIndex] = false;
        }
    }

    private static void displayResult() {
        System.out.println("\nQuiz Over!");
        System.out.println("Your score: " + score + "/" + questions.length);
        for (int i = 0; i < questions.length; i++) {
            System.out.println((i + 1) + ". " + questions[i]);
            System.out.println("Your answer was " + (correctAnswersFlag[i] ? "correct" : "incorrect"));
        }
    }
}

