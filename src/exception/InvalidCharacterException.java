package exception;

public class InvalidCharacterException extends Exception
{

	/**
	 * This exception gets thrown whenever an invalid character appears when adding a cluster center
	 * to the random data generator. 
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
