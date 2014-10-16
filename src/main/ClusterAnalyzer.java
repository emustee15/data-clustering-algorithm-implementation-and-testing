package main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import gui.MainGUI;

import computers.*;
/*
 * The meat and the bones of the entire project, this is where all of the analysis happens. 
 * This class first computes a lambda vector, and then computes the qVector, the cVector,
 * and finally the sigmaVector. If no clusters have moved in the sigmaVector, then the
 * analysis is over. Otherwise, the process is repeated until all of the cluster centers
 * have stabilized. 
 */
public class ClusterAnalyzer
{
	CComputer cComputer;
	LambdaComputer lComputer;
	QVectorComputer qComputer;
	SigmaComputer sComputer;

	int numberClusters;

	ArrayList<Double> lambdaVector;
	ArrayList<Double> cVector;
	ArrayList<RankedData> sigmaVector;
	ArrayList<RankedData> piVector;

	QVector qVector;

	String sigmaInformation;

	// The constructor initializes all of the vectors, except the piVector. 
	public ClusterAnalyzer(ArrayList<RankedData> piVector, int numberClusters, int masterRankings)
	{
		qVector = new QVector(numberClusters, piVector.size());
		lambdaVector = new ArrayList<Double>();
		cVector = new ArrayList<Double>();
		sigmaVector = new ArrayList<RankedData>();

		this.piVector = piVector;
		this.numberClusters = numberClusters;

		// Fill our sigma vector with generic values of (1,2,3,4,5) if 
		// randomization is not an option.
		int[] masterRanking = new int[masterRankings];
		int[] oppositeRanking = new int[masterRankings];
		for (int i = 1; i <= masterRankings; i++)
		{
			masterRanking[i - 1] = i;
		}

		for (int i = oppositeRanking.length; i >= 1; i--)
		{
			oppositeRanking[i - 1] = (masterRankings - i + 1);
		}

		// Fill our lambda and sigma vectors with default values
		if (!MainGUI.getInstance().getRandomizeSigma() && (numberClusters == 1 || numberClusters == 2))
		{
			if (numberClusters == 2)
			{
				for (int i = 0; i < numberClusters; i++)
				{
					lambdaVector.add(0d);
					cVector.add(1.0d / piVector.size());
				}
				sigmaVector.add(new RankedData(oppositeRanking.clone()));
				sigmaVector.add(new RankedData(masterRanking.clone()));
			}
			else if (numberClusters == 1)
			{
				lambdaVector.add(0d);
				cVector.add(1.0d / piVector.size());
				sigmaVector.add(new RankedData(masterRanking.clone()));
			}
		}
		else
		{
			for (int i = 0; i < numberClusters; i++)
			{
				lambdaVector.add(0d);
				sigmaVector.add(new RankedData(masterRanking.clone()));
				sigmaVector.get(i).randomize();
				cVector.add(1.0d / piVector.size());
			}
		}

		for (int i = 0; i < numberClusters; i++)
		{
			for (int j = 0; j < piVector.size(); j++)
			{
				qVector.set(i, j, 1.0d / numberClusters);
			}
		}

		lComputer = new LambdaComputer(sigmaVector, piVector, lambdaVector);
		qComputer = new QVectorComputer(sigmaVector, piVector, lambdaVector, cVector, numberClusters, qVector);
		sComputer = new SigmaComputer(piVector, sigmaVector, qVector);
		cComputer = new CComputer(qVector, piVector, cVector, numberClusters);
		
	}
	
	// This method records does the analysis and records the sigma history.
	public void doAnalyisis()
	{
		sigmaInformation = "";
		addSigmaInfo(0);
		int count = 0;
		boolean continueAnalysis = true;
		lComputer.computeLVector();
		while (continueAnalysis)
		{
			qComputer.computeQVector();
			cComputer.computeCVector();

			if (sComputer.computeSVector())
			{
				lComputer.computeLVector();
			}
			else
			{
				continueAnalysis = false;
			}
			addSigmaInfo(++count);

		}

	}

	// This method adds a bit of information to the sigma timeline. 
	private void addSigmaInfo(int step)
	{
		sigmaInformation += "Step " + step + ":\n";
		for (int index = 0; index < sigmaVector.size(); index++)
		{
			sigmaInformation += "\tσ\\~s" + index + "\\~s: " + sigmaVector.get(index).toString() + "\n";
		}

	}

	// This method return information on the sigma vectors, or the cluster centers. 
	public String getClusterCenterInformation()
	{
		String info = "";
		for (int index = 0; index < sigmaVector.size(); index++)
		{
			info += "Cluster Center σ\\~s" + index + "\\~s: ";
			info += sigmaVector.get(index).toString();
			info += '\n';
		}

		return info;
	}

	
	// This method returns information on the qVector, or the probability that a given partial ranking
	// belongs to some cluster center. 
	public String getQVectorInfo()
	{
		String info = "";

		DecimalFormat df = new DecimalFormat("#.####");
		for (int jRanking = 0; jRanking < piVector.size(); jRanking++)
		{
			info += "Partial Ranking π\\~s" + jRanking + "\\~s: " + piVector.get(jRanking).toString() + "\n";
			for (int iCluster = 0; iCluster < numberClusters; iCluster++)
			{
				info += "\tq(" + iCluster +", " + jRanking + ") = " + df.format(qVector.get(iCluster, jRanking)) + " for " + sigmaVector.get(iCluster).toString() + "\n";
			}
		}

		return info;
	}

	// This method gets the sigma timeline. 
	public String getSigmaOverTime()
	{
		return sigmaInformation;
	}
	
	// This method gets the cVector information, or the cluster weights
	public String getCVectorInfo()
	{
		String info = "";
		DecimalFormat df = new DecimalFormat("#.####");
		for (int cRanking = 0; cRanking < cVector.size(); cRanking++)
		{
			info += "c\\~s" + cRanking + "\\~s = " + df.format(cVector.get(cRanking)) +" for " + sigmaVector.get(cRanking).toString() + "\n";
			
		}

		
		return info;
	}
	
	// this method returns information on the lambda vector, or the
	// the standard deviation equivalence for each cluster center
	public String getLVectorInfo()
	{
		String info = "";
		DecimalFormat df = new DecimalFormat("#.####");
		for (int lRanking = 0; lRanking < cVector.size(); lRanking++)
		{
			info += "λ\\~s" + lRanking + "\\~s = " + df.format(lambdaVector.get(lRanking)) +" for " + sigmaVector.get(lRanking).toString() + "\n";
			
		}

		
		return info;
	}
}
