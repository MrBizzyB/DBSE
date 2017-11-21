import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

// Index class implements file indexing operations
public class Index 
{
	// Rows in two-column file table
	static Object[] row = new Object[2];
	
	// Column reference constants
	final static int fileColumn = 0;
	final static int statusColumn = 1;
	// Index file
	
	static File indexFile = new File( "index.txt" );

	// Index data structure
	// Number of files indexed
	static int numFiles;
	
	// Pathnames of files 
	static ArrayList<String> fileNames = new ArrayList<>();
	
	// Last file modification time/date
	static List<Long> lastMod = new ArrayList<Long>();
	
	// List of words (word index)
	static ArrayList<String> wordIndex = new ArrayList<>();
	
	// Word count for each file
	static List<Integer> wordCount = new ArrayList<Integer>();
		
	public static void initIndex()
	{
		// Initialize file table and data structure 
		// If the index file exists...
		if ( indexFile.exists() )
		{
			readIndexFile();
			// Populate files table
			if (numFiles > 0)
			{
				// Loop through files in index
				for (int i = 0; i <= (numFiles - 1); ++i)
				{
					// Put the file name in the table
					row[fileColumn] = fileNames.get(i);
			    
					// Create a reference to the file 
					File file = new File(fileNames.get(i));
					
					// If the file still exists...
					if ( file.exists() )
					{
						// Check last modified date/time  of file
						long timeModified = (long)file.lastModified();
		
						// If the file has not changed...
						if (lastMod.get(i) == timeModified)
						{
							row[statusColumn] = "Indexed";
						} // If file unchanged
						
						else // The file changed
						{
							row[statusColumn] = "File changed since last indexed";
						} // Else the file changed
						
					} // If file exists
					else // The file no longer exists...
					{
						row[statusColumn] = "File no longer exists";
					}
					// Update table
					
					GUI.fileTableModel.addRow(row);
				} // For i
			} // If numFiles > 0
		} // If index file exists
		else // The index file does not exist
		{
			numFiles = 0;
			writeIndexFile();
		}
	} // initIndex	
	
	// Add a file
	public static void addFile()
	{
		// File chooser dialog box
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		File f = chooser.getSelectedFile();
	
		if (f != null) // If a file was selected...
		{
			String fileName = f.getAbsolutePath();
	
			// Update file table
			row[fileColumn] = fileName;
			
			row[statusColumn] = "Indexed";
			
			GUI.fileTableModel.addRow(row);

			// Increment number of files
			++numFiles;
	
			// Add file name to data structure
			fileNames.add(fileName);
	
			// Create a reference to the file 
			File file = new File(fileName);
		
			// Add last modified date/time of file to data structure
			lastMod.add( file.lastModified() );

			// Parse the file and add the words to the data structure
			wordCount.add( Utils.parseFile( file ) );
		
			// Write the updated data to the index file
			writeIndexFile();
		
		} // If file name not null
		else // Handle possibility of null (no file selected)
			JOptionPane.showMessageDialog( 
					null, 
					"You didn't select a file", 
					"ERROR - NO FILE SELECTED!", 
					JOptionPane.WARNING_MESSAGE );
	} // add File
	
	// Remove file
	public static void removeFile()
	{
		// Get row selected by user for deletion
		int rowToRemove =  GUI.fileTable.getSelectedRow();
		
		// If a row was not selected...
		if (rowToRemove < 0)
		{
			JOptionPane.showMessageDialog( 
				null, 
				"You didn't select a file", 
				"ERROR - NO FILE SELECTED!!!", 
				JOptionPane.WARNING_MESSAGE );
		} // If a row was not selected
		// If a row was selected...
		else
		{
			// Remove the row from the table
			GUI.fileTableModel.removeRow(rowToRemove);
	
			// Update data structure
			--numFiles;
			fileNames.remove( rowToRemove );
			lastMod.remove( rowToRemove );
			wordCount.remove( rowToRemove );

			updateIndex();
		} // Else a row was selected
	} // removeFile	
	
	// Updates the index file
	public static void updateIndex()
	{
		// Clear index so that it can be regenerated
		wordIndex.clear();
		
		// numFiles may change in loop, so assign to loop control variable
		int numFilesInit = numFiles;
		
		// Loop through files			
		for (int i = 0; i <= (numFilesInit - 1); ++i)
		{
			if (i > (numFiles - 1)) // If all files have been indexed already... 
			{
				// Break out of the loop				
				break;
			}
			
			// Create a reference to the file 
		   	File file = new File(fileNames.get(i));
    		if ( file.exists() )  // If the file still exists...
    		{
    			// Get its last modified time/date
    			long timeModified = (long)file.lastModified();
			
			   // Set the last modified parameter in the data structure
			   lastMod.set(i, timeModified);
						
			   // Set the word count for the file in the data structure
			   wordCount.set( i, Utils.parseFile( file ) );
			
			   // Update table
		       GUI.fileTableModel.setValueAt("Indexed", i, statusColumn);
    		} // If file exists
    		else // File no longer exists...
    		{
    			// Remove the file from the table
    			GUI.fileTableModel.removeRow(i);
    			
    			// Update data structure 
    			--numFiles;
    			fileNames.remove(i);
    			lastMod.remove(i);
    			wordCount.remove( i );
    			
    			// Make sure all files are indexed (recursive call)
    			updateIndex();
    		} // Else file no longer exists
		} // For i
		writeIndexFile();
	} // updateIndex 
	
	// Write the Index file
	public static void writeIndexFile()
	{
		PrintWriter outputFile;
		try 
		{
			// Get reference to file
			outputFile = new PrintWriter(indexFile);
			// Write number of files
			outputFile.println(numFiles);
			if (numFiles > 0) // If files have been indexed
			{
				/* Loop to write file names, last modification dates/times,
				 * and word counts */
				for (int i = 0; i <= (numFiles - 1); ++i) 
				{
					// Write file Name
					outputFile.println(fileNames.get(i));

					// Write last modification date/time
					outputFile.println(lastMod.get(i));
					
					// Write word count for file
					outputFile.println( wordCount.get( i ) );
				} // For i
				// Loop to write indexed words
				for (int i = 0; i <= (wordIndex.size() - 1); ++i)
				{
					// Write indexed words
					outputFile.println(wordIndex.get(i));
				} // For i
			} // If files have been indexed
			
			// Close the file
			outputFile.close();
		} // Try
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} // Catch
     } // writeIndexFile
	
	// Read the index file
	public static void readIndexFile()
	// Need to be able to handle corrupted file? - Doug Linkhart
	{
		try 
		{
			// Get reference to file
			Scanner inputFile = new Scanner(indexFile);
			// Input number of files
			numFiles = inputFile.nextInt();
			if (numFiles > 0) // If files have been indexed
			{
				/* Loop to read file names, last modification dates/times, 
				 * and word counts */
				for (int i = 1; i <= numFiles; ++i) 
				{
					// Read file Name
					fileNames.add(inputFile.next()); // Read file Name

					// Read last modification date/time
					lastMod.add(inputFile.nextLong()); 
					
					// Read word count for file
					wordCount.add( inputFile.nextInt() );
				} // For i
				while(inputFile.hasNext()) // While there are more words...
				{
					// Read indexed words
					wordIndex.add(inputFile.next()); 
				} // While
			} // If files have been indexed
					
			inputFile.close(); // Close the file
		} // Try
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} // Catch
	} // readIndexFile Method
}// Class Index 
