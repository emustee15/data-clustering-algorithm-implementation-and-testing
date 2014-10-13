package gui;

import java.io.File;
import java.util.ArrayList;

import main.FileLoader;
import main.RankedData;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Point;

import selectionListeners.AnalyzeBehavior;
import selectionListeners.ExitBehavior;
import selectionListeners.HelpMenuBehavior;
import selectionListeners.OpenFileBehavior;

public class MainGUI extends Shell
{
	
	private ArrayList<RankedData> piVector;
	private Text clusterText, qVectorText, cVectorText, lVectorText, sigmaTimeLineText;
	private Spinner completeRankings, numClusters;
	private Button btnRandomizeSigmaVector;
	private static MainGUI instance;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[])
	{
		try
		{
			Display display = Display.getDefault();
			MainGUI shell = new MainGUI(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed())
			{
				if (!display.readAndDispatch())
				{
					display.sleep();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	private MainGUI(Display display)
	{
		super(display, SWT.SHELL_TRIM);
		instance = this;
		setMinimumSize(new Point(640, 480));
		setLayout(new GridLayout(2, false));
		
		Menu menu = new Menu(this, SWT.BAR);
		setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");
		
		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);
		
		MenuItem mntmOpen = new MenuItem(menu_1, SWT.NONE);
		mntmOpen.setText("Open");
		mntmOpen.addSelectionListener(new OpenFileBehavior(this));
		
		MenuItem mntmSave = new MenuItem(menu_1, SWT.NONE);
		mntmSave.setText("Save");
		
		new MenuItem(menu_1, SWT.SEPARATOR);
		
		MenuItem mntmExit = new MenuItem(menu_1, SWT.NONE);
		mntmExit.setText("Exit");
		mntmExit.addSelectionListener(new ExitBehavior());
		
		MenuItem mntmEdit = new MenuItem(menu, SWT.CASCADE);
		mntmEdit.setText("Edit");
		
		Menu menu_2 = new Menu(mntmEdit);
		mntmEdit.setMenu(menu_2);
		
		MenuItem mntmCut = new MenuItem(menu_2, SWT.NONE);
		mntmCut.setText("Cut");
		
		MenuItem mntmCopy = new MenuItem(menu_2, SWT.NONE);
		mntmCopy.setText("Copy");
		
		MenuItem mntmPaste = new MenuItem(menu_2, SWT.NONE);
		mntmPaste.setText("Paste");
		
		MenuItem menuItem = new MenuItem(menu_2, SWT.SEPARATOR);
		menuItem.setText("Select All");
		
		MenuItem mntmSelectAll = new MenuItem(menu_2, SWT.NONE);
		mntmSelectAll.setText("Select All");
		
		MenuItem mntmView = new MenuItem(menu, SWT.CASCADE);
		mntmView.setText("View");
		
		Menu menu_3 = new Menu(mntmView);
		mntmView.setMenu(menu_3);
		
		MenuItem mntmZoomIn = new MenuItem(menu_3, SWT.NONE);
		mntmZoomIn.setText("Zoom in");
		
		MenuItem mntmZoomOut = new MenuItem(menu_3, SWT.NONE);
		mntmZoomOut.setText("Zoom out");
		
		new MenuItem(menu_3, SWT.SEPARATOR);
		
		MenuItem mntmToggleSidebar = new MenuItem(menu_3, SWT.NONE);
		mntmToggleSidebar.setText("Toggle Sidebar");
		
		MenuItem mntmHelp = new MenuItem(menu, SWT.CASCADE);
		mntmHelp.setText("Help");
		
		
		Menu menu_4 = new Menu(mntmHelp);
		mntmHelp.setMenu(menu_4);
		
		MenuItem mntmViewHelp = new MenuItem(menu_4, SWT.NONE);
		mntmViewHelp.setText("View Help");
		mntmViewHelp.addSelectionListener(new HelpMenuBehavior());
		
		MenuItem mntmAbout = new MenuItem(menu_4, SWT.NONE);
		mntmAbout.setText("About");
		
		ExpandBar expandBar = new ExpandBar(this, SWT.NONE);
		expandBar.setBackground(new Color(Display.getCurrent(),245,246,247));
		GridData gd_expandBar = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 2);
		gd_expandBar.widthHint = 195;
		expandBar.setLayoutData(gd_expandBar);
		
		ExpandItem xpndtmClusterAnalyzer = new ExpandItem(expandBar, SWT.NONE);
		xpndtmClusterAnalyzer.setExpanded(true);
		xpndtmClusterAnalyzer.setText("Cluster Analyzer");
		
		Composite composite = new Composite(expandBar, SWT.NONE);
		composite.setBackground(new Color(Display.getCurrent(), 240,240,240));
		xpndtmClusterAnalyzer.setControl(composite);
		xpndtmClusterAnalyzer.setHeight(96);
		GridLayout gl_composite = new GridLayout(2, false);
		gl_composite.marginLeft = 5;
		composite.setLayout(gl_composite);
		
		Label lblNumberClusters = new Label(composite, SWT.NONE);
		lblNumberClusters.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNumberClusters.setText("Number Clusters");
		
		numClusters = new Spinner(composite, SWT.BORDER);
		numClusters.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		numClusters.setMaximum(9);
		numClusters.setMinimum(1);
		
		Label lblCompleteRankings = new Label(composite, SWT.NONE);
		lblCompleteRankings.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCompleteRankings.setText("Complete Rankings");
		
		completeRankings = new Spinner(composite, SWT.BORDER);
		completeRankings.setMinimum(2);
		completeRankings.setMaximum(100);
		new Label(composite, SWT.NONE);
		
		Button startAnalysis = new Button(composite, SWT.NONE);
		startAnalysis.setText("Start");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		ExpandItem analyzeSettings = new ExpandItem(expandBar, 0);
		analyzeSettings.setText("Settings");
		analyzeSettings.setExpanded(true);
		
		Composite composite_1 = new Composite(expandBar, SWT.NONE);
		analyzeSettings.setControl(composite_1);
		composite_1.setLayout(new GridLayout(1, false));
		
		btnRandomizeSigmaVector = new Button(composite_1, SWT.CHECK);
		btnRandomizeSigmaVector.setSelection(true);
		btnRandomizeSigmaVector.setText("Randomize Sigma Vector");
		analyzeSettings.setHeight(40);
		startAnalysis.addSelectionListener(new AnalyzeBehavior(this));
		
		
		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		
		TabItem tbtmClusterCenters = new TabItem(tabFolder, SWT.NONE);
		tbtmClusterCenters.setText("Cluster Centers");
		clusterText = new Text(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.READ_ONLY);
		tbtmClusterCenters.setControl(clusterText);
		
		TabItem tbtmQvector = new TabItem(tabFolder, SWT.NONE);
		tbtmQvector.setText("Q Vector");
		qVectorText = new Text(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.READ_ONLY);
		tbtmQvector.setControl(qVectorText);
		
		TabItem tbtmCvector = new TabItem(tabFolder, SWT.NONE);
		tbtmCvector.setText("C Vector");
		cVectorText = new Text(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.READ_ONLY);
		tbtmCvector.setControl(cVectorText);
		
		TabItem tbtmLvector = new TabItem(tabFolder, SWT.NONE);
		tbtmLvector.setText("λ Vector");
		lVectorText = new Text(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.READ_ONLY);
		tbtmLvector.setControl(lVectorText);
		
		TabItem tbtmLSigmaOverTime = new TabItem(tabFolder, SWT.NONE);
		tbtmLSigmaOverTime.setText("σ Timeline");
		sigmaTimeLineText = new Text(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.READ_ONLY);
		tbtmLSigmaOverTime.setControl(sigmaTimeLineText);
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents()
	{
		setText("Ranked Data Clustering");
		setSize(710, 528);

	}
	
	public void openPiVector(String filename)
	{
		File file = new File(filename);
		
		if (!file.exists())
		{
			return;
		}
		
		FileLoader fLoader = new FileLoader();
		
		fLoader.loadFile(filename);
		
		
		
		if (fLoader.getPartialRankings() != null)
		{
			piVector = new ArrayList<>();
			int maxLength = 0;
			for (RankedData pr : fLoader.getPartialRankings())
			{
				piVector.add(pr.clone());
				if (pr.getSize() > maxLength)
				{
					maxLength = pr.getSize();
				}
				
				completeRankings.setSelection(maxLength);
				completeRankings.setMinimum(maxLength);
			}
			
			
		}
	}
	
	public void setQVectorText(String text)
	{
		qVectorText.setText(text);
	}
	
	public void setLVectorText(String text)
	{
		lVectorText.setText(text);
	}
	
	public void setClusterText(String text)
	{
		clusterText.setText(text);
	}
	
	public void setCVectorText(String text)
	{
		cVectorText.setText(text);
	}
	
	public void setSigmaTimelineText(String text)
	{
		sigmaTimeLineText.setText(text);
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
	
	public ArrayList<RankedData> getPiVector()
	{
		return piVector;
	}
	
	public int getNumberClusters()
	{
		return numClusters.getSelection();
	}
	
	public int getCompleteRankings()
	{
		return completeRankings.getSelection();
	}
	
	public boolean getRandomizeSigma()
	{
		return btnRandomizeSigmaVector.getSelection();
	}
	
	public static MainGUI getInstance()
	{
		return instance;
	}
}
