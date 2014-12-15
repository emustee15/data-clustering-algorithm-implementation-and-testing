package exception;

public class ZeroInFileException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// This exception gets thrown if any ranked data element contains a 0.
	private int line;
	
	public ZeroInFileException(int line)
	{
		this.line = line;
	}
	
	public int getLine()
	{
		return line;
	}
}
