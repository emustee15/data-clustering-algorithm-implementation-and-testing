package tests;

import static org.junit.Assert.*;
import main.FileLoader;
import main.FileType;
import org.junit.Test;

public class FileLoadingTesting {

	//Test loading a standard (with commas) .txt file
	@Test
	public void load_txtFileTest1() 
	{
		FileLoader fileLoader = new FileLoader(true);
		fileLoader.loadFile("testFiles/test1.txt", FileType.RankedData);
	}

	//Test loading a standard (with spaces) .txt file
	@Test
	public void load_txtFileTest2() 
	{
		FileLoader fileLoader = new FileLoader(true);
		fileLoader.loadFile("testFiles/test2.txt", FileType.RankedData);
	}

	//Test loading a .csv file does not fail
	@Test
	public void load_csvFileTest() 
	{
		FileLoader fileLoader = new FileLoader(true);
		fileLoader.loadFile("testFiles/csvTest1.csv", FileType.RankedData);
	}

	//Test loading a .txt file with nonNumeric characters if expecting ranked data.
	//Will pass if it catches the numberFormatException.
	@Test
	public void loadNonNumericFileTest() 
	{
		FileLoader fileLoader = new FileLoader(true);
		fileLoader.loadFile("testFiles/badFile1.txt", FileType.RankedData);
	}
	
	//Test loading a .txt file with a zero in one of the pi vectors.
	//Will pass if it catches the ZeroInFileException.
	@Test
	public void loadZeroInFileTest() 
	{
		FileLoader fileLoader = new FileLoader(true);
		fileLoader.loadFile("testFiles/badFile2.txt", FileType.RankedData);
	}
	
	//Test loading a .txt file with duplicate integers in one of the pi vectors.
	//Will pass if it catches the DuplicateIntegerException.
	@Test
	public void loadDuplicateIntegerInFileTest() 
	{
		FileLoader fileLoader = new FileLoader(true);
		fileLoader.loadFile("testFiles/badFile3.txt", FileType.RankedData);
	}
	
	//Test loading an empty file.
	//Will pass if it catches the EmptyPiVectorException.
	@Test
	public void loadEmptyFileTest() 
	{
		FileLoader fileLoader = new FileLoader(true);
		fileLoader.loadFile("testFiles/emptyFile.txt", FileType.RankedData);
	}
	
	//Test loading a .rnkr save file
	//If there is an exception that has not been caught, this will fail
	@Test
	public void loadSaveFileTest() 
	{
		FileLoader fileLoader = new FileLoader(true);
		fileLoader.loadFile("testFiles/saveFile.rnkr", FileType.Settings);
	}
	
	//Test loading a .txt file with descriptive words for the ranked data
	@Test
	public void loadWordFileTest() 
	{
		FileLoader fileLoader = new FileLoader(true);
		fileLoader.loadFile("testFiles/wordFile.txt", FileType.WordList);
	}


}
