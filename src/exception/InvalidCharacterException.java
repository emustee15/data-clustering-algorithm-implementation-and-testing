package exception;

public class InvalidCharacterException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private char invalidChar = ' ';
	
	public InvalidCharacterException(char c)
	{
		this.invalidChar = c;
	}
	
	public char getInvalidChar()
	{
		return invalidChar;
	}

}
