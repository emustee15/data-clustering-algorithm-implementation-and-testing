package gui;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

 
public class SuperStyledText
{

	private StyledText styledText;
	private Textilizer textilizer;
	private String text;
	
	public SuperStyledText(StyledText styledText)
	{
		textilizer = new Textilizer();
		this.styledText = styledText;
		text = "";
		makeBigger();
		makeBigger();

		// TODO Auto-generated constructor stub
	}
	
	public void setText(String text)
	{
		this.text = text;
		textilizer.processText(text, styledText.getFont().getFontData()[0]);
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

		Font biggerFont = new Font(Display.getCurrent(), data);
		styledText.setFont(biggerFont);
		
		textilizer.processText(text, styledText.getFont().getFontData()[0]);
		styledText.setText(textilizer.getNewText());
		styledText.setStyleRanges(textilizer.getRanges(), textilizer.getStyleRanges());
	}
	
	public StyledText getStylizedText()
	{
		return styledText;
	}

}
