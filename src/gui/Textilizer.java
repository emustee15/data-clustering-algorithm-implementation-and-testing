package gui;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

public class Textilizer
{

	private int numberOfRanges = 0;
	private int[] numericRanges;
	private StyleRange[] styleRanges;
	private String newText;

	public void processText(String text, FontData data)
	{
		String[] texts = text.split("\\\\");
		numberOfRanges = texts.length;
		numericRanges = new int[numberOfRanges * 2];
		styleRanges = new StyleRange[numberOfRanges];
		newText = "";

		int characterCount = 0;
		int index = 0;

		boolean needsFormatting = false;
		for (String t : texts)
		{

			char escapeChar = ' ';

			if (t.length() > 1)
			{
				char needsFormattingChar = t.charAt(0);
				if (needsFormattingChar == '~')
				{
					escapeChar = t.charAt(1);
					t = t.substring(2);
					needsFormatting = (needsFormatting) ? false : true;
				}

			}

			numericRanges[index * 2] = characterCount;
			numericRanges[index * 2 + 1] = t.length();

			characterCount += t.length();

			if (needsFormatting)
			{
				switch (escapeChar)
				{
				case 'S': // Superscript
					styleRanges[index] = new StyleRange();
					styleRanges[index].rise = data.getHeight() / 3;
					styleRanges[index].font = new Font(Display.getCurrent(), data.getName(), (int)(data.getHeight()*(.83333333)), data.getStyle());
					break;
				case 's': // Subscript
					styleRanges[index] = new StyleRange();
					styleRanges[index].rise = -data.getHeight() / 3;
					styleRanges[index].font = new Font(Display.getCurrent(), data.getName(), (int)(data.getHeight()*(.83333333)), data.getStyle());
					break;
				}
			}
			else
			{
				styleRanges[index] = new StyleRange();
			}

			newText += t;

			index++;

		}

	}

	public int[] getRanges()
	{
		return numericRanges;
	}

	public StyleRange[] getStyleRanges()
	{
		return styleRanges;
	}

	public String getNewText()
	{
		return newText;
	}

}
