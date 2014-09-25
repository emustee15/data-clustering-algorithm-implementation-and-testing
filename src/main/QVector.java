package main;

public class QVector
{
	double[][] qVector;
	
	public QVector(int numberOfClusters, int numberOfRankings)
	{
		qVector = new double[numberOfClusters][numberOfRankings];
	}
	public void set(int cluster, int ranking, double value)
	{
		qVector[cluster][ranking] = value;
	}
	
	public double get(int cluster, int ranking)
	{
		return qVector[cluster][ranking];
	}
}
