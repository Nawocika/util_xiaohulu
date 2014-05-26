package work;

public class Question implements Complexity
{
	private String question;
	private String answer;
	private int complexityLevel;

	public Question(String query, String result)
	{
		this.question = query;
		this.answer = result;
		this.complexityLevel = 1;
	}

	public void setComplexity(int level)
	{
		this.complexityLevel = level;
	}

	public int getComplexity()
	{
		return this.complexityLevel;
	}

	public String getQuestion()
	{
		return this.question;
	}

	public String getAnswer()
	{
		return this.answer;
	}

	public boolean answerCorrect(String candidateAnswer)
	{
		return this.answer.equals(candidateAnswer);
	}

	public String toString()
	{
		return this.question + "\n" + this.answer;
	}
}
