package gui;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

 
/*
 * This class is a wrapping class for styled text. It allows us to use only the 
 * methods we need to use in a way that is easier to use. This does not use the 
 * decorator pattern because SWT widgets have not been designed with inheritance
 * in mind. When text is set, it uses a textilizer to automatically format text.
 */

public class SuperStyledText
{

	private StyledText styledText;
	private Textilizer textilizer;
	private String text;
	private Font oldFont;
	
	public SuperStyledText(StyledText styledText)
	{
		
		this.styledText = styledText;
		textilizer = new Textilizer(styledText.getFont().getFontData());
		textilizer.processText("", styledText.getFont().getFontData()[0], true);
		text = "";
		makeBigger();
		makeBigger();

		// TODO Auto-generated constructor stub
	}
	
	public void setText(String text)
	{
		this.text = text;
		textilizer.processText(text, styledText.getFont().getFontData()[0], true);
		styledText.setText(textilizer.getNewText());
		styledText.setStyleRanges(textilizer.getRanges(), textilizer.getStyleRanges());
	}
	
	public void makeBigger()
	{
		adjustTextSize(2);

	}
	
	public void makeSmaller()
	{
		adjustTextSize(-2);
	}
	
	private void adjustTextSize(int delta)
	{

		
		Font font = styledText.getFont();
		FontData[] data = font.getFontData();
		for (int i = 0; i < data.length; i++)
		{

			data[i].setHeight(data[i].getHeight() + delta);
		}

		Textilizer.recomputeFonts(data);
		Font biggerFont = new Font(Display.getCurrent(), data);
		styledText.setFont(biggerFont);
		
		if (oldFont !=null)
		{
			oldFont.dispose();
		}
		
		oldFont = biggerFont;
		
		
		
		styledText.setStyleRanges(textilizer.getRanges(), textilizer.getStyleRanges());
	}
	
	public StyledText getStylizedText()
	{
		return styledText;
	}
	
	public String getText()
	{
		return text;
	}

}
