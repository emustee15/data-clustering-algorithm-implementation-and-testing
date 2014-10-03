package main;

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

		for (int i = 1; i <= masterRankings; i++)
		{
			masterRanking[i-1] = i;
		}

		// Fill our lambda and sigma vectors with default values
		for (int i = 0; i < numberClusters; i++)
		{
			lambdaVector.add(.5d);
			sigmaVector.add(new RankedData(masterRanking.clone()));
			//sigmaVector.get(i).randomize();
			sigmaVector.get(i).print();
			cVector.add(.5d);
		}



		lComputer = new LambdaComputer(sigmaVector, piVector, lambdaVector);
		qComputer = new QVectorComputer(sigmaVector, piVector, lambdaVector,
				cVector, numberClusters, qVector);
		sComputer = new SigmaComputer(piVector,sigmaVector,qVector);
		cComputer = new CComputer(qVector,piVector,cVector,numberClusters);
		lComputer.computeLVector();
	}

	public void doAnalyisis()
	{
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
			
		}
		
		
		System.out.println("-Final Sigma Vectors-");
		for (int i = 0; i < sigmaVector.size(); i++)
		{
			sigmaVector.get(i).print();
		}

	}
}
