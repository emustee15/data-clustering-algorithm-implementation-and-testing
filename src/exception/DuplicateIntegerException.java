package exception;

public class DuplicateIntegerException extends Exception 
{

	/**
	 * 
	 */
	
	private int duplicatedInteger = 0;
	private int line = 0;
	private static final long serialVersionUID = 1L;
	// This exception gets thrown if a ranked data set contains duplicate
	// numbers, regardless of sign.
	
	public DuplicateIntegerException(int integer, int line)
	{
		this.duplicatedInteger = integer;
		this.line = line;
	}
	
	public int getInteger()
	{
		return duplicatedInteger;
	}
	
	public int getLine()
	{
		return line;
	}
}
