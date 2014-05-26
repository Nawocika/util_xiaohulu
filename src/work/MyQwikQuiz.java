package work;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//2、Create a driver class called MyQwikQuiz (to replace the MiniQuiz class) that provides 
// a basic menu ‘interface’ (text based, not graphical) that can be used by the ‘contestant’ (user). 
//The MyQwikQuiz class should also ‘automatically load’ the questions into the quiz when run.
public class MyQwikQuiz {
    final static String FILENAME = "group74.txt";
    static String FILEPATH;
    private static QwikQuiz quiz = new QwikQuiz();
    private static String stuName = null, maxLevel = null, minLevel = null,
            questionNum = null, tQuestion = null, tAnswer = null, clevel = null;

    static {
        FILEPATH = MyQwikQuiz.class.getClassLoader().getResource("").getPath();
        if (!FILEPATH.endsWith("/"))
            FILEPATH += "/";
    }

    // clear console
    public static void clearTheConsoleStr() {
        try {
            Runtime.getRuntime().exec("cls");
        } catch (IOException e) {

        }
    }

    /**
     * @param num
     * @param params
     */
    public static void defaultShowMenu(int num, String... params) {
        if (0 == num) {
            System.out.println("---------------------------------------------");
            System.out.println("           The Menu  [input 1-5 option]           ");
            System.out.println(" 1.Start the quiz                          ");
            System.out.println(" 2.Show the score of the quiz              ");
            System.out.println(" 3.Add The new question                        ");
            System.out.println(" 4.Print all the questions and answers     ");
            System.out.println(" 5.The Exit                                    ");
            System.out.println("---------------------------------------------");
        } else if (1 == num) {
            System.out.println("---------------------------------------------");
            System.out.println("             1.Start the quiz               ");
            if (params != null && params.length > 0) {
                String msg = null;
                if (params[0] != null)
                    msg = " name:" + params[0] + " ";
                if (params.length > 1 && params[1] != null)
                    msg += "\n minimum complexity:" + params[1] + " ";
                if (params.length > 2 && params[2] != null)
                    msg += "\n maximum complexity:" + params[2] + " ";
                if (params.length > 3 && params[3] != null)
                    msg += "\n question number:" + params[3] + " ";
                System.out.println(msg);
            }
            System.out.println("---------------------------------------------");
        } else if (2 == num) {
            System.out.println("---------------------------------------------");
            System.out.println("        2.Show the score of the quiz        ");
            System.out.println("---------------------------------------------");
        } else if (3 == num) {
            System.out.println("---------------------------------------------");
            System.out.println("            3.Add The new question              ");
            if (params != null && params.length > 0) {
                String msg = null;
                if (params[0] != null)
                    msg = " Complexity level:" + params[0] + " ";
                if (params.length > 1 && params[1] != null)
                    msg += "\n Question:" + params[1] + " ";
                if (params.length > 2 && params[2] != null)
                    msg += "\n Anwser:" + params[2] + " ";
                System.out.println(msg);
            }
            System.out.println("---------------------------------------------");
        } else if (4 == num) {
            System.out.println("---------------------------------------------");
            System.out.println("    4.Print all othe questions and answers   ");
            System.out.println("---------------------------------------------");
        }
    }

    /**
     * @param qwikQuiz
     */
    public static void addTheDefaultQuestions(QwikQuiz qwikQuiz) {
        Question question;
        question = new Question("Who is the creator of the JAVA?",
                "James Gosling");
        question.setComplexity(1);
        qwikQuiz.addQuestion(question);

        question = new Question(
                "JAVA by which company?", "Sun Microsystems");
        question.setComplexity(1);
        qwikQuiz.addQuestion(question);

        question = new Question(
                "How much is the first JAVA version was born time?", "In January 1996");
        question.setComplexity(1);
        qwikQuiz.addQuestion(question);

        question = new Question("If there is a \"JAVA goto\" grammar?", "No");
        question.setComplexity(1);
        qwikQuiz.addQuestion(question);

        question = new Question(
                "How much is the latest JAVA version at present?", "1.8");
        question.setComplexity(2);

        question = new Question("\"A\" in the alphabet is which one?",
                "1");
        question.setComplexity(1);
        qwikQuiz.addQuestion(question);

        question = new Question(
                "\"B\" in the alphabet is which one?", "2");
        question.setComplexity(1);
        qwikQuiz.addQuestion(question);

        question = new Question(
                "\"C\" in the alphabet is which one?", "3");
        question.setComplexity(1);
        qwikQuiz.addQuestion(question);

        question = new Question("\"D\" in the alphabet is which one?",
                "4");
        question.setComplexity(1);
        qwikQuiz.addQuestion(question);

        question = new Question(
                "\"E\" in the alphabet is which one?", "5");
        question.setComplexity(1);
        qwikQuiz.addQuestion(question);

        question = new Question(
                "\"F\" in the alphabet is which one?", "6");
        question.setComplexity(1);
        qwikQuiz.addQuestion(question);

        question = new Question("\"G\" in the alphabet is which one?",
                "7");
        question.setComplexity(1);
        qwikQuiz.addQuestion(question);

        question = new Question(
                "\"H\" in the alphabet is which one?", "8");
        question.setComplexity(1);
        qwikQuiz.addQuestion(question);

        question = new Question(
                "\"I\" in the alphabet is which one?", "9");
        question.setComplexity(1);
        qwikQuiz.addQuestion(question);

        question = new Question("\"J\" in the alphabet is which one?",
                "10");
        question.setComplexity(1);
        qwikQuiz.addQuestion(question);

        question = new Question(
                "\"K\" in the alphabet is which one?", "11");
        question.setComplexity(1);
        qwikQuiz.addQuestion(question);

        question = new Question(
                "\"L\" in the alphabet is which one?", "12");
        question.setComplexity(1);
        qwikQuiz.addQuestion(question);

        question = new Question("\"M\" in the alphabet is which one?",
                "13");
        question.setComplexity(1);
        qwikQuiz.addQuestion(question);

        question = new Question(
                "\"N\" in the alphabet is which one?", "14");
        question.setComplexity(1);
        qwikQuiz.addQuestion(question);

        question = new Question(
                "\"O\" in the alphabet is which one?", "15");
        question.setComplexity(1);
        qwikQuiz.addQuestion(question);

    }

    private static void writeResult(String inputValue, Scanner scanner) {


        int temp = 0;
        try {
            temp = Integer.parseInt(inputValue);
        } catch (NumberFormatException e) {
            System.out.println("input 1-5 option ,3 seconds after the jump to the main menu !");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        clearTheConsoleStr();
        switch (temp) {
            case 1:

                int a = -1, b = -1, c = -1;
                while (stuName == null || "".equals(stuName)) {
                    defaultShowMenu(1);
                    System.out
                            .println("Enter your name(name cannot be empty):");
                    stuName = scanner.nextLine();
                }
                while (a == -1) {
                    clearTheConsoleStr();
                    defaultShowMenu(1, stuName);
                    System.out
                            .println("Enter the minmum complexity levels of the quiz(complexity levels must be a integer):");
                    minLevel = scanner.nextLine();
                    try {
                        a = Integer.parseInt(minLevel);
                    } catch (Exception e) {
                        a = -1;
                    }
                }
                while (b == -1) {
                    clearTheConsoleStr();
                    defaultShowMenu(1, stuName, minLevel);
                    System.out
                            .println("Enter the maxmum complexity levels of the quiz(complexity levels must be a integer):");
                    maxLevel = scanner.nextLine();
                    try {
                        b = Integer.parseInt(maxLevel);
                    } catch (Exception e) {
                        b = -1;
                    }
                }
                while (c == -1) {
                    clearTheConsoleStr();
                    defaultShowMenu(1, stuName, minLevel, maxLevel);
                    System.out
                            .println("Enter the question number(question number must be 5,10,15 or 20):");
                    questionNum = scanner.nextLine();
                    try {
                        if (questionNum.equals("5") || questionNum.equals("10")
                                || questionNum.equals("15")
                                || questionNum.equals("20"))
                            c = Integer.parseInt(questionNum);
                    } catch (Exception e) {
                        c = -1;
                    }
                }
                clearTheConsoleStr();
                defaultShowMenu(1, stuName, minLevel, maxLevel, questionNum);
                String resultMsg = quiz.askQuestion(a, b, c);
                if (resultMsg != null && !"".equals(resultMsg)) {
                    System.out.println(resultMsg);
                    BufferedWriter br = null;
                    FileWriter out = null;
                    try {
                        String conent = "[youName]:" + stuName + " "
                                + "[minmum complexity levels]:" + minLevel + " "
                                + "[maxmum complexity levels]:" + maxLevel + " "
                                + "[question quantity]:" + questionNum + " "
                                + resultMsg
                                + "\r\n";
                        out = new FileWriter(FILEPATH + FILENAME, true);
                        br = new BufferedWriter(out);
                        br.append(conent);
                        br.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (br != null) {
                                br.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (out != null) {
                                    out.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            case 2:
                defaultShowMenu(2);
                FileReader reader = null;
                try {
                    File tfile = new File(FILEPATH + FILENAME);
                    if (!tfile.exists())
                        tfile.createNewFile();
                    reader = new FileReader(tfile);
                    int tempchar;
                    StringBuffer sb = new StringBuffer();
                    while ((tempchar = reader.read()) != -1) {
                        char tchar = (char) tempchar;
                        sb.append(tchar);
                    }
                    System.out.println(sb.toString());
                } catch (FileNotFoundException e) {
                    System.out.println("score file does not exists!");
                } catch (IOException e) {
                    System.out.println("score file does not exists!");
                } finally {
                    if (reader != null)
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }

            case 3:
                int cl = -1;
                while (cl == -1) {

                    defaultShowMenu(3);
                    System.out.println("Enter complexity level:");
                    clevel = scanner.nextLine();
                    try {
                        cl = Integer.parseInt(clevel);
                    } catch (Exception e) {
                        cl = -1;
                    }
                    if (cl < 0)
                        cl = -1;
                }
                while (tQuestion == null) {
                    clearTheConsoleStr();
                    defaultShowMenu(3, clevel);
                    System.out.println("Enter question:");
                    tQuestion = scanner.nextLine();
                    if (tQuestion == null || "".equals(tQuestion))
                        tQuestion = null;
                }
                while (tAnswer == null) {
                    clearTheConsoleStr();
                    defaultShowMenu(3, clevel, tQuestion);
                    System.out.println("Enter answer:");
                    tAnswer = scanner.nextLine();
                    if (tAnswer == null || "".equals(tAnswer))
                        tAnswer = null;
                }
                Question tquestion = new Question(tQuestion, tAnswer);
                tquestion.setComplexity(cl);
                quiz.addQuestion(tquestion);
                clearTheConsoleStr();
                defaultShowMenu(3, clevel, tQuestion, tAnswer);

            case 4:
                defaultShowMenu(4);
                quiz.printAllQuestion();

            default:
                System.out.println();
        }
    }

    public static void main(String[] args) {
        String inputValue = null;
        addTheDefaultQuestions(quiz);
        Scanner scanner = new Scanner(System.in);
        while (!"5".equals(inputValue)) {
            clearTheConsoleStr();
            defaultShowMenu(0);
            inputValue = scanner.nextLine();
            writeResult(inputValue, scanner);
            System.out.println("press any key to return the menu");
        }
        System.out.println("Exit...ing...");
    }

}
