import java.util.ArrayList;
import java.util.Scanner;

// This class contains the doSearch method, which executes searches
public class SearchLogic{
	// Executes searches
	public static void doSearch()
	{
		/* Each word of each file is contained in the index file.
		 * The words are loaded into RAM as wordIndex.  
		 * numFiles is the number of files that have been indexed.
		 * fileNum is the numerical designation of each file.
		 * Each file has an associated word count, wordCount.
		 * Each word has a position in its file, which is posn.
		 * word, an integer, is the position of a word in wordIndex. */
		
		// searchTerms contains the string of search terms entered by the user
		Scanner searchTerms = new Scanner( GUI.txtSearchTerms.getText() );
	
		// Start to build a string to display as search results
		StringBuilder sbResults = new StringBuilder();
		sbResults.append( "You searched for:\r\n \r\n" );
		
		/* Separate the search terms into elements of an ArrayList so that 
		 * each one can be easily accessed */
		ArrayList<String> searchTerm = new ArrayList<>();
	
		// While there are still search terms...
		while(searchTerms.hasNext()) //
		{
			// Load the ArrayList with search terms
			searchTerm.add( searchTerms.next() );
		} // While
		
		// Loop through the search terms and add them to the results string
		for (int i = 0; i < searchTerm.size(); ++i )
		{
			sbResults.append( searchTerm.get( i ) + " " ); 
	
			// If OR and not last search term
			if ( GUI.orBtnSelected && ( i < ( searchTerm.size() - 1 ) ) )
				sbResults.append( "OR " );
					
			// If AND and not last search term
			if ( GUI.andBtnSelected && ( i < ( searchTerm.size() - 1 ) ) )
				sbResults.append( "AND " );
					
			// If PHRASE and last search term
			if ( GUI.phraseBtnSelected && !( i < ( searchTerm.size() - 1 ) ) )
				sbResults.append( "(PHRASE; terms in this order) " );
		} // For i
		
		sbResults.append( "\r\n\r\n" );
	
		// Close scanner
		searchTerms.close();
		
		boolean noResultsFound = true;
		int word;
	
		/* There are three search routines below.  One for OR, one for AND,
		 * and one for PHRASE */
		if ( GUI.orBtnSelected )
		{
			// OR search:
			// Loop through search terms
			for (int term = 0; term < searchTerm.size(); ++term)
			{
				word = -1;
				// Loop through the files
				for (int fileNum = 0; fileNum < Index.numFiles; ++fileNum)
				{
					// Loop through the words in each file
					for (int posn = 0; posn < Index.wordCount.get(fileNum); 
						 ++ posn)
					{
						++ word;
						// If the word matches the search term...
						if (Index.wordIndex.get(word).equals
							(searchTerm.get(term)))
						{
							// Unset flag, indicating that a match was found 
							noResultsFound = false;
							// Add the result to the results string
							sbResults.append("\"" + searchTerm.get(term) 
							                 + "\" was found in file " 
									         + Index.fileNames.get(fileNum) 
									         + " at position " + posn 
									         + "\r\n");
						} // If word matches search term
					} // For posn
				} // For fileNum
			} // For term
		} // If OR
		
		else if ( GUI.andBtnSelected )
		{
			// AND search:
			int wordLow = 0;
			boolean termFound;
			boolean allTermsFound = true;
			// Loop through files
			for (int fileNum = 0; fileNum < Index.numFiles; ++fileNum)
			{
				allTermsFound = true;
				// Loop through search terms
				for (int term = 0; term < searchTerm.size(); ++term)
				{
					termFound = false;
					word = wordLow - 1;
					// Loop through the words in each file
					for (int posn = 0; posn < Index.wordCount.get( fileNum ); 
						 ++ posn)
					{
						++ word;
						// If the word matches the search term...
						if (Index.wordIndex.get(word).equals
							(searchTerm.get(term)))
						{
							// Flag as search term found
							termFound = true;
						} // If matches
					} // for posn
					// If the search term was not found...
					if ( !termFound )
					{
						// Flag as not all search terms were found
						allTermsFound = false;
					} // If term not found
				} // for term
				// Set the word pointer to the beginning of the next file
				wordLow += Index.wordCount.get( fileNum );
				// If all of the search terms were found in a file...
				if ( allTermsFound )
				{
					// Add the result to the results string
					sbResults.append( "All search terms were found in file " 
					                  + Index.fileNames.get( fileNum ) 
					                  + "\r\n");
					// Unset flag, thus indicating that a match was found 
					noResultsFound = false;
				} // If allTermsFound
			} // for fileNum
		} // If AND
	
		else if ( GUI.phraseBtnSelected )
		{
			// PHRASE search:
			int wordLow = 0;
			
			boolean phraseFound;
			// Loop through files
			for (int fileNum = 0; fileNum < Index.numFiles; ++fileNum)
			{
				// First search term in phrase
				int term = 0;
				word = wordLow - 1;
				// Loop through the words in each file
				for (int posn = 0; posn < Index.wordCount.get(fileNum); 
					 ++posn)
				{
					++ word;
					// If the word matches the search term...
					if (Index.wordIndex.get(word).equals
						(searchTerm.get(term)))
					{
						phraseFound = true;
						// Loop through rest of search terms
						for (int i = 0; i < searchTerm.size(); ++i)
						{
							// If word pointer would be out of range...
							if ((word + searchTerm.size() - 1) 
								> (Index.wordIndex.size() - 1))
							{
								// The rest of terms would not be found
								phraseFound = false;
							} // If word pointer would be out of range 
							// Else if next term does not match next word...
							else if (!Index.wordIndex.get(word + i).equals
									 (searchTerm.get(i)))
							{
								// Flag as phrase not found
								phraseFound = false;
							} // If next term does not match next word
						} // For i
						// If the phase was found...
						if ( phraseFound )
						{
							// Add the result to the results string
							sbResults.append("\"" 
						                     + GUI.txtSearchTerms.getText() 
						                     + "\" was found in file " 
						                     + Index.fileNames.get(fileNum) 
						                     + " at position " + posn 
						                     + "\r\n");
							// Unset flag, indicating that a match was found 
							noResultsFound = false;
						} // If phrase found
					} // If word matches search term
				} // For posn
				// Set the word pointer to the beginning of the next file
				wordLow += Index.wordCount.get( fileNum );
			} // for fileNum
		} // If PHRASE
	
		// If no results were found...
		if ( noResultsFound )
		{
			// Add the result to the results string
			sbResults.append( "No results found." );
		}
		
		// Write the results string to the results text area
		GUI.txtResults.setText( sbResults.toString() );
	} // doSearch
} // Class SearchLogic
