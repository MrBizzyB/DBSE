import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Utils 
{
	// Parses the specified file and loads words into wordIndex
	// Returns word count
	public static Integer parseFile(File file)
	{
		Integer numWords = 0;
		try
		{
			Scanner addedFile = new Scanner(file);
			while(addedFile.hasNext())
			{
				// Read and index words
				Index.wordIndex.add(addedFile.next());
				++ numWords; 
			} // While
				
			// Close the file
			addedFile.close();
		} // Try
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} // Catch
		return numWords;
	} // parseFile
} // Class Utils
