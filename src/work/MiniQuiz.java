package work;

import java.util.Scanner;

public class MiniQuiz
{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);

		Question q1 = new Question("What is the capital of Jamaica?",
				"Kingston");

		q1.setComplexity(4);

		Question q2 = new Question("Which is worse, ignorance or apathy?",
				"I don't know and I don't care");

		q2.setComplexity(10);

		System.out.print(q1.getQuestion());
		System.out.println(" (Level: " + q1.getComplexity() + ")");
		String possible = scan.nextLine();
		if (q1.answerCorrect(possible))
		{
			System.out.println("Correct");
		}
		else
		{
			System.out.println("No, the answer is " + q1.getAnswer());
		}
		System.out.println();
		System.out.print(q2.getQuestion());
		System.out.println(" (Level: " + q2.getComplexity() + ")");
		possible = scan.nextLine();
		if (q2.answerCorrect(possible))
		{
			System.out.println("Correct");
		}
		else
		{
			System.out.println("No, the answer is " + q2.getAnswer());
		}
	}
}
