package gui;

import java.io.File;
import java.util.ArrayList;

import main.FileLoader;
import main.FileType;
import main.RankedData;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
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

import selectionListeners.AddClusterCenter;
import selectionListeners.AddDescriptions;
import selectionListeners.AnalyzeBehavior;
import selectionListeners.ExitBehavior;
import selectionListeners.HelpMenuBehavior;
import selectionListeners.OpenFileBehavior;
import selectionListeners.RandomDataGeneratorStartBehavior;
import selectionListeners.RemoveClusterCenter;
import selectionListeners.RemoveDescription;
import selectionListeners.ZoomInBehavior;
import selectionListeners.ZoomOutBehavior;

import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.List;

public class MainGUI extends Shell
{

	/*
	 * Authors: Eric M., Brendan M., Dan S. Description: This application allows
	 * one to cluster ranked data. A ranking is a set of relationships such that
	 * one object is "ranked" higher than the other. Clustering ranked data
	 * allows us to find the center or centers of the ranked data.
	 */

	// piVector will contain all the rankings of the input file
	private ArrayList<RankedData> piVector;

	// these styled texts need to be saved as instance variables so they can be
	// updated.
	private SuperStyledText clusterText, qVectorText, cVectorText, lVectorText, sigmaTimeLineText;

	// these UI elements are used in the analysis so they are saved as rankings
	private Spinner completeRankings, numClusters;

	// this checkbox tells the analyzer whether the sigma vector should be
	// randomized
	private Button btnRandomizeSigmaVector;

	// this label displays the filename of the loaded file
	private Label lblFileName;

	// this GUI follows the singleton pattern and saving an instance is done so
	// that
	// it can be retireved from anywhere in the program.
	private static MainGUI instance;
	private Text descriptText;
	public static List descriptList;

	/**
	 * Launch the application.
	 * 
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

	// Creates the GUI. This class may be loaded with WindowBuilderPro, and
	// elements can be moved around in that way.
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

		Composite composite_2 = new Composite(this, SWT.NONE);
		GridData gd_composite_2 = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_composite_2.widthHint = 195;
		composite_2.setLayoutData(gd_composite_2);
		composite_2.setLayout(new GridLayout(1, false));

		lblFileName = new Label(composite_2, SWT.WRAP);
		lblFileName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lblFileName.setText("No File Loaded");

		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));

		TabItem tbtmClusterCenters = new TabItem(tabFolder, SWT.NONE);
		tbtmClusterCenters.setText("Cluster Centers");
		StyledText clusterText_1 = new StyledText(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.READ_ONLY);
		tbtmClusterCenters.setControl(clusterText_1);

		TabItem tbtmQvector = new TabItem(tabFolder, SWT.NONE);
		tbtmQvector.setText("Q Vector");
		StyledText qVectorText_1 = new StyledText(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.READ_ONLY);
		tbtmQvector.setControl(qVectorText_1);

		TabItem tbtmCvector = new TabItem(tabFolder, SWT.NONE);
		tbtmCvector.setText("C Vector");
		StyledText cVectorText_1 = new StyledText(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.READ_ONLY);
		tbtmCvector.setControl(cVectorText_1);

		TabItem tbtmLvector = new TabItem(tabFolder, SWT.NONE);
		tbtmLvector.setText("λ Vector");
		StyledText lVectorText_1 = new StyledText(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.READ_ONLY);
		tbtmLvector.setControl(lVectorText_1);

		TabItem tbtmLSigmaOverTime = new TabItem(tabFolder, SWT.NONE);
		tbtmLSigmaOverTime.setText("σ Timeline");
		StyledText sigmaTimeLineText_1 = new StyledText(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.READ_ONLY);
		tbtmLSigmaOverTime.setControl(sigmaTimeLineText_1);

		// These super styled texts are used for formatting and for a wrapper
		// for
		// the styled text class. This makes them easier to interface with.
		this.clusterText = new SuperStyledText(clusterText_1);
		this.qVectorText = new SuperStyledText(qVectorText_1);
		this.cVectorText = new SuperStyledText(cVectorText_1);
		this.lVectorText = new SuperStyledText(lVectorText_1);
		this.sigmaTimeLineText = new SuperStyledText(sigmaTimeLineText_1);

		ExpandBar expandBar = new ExpandBar(this, SWT.NONE);
		expandBar.setBackground(new Color(Display.getCurrent(), 245, 246, 247));
		GridData gd_expandBar = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 2);
		gd_expandBar.heightHint = 254;
		gd_expandBar.widthHint = 195;
		expandBar.setLayoutData(gd_expandBar);

		ExpandItem xpndtmClusterAnalyzer = new ExpandItem(expandBar, SWT.NONE);
		xpndtmClusterAnalyzer.setExpanded(true);
		xpndtmClusterAnalyzer.setText("Cluster Analyzer");

		Composite composite = new Composite(expandBar, SWT.NONE);
		composite.setBackground(new Color(Display.getCurrent(), 240, 240, 240));
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

		ExpandItem xpndtmDataDescription = new ExpandItem(expandBar, SWT.NONE);
		xpndtmDataDescription.setExpanded(true);
		xpndtmDataDescription.setText("Data Description");
//---------------
		Composite descriptionComposite = new Composite(expandBar, SWT.NONE);
		xpndtmDataDescription.setControl(descriptionComposite);
		xpndtmDataDescription.setHeight(110);
		descriptionComposite.setLayout(new GridLayout(4, false));

		descriptList = new List(descriptionComposite, SWT.BORDER);
		GridData gd_list = new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1);
		gd_list.widthHint = 177;
		descriptList.setLayoutData(gd_list);

		descriptText = new Text(descriptionComposite, SWT.BORDER);
		GridData gd_text_1 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text_1.widthHint = 120;
		descriptText.setLayoutData(gd_text_1);

		Button btnAddDescription = new Button(descriptionComposite, SWT.NONE);
		btnAddDescription.setText("+");

		Button btnRemoveDescription = new Button(descriptionComposite, SWT.NONE);
		btnRemoveDescription.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnRemoveDescription.setText(" - ");
		new Label(descriptionComposite, SWT.NONE);
		
		btnAddDescription.addSelectionListener(new AddDescriptions(descriptList, descriptText));
		btnRemoveDescription.addSelectionListener(new RemoveDescription(descriptList));
//-------------------

		startAnalysis.addSelectionListener(new AnalyzeBehavior(this));
		ArrayList<SuperStyledText> styledTexts = new ArrayList<>();
		styledTexts.add(this.clusterText);
		styledTexts.add(this.qVectorText);
		styledTexts.add(this.cVectorText);
		styledTexts.add(this.lVectorText);
		styledTexts.add(this.sigmaTimeLineText);
		mntmZoomIn.addSelectionListener(new ZoomInBehavior(styledTexts, display, mntmZoomOut, mntmZoomIn));
		mntmZoomOut.addSelectionListener(new ZoomOutBehavior(styledTexts, display, mntmZoomOut, mntmZoomIn));
		mntmZoomIn.setAccelerator(SWT.CONTROL + '=');
		mntmZoomOut.setAccelerator(SWT.CONTROL + '-');

		MenuItem mntmRandomDataGeneration = new MenuItem(menu_3, SWT.NONE);
		mntmRandomDataGeneration.setText("Random Data Generation");
		mntmRandomDataGeneration.addSelectionListener(new RandomDataGeneratorStartBehavior());
		createContents();

		addListener(SWT.Close, new Listener()
		{

			@Override
			public void handleEvent(Event arg0)
			{
				if (RandomDataGenerator.getInstance()!=null && !RandomDataGenerator.getInstance().isDisposed())
				{
					RandomDataGenerator.getInstance().dispose();
				}

			}
		});
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents()
	{
		setText("Ranked Data Clustering");
		setSize(710, 528);

	}

	// This method opens a file from a given filename and stores its contents as
	// a pi vector.
	public void openPiVector(String fileName)
	{
		File file = new File(fileName);

		if (!file.exists())
		{
			return;
		}

		FileLoader fLoader = new FileLoader();

		fLoader.loadFile(fileName, FileType.RankedData);

		if (fLoader.getPartialRankings() != null)
		{
			piVector = new ArrayList<>();
			int maxLength = 0;
			for (RankedData pr : fLoader.getPartialRankings())
			{
				piVector.add(pr.clone());
				int largestValue = pr.largestValue();
				if (largestValue > maxLength)
				{
					maxLength = largestValue;
				}

				completeRankings.setSelection(maxLength);
				completeRankings.setMinimum(maxLength);
			}

		}

		lblFileName.setText("Current File: " + file.getName());
	}

	public void openPiVector(ArrayList<RankedData> piVector)
	{
		this.piVector = piVector;

		lblFileName.setText("Loaded π vector");
		int maxLength = 0;
		for (RankedData pr : piVector)
		{
			int largestValue = pr.largestValue();
			if (largestValue > maxLength)
			{
				maxLength = largestValue;
			}

			completeRankings.setSelection(maxLength);
			completeRankings.setMinimum(maxLength);
		}

	}

	// This method sets the text of the qVector super styled text box
	public void setQVectorText(String text)
	{
		qVectorText.setText(text);
	}

	// This method sets the text of the lambda vector super styled text box
	public void setLVectorText(String text)
	{
		lVectorText.setText(text);
	}

	// This method sets the text of the cluter centers super styled text box
	public void setClusterText(String text)
	{
		clusterText.setText(text);
	}

	// This method sets the text of the cVector super styled text box
	public void setCVectorText(String text)
	{
		cVectorText.setText(text);
	}

	// This method sets the text of the sigma timeline super styled text box
	public void setSigmaTimelineText(String text)
	{
		sigmaTimeLineText.setText(text);
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	// This method gets the piVector that is stored here.
	public ArrayList<RankedData> getPiVector()
	{
		return piVector;
	}

	// This method gets the number of clusters and is used in analysis
	public int getNumberClusters()
	{
		return numClusters.getSelection();
	}

	// This method gets the number of complete rankings
	public int getCompleteRankings()
	{
		return completeRankings.getSelection();
	}

	// This method gets whether the initial sigma vector should be randomized or
	// not.
	public boolean getRandomizeSigma()
	{
		return btnRandomizeSigmaVector.getSelection();
	}

	// This method gets the instance of this class, where there is only one.
	public static MainGUI getInstance()
	{
		return instance;
	}
	
	public static String getDesciptList(int index)
	{
		boolean isNegative = index < 0;
		index = Math.abs(index);
		if (index > descriptList.getItemCount())
		{
			if (isNegative)
			{
				return Integer.valueOf(-index).toString();
			}
			return Integer.valueOf(index).toString();
		}
		else
		{
			System.out.println(index);
			if (isNegative)
			{
				return "Not " + descriptList.getItem(index-1);
			}
			return descriptList.getItem(index-1);
		}
	}
	
}
