package selectionListeners;

import main.ClusterAnalyzer;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import gui.MainGUI;

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
			ClusterAnalyzer ca = new ClusterAnalyzer(shell.getPiVector(),
					shell.getNumberClusters(), shell.getCompleteRankings());
			ca.doAnalyisis();
			shell.setClusterText(ca.getClusterCenterInformation());
			shell.setQVectorText(ca.getQVectorInfo());
			shell.setCVectorText(ca.getCVectorInfo());
			shell.setLVectorText(ca.getLVectorInfo());
			shell.setSigmaTimelineText(ca.getSigmaOverTime());
		}

	}

}
