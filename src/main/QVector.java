package main;

public class QVector
{
	// The qVector is simply a nice wrapper for a 2d array that allows us to see
	// if we are using accessing a cluster or ranking. 
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
