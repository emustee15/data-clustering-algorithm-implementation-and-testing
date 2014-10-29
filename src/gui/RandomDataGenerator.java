package gui;

import java.util.ArrayList;

import main.RandomizableRankedData;
import main.RankedData;

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

public class RandomDataGenerator extends Shell
{
	private static RandomDataGenerator instance;
	private ArrayList<RandomizableRankedData> randomizableRankedData;
	private Button randomizedLengths;
	private Spinner numberOfSwaps;
	private Spinner probabilityOfSwaps;
	private Spinner numberOfChildren;
	private Spinner minRankings, maxRankings;
	private RandomizableRankedData currentSelection;
	private ArrayList<RankedData> piVector;
	private Text piVectorText;

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
		randomizableRankedData = new ArrayList<>();

		instance = this;
		setMinimumSize(new Point(640, 480));
		setLayout(new GridLayout(2, false));

		Composite composite_2 = new Composite(this, SWT.NONE);
		GridData gd_composite_2 = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd_composite_2.widthHint = 266;
		composite_2.setLayoutData(gd_composite_2);
		composite_2.setLayout(new GridLayout(1, false));

		List clusterCenters = new List(composite_2, SWT.BORDER);
		clusterCenters.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		

		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
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

		Label lblNewLabel = new Label(grpSwappingSettings, SWT.NONE);
		lblNewLabel.setText("Max number of swaps");

		numberOfSwaps = new Spinner(grpSwappingSettings, SWT.BORDER);
		numberOfSwaps.setSelection(2);

		Label lblProbabilityOfSwap = new Label(grpSwappingSettings, SWT.NONE);
		lblProbabilityOfSwap.setText("Probability of swap");

		probabilityOfSwaps = new Spinner(grpSwappingSettings, SWT.BORDER);
		probabilityOfSwaps.setSelection(100);
		probabilityOfSwaps.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				if (currentSelection != null)
				{
					currentSelection.setProbabilityOfSwap(probabilityOfSwaps.getSelection());
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

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
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});

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
		minRankings.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				maxRankings.setMinimum(minRankings.getSelection());
				currentSelection.setMinPartialRankings(minRankings.getSelection());

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{

			}
		});
		Label lblMaxNumberOf = new Label(grpNumberOfPartial, SWT.NONE);
		lblMaxNumberOf.setText("Max number of partial rankings");

		maxRankings = new Spinner(grpNumberOfPartial, SWT.BORDER);
		maxRankings.setMinimum(1);
		maxRankings.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				minRankings.setMaximum(maxRankings.getSelection());
				currentSelection.setMaxPartialRankings(maxRankings.getSelection());

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

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		Label lblNumberOfChildren = new Label(composite, SWT.NONE);
		lblNumberOfChildren.setText("Number of children");

		numberOfChildren = new Spinner(composite, SWT.BORDER);
		numberOfChildren.setMaximum(20000);
		numberOfChildren.setMinimum(1);
		numberOfChildren.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				if (currentSelection != null)
				{
					currentSelection.setNumberOfChildren(numberOfChildren.getSelection());
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});

		TabItem tbtmGeneratedPiVector = new TabItem(tabFolder, SWT.NONE);
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
		Button btnGenerateData = new Button(composite_1, SWT.NONE);
		btnGenerateData.setText("Generate Data");
		btnGenerateData.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				doRandomization();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});

		Button btnAnalyzeButton = new Button(composite_1, SWT.NONE);
		btnAnalyzeButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnAnalyzeButton.setText("Analyze Data");
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

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});
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

		noSelection();
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

		randomizedLengths.setSelection(currentSelection.isRandomziedLenghts());
		numberOfSwaps.setSelection(currentSelection.getNumberOfSwaps());
		probabilityOfSwaps.setSelection(currentSelection.getProbabilityOfSwap());
		numberOfChildren.setSelection(currentSelection.getNumberOfChildren());
		maxRankings.setMinimum(1);
		maxRankings.setMaximum(currentSelection.getSize());
		maxRankings.setSelection(currentSelection.getMaxPartialRankings());

		minRankings.setMaximum(maxRankings.getSelection());
		minRankings.setSelection(currentSelection.getMinPartialRankings());
		maxRankings.setMinimum(minRankings.getSelection());

		minRankings.setEnabled(randomizedLengths.getSelection());
		maxRankings.setEnabled(randomizedLengths.getSelection());
	}

	public void noSelection()
	{
		randomizedLengths.setEnabled(false);
		numberOfSwaps.setEnabled(false);
		probabilityOfSwaps.setEnabled(false);
		numberOfChildren.setEnabled(false);
		minRankings.setEnabled(false);
		maxRankings.setEnabled(false);
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
			for (int index = 0; index < data.getNumberOfChildren(); index++)
			{
				RankedData newData = data.nextRankedData();
				piVector.add(newData);
				piVectorString += newData.toString() + '\n';
			}
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

	}

	public ArrayList<RankedData> getPiVector()
	{
		return piVector;
	}
}
