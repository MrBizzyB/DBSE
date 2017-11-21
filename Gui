import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class GUI extends JPanel 
{
	//Eclipse whines if this line isn't here...
	private static final long serialVersionUID = 1L;
		
    // These need to be accessible to other classes
	// Initialize radio button status
	public static boolean orBtnSelected     = true,
			              andBtnSelected    = false,
			              phraseBtnSelected = false;	

	public static JTextArea txtResults = new JTextArea(25, 70);
	
	public static JTextField txtSearchTerms = new JTextField( "Enter search terms here", 40 );
	
	public static DefaultTableModel fileTableModel = new DefaultTableModel();
	
	public static JTable fileTable = new JTable();
		
	public GUI()
	{
		//used to set tabs to top left
		super (new GridLayout(1,1));
			
		// Build tabbed pane
		JTabbedPane tabbedPane = new JTabbedPane();
		//new TheHandler object for buttons
		theHandler handler = new theHandler();
			
		// Add Search panel
		JComponent searchPanel = textPanel( "" );
		// Add Search tab
		tabbedPane.addTab("Search", searchPanel);
				
		// Add border to Search Term text box
		txtSearchTerms.setBorder(BorderFactory.createLineBorder(Color.black));
		// Add Search Terms text box to panel
		searchPanel.add((Component) txtSearchTerms);
						
		// Create buttons for search panel
		JButton btnSearch = new JButton( "Search" );
		btnSearch.setToolTipText("Click to search indexed files");
		btnSearch.setActionCommand( "search" ); 
	    btnSearch.addActionListener( handler );
		    //OR, AND, PHRASE wording
	    JRadioButton btnOr = new JRadioButton( "OR" );
	    JRadioButton btnAnd = new JRadioButton( "AND" );
	    JRadioButton btnPhrase = new JRadioButton( "Phrase" );
	    btnOr.setActionCommand( "or" ); 
	    btnOr.addActionListener( handler );
	    btnAnd.setActionCommand( "and" ); 
	    btnAnd.addActionListener( handler );
	    btnPhrase.setActionCommand( "phrase" ); 
	    btnPhrase.addActionListener( handler );
	 
	    // Group the radio buttons
	    ButtonGroup group = new ButtonGroup();
	    group.add( btnOr );
	    group.add( btnAnd );
	    group.add( btnPhrase ); 
	 
	    // Add buttons to Search panel
	    searchPanel.add( btnSearch );
		searchPanel.add( btnOr );
		searchPanel.add( btnAnd );
		searchPanel.add( btnPhrase );
					
		// Set buttons to according to status that was initialized previously
		btnOr.setSelected( orBtnSelected );
		btnAnd.setSelected( andBtnSelected );
		btnPhrase.setSelected( phraseBtnSelected );
			
		// Add result text area to show matched files when search is completed
		// Set line wrapping
		txtResults.setLineWrap(true);
		txtResults.setWrapStyleWord(true);
		// Add border to Results box
		txtResults.setBorder(BorderFactory.createLineBorder(Color.black));
		// Add Results box to panel
		searchPanel.add((Component) txtResults);
			
		// Add File Upload/Update panel
		JComponent files = textPanel("");
		// Add Files tab 
		tabbedPane.addTab("Files", files);
			
		String[] columnNames = {"File", "Status"};
		fileTableModel.setColumnIdentifiers(columnNames);
		fileTable.setModel(fileTableModel);
		fileTable.setPreferredScrollableViewportSize(new Dimension(740,360));
		fileTable.setFillsViewportHeight(true);
		JScrollPane jps = new JScrollPane(fileTable);
			
		// Add jtable to file tab
		files.add(jps);
			
		//create buttons for file tab
		JButton btnAddFile = new JButton("Add File");
		btnAddFile.setToolTipText("Open browse window to select and add a file");
		btnAddFile.setActionCommand("addFile");
		btnAddFile.addActionListener(handler);
		
			//remove button
		JButton btnRmvFile = new JButton("Remove File");
		btnRmvFile.setToolTipText("Remove selected file");
		btnRmvFile.setActionCommand("rmvFile");
		btnRmvFile.addActionListener(handler);
		
			//update button
		JButton btnUpdateFiles = new JButton("Update Index");
		btnUpdateFiles.setToolTipText("Update the index if they have been modified");
		btnUpdateFiles.setActionCommand("updateIndex");
		btnUpdateFiles.addActionListener(handler);
			
		// Add buttons to file tab
		files.add(btnAddFile);
		files.add(btnRmvFile);
		files.add(btnUpdateFiles);
				
		// Build string for About tab using HTML
		StringBuilder sbAbout = new StringBuilder();
		sbAbout.append( "<html>" );
		sbAbout.append( "<font face='Times New Roman'>" );
		sbAbout.append( "<font size='+2'>");
		sbAbout.append( "<br> Database Search Engine  <br>");
		sbAbout.append( "<br> Capstone Project  <br>");
		sbAbout.append( "<font size='+1'>");
		sbAbout.append( "<i> Brandon Quijano <br> </i> </font>");
		sbAbout.append( "<font face='Arial'>" );
		sbAbout.append( "<font size='-1'>");
		sbAbout.append( "</font>");
		sbAbout.append( "</html>" );
			
		// Instantiate About panel and add string
		JComponent aboutPanel = textPanel(sbAbout.toString());
		
		// Add tab and About panel
		tabbedPane.addTab("About", aboutPanel);
			
		add(tabbedPane);	
		
		// Initialize the word index in memory
		Index.initIndex();
	} // GUI
		
	// Event handler
	private class theHandler implements ActionListener
	{
		// Event handler
		public void actionPerformed(ActionEvent e) 
		{
			if (e.getActionCommand().equals("search")) 
			{
				SearchLogic.doSearch();
			} // If search
			
			else if (e.getActionCommand().equals("or"))
			{
				orBtnSelected     = true;
				andBtnSelected    = false;
				phraseBtnSelected = false;
			} // If OR
				
			else if (e.getActionCommand().equals("and"))
			{
				orBtnSelected     = false;
				andBtnSelected    = true;
				phraseBtnSelected = false;
			} // If AND
				
			else if (e.getActionCommand().equals("phrase"))
			{
				orBtnSelected     = false;
				andBtnSelected    = false;
				phraseBtnSelected = true;
			} // If PHRASE
				
			else if (e.getActionCommand().equals("addFile"))
			{
				Index.addFile();
			} // If Add File
				
			else if (e.getActionCommand().equals("rmvFile"))
			{
				Index.removeFile();
			} // If Remove File
				
			else if (e.getActionCommand().equals("updateIndex"))
			{
				Index.updateIndex();
			} // If Update Index
		} // actionPerformed
	} // Class theHandler
	
	protected JComponent textPanel(String text)
	{
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.add(filler);
		return panel; 
	} // textPanel
		
	// Build main container
	static void createAndShowGUI() 
	{
		JFrame frame = new JFrame("Database Search Engine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Add tabbed pane to the JFrame
		frame.add(new GUI(), BorderLayout.CENTER);
		frame.setSize( 800, 500);
		frame.setLocationRelativeTo( null ); // Center frame on screen
		frame.setVisible( true );
		// Don't let user resize window to keep GUI formatting constant
		frame.setResizable(false);  
	} // creatAndShowGUI
} // Class GUI
