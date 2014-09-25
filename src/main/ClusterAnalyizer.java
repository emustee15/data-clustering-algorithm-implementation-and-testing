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
	
	
	public ClusterAnalyizer(ArrayList<RankedData> piVector, int numberClusters)
	{
		cComputer = new CComputer();
		lComputer = new LambdaComputer();
		qComputer = new QVectorComputer();
		sComputer = new SigmaComputer();
		
		lambdaVector = new ArrayList<Double>();
		cVector = new ArrayList<Double>();
		sigmaVector = new ArrayList<RankedData>();
		this.piVector = piVector;
		this.numberClusters = numberClusters;
	}
	
	public void doAnalyisis()
	{
		qComputer.computeQVector(sigmaVector, piVector, lambdaVector, cVector, numberClusters);
		qVector = qComputer.getQvector();
		
	}
}
