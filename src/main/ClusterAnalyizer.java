package main;

import java.text.DecimalFormat;
import java.util.ArrayList;

import computers.*;

public class ClusterAnalyizer
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

	public ClusterAnalyizer(ArrayList<RankedData> piVector, int numberClusters,
			int masterRankings)
	{
		qVector = new QVector(numberClusters, piVector.size());
		lambdaVector = new ArrayList<Double>();
		cVector = new ArrayList<Double>();
		sigmaVector = new ArrayList<RankedData>();

		this.piVector = piVector;
		this.numberClusters = numberClusters;

		// Fill our sigma vector with generic values of (1,2,3,4,5).
		int[] masterRanking = new int[masterRankings];
		int[] oppositeRanking = new int[masterRankings];
		for (int i = 1; i <= masterRankings; i++)
		{
			masterRanking[i - 1] = i;
		}

		for (int i = oppositeRanking.length; i >= 1; i--)
		{
			oppositeRanking[i - 1] = (masterRankings-i+1);
		}

		// Fill our lambda and sigma vectors with default values

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
		else
		{
			for (int i = 0; i < numberClusters; i++)
			{
				lambdaVector.add(0d);
				sigmaVector.add(new RankedData(masterRanking.clone()));
				sigmaVector.get(i).randomize();
				sigmaVector.get(i).print();
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
		qComputer = new QVectorComputer(sigmaVector, piVector, lambdaVector,
				cVector, numberClusters, qVector);
		sComputer = new SigmaComputer(piVector, sigmaVector, qVector);
		cComputer = new CComputer(qVector, piVector, cVector, numberClusters);
		lComputer.computeLVector();
	}

	public void doAnalyisis()
	{
		sigmaInformation = "";
		addSigmaInfo(0);
		int count = 0;
		boolean continueAnalysis = true;

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

	private void addSigmaInfo(int step)
	{
		sigmaInformation += "Step " + step + ":\n";
		for (int index = 0; index < sigmaVector.size(); index++)
		{
			sigmaInformation += "\ts" + index + ": "
					+ sigmaVector.get(index).toString() + "\n";
		}

	}

	public String getClusterCenterInformation()
	{
		String info = "";
		for (int index = 0; index < sigmaVector.size(); index++)
		{
			info += "Cluster Center " + index + ": ";
			info += sigmaVector.get(index).toString();
			info += '\n';
		}

		return info;
	}

	public String getQVectorInfo()
	{
		String info = "";

		// for (int iCluster = 0; iCluster < numberClusters; iCluster++)
		// {
		// info += "Cluster " + sigmaVector.get(iCluster).toString() +":\n";
		// for (int jRanking = 0; jRanking < piVector.size(); jRanking++)
		// {
		// info += "\t" + piVector.get(jRanking).toString() + ": " +
		// qVector.get(iCluster, jRanking) + "\n";
		// }
		// }

		DecimalFormat df = new DecimalFormat("#.####");
		for (int jRanking = 0; jRanking < piVector.size(); jRanking++)
		{
			info += "Partial Ranking " + jRanking + ": "
					+ piVector.get(jRanking).toString() + "\n";
			for (int iCluster = 0; iCluster < numberClusters; iCluster++)
			{
				info += "\t" + sigmaVector.get(iCluster).toString() + ": "
						+ df.format(qVector.get(iCluster, jRanking)) + "\n";
			}
		}

		return info;
	}

	public String getSigmaOverTime()
	{
		return sigmaInformation;
	}
}
