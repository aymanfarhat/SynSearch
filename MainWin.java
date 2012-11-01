/**
 * @author Ayman
 *
 */
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.JTextPane;
import java.awt.ComponentOrientation;


public class MainWin {

	private JFrame frmSynonymDictionaryWith;
	private JPanel header;
	private JLabel InputTitle;
	private JTextArea InputTextArea;
	private JScrollPane inputTextScrollPane;
	private JPanel bodyPanel;
	private JPanel DetectedWordsPanel;
	private JLabel DetectedWordsTitle;
	private JList DetectedWordsList;
	private DefaultListModel DetectedWordsList_model; 
	private JPanel DefinitionPanel;
	private JButton LoadTextButton;
	private JPanel results;
	private JLabel lblDd;
	public final JPanel ButtonsPanel = new JPanel();
	public final JButton ClearTextButton = new JButton("Clear All");
	private JTextPane synonymsTextPane;
	private JLabel lblWordsSimilarTo;
	private JTextPane simWordsTextPane;
	
	private Tree dictionary;
	private JScrollPane scrollPane;
	private JLabel lblDefinitionOfSelected;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWin window = new MainWin();
					window.frmSynonymDictionaryWith.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainWin() 
	{
		loadDictionary();
		initialize();
	}

	/* Load data from text file to the tree */
	private void loadDictionary()
	{
		dictionary = new Tree(null);

		/* Load the text file into an array of words and their synonyms, 
		 * we can specify the size of the array too */
		Word[] dicArr = Helper.getDictionary("dictionary.txt", 619);
			
		/* Load the sorted array to the bst using a method to insert values recusively as sub 
		 * arrays starting from middle to ensure that the bst is perfectly balanced */
		dictionary.setRoot(dictionary.SArraytoBST(dicArr, 0, dicArr.length-1));
	}
	
	/* Initialize the gui and set the action listeners */
	private void initialize() {
		frmSynonymDictionaryWith = new JFrame();
		frmSynonymDictionaryWith.setResizable(false);
		frmSynonymDictionaryWith.setTitle("SynSearch - Synonyms Dictionary using Binary Search Trees");
		frmSynonymDictionaryWith.setBounds(100, 100, 882, 585);
		frmSynonymDictionaryWith.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSynonymDictionaryWith.getContentPane().setLayout(new BorderLayout(0, 0));
		
		header = new JPanel();
		header.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		frmSynonymDictionaryWith.getContentPane().add(header, BorderLayout.NORTH);
		header.setLayout(new BorderLayout(0, 0));
		
		InputTitle = new JLabel("Text Input");
		InputTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		InputTitle.setHorizontalAlignment(SwingConstants.LEFT);
		header.add(InputTitle, BorderLayout.NORTH);
						
		InputTextArea = new JTextArea();
		InputTextArea.setTabSize(0);
		InputTextArea.setColumns(10);
		
		inputTextScrollPane = new JScrollPane(InputTextArea);
		header.add(inputTextScrollPane, BorderLayout.CENTER);
		
		InputTextArea.setRows(4);
		FlowLayout fl_ButtonsPanel = (FlowLayout) ButtonsPanel.getLayout();
		fl_ButtonsPanel.setAlignment(FlowLayout.RIGHT);
		
		header.add(ButtonsPanel, BorderLayout.SOUTH);
		
		ButtonsPanel.add(ClearTextButton);
		
		LoadTextButton = new JButton("Load Text From Text File...");
		ButtonsPanel.add(LoadTextButton);

		bodyPanel = new JPanel();
		frmSynonymDictionaryWith.getContentPane().add(bodyPanel, BorderLayout.CENTER);
		bodyPanel.setLayout(null);
		
		DetectedWordsPanel = new JPanel();
		DetectedWordsPanel.setBounds(0, 0, 511, 421);
		bodyPanel.add(DetectedWordsPanel);
		DetectedWordsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		DetectedWordsPanel.setLayout(new BorderLayout(0, 0));
		
		DetectedWordsTitle = new JLabel("Detected Words In Text");
		DetectedWordsTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		DetectedWordsPanel.add(DetectedWordsTitle, BorderLayout.NORTH);
		
		DetectedWordsList_model = new DefaultListModel();
		DetectedWordsList = new JList(DetectedWordsList_model);
		DetectedWordsList.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		DetectedWordsList.setLayoutOrientation(JList.VERTICAL_WRAP);
		DetectedWordsList.setVisibleRowCount(15);
		DetectedWordsList.setFont(new Font("Tahoma", Font.PLAIN, 14));
		DetectedWordsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DetectedWordsList.setFixedCellHeight(30);

		scrollPane = new JScrollPane(DetectedWordsList);
		DetectedWordsPanel.add(scrollPane, BorderLayout.CENTER);
		
		DefinitionPanel = new JPanel();
		DefinitionPanel.setBounds(511, 0, 365, 421);
		bodyPanel.add(DefinitionPanel);
		DefinitionPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		DefinitionPanel.setLayout(new BorderLayout(0, 0));
		
		lblDefinitionOfSelected = new JLabel("Results");
		lblDefinitionOfSelected.setFont(new Font("Tahoma", Font.BOLD, 12));
		DefinitionPanel.add(lblDefinitionOfSelected);
		
		results = new JPanel();
		results.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		DefinitionPanel.add(results);
		results.setLayout(null);
		
		lblDd = new JLabel("Synonyms For the selected word:");
		lblDd.setBounds(5, 2, 187, 14);
		lblDd.setFont(new Font("Tahoma", Font.BOLD, 11));
		results.add(lblDd);
		
		synonymsTextPane = new JTextPane();
		synonymsTextPane.setFont(new Font("Tahoma", Font.PLAIN, 13));
		synonymsTextPane.setForeground(Color.black);
		synonymsTextPane.setBounds(2, 16, 349, 143);
		synonymsTextPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		synonymsTextPane.setEditable(false);
		results.add(synonymsTextPane);
		
		lblWordsSimilarTo = new JLabel("Words Similar to seletected:");
		lblWordsSimilarTo.setBounds(8, 170, 159, 14);
		lblWordsSimilarTo.setFont(new Font("Tahoma", Font.BOLD, 11));
		results.add(lblWordsSimilarTo);
		
		simWordsTextPane = new JTextPane();
		simWordsTextPane.setFont(new Font("Tahoma", Font.PLAIN, 13));
		simWordsTextPane.setBounds(5, 184, 349, 143);
		simWordsTextPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		simWordsTextPane.setEditable(false);
		results.add(simWordsTextPane);
		
		/* Events */
		
		/* Detected words list mouse click event */
		DetectedWordsList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(DetectedWordsList.getSelectedValue() != null)
				{
					/* Check the tree and get the synonyms if the word exists  */
					String selected = DetectedWordsList.getSelectedValue().toString();
					
					/* The value of the word itself */
					Node word = dictionary.findWord(selected, dictionary.getRoot());
										
					if(word !=null)
					{
						/* Set the word synonyms */
						synonymsTextPane.setForeground(Color.black);
						synonymsTextPane.setText(Helper.arrayToString(word.getWord().getSynonyms(), ", "));
					}
					else 
					{
						synonymsTextPane.setForeground(Color.red);
						synonymsTextPane.setText("Word not found!" +
											" make sure that it isn't misspelled. Check the suggestions " +
											"below for possible corrections");
					}
					
					/* The value of the nearest parent of selected word */
					Node targetParent = dictionary.getNearestParent(selected, dictionary.getRoot(), null);
					
					String similar = "";
					if(targetParent != null)
					{
						similar = dictionary.getSimilarNodes(targetParent,selected);
						simWordsTextPane.setText(similar);
					}
				}
			}
		});
		
		/* Load text file button click event */
		LoadTextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser textFileDialog = new JFileChooser();
				textFileDialog.setFileFilter(new FileNameExtensionFilter("Text File","txt"));
				
				File inputFile;
				if(textFileDialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					inputFile = textFileDialog.getSelectedFile();
					String text = Helper.textFileToString(inputFile);
					
					InputTextArea.setText(text);
					
					detectWords();
				}
			}
		});
		
		/* Input text area text added(input changed event) */
		InputTextArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				detectWords();
			}
		});
		
		/* Clear text button event */
		ClearTextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			InputTextArea.setText("");
			DetectedWordsList_model.clear();
			synonymsTextPane.setText("");
			simWordsTextPane.setText("");
			}
		});
	}
	
	/* Extracts all words in input text, filters it and fills the detected words list */
	private void detectWords()
	{
		DetectedWordsList_model.clear();
		
		// Get text and filter it from punctuations
		String input = Helper.filterString(InputTextArea.getText());
		
		//Extract all words from text area using white space as delimiter
		String[] inputWords = input.split("\\s+");
		
		/* Add all extracted words to the clickable list */
		for(int i = 0; i < inputWords.length; i++)
		{
			if(inputWords[i].trim().length() > 0)
				DetectedWordsList_model.addElement(inputWords[i]);
		}
	}
}
