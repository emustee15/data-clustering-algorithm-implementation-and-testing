package gui;

import java.util.ArrayList;

import main.RandomizableRankedData;
import main.RankedData;
import main.Settings;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Spinner;

import selectionListeners.AddClusterCenter;
import selectionListeners.ChangeClusterCenterBehavior;
import selectionListeners.ListChangedBehavior;
import selectionListeners.RemoveClusterCenter;

import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.wb.swt.SWTResourceManager;

public class RandomDataGenerator extends Shell
{
	private static RandomDataGenerator instance;
	private static ArrayList<RandomizableRankedData> randomizableRankedData;
	private Button randomizedLengths, btnAnalyzeButton, btnGenerateData;
	private Spinner numberOfSwaps;
	private Spinner probabilityOfSwaps;
	private Spinner numberOfChildren;
	private Spinner minRankings, maxRankings;
	private RandomizableRankedData currentSelection;
	private static ArrayList<RankedData> piVector;
	private Text piVectorText;
	private Button distanceSwap, randomSwap, symmetricSwap;
	private List clusterCenters;
	private static Settings settings;
	private TabFolder tabFolder;
	private TabItem tbtmGeneratedPiVector;
	private Label lblNumberOfChildren;
	private boolean fixedSwapMode = false;
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
			RandomDataGenerator shell = new RandomDataGenerator(display);
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
	 * 
	 * @param display
	 */

	public static void startRandomDataGenerator()
	{
		if (instance == null)
		{
			try
			{
				Display display = Display.getDefault();
				instance = new RandomDataGenerator(display);
				instance.open();
				instance.layout();
				while (instance != null && !instance.isDisposed())
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
		else
		{
			instance.setVisible(true);
			instance.setFocus();
		}
	}

	private RandomDataGenerator(Display display)
	{
		super(display, SWT.SHELL_TRIM);
		setImage(SWTResourceManager.getImage(RandomDataGenerator.class, "/images/icon_small.png"));
		if (randomizableRankedData == null)
		{
			randomizableRankedData = new ArrayList<>();
		}

		instance = this;
		setMinimumSize(new Point(640, 480));
		setLayout(new GridLayout(2, false));

		Composite composite_2 = new Composite(this, SWT.NONE);
		GridData gd_composite_2 = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd_composite_2.widthHint = 266;
		composite_2.setLayoutData(gd_composite_2);
		composite_2.setLayout(new GridLayout(1, false));

		clusterCenters = new List(composite_2, SWT.BORDER);
		clusterCenters.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		tabFolder = new TabFolder(this, SWT.NONE);
		GridData gd_tabFolder = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
		gd_tabFolder.heightHint = 368;
		tabFolder.setLayoutData(gd_tabFolder);

		TabItem tbtmClusterSettings = new TabItem(tabFolder, SWT.NONE);
		tbtmClusterSettings.setText("Cluster Settings");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmClusterSettings.setControl(composite);
		composite.setLayout(new GridLayout(2, false));

		Group grpSwappingSettings = new Group(composite, SWT.NONE);
		grpSwappingSettings.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		grpSwappingSettings.setText("Swapping Settings");
		grpSwappingSettings.setLayout(new GridLayout(2, false));

		distanceSwap = new Button(grpSwappingSettings, SWT.RADIO);
		distanceSwap.setSelection(true);
		distanceSwap.setText("Distance Swap");

		new Label(grpSwappingSettings, SWT.NONE);

		randomSwap = new Button(grpSwappingSettings, SWT.RADIO);
		randomSwap.setText("Standard Swap");

		new Label(grpSwappingSettings, SWT.NONE);

		symmetricSwap = new Button(grpSwappingSettings, SWT.RADIO);
		symmetricSwap.setEnabled(false);
		symmetricSwap.setText("3x3 Symmetric Swap");

		new Label(grpSwappingSettings, SWT.NONE);

		Label lblNewLabel = new Label(grpSwappingSettings, SWT.NONE);
		lblNewLabel.setText("Max number of swaps");

		numberOfSwaps = new Spinner(grpSwappingSettings, SWT.BORDER);
		numberOfSwaps.setSelection(2);

		Label lblProbabilityOfSwap = new Label(grpSwappingSettings, SWT.NONE);
		lblProbabilityOfSwap.setText("Probability of swap");

		probabilityOfSwaps = new Spinner(grpSwappingSettings, SWT.BORDER);
		probabilityOfSwaps.setSelection(100);

		Group grpNumberOfPartial = new Group(composite, SWT.NONE);
		grpNumberOfPartial.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		grpNumberOfPartial.setText("Number Of Partial Rankings");
		grpNumberOfPartial.setLayout(new GridLayout(2, false));

		randomizedLengths = new Button(grpNumberOfPartial, SWT.CHECK);
		randomizedLengths.setText("Randomize lengths");
		new Label(grpNumberOfPartial, SWT.NONE);

		Label lblMinNumberOf = new Label(grpNumberOfPartial, SWT.NONE);
		lblMinNumberOf.setText("Min number of partial rankings");

		minRankings = new Spinner(grpNumberOfPartial, SWT.BORDER);
		minRankings.setMinimum(1);

		Label lblMaxNumberOf = new Label(grpNumberOfPartial, SWT.NONE);
		lblMaxNumberOf.setText("Max number of partial rankings");

		maxRankings = new Spinner(grpNumberOfPartial, SWT.BORDER);
		maxRankings.setMinimum(1);

		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		lblNumberOfChildren = new Label(composite, SWT.NONE);
		lblNumberOfChildren.setText("Number of children");

		numberOfChildren = new Spinner(composite, SWT.BORDER);
		numberOfChildren.setMaximum(20000);
		numberOfChildren.setMinimum(1);

		tbtmGeneratedPiVector = new TabItem(tabFolder, SWT.NONE);
		tbtmGeneratedPiVector.setText("Generated Pi Vector");

		piVectorText = new Text(tabFolder, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		piVectorText.setText("No generated data!");
		tbtmGeneratedPiVector.setControl(piVectorText);

		Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setLayout(new GridLayout(5, false));
		GridData gd_composite_1 = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_composite_1.heightHint = 68;
		composite_1.setLayoutData(gd_composite_1);

		Text clusterCenterText = new Text(composite_1, SWT.BORDER);
		clusterCenterText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		Button btnAddClusterCenter = new Button(composite_1, SWT.NONE);
		btnAddClusterCenter.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnAddClusterCenter.setText("+");

		Button btnRemoveClusterCenter = new Button(composite_1, SWT.NONE);
		btnRemoveClusterCenter.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnRemoveClusterCenter.setText(" - ");

		Button btnChange = new Button(composite_1, SWT.NONE);
		btnChange.setText("Modify");
		btnGenerateData = new Button(composite_1, SWT.NONE);
		btnGenerateData.setText("Generate Data");

		btnAnalyzeButton = new Button(composite_1, SWT.NONE);
		btnAnalyzeButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnAnalyzeButton.setText("Analyze Data");

		new Label(composite_1, SWT.NONE);
		createContents();

		instance.addListener(SWT.Close, new Listener()
		{

			@Override
			public void handleEvent(Event arg0)
			{
				if (MainGUI.getInstance() == null)
				{

				}
				else if (!MainGUI.getInstance().isDisposed())
				{

					instance.setVisible(false);
					arg0.doit = false;
				}
			}
		});
		btnAddClusterCenter.addSelectionListener(new AddClusterCenter(clusterCenters, clusterCenterText));
		btnRemoveClusterCenter.addSelectionListener(new RemoveClusterCenter(clusterCenters));
		btnChange.addSelectionListener(new ChangeClusterCenterBehavior(clusterCenters, clusterCenterText));

		clusterCenters.addSelectionListener(new ListChangedBehavior(clusterCenters, clusterCenterText));
		createSelectionListeners();
		noSelection();

		if (settings != null)
		{
			openSettingsInternal(settings);
		}
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents()
	{
		setText("Random Data Generator");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	public boolean getRandomizedLengths()
	{
		return randomizedLengths.getSelection();
	}

	public int getProbabilityOfSwap()
	{
		return probabilityOfSwaps.getSelection();
	}

	public int getNumberOfSwaps()
	{
		return numberOfSwaps.getSelection();
	}

	public static RandomDataGenerator getInstance()
	{
		return instance;
	}

	public void addClusterCenter(RandomizableRankedData clusterCenter)
	{
		randomizableRankedData.add(clusterCenter);
	}

	public void removeClusterCenter(int index)
	{
		randomizableRankedData.remove(index);
	}

	public void setCurrentSelection(int index)
	{
		currentSelection = randomizableRankedData.get(index);
		
		
		randomizedLengths.setEnabled(true);
		numberOfSwaps.setEnabled(true);
		probabilityOfSwaps.setEnabled(true);
		numberOfChildren.setEnabled(true);
		distanceSwap.setEnabled(true);
		randomSwap.setEnabled(true);
		symmetricSwap.setEnabled(true);
		setFixedSwapMode(currentSelection.getTechnique()==RandomizableRankedData.SYMMETRIC_SWAP);

		randomizedLengths.setSelection(currentSelection.isRandomziedLenghts());
		numberOfSwaps.setSelection(currentSelection.getNumberOfSwaps());
		probabilityOfSwaps.setSelection(currentSelection.getProbabilityOfSwap());
		
		maxRankings.setMinimum(1);
		maxRankings.setMaximum(currentSelection.getSize());
		maxRankings.setSelection(currentSelection.getMaxPartialRankings());

		minRankings.setMaximum(maxRankings.getSelection());
		minRankings.setSelection(currentSelection.getMinPartialRankings());
		maxRankings.setMinimum(minRankings.getSelection());

		minRankings.setEnabled(randomizedLengths.getSelection());
		maxRankings.setEnabled(randomizedLengths.getSelection());

		int technique = currentSelection.getTechnique();

		setTechnique(technique);
		
	}

	private void setTechnique(int technique)
	{
		switch (technique)
		{
		case RandomizableRankedData.DISTANCE_SWAP:
			distanceSwap.setSelection(true);
			randomSwap.setSelection(false);
			symmetricSwap.setSelection(false);
			break;
		case RandomizableRankedData.RANDOM_SWAP:
			distanceSwap.setSelection(false);
			randomSwap.setSelection(true);
			symmetricSwap.setSelection(false);
			break;
		case RandomizableRankedData.SYMMETRIC_SWAP:
			distanceSwap.setSelection(false);
			randomSwap.setSelection(false);
			symmetricSwap.setSelection(true);
			break;
		}
	}

	public void noSelection()
	{
		randomizedLengths.setEnabled(false);
		numberOfSwaps.setEnabled(false);
		probabilityOfSwaps.setEnabled(false);
		numberOfChildren.setEnabled(false);
		minRankings.setEnabled(false);
		maxRankings.setEnabled(false);
		distanceSwap.setEnabled(false);
		randomSwap.setEnabled(false);
		symmetricSwap.setEnabled(false);

	}

	public void doRandomization()
	{
		if (randomizableRankedData.size() == 0)
		{
			piVectorText.setText("Add cluster centers first!");
			return;
		}
		piVector = new ArrayList<>();

		String piVectorString = "";

		for (RandomizableRankedData data : randomizableRankedData)
		{
			int sizeMultiplier = 1;
			if (data.getTechnique() == RandomizableRankedData.SYMMETRIC_SWAP)
			{
				sizeMultiplier = RandomizableRankedData.getSumOfSymmetricWeightings();
				
			}
			for (int index = 0; index < (fixedSwapMode ? data.getNumberOfRepeats()*sizeMultiplier:data.getNumberOfChildren()); index++)
			{
				RankedData newData = data.nextRankedData();
				piVector.add(newData);
				piVectorString += newData.toString() + '\n';
			}
			
			data.doneWithData();
		}

		piVectorText.setText(piVectorString);
	}

	public void modifyDataOfCurrentCluster(RankedData data)
	{
		currentSelection.setData(data);
		maxRankings.setMinimum(1);
		maxRankings.setMaximum(currentSelection.getSize());
		minRankings.setMaximum(maxRankings.getSelection());
		maxRankings.setMinimum(minRankings.getSelection());
		if (currentSelection != null && symmetricSwap.getSelection())
		{
			if (currentSelection.getSize() != 9)
			{
				ErrorDialog dialog = new ErrorDialog(getShell());
				setTechnique(RandomizableRankedData.DISTANCE_SWAP);
				currentSelection.setTechnique(RandomizableRankedData.DISTANCE_SWAP);
				dialog.open("A 3x3 symmetric swap requires a ranking with 9 elements. Reverting to distance swap.");
				setFixedSwapMode(false);
			}
		}

	}

	public static ArrayList<RankedData> getPiVector()
	{
		return piVector;
	}

	public static void openSettings(Settings settings)
	{
		randomizableRankedData = settings.getRandomizableRankings();
		piVector = settings.getRandomizedRankings();
		if (instance == null)
		{
			RandomDataGenerator.settings = settings;
			return;
		}
		else
		{
			instance.openSettingsInternal(settings);
		}
	}

	private void openSettingsInternal(Settings settings)
	{

		if (piVector != null && !piVector.isEmpty())
		{
			String piVectorString = "";
			for (RankedData d : piVector)
			{
				piVectorString += d.toString() + "\n";
			}

			piVectorText.setText(piVectorString);
		}

		clusterCenters.removeAll();
		for (RandomizableRankedData d : randomizableRankedData)
		{
			clusterCenters.add(d.toString());
		}

		if (randomizableRankedData.size() > 0)
		{
			clusterCenters.setSelection(0);
			setCurrentSelection(0);
		}

	}

	public static ArrayList<RandomizableRankedData> getRandomizeableRankedData()
	{
		return randomizableRankedData;
	}

	public void createSelectionListeners()
	{
		btnGenerateData.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				doRandomization();
				tabFolder.setSelection(tbtmGeneratedPiVector);
				MainGUI.modifyStateFlag();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});

		btnAnalyzeButton.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				if (piVector != null)
				{
					MainGUI.getInstance().openPiVector(piVector);
					MainGUI.getInstance().setFocus();
				}
				MainGUI.modifyStateFlag();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});
		distanceSwap.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				if (currentSelection != null && distanceSwap.getSelection())
				{
					currentSelection.setTechnique(RandomizableRankedData.DISTANCE_SWAP);
					setFixedSwapMode(false);
				}
				MainGUI.modifyStateFlag();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});

		probabilityOfSwaps.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				if (currentSelection != null)
				{
					currentSelection.setProbabilityOfSwap(probabilityOfSwaps.getSelection());
				}
				MainGUI.modifyStateFlag();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});

		randomSwap.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				if (currentSelection != null && randomSwap.getSelection())
				{
					currentSelection.setTechnique(RandomizableRankedData.RANDOM_SWAP);
					setFixedSwapMode(false);
				}
				MainGUI.modifyStateFlag();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});

		symmetricSwap.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				if (currentSelection != null && symmetricSwap.getSelection())
				{
					if (currentSelection.getSize() == 9)
					{
						currentSelection.setTechnique(RandomizableRankedData.SYMMETRIC_SWAP);
						setFixedSwapMode(true);
					}
					else
					{
						ErrorDialog dialog = new ErrorDialog(getShell());
						setTechnique(currentSelection.getTechnique());
						dialog.open("A 3x3 symmetric swap requires a ranking with 9 elements.");
					}
				}
				MainGUI.modifyStateFlag();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});

		numberOfChildren.addSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				if (currentSelection != null)
				{
					if (fixedSwapMode)
					{
						currentSelection.setNumberOfRepeats(numberOfChildren.getSelection());
					}
					else
					{
						currentSelection.setNumberOfChildren(numberOfChildren.getSelection());
					}
				}
				MainGUI.modifyStateFlag();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});

		randomizedLengths.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				if (currentSelection != null)
				{
					currentSelection.setRandomziedLenghts(randomizedLengths.getSelection());
				}

				if (randomizedLengths.getSelection())
				{
					minRankings.setEnabled(true);
					maxRankings.setEnabled(true);
				}
				else
				{
					minRankings.setEnabled(false);
					maxRankings.setEnabled(false);
				}
				MainGUI.modifyStateFlag();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});

		maxRankings.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				minRankings.setMaximum(maxRankings.getSelection());
				currentSelection.setMaxPartialRankings(maxRankings.getSelection());
				MainGUI.modifyStateFlag();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});

		minRankings.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				maxRankings.setMinimum(minRankings.getSelection());
				currentSelection.setMinPartialRankings(minRankings.getSelection());
				MainGUI.modifyStateFlag();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{

			}
		});

		numberOfSwaps.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				if (currentSelection != null)
				{
					currentSelection.setNumberOfSwaps(numberOfSwaps.getSelection());
					MainGUI.modifyStateFlag();
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});

	}

	private void setFixedSwapMode(boolean mode)
	{
		// static swap mode is on, we make a fixed number of swaps so there is
		// no probability of swaps
		// or number of swaps.
		numberOfSwaps.setEnabled(!mode);
		probabilityOfSwaps.setEnabled(!mode);
		lblNumberOfChildren.setText((mode) ? "Number of repeats" : "Number of children");
		fixedSwapMode = mode;
		
		if (fixedSwapMode)
		{
			numberOfChildren.setSelection(currentSelection.getNumberOfRepeats());
		}
		else
		{
			numberOfChildren.setSelection(currentSelection.getNumberOfChildren());
		}
	}
	
	public static ArrayList<RankedData> getSupposedClusters()
	{
		
		ArrayList<RankedData> supposedClusters = new ArrayList<>();
		
		for (RandomizableRankedData rd : randomizableRankedData)
		{
			supposedClusters.add(rd.clone());
		}
		
		return supposedClusters;
	}
}
