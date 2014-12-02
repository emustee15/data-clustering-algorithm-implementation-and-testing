package selectionListeners;

import java.util.ArrayList;
import main.ClusterAnalyzer;
import main.DistanceRanker;
import main.RankedData;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import gui.MainGUI;
import gui.RandomDataGenerator;

/*
 * This class is used when the analyze button is pressed. It will cause
 * the currently loaded piVector to be analyzed by a ClusterAnalyzer.
 */
public class AnalyzeBehavior implements SelectionListener
{
	private MainGUI shell;

	public AnalyzeBehavior(MainGUI shell)
	{
		this.shell = shell;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void widgetSelected(SelectionEvent arg0)
	{
		if (shell.getPiVector() != null)
		{

			String cumulitiveText = "";
			ClusterAnalyzer ca = null;
			for (int index = 0; index < MainGUI.getInstance().getNumberOfRuns();index++)
			{
				ca = new ClusterAnalyzer(shell.getPiVector(),
						shell.getNumberClusters(), shell.getCompleteRankings());
				ca.doAnalyisis();
				cumulitiveText = GenerateCumulativeText(cumulitiveText, ca, index);
				
			}
			shell.setClusterText(ca.getClusterCenterInformation());
			shell.setQVectorText(ca.getQVectorInfo());
			shell.setCVectorText(ca.getCVectorInfo());
			shell.setLVectorText(ca.getLVectorInfo());
			shell.setSigmaTimelineText(ca.getSigmaOverTime());
			shell.setCumulitiveRunsText(cumulitiveText);
			

		}

	}

	private String GenerateCumulativeText(String text, ClusterAnalyzer ca, int index)
	{
		text+="Run " + index + "\n";
		text = AddSigmaDistanceInfo(text, ca,ca.getSigmaInitial(),"Initial");
		text+=ca.getSigmaOverTime() + "\n";
		text = AddSigmaDistanceInfo(text, ca,ca.getSigmaFinal(),"Final");
		text+="\n------------------------------------------------------------\n";
		return text;
	}

	private String AddSigmaDistanceInfo(String text, ClusterAnalyzer ca, ArrayList<RankedData> sigmas, String initialFinalText)
	{
		if (RandomDataGenerator.getRandomizeableRankedData() != null)
		{
			DistanceRanker dr = new DistanceRanker();
			for (RankedData rd : RandomDataGenerator.getSupposedClusters())
			{
				for (RankedData sigs: sigmas)
				{
					text+= initialFinalText + " = " + sigs.toString() + "\n";
					text+="ActualSigma = " + rd.toString() + "\n";
					text+="Distance = " + dr.getDistance(sigs, rd) +"\n\n";
					
				}
			}
		}
		return text;
	}

}
