package main;

// This lists the types of files we may load with the file loader.
public enum FileType
{
	/*
	 * RankedData - a list of integers separated by commas or spaces representing pi vectors
	 * WordList - a list of words that provide meaning to the numbers in ranked data
	 * Settings - represents a previous state that can be loaded in to start the program with the saved settings 
	 */
	RankedData, WordList, Settings, ExportedResults
}
