package gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import main.FileLoader;
import main.FileType;
import main.RankedData;
import main.Settings;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

import selectionListeners.AboutDialogBehavior;
import selectionListeners.AddDescriptions;
import selectionListeners.AnalyzeBehavior;
import selectionListeners.CopyBehavior;
import selectionListeners.ExitBehavior;
import selectionListeners.HelpMenuBehavior;
import selectionListeners.OpenFileBehavior;
import selectionListeners.RandomDataGeneratorStartBehavior;
import selectionListeners.RemoveDescription;
import selectionListeners.SaveFileBehavior;
import selectionListeners.SelectAllBehavior;
import selectionListeners.ShowOrHideExpandBar;
import selectionListeners.ZoomInBehavior;
import selectionListeners.ZoomOutBehavior;

import org.eclipse.swt.widgets.List;
import org.eclipse.wb.swt.SWTResourceManager;

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
	private SuperStyledText clusterText, qVectorText, cVectorText, lVectorText, sigmaTimeLineText, cumulitiveRunsText;

	// these UI elements are used in the analysis so they are saved as rankings
	private Spinner completeRankings, numClusters, numberOfRuns;

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
	private TabFolder displayTabs;
	private static boolean modified = false;

	private ArrayList<SuperStyledText> styledTexts;

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
		setImage(SWTResourceManager.getImage(MainGUI.class, "/images/icon_small.png"));
		instance = this;
		setMinimumSize(new Point(710, 528));
		setLayout(new GridLayout(2, false));
		Menu menu = new Menu(this, SWT.BAR);
		setMenuBar(menu);

		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");

		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

		MenuItem mntmOpenSettings = new MenuItem(menu_1, SWT.NONE);
		mntmOpenSettings.setText("Open Session");
		mntmOpenSettings.addSelectionListener(new OpenFileBehavior(this, FileType.Settings));
		mntmOpenSettings.setAccelerator(SWT.CONTROL + 'O');

		MenuItem mntmSave = new MenuItem(menu_1, SWT.NONE);
		mntmSave.setText("Save Session");
		mntmSave.addSelectionListener(new SaveFileBehavior(FileType.Settings));
		mntmSave.setAccelerator(SWT.CONTROL + 'S');

		MenuItem mntmOpen = new MenuItem(menu_1, SWT.NONE);
		mntmOpen.setText("Import Rankings");
		mntmOpen.addSelectionListener(new OpenFileBehavior(this, FileType.RankedData));
		mntmOpen.setAccelerator(SWT.CONTROL + 'I');

		MenuItem mntmExport = new MenuItem(menu_1, SWT.NONE);
		mntmExport.setText("Export Results");
		mntmExport.addSelectionListener(new SaveFileBehavior(FileType.ExportedResults));
		mntmExport.setAccelerator(SWT.CONTROL + 'E');

		new MenuItem(menu_1, SWT.SEPARATOR);

		MenuItem mntmExit = new MenuItem(menu_1, SWT.NONE);
		mntmExit.setText("Exit");
		mntmExit.addSelectionListener(new ExitBehavior());

		MenuItem mntmEdit = new MenuItem(menu, SWT.CASCADE);
		mntmEdit.setText("Edit");

		Menu menu_2 = new Menu(mntmEdit);
		mntmEdit.setMenu(menu_2);

		MenuItem mntmCopy = new MenuItem(menu_2, SWT.NONE);
		mntmCopy.setText("Copy");
		mntmCopy.addSelectionListener(new CopyBehavior());
		mntmCopy.setAccelerator(SWT.CONTROL + 'C');

		new MenuItem(menu_2, SWT.SEPARATOR);

		MenuItem mntmSelectAll = new MenuItem(menu_2, SWT.NONE);
		mntmSelectAll.setText("Select All");
		mntmSelectAll.addSelectionListener(new SelectAllBehavior());
		mntmSelectAll.setAccelerator(SWT.CONTROL + 'A');

		MenuItem mntmView = new MenuItem(menu, SWT.CASCADE);
		mntmView.setText("View");

		Menu menu_3 = new Menu(mntmView);
		mntmView.setMenu(menu_3);

		MenuItem mntmZoomIn = new MenuItem(menu_3, SWT.NONE);
		mntmZoomIn.setText("Zoom in");

		MenuItem mntmZoomOut = new MenuItem(menu_3, SWT.NONE);
		mntmZoomOut.setText("Zoom out");

		new MenuItem(menu_3, SWT.SEPARATOR);

		MenuItem mntmToggleSidebar = new MenuItem(menu_3, SWT.CHECK);
		mntmToggleSidebar.setSelection(true);
		mntmToggleSidebar.setText("View Sidebar");

		MenuItem mntmHelp = new MenuItem(menu, SWT.CASCADE);
		mntmHelp.setText("Help");
		Menu menu_4 = new Menu(mntmHelp);
		mntmHelp.setMenu(menu_4);

		MenuItem mntmViewHelp = new MenuItem(menu_4, SWT.NONE);
		mntmViewHelp.setText("View Help");
		mntmViewHelp.addSelectionListener(new HelpMenuBehavior());
		mntmViewHelp.setAccelerator(SWT.F1);
		
		MenuItem mntmAbout = new MenuItem(menu_4, SWT.NONE);
		mntmAbout.setText("About");
		
		mntmAbout.addSelectionListener(new AboutDialogBehavior());

		Composite settingsComposite = new Composite(this, SWT.NONE);
		settingsComposite.setLayout(new GridLayout(1, false));
		GridData gd_settingsComposite = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_settingsComposite.widthHint = 195;
		settingsComposite.setLayoutData(gd_settingsComposite);

		lblFileName = new Label(settingsComposite, SWT.WRAP);
		lblFileName.setText("No File Loaded");

		ExpandBar expandBar = new ExpandBar(settingsComposite, SWT.NONE);
		GridData gd_expandBar = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_expandBar.widthHint = 187;
		expandBar.setLayoutData(gd_expandBar);
		expandBar.setSize(195, 429);

		mntmToggleSidebar.addSelectionListener(new ShowOrHideExpandBar(settingsComposite, this, mntmToggleSidebar));
		expandBar.setBackground(new Color(Display.getCurrent(), 245, 246, 247));

		ExpandItem xpndtmClusterAnalyzer = new ExpandItem(expandBar, SWT.NONE);
		xpndtmClusterAnalyzer.setExpanded(true);
		xpndtmClusterAnalyzer.setText("Cluster Analyzer");

		Composite composite = new Composite(expandBar, SWT.NONE);
		xpndtmClusterAnalyzer.setControl(composite);
		composite.setBackground(new Color(Display.getCurrent(), 240, 240, 240));
		GridLayout gl_composite = new GridLayout(2, false);
		gl_composite.marginLeft = 5;
		composite.setLayout(gl_composite);

		Label lblNumberClusters = new Label(composite, SWT.NONE);
		lblNumberClusters.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNumberClusters.setText("Number of Clusters");

		numClusters = new Spinner(composite, SWT.BORDER);
		numClusters.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		numClusters.setMaximum(9);
		numClusters.setMinimum(1);
		numClusters.addSelectionListener(modifiedState);
	
		Label lblCompleteRankings = new Label(composite, SWT.NONE);
		lblCompleteRankings.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		lblCompleteRankings.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCompleteRankings.setText("Complete Rankings");

		completeRankings = new Spinner(composite, SWT.BORDER);
		completeRankings.setMinimum(2);
		completeRankings.setMaximum(100);
		completeRankings.addSelectionListener(modifiedState);
		
		new Label(composite, SWT.NONE);

		Button startAnalysis = new Button(composite, SWT.NONE);
		startAnalysis.setText("Start");
		// -------------------

		startAnalysis.addSelectionListener(new AnalyzeBehavior(this));
		xpndtmClusterAnalyzer.setHeight(96);

		ExpandItem analyzeSettings = new ExpandItem(expandBar, 0);
		analyzeSettings.setText("Settings");
		analyzeSettings.setExpanded(true);

		Composite composite_1 = new Composite(expandBar, SWT.NONE);
		analyzeSettings.setControl(composite_1);
		composite_1.setLayout(new GridLayout(1, false));

		btnRandomizeSigmaVector = new Button(composite_1, SWT.CHECK);
		btnRandomizeSigmaVector.setSelection(true);
		btnRandomizeSigmaVector.setText("Randomize Sigma Vector");
		btnRandomizeSigmaVector.addSelectionListener(modifiedState);

		Composite composite_3 = new Composite(composite_1, SWT.NONE);
		composite_3.setLayout(new GridLayout(2, false));

		Label lblNumberRuns = new Label(composite_3, SWT.NONE);
		lblNumberRuns.setText("Number Runs");

		numberOfRuns = new Spinner(composite_3, SWT.BORDER);
		numberOfRuns.setMinimum(1);
		numberOfRuns.addSelectionListener(modifiedState);
		analyzeSettings.setHeight(80);

		ExpandItem xpndtmDataDescription = new ExpandItem(expandBar, SWT.NONE);
		xpndtmDataDescription.setExpanded(true);
		xpndtmDataDescription.setText("Data Description");
		// ---------------
		Composite descriptionComposite = new Composite(expandBar, SWT.NONE);
		xpndtmDataDescription.setControl(descriptionComposite);
		xpndtmDataDescription.setHeight(110);
		descriptionComposite.setLayout(new GridLayout(4, false));

		descriptList = new List(descriptionComposite, SWT.BORDER);
		GridData gd_list = new GridData(SWT.LEFT, SWT.CENTER, true, false, 4, 1);
		gd_list.widthHint = 177;
		descriptList.setLayoutData(gd_list);

		descriptText = new Text(descriptionComposite, SWT.BORDER);
		GridData gd_text_1 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1);
		gd_text_1.widthHint = 120;
		descriptText.setLayoutData(gd_text_1);

		Button btnAddDescription = new Button(descriptionComposite, SWT.NONE);
		btnAddDescription.setText("+");

		btnAddDescription.addSelectionListener(new AddDescriptions(descriptList, descriptText));

		Button btnRemoveDescription = new Button(descriptionComposite, SWT.NONE);
		btnRemoveDescription.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnRemoveDescription.setText(" - ");
		btnRemoveDescription.addSelectionListener(new RemoveDescription(descriptList));

		displayTabs = new TabFolder(this, SWT.NONE);
		displayTabs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));

		TabItem tbtmClusterCenters = new TabItem(displayTabs, SWT.NONE);
		tbtmClusterCenters.setText("Cluster Centers");
		StyledText clusterText_1 = new StyledText(displayTabs, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.READ_ONLY);
		clusterText_1.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		tbtmClusterCenters.setControl(clusterText_1);

		TabItem tbtmQvector = new TabItem(displayTabs, SWT.NONE);
		tbtmQvector.setText("Q Vector");
		StyledText qVectorText_1 = new StyledText(displayTabs, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.READ_ONLY);
		tbtmQvector.setControl(qVectorText_1);

		TabItem tbtmCvector = new TabItem(displayTabs, SWT.NONE);
		tbtmCvector.setText("C Vector");
		StyledText cVectorText_1 = new StyledText(displayTabs, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.READ_ONLY);
		tbtmCvector.setControl(cVectorText_1);

		TabItem tbtmLvector = new TabItem(displayTabs, SWT.NONE);
		tbtmLvector.setText("λ Vector");
		StyledText lVectorText_1 = new StyledText(displayTabs, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.READ_ONLY);
		tbtmLvector.setControl(lVectorText_1);

		TabItem tbtmLSigmaOverTime = new TabItem(displayTabs, SWT.NONE);
		tbtmLSigmaOverTime.setText("σ Timeline");
		StyledText sigmaTimeLineText_1 = new StyledText(displayTabs, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.READ_ONLY);
		tbtmLSigmaOverTime.setControl(sigmaTimeLineText_1);

		TabItem tbtmMultipleRunSummary = new TabItem(displayTabs, SWT.NONE);
		tbtmMultipleRunSummary.setText("Multiple Run Summary");
		StyledText cumulitiveResults = new StyledText(displayTabs, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.READ_ONLY);
		tbtmMultipleRunSummary.setControl(cumulitiveResults);

		// These super styled texts are used for formatting and for a wrapper
		// for
		// the styled text class. This makes them easier to interface with.
		this.clusterText = new SuperStyledText(clusterText_1);
		this.qVectorText = new SuperStyledText(qVectorText_1);
		this.cVectorText = new SuperStyledText(cVectorText_1);
		this.lVectorText = new SuperStyledText(lVectorText_1);
		this.sigmaTimeLineText = new SuperStyledText(sigmaTimeLineText_1);
		this.cumulitiveRunsText = new SuperStyledText(cumulitiveResults);
		styledTexts = new ArrayList<>();
		styledTexts.add(this.clusterText);
		styledTexts.add(this.qVectorText);
		styledTexts.add(this.cVectorText);
		styledTexts.add(this.lVectorText);
		styledTexts.add(this.sigmaTimeLineText);
		styledTexts.add(this.cumulitiveRunsText);
		mntmZoomIn.addSelectionListener(new ZoomInBehavior(styledTexts, mntmZoomOut, mntmZoomIn));
		mntmZoomOut.addSelectionListener(new ZoomOutBehavior(styledTexts, mntmZoomOut, mntmZoomIn));
		mntmZoomIn.setAccelerator(SWT.CONTROL + '=');
		mntmZoomOut.setAccelerator(SWT.CONTROL + '-');

		MenuItem mntmRandomDataGeneration = new MenuItem(menu_3, SWT.NONE);
		mntmRandomDataGeneration.setText("Random Data Generation");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		mntmRandomDataGeneration.addSelectionListener(new RandomDataGeneratorStartBehavior());
		createContents();

		addListener(SWT.Close, new Listener()
		{

			@Override
			public void handleEvent(Event arg0)
			{
				ExitBehavior exit = new ExitBehavior();
				exit.setEventToCancel(arg0);
				exit.widgetSelected(null);
				if (RandomDataGenerator.getInstance() != null && !RandomDataGenerator.getInstance().isDisposed() && arg0.doit)
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

	// This method returns a single item from the description list. The number is returned
	// if no description is given. 
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
				return "Not " + descriptList.getItem(index - 1);
			}
			return descriptList.getItem(index - 1);
		}
	}

	// This method takes a settings memento and restores the program's state from the file. 
	public void openSettings(Settings settings)
	{
		numClusters.setSelection(settings.getNumberClusters());
		completeRankings.setSelection(settings.getCompleteRankings());

		if (settings.getRandomizableRankings() != null)
		{
			RandomDataGenerator.openSettings(settings);
		}

		piVector = settings.getPiVector();
		numClusters.setMinimum(settings.getMinClusters());
		completeRankings.setMinimum(settings.getMinRankings());
		numberOfRuns.setSelection(settings.getNumberRuns());

		clusterText.setText(settings.getClusterCenterInfo());
		qVectorText.setText(settings.getqVectorInfo());
		lVectorText.setText(settings.getlVectorInfo());
		cVectorText.setText(settings.getcVectorInfo());
		sigmaTimeLineText.setText(settings.getSigmaTimelineInfo());
		cumulitiveRunsText.setText(settings.getCumululitiveText());

		descriptList.removeAll();

		for (String s : settings.getDescriptions())
		{
			descriptList.add(s);
		}

	}

	// This method saves the settings to disk at a specified file path. 
	public void saveSettings(String filepath)
	{
		Settings settings;
		ObjectOutputStream objectOutputStream = null;

		settings = new Settings(numClusters.getSelection(), completeRankings.getSelection(), RandomDataGenerator.getPiVector(), RandomDataGenerator.getRandomizeableRankedData());

		settings.setClusterCenterInfo(clusterText.getText());
		settings.setqVectorInfo(qVectorText.getText());
		settings.setlVectorInfo(lVectorText.getText());
		settings.setcVectorInfo(cVectorText.getText());
		settings.setSigmaTimelineInfo(sigmaTimeLineText.getText());
		settings.setMinClusters(numClusters.getMinimum());
		settings.setMinRankings(completeRankings.getMinimum());
		settings.setPiVector(piVector);
		settings.setDescriptions(descriptList.getItems());
		settings.setNumberRuns(numberOfRuns.getSelection());
		settings.setCumululitiveText(cumulitiveRunsText.getText());
		try
		{
			FileOutputStream outputStream = new FileOutputStream(filepath);
			objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(settings);

		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if (objectOutputStream != null)
			{
				try
				{
					objectOutputStream.close();
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// This method gets how many times the test should take place. 
	public int getNumberOfRuns()
	{
		return numberOfRuns.getSelection();
	}

	// This method sets the text for the cumulative runs tab. 
	public void setCumulitiveRunsText(String text)
	{
		cumulitiveRunsText.setText(text);
	}

	// This method is called when the user selects File>Export Results. It exports the text of all the tabs
	// to a formatted file so it may be read by anyone without the program. 
	public void exportResults(String file)
	{
		BufferedWriter out = null;
		try
		{

			out = new BufferedWriter(new FileWriter(new File(file)));

			writeOneSection(out, "Cluster Center Information", clusterText.getUnformattedText());
			writeOneSection(out, "Sigma Timeline", sigmaTimeLineText.getUnformattedText());
			writeOneSection(out, "Q Vector Info", qVectorText.getUnformattedText());
			writeOneSection(out, "Lambda Vector Info", lVectorText.getUnformattedText());
			writeOneSection(out, "C Vector Info", cVectorText.getUnformattedText());
			writeOneSection(out, "Other runs", cumulitiveRunsText.getUnformattedText());

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (out != null)
			{
				try
				{
					out.flush();
					out.close();
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

	// This method writes a portion of the cluster analyzer to disk using a buffered writer. 
	private void writeOneSection(BufferedWriter out, String heading, String body) throws IOException
	{
		out.write(heading + "\r\n");
		out.write("__________________________________________________\r\n");
		out.write(body.replace("\n", "\r\n"));
		out.write("\n\r");

	}

	// This method is used when the user selects Select All or Copy from the Edit menu.
	// It returns the selected tab.
	public SuperStyledText getActiveTab()
	{
		int index = displayTabs.getSelectionIndex();

		return styledTexts.get(index);
	}
	
	// This boolean returns whether the user modified any settings or not. 
	public static boolean getModifiedState()
	{
		return modified;
	}
	
	// This method flags the modified flag, meaning the user changed some setting. This means that we need to ask 
	// if it is okay to close the program before exiting.
	public static void modifyStateFlag()
	{
		modified = true;
	}
	
	// This method is called when the user saves the session, meaning that nothing was modified since
	// the last save. 
	public static void resetModifiedState()
	{
		modified = false;
	}
	
	// This adapter modifies the modified flag. It SHOULD be attached to any control the execution of the
	// cluster analyzer process. 
	public static SelectionAdapter modifiedState = new SelectionAdapter()
	{
		@Override
	 	public void widgetSelected(SelectionEvent e)
		{
			MainGUI.modifyStateFlag();
		}
	};
	
	

}
