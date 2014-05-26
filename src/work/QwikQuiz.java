package work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//Create a class called QwikQuiz.java
public class QwikQuiz {
    // question map, Map<complexityLevel,List<Question>>
    private List<Question> questionList;
    // maximum of question number
    private int maxSize;

    // Write a constructor that sets up a maximum of 20 questions,
    // and also keeps track of the current question number and the number of
    // correct and incorrect answers
    public QwikQuiz() {
        maxSize = 20;
        questionList = new ArrayList<Question>(maxSize);
    }

    // Write an addQuestion method to add a new question to the Quiz
    public void addQuestion(Question question) {
        if (questionList.size() <= maxSize) {
            questionList.add(question);
        } else {
            // error message
            System.out.println("current question number overstep " + maxSize);
        }
    }

    // Write an askQuestion method to present each question in turn to the user,
    // accept an answer for each one, and keep track of the results
    public String askQuestion() {
        int correctCount = 0, wrongCount = 0;
        Scanner scan = new Scanner(System.in);
        for (Question tq : questionList) {
            System.out.print(tq.getQuestion());
            System.out.println(" (Level: " + tq.getComplexity() + ")");
            String possible = scan.nextLine();
            if (tq.answerCorrect(possible)) {
                System.out.println("Correct");
                correctCount++;
            } else {
                System.out.println("No, the answer is " + tq.getAnswer());
                wrongCount++;
            }
            System.out.println();
        }
        String msg = "correct:" + correctCount + "  wrong:" + wrongCount;
        return msg;
    }

    // Write an overloaded askQuestion method so that it accepts two integer
    // parameters that specify the minimum and maximum complexity levels for the
    // quiz questions and only presents questions in that complexity range
    public String askQuestion(int a, int b, int c) {
        String msg = null;
        if (a > b) {
            // error message
            System.out
                    .println("Complexity level parameter format：first min number, second max number");
        } else {
            // question number
            int count = 0, correctCount = 0, wrongCount = 0;
            Map<Integer, Object> cMap = new HashMap<Integer, Object>();
            Scanner scan = new Scanner(System.in);
            while (a <= b) {
                cMap.put(a, null);
                a++;
            }
            for (Question tq : questionList) {
                // check current question compliance with the complexity level
                if (cMap.containsKey(tq.getComplexity())) {
                    System.out.print(tq.getQuestion());
                    System.out.println(" (Level: " + tq.getComplexity() + ")");
                    String possible = scan.nextLine();
                    if (tq.answerCorrect(possible)) {
                        System.out.println("Correct");
                        correctCount++;
                    } else {
                        System.out.println("No, the answer is "
                                + tq.getAnswer());
                        wrongCount++;
                    }
                    System.out.println();
                    ++count;
                }
                if (count >= c)
                    break;
            }
            if (count == 0) {
                // error message
                System.out
                        .println("Does not containe the question of compliance with the complexity level");
            } else {
                msg = "[correct]:" + correctCount + "  [wrong]:" + wrongCount;
            }
        }
        return msg;
    }

    public void printAllQuestion() {
        for (Question tq : questionList) {
            System.out.println("Complexity level:" + tq.getComplexity());
            System.out.println("Q:" + tq.getQuestion());
            System.out.println("A:" + tq.getAnswer());
        }
    }
}
