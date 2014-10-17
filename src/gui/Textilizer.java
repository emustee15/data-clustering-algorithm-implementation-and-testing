package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

public class Textilizer
{

	/*
	 * This class returns formatting for a given string. Strings that can be
	 * formatted use an escape sequence to indicate they need to be formatted.
	 * The escape sequence to format text is \\~C, where c designates the
	 * formatting to be done. A given substring of text can only be formatted
	 * once. Ranges of the text to be formatted and the actual formatting they
	 * use can be accessed once the text has been formatted.
	 */
	private int numberOfRanges = 0;
	private int[] numericRanges;
	private StyleRange[] styleRanges;
	private String newText;
	private static FontData[] data;

	private static StyleRange subscriptStyleRange, superscriptStyleRange, defaultStyleRange;

	public Textilizer(FontData[] data)
	{
		recomputeFonts(data);
		Textilizer.data = data;
	}

	// This method processes the text
	public void processText(String text, FontData data, boolean textIsNew)
	{
		String[] texts = text.split("\\\\");
		if (textIsNew)
		{
			numberOfRanges = texts.length;
			numericRanges = new int[numberOfRanges * 2];
			styleRanges = new StyleRange[numberOfRanges];
			newText = "";
		}

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

			// Additional formatting can be added by simply adding more cases.
			// Currently, subscript and
			// superscript have been implemented.
			if (needsFormatting)
			{
				switch (escapeChar)
				{
				case 'S': // Superscript
					styleRanges[index] = superscriptStyleRange;
					break;
				case 's': // Subscript
					styleRanges[index] = subscriptStyleRange;
					break;
				}
			}
			else
			{
				styleRanges[index] = defaultStyleRange;
			}

			if (textIsNew)
			{
				newText += t;
			}

			index++;

		}

	}

	// This method returns the numeric ranges of the text.
	// For instance, the word cat in the phrase, "the cat"
	// has the range 5,3, where 5 is the starting position
	// and 3 is the length.
	public int[] getRanges()
	{
		return numericRanges;
	}

	// This method returns the actual formatting.
	public StyleRange[] getStyleRanges()
	{
		return styleRanges;
	}

	// This method returns the processed text without all the escape sequences.
	public String getNewText()
	{
		return newText;
	}

	public static void recomputeFonts(FontData[] data)
	{
		if (superscriptStyleRange == null)
		{
			superscriptStyleRange = new StyleRange();
			superscriptStyleRange.font = new Font(MainGUI.getInstance().getDisplay(), data[0].getName(), (int) (data[0].getHeight() * (.83333333)), data[0].getStyle());
			superscriptStyleRange.rise = data[0].getHeight() / 3;
		}
		else
		{
			superscriptStyleRange.rise = data[0].getHeight() / 3;
			Font oldFont = superscriptStyleRange.font;
			superscriptStyleRange.font = new Font(MainGUI.getInstance().getDisplay(), data[0].getName(), (int) (data[0].getHeight() * (.83333333)), data[0].getStyle());
			oldFont.dispose();
		}
		if (subscriptStyleRange == null)
		{
			subscriptStyleRange = new StyleRange();
			subscriptStyleRange.font = new Font(MainGUI.getInstance().getDisplay(), data[0].getName(), (int) (data[0].getHeight() * (.83333333)), data[0].getStyle());
			subscriptStyleRange.rise = -data[0].getHeight() / 3;
		}
		else
		{
			subscriptStyleRange.rise = -data[0].getHeight() / 3;
			for (FontData d : subscriptStyleRange.font.getFontData())
			{
				d.setHeight((int) (d.getHeight() * (.83333333)));
			}

			Font oldFont = subscriptStyleRange.font;
			subscriptStyleRange.font = new Font(MainGUI.getInstance().getDisplay(), data[0].getName(), (int) (data[0].getHeight() * (.83333333)), data[0].getStyle());;
			oldFont.dispose();
		}

		if (defaultStyleRange == null)
		{
			defaultStyleRange = new StyleRange();
		}
	}

	public static void recomputeFonts()
	{
		recomputeFonts(data);
	}

}
