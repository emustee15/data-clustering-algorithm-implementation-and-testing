package main;

import java.io.Serializable;
import java.util.ArrayList;

public class Settings implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private int numberClusters;
	private int completeRankings;
	private ArrayList<RankedData> randomizedRankings;
	private ArrayList<RandomizableRankedData> randomizableRankings;
	private String clusterCenterInfo, qVectorInfo, lVectorInfo, cVectorInfo,sigmaTimelineInfo, cumululitiveText;
	public String getCumululitiveText()
	{
		return cumululitiveText;
	}
	public void setCumululitiveText(String cumululitiveText)
	{
		this.cumululitiveText = cumululitiveText;
	}
	public int getNumberRuns()
	{
		return numberRuns;
	}
	public void setNumberRuns(int numberRuns)
	{
		this.numberRuns = numberRuns;
	}
	private int minClusters, minRankings;
	private ArrayList<RankedData> piVector;
	private String[] descriptions;
	private int numberRuns;
	
	public String[] getDescriptions()
	{
		return descriptions;
	}
	public void setDescriptions(String[] descriptions)
	{
		this.descriptions = descriptions;
	}
	public String getClusterCenterInfo()
	{
		return clusterCenterInfo;
	}
	public void setClusterCenterInfo(String clusterCenterInfo)
	{
		this.clusterCenterInfo = clusterCenterInfo;
	}
	public String getqVectorInfo()
	{
		return qVectorInfo;
	}
	public void setqVectorInfo(String qVectorInfo)
	{
		this.qVectorInfo = qVectorInfo;
	}
	public String getlVectorInfo()
	{
		return lVectorInfo;
	}
	public void setlVectorInfo(String lVectorInfo)
	{
		this.lVectorInfo = lVectorInfo;
	}
	public String getcVectorInfo()
	{
		return cVectorInfo;
	}
	public void setcVectorInfo(String cVectorInfo)
	{
		this.cVectorInfo = cVectorInfo;
	}
	public String getSigmaTimelineInfo()
	{
		return sigmaTimelineInfo;
	}
	public void setSigmaTimelineInfo(String sigmaTimelineInfo)
	{
		this.sigmaTimelineInfo = sigmaTimelineInfo;
	}
	public int getMinClusters()
	{
		return minClusters;
	}
	public void setMinClusters(int minClusters)
	{
		this.minClusters = minClusters;
	}
	public int getMinRankings()
	{
		return minRankings;
	}
	public void setMinRankings(int minRankings)
	{
		this.minRankings = minRankings;
	}
	public int getNumberClusters()
	{
		return numberClusters;
	}
	public void setNumberClusters(int numberClusters)
	{
		this.numberClusters = numberClusters;
	}
	public int getCompleteRankings()
	{
		return completeRankings;
	}
	public void setCompleteRankings(int completeRankings)
	{
		this.completeRankings = completeRankings;
	}
	public ArrayList<RankedData> getRandomizedRankings()
	{
		return randomizedRankings;
	}
	public void setRandomizedRankings(ArrayList<RankedData> rankings)
	{
		this.randomizedRankings = rankings;
	}
	public ArrayList<RandomizableRankedData> getRandomizableRankings()
	{
		return randomizableRankings;
	}
	public void setRandomizableRankings(ArrayList<RandomizableRankedData> randomizableRankings)
	{
		this.randomizableRankings = randomizableRankings;
	}
	public Settings(int numberClusters, int completeRankings, ArrayList<RankedData> rankings, ArrayList<RandomizableRankedData> randomizableRankings)
	{
		this.numberClusters = numberClusters;
		this.completeRankings = completeRankings;
		this.randomizedRankings = rankings;
		this.randomizableRankings = randomizableRankings;
	}
	public ArrayList<RankedData> getPiVector()
	{
		return piVector;
	}
	public void setPiVector(ArrayList<RankedData> piVector)
	{
		this.piVector = piVector;
	}
	
	
}
