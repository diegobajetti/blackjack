import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class Blackjack extends JFrame implements ActionListener, KeyListener {

	/**
	 * NAME: Diego Bajetti
	 * DATE: Thursday, January 16, 2020
	 * COURSE CODE: ICS4U
	 * DESCRIPTION: This program models a Blackjack game that's usually played
	 * between one or more players and a dealer. The objective of the game is to get
	 * the highest amount of points without exceeding 21. All cards from two through
	 * ten have a value that corresponds to their pip value (a three of diamonds has
	 * a value of 3); all face cards (Jack, Queen, and King) have a value of 10; and
	 * Aces can have a value of 1 or 11. At the start of the game, the player and
	 * the dealer receive two cards; however, the dealer has one card facing down
	 * and the other card facing up. The player has the option to hit (receive one
	 * more card) or stand (end the turn). After the player’s turn has ended, the
	 * dealer flips its card and must hit until its hand totals 17 or more points.
	 * If either the player or the dealer exceeds 21 points, they have “busted.”
	 * Players win by not busting and having a total higher than the dealer, not
	 * busting and having the dealer bust, or getting Blackjack without the dealer
	 * getting Blackjack (hand totals 21 points). This program has three classes:
	 * the main class, a card class and a deck class. The card class creates a card
	 * object which holds two integer values: the card suit (Diamonds), and the card
	 * rank (Jack). The deck class creates a deck composed of 52 card objects. All
	 * of these 52 cards have different suits and ranks, just like a real deck. In
	 * specific, each deck has 13 cards (Ace–King) and 4 suits (Clubs–Spades). The
	 * deck class shuffles the deck and draws cards from the deck. The card class
	 * can return the value of the card and return the card as a string. The main
	 * class displays the game and records the permanent information of each user in
	 * a text file, while also updating the information.
	 */
	
	// Declares JComponents used for frame's layout
	private JPanel pnlWelcome, pnlMain, pnlGame, pnlDealerHand, pnlPlayerHand;
	private JButton btnStart, btnPlay, btnLeave, btnHit, btnStand, btnContinue;
	private JLabel lblLifetimeEarnings, lblBankBalance, lblEarnings, lblMoneyRiding, lblHandValue, lblWonLost;
	private JLabel[] lblDealerCard, lblPlayerCard;
	private JTextField txtName, txtBet;
	private JTextArea txtA;

	// Declares and initializes various objects used to set properties of JComponents
	private Font f1 = new Font("Courier New", Font.PLAIN, 19);
	private Font f2 = new Font("Courier New", Font.PLAIN, 13);
	private Font f3 = new Font("Courier New", Font.PLAIN, 17);
	private FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 0, 0);
	private LineBorder border = new LineBorder(Color.black, 1);
	private Dimension d = new Dimension(260, 20);

	// Declares and initializes DecimalFormat object used to format of integer values
	private DecimalFormat df = new DecimalFormat("$#,###,###");

	public Blackjack() {
		// Declares, initializes and sets properties of JComponents for pnlWelcome
		JLabel lblCasino = new JLabel("Casino");
		lblCasino.setFont(f1);
		lblCasino.setHorizontalAlignment(JLabel.CENTER);
		lblCasino.setBorder(border);
		lblCasino.setPreferredSize(new Dimension(320, 20));
		
		txtA = new JTextArea(/*5, 26*/);
		txtA.setFont(f3);
		txtA.setBorder(border);
		txtA.setPreferredSize(new Dimension(260, 100));
		txtA.setEditable(false);
		
		readFile();
		
		JLabel lblEnterName = new JLabel("Please enter your name");
		lblEnterName.setFont(f3);
		lblEnterName.setHorizontalAlignment(JLabel.CENTER);
		lblEnterName.setBorder(border);
		lblEnterName.setPreferredSize(d);
		
		txtName = new JTextField();
		txtName.setFont(f3);
		txtName.setHorizontalAlignment(JTextField.CENTER);
		txtName.setBorder(border);
		txtName.setPreferredSize(d);
		txtName.addKeyListener(this);
		
		btnStart = new JButton("Start");
		btnStart.setFont(f3);
		btnStart.setHorizontalAlignment(JButton.CENTER);
		btnStart.setPreferredSize(d);
		btnStart.addActionListener(this);
		
		// Initializes JPanel object, sets layout and adds JComponents
		pnlWelcome = new JPanel();
		pnlWelcome.setLayout(layout);
		pnlWelcome.add(Box.createRigidArea(d));
		pnlWelcome.add(lblCasino);
		pnlWelcome.add(Box.createRigidArea(d));
		pnlWelcome.add(txtA);
		pnlWelcome.add(Box.createRigidArea(d));
		pnlWelcome.add(lblEnterName);
		pnlWelcome.add(txtName);
		pnlWelcome.add(Box.createRigidArea(d));
		pnlWelcome.add(btnStart);
		
		// Declares, initializes and sets properties of JComponents for pnlMain
		JLabel lblTitle = new JLabel("Binion’s Horseshoe Casino");
		lblTitle.setFont(f1);
		lblTitle.setHorizontalAlignment(JLabel.CENTER);
		lblTitle.setBorder(border);
		lblTitle.setPreferredSize(new Dimension(320, 20));

		lblLifetimeEarnings = new JLabel("<html><div style='text-align: center;'>You have lifetime casino<br>earnings of "
						+ df.format(lifetimeEarnings) + ".</html>");
		lblLifetimeEarnings.setFont(f2);
		lblLifetimeEarnings.setHorizontalAlignment(JLabel.CENTER);
		lblLifetimeEarnings.setBorder(border);
		lblLifetimeEarnings.setPreferredSize(new Dimension(260, 35));

		JLabel lblPlaceBets = new JLabel("Place your Blackjack bets!");
		lblPlaceBets.setFont(f2);
		lblPlaceBets.setHorizontalAlignment(JLabel.CENTER);
		lblPlaceBets.setBorder(border);
		lblPlaceBets.setPreferredSize(d);

		lblBankBalance = new JLabel("<html><b>Bank Balance</b>: " + df.format(bankBalance) + "</html>");
		lblBankBalance.setFont(f2);
		lblBankBalance.setHorizontalAlignment(JLabel.LEFT);
		lblBankBalance.setBorder(border);
		lblBankBalance.setPreferredSize(d);

		lblEarnings = new JLabel("<html><b>Session Earnings</b>: " + df.format(sessionEarnings) + "</html>");
		lblEarnings.setFont(f2);
		lblEarnings.setHorizontalAlignment(JLabel.LEFT);
		lblEarnings.setBorder(border);
		lblEarnings.setPreferredSize(d);

		JLabel lblBet = new JLabel("<html><b>Pick your bet:</b></html>");
		lblBet.setFont(f3);
		lblBet.setHorizontalAlignment(JLabel.LEFT);
		lblBet.setBorder(border);
		lblBet.setPreferredSize(d);

		txtBet = new JTextField();
		txtBet.setFont(f3);
		txtBet.setBorder(border);
		txtBet.setPreferredSize(d);
		txtBet.addKeyListener(this);

		btnPlay = new JButton("Deal them!");
		btnPlay.setFont(f3);
		btnPlay.setPreferredSize(d);
		btnPlay.addActionListener(this);

		btnLeave = new JButton("Leave the casino");
		btnLeave.setFont(f3);
		btnLeave.setPreferredSize(d);
		btnLeave.addActionListener(this);

		// Initializes JPanel object, sets layout and adds JComponents
		pnlMain = new JPanel();
		pnlMain.setLayout(layout);
		pnlMain.add(Box.createRigidArea(d));
		pnlMain.add(lblTitle);
		pnlMain.add(Box.createRigidArea(d));
		pnlMain.add(lblLifetimeEarnings);
		pnlMain.add(Box.createRigidArea(new Dimension(260, 5)));
		pnlMain.add(lblPlaceBets);
		pnlMain.add(Box.createRigidArea(d));
		pnlMain.add(lblBankBalance);
		pnlMain.add(lblEarnings);
		pnlMain.add(Box.createRigidArea(d));
		pnlMain.add(lblBet);
		pnlMain.add(txtBet);
		pnlMain.add(Box.createRigidArea(new Dimension(260, 18)));
		pnlMain.add(btnPlay);
		pnlMain.add(Box.createRigidArea(new Dimension(260, 5)));
		pnlMain.add(btnLeave);

		// Declares, initializes and sets properties of JComponents for pnlGame
		JLabel lblDealer = new JLabel("Samantha Rea (Dealer)");
		lblDealer.setFont(f1);
		lblDealer.setHorizontalAlignment(JLabel.CENTER);
		lblDealer.setBorder(border);
		lblDealer.setPreferredSize(new Dimension(320, 20));

		JLabel lblGet21 = new JLabel("<html><div style='text-align: center;'>Get as close as possible to<br>21 without going over!</html>");
		lblGet21.setFont(f2);
		lblGet21.setHorizontalAlignment(JLabel.CENTER);
		lblGet21.setBorder(border);
		lblGet21.setPreferredSize(new Dimension(260, 35));

		lblMoneyRiding = new JLabel();
		lblMoneyRiding.setFont(f2);
		lblMoneyRiding.setHorizontalAlignment(JLabel.CENTER);
		lblMoneyRiding.setBorder(border);
		lblMoneyRiding.setPreferredSize(new Dimension(260, 35));

		lblHandValue = new JLabel("<html><div style='text-align: center;'>Rea: " + dealerValue + "<br>You: " + playerValue);
		lblHandValue.setFont(f2);
		lblHandValue.setHorizontalAlignment(JLabel.CENTER);
		lblHandValue.setPreferredSize(new Dimension(320, 35));

		lblWonLost = new JLabel();
		lblWonLost.setFont(f3);
		lblWonLost.setHorizontalAlignment(JLabel.CENTER);
		lblWonLost.setBorder(border);
		lblWonLost.setPreferredSize(d);
		lblWonLost.setVisible(false);

		btnHit = new JButton("Hit me");
		btnHit.setFont(f3);
		btnHit.setPreferredSize(d);
		btnHit.addActionListener(this);

		btnStand = new JButton("I'll stand");
		btnStand.setFont(f3);
		btnStand.setPreferredSize(d);
		btnStand.addActionListener(this);

		btnContinue = new JButton("Continue");
		btnContinue.setFont(f3);
		btnContinue.setPreferredSize(d);
		btnContinue.addActionListener(this);
		btnContinue.setVisible(false);

		// Initializes JPanel object used for the dealer's hand and sets layout
		pnlDealerHand = new JPanel();
		pnlDealerHand.setLayout(layout);

		/**
		 * The JLabel arrays used for the dealer's and player's hand have a size of 10.
		 * Only the first two cards are visible, and as the dealer or player hits (draws
		 * card), the next location in the array is set to visible.
		 */
		
		lblDealerCard = new JLabel[10];
		
		for (int counter = 0; counter < 10; counter++) {
			lblDealerCard[counter] = new JLabel();
			lblDealerCard[counter].setFont(f3);
			lblDealerCard[counter].setHorizontalAlignment(JLabel.CENTER);
			lblDealerCard[counter].setBorder(border);
			lblDealerCard[counter].setPreferredSize(new Dimension(40, 60));

			if (counter > 1) {
				lblDealerCard[counter].setVisible(false);
			}

			pnlDealerHand.add(lblDealerCard[counter]);
			pnlDealerHand.add(Box.createRigidArea(new Dimension(1, 60)));
		}

		// Initializes JPanel object used for the player's hand and sets layout
		pnlPlayerHand = new JPanel();
		pnlPlayerHand.setLayout(layout);

		lblPlayerCard = new JLabel[10];

		for (int counter = 0; counter < 10; counter++) {
			lblPlayerCard[counter] = new JLabel();
			lblPlayerCard[counter].setFont(f3);
			lblPlayerCard[counter].setHorizontalAlignment(JLabel.CENTER);
			lblPlayerCard[counter].setBorder(border);
			lblPlayerCard[counter].setPreferredSize(new Dimension(40, 60));

			if (counter > 1) {
				lblPlayerCard[counter].setVisible(false);
			}

			pnlPlayerHand.add(lblPlayerCard[counter]);
			pnlPlayerHand.add(Box.createRigidArea(new Dimension(1, 60)));
		}

		// Initializes JPanel object, sets layout and adds JComponents and JPanels
		pnlGame = new JPanel();
		pnlGame.setLayout(layout);
		pnlGame.add(Box.createRigidArea(d));
		pnlGame.add(lblDealer);
		pnlGame.add(Box.createRigidArea(d));
		pnlGame.add(lblGet21);
		pnlGame.add(Box.createRigidArea(new Dimension(260, 5)));
		pnlGame.add(lblMoneyRiding);
		pnlGame.add(Box.createRigidArea(new Dimension(320, 20)));
		pnlGame.add(pnlDealerHand);
		pnlGame.add(lblHandValue);
		pnlGame.add(pnlPlayerHand);
		pnlGame.add(Box.createRigidArea(d));
		pnlGame.add(lblWonLost);
		pnlGame.add(btnHit);
		pnlGame.add(Box.createRigidArea(new Dimension(260, 5)));
		pnlGame.add(btnStand);
		pnlGame.add(btnContinue);

		// Sets properties of frame
		setContentPane(pnlWelcome);
		setTitle("BLACKJACK");
		setSize(320, /*182*/ 302);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	// Declares Deck and Card objects
	private Deck deck;
	private ArrayList<Card> playerHand, dealerHand;

	// Declares objects and variables used for writing and reading data files
	private File file = new File("Users.txt");

	private BufferedWriter out;
	private FileWriter writer;

	private BufferedReader in;
	private FileReader reader;

	private String name;
	private int lineCounter, lineNo;
	private boolean userExists;

	// Declares and initializes integer variables
	private int playerValue, dealerValue, bet, lifetimeEarnings, bankBalance, sessionEarnings;
	private int counter1 = 1;
	private int counter2 = 1;

	public void actionPerformed(ActionEvent e) {
		/* START */
		if (e.getSource() == btnStart) {
			/**
			 * The File object is used in order to store permanent information. In this
			 * case, the users and their corresponding lifetime earnings are stored in the
			 * data file "Users.txt", and every time the program is initiated, the file and
			 * its contents are accessed through input and output file streams. If the file
			 * doesn't exist prior to the launch of the program, meaning the program is
			 * being run for the first time, a new file is created. The user is prompted to
			 * input the name they wish to register under. If the user already exists, the
			 * lifetime earnings variable is updated to whatever value has been stored in
			 * the data file under said user. If the user doesn't already exist, the program
			 * writes the name and lifetime earnings to the output stream. The program also
			 * keeps track of how many lines there are in the data file (lineCounter) and
			 * the line at which the user is located (lineNo).
			 */
			
			// Initializes String variable name to the name inputted by the user
			name = txtName.getText();

			// Executes if file doesn't exist
			if (!file.exists()) {
				try {
					file.createNewFile();

					writer = new FileWriter(file, true);
					out = new BufferedWriter(writer);

					// Writes name and lifetime earnings to the output stream
					out.write(name + ": " + lifetimeEarnings);
					out.newLine();
					out.close();

					lineCounter++;
					lineNo++;
				}

				catch (IOException a) {
					a.printStackTrace();
				}
			}
			
			// Executes if file exists
			else if (file.exists()) {
				try {
					reader = new FileReader(file);
					in = new BufferedReader(reader);

					// Reads a line of text from the input stream and returns it as a String
					String line = in.readLine();

					// Executes as long as the end of the line has not been reached
					while (line != null) {
						lineCounter++;

						// Splits line around matches of the expression ": " thus initializing
						// data array locations with the name of the user and their lifetime earnings
						String[] data = line.split(": ");

						// Executes if name stored in file equals name inputted by the user
						if (data[0].equals(name)) {
							// Initializes lifetime earnings to value stored in file
							lifetimeEarnings = Integer.parseInt(data[1]);

							// Stores the line number at which the user is stored
							lineNo = lineCounter;

							userExists = true;
						}

						// Reads a line of text from the input stream and returns it as a String
						line = in.readLine();
					}
					
					// Closes input file stream object
					in.close();
					
					// Executes if user doesn't exist
					if (!userExists) {
						writer = new FileWriter(file, true);
						out = new BufferedWriter(writer);

						// Writes name and lifetime earnings to the output stream
						out.write(name + ": " + lifetimeEarnings);
						out.newLine();
						out.close();

						lineCounter++;
						lineNo = lineCounter;
					}
				}

				catch (IOException a) {
					a.printStackTrace();
				}
			}

			// Sets main panel visible
			pnlWelcome.setVisible(false);
			pnlMain.setVisible(true);
			pnlGame.setVisible(false);

			// Sets properties of frame
			setContentPane(pnlMain);
			setSize(320, 352);
			setLocationRelativeTo(null);

			// Sets text of JLabel objects
			lblLifetimeEarnings.setText("<html><div style='text-align: center;'>You have lifetime casino<br>earnings of "
							+ df.format(lifetimeEarnings) + ".</html>");
			lblBankBalance.setText("<html><b>Bank Balance</b>: " + df.format(bankBalance = 100000) + "</html>");
			txtBet.requestFocus();
		}

		/* PLAY */
		else if (e.getSource() == btnPlay) {
			// Declares boolean variable; if statements in a try block
			// don't throw an exception, boolean variable is set to true
			boolean isInt;

			// Try-catch statement that verifies
			// if data entered is of a valid form
			try {
				bet = Integer.parseInt(txtBet.getText());
				lblMoneyRiding.setText("<html><div style='text-align: center;'>You have " + df.format(bet)
						+ "<br>riding on this bet.</html>");
				isInt = true;
			}

			catch (NumberFormatException a) {
				JOptionPane.showMessageDialog(null, "Please enter a valid bet.", "", JOptionPane.PLAIN_MESSAGE);
				txtBet.requestFocus();
				txtBet.selectAll();
				isInt = false;
			}

			// Executes if bet is an integer, if bet is more than 
			// zero, and if bet is less than or equal to bank balance
			if ((isInt) && (bet > 0 && bet <= bankBalance)) {
				// Sets game panel visible
				pnlWelcome.setVisible(false);
				pnlMain.setVisible(false);
				pnlGame.setVisible(true);

				// Sets properties of frame
				setContentPane(pnlGame);
				setSize(320, 417);
				setLocationRelativeTo(null);
				
				btnHit.setVisible(true);
				btnHit.setEnabled(true);
				btnStand.setVisible(true);
				btnStand.setEnabled(true);
				btnContinue.setVisible(false);
				
				lblWonLost.setVisible(false);

				// Initializes Deck object and shuffles deck
				deck = new Deck();
				deck.shuffle();

				// Initializes ArrayList objects used to hold the player's hand and the dealer's hand
				playerHand = new ArrayList<Card>();
				dealerHand = new ArrayList<Card>();

				// Draws two cards for player and dealer
				playerHand.add(deck.drawCard());
				playerHand.add(deck.drawCard());
				dealerHand.add(deck.drawCard());
				dealerHand.add(deck.drawCard());
				
				// Displays player hand and dealer hand (in Blackjack, only one of the dealer's cards is visible to the player) 
				lblPlayerCard[0].setText("" + playerHand.get(0));
				lblPlayerCard[1].setText("" + playerHand.get(1));
				lblDealerCard[0].setText("" + dealerHand.get(0));
				
				for (int counter = 0; counter < playerHand.size(); counter++) {
					// Calculates value of player's hand and dealer's hand
					playerValue += playerHand.get(counter).getValue();
					playerValue = calcHandValue(playerHand, playerValue);
					dealerValue = dealerHand.get(0).getValue();
				}

				lblHandValue.setText("<html><div style='text-align: center;'>Rea: " + dealerValue + "<br>You: " + playerValue);
			}

			// Executes if bet is an integer, and if bet is less
			// than zero or if bet is more than bank balance
			else if ((isInt) && (bet < 0 || bet > bankBalance)) {
				JOptionPane.showMessageDialog(null, "Please enter a valid bet.", "", JOptionPane.PLAIN_MESSAGE);
			}
		}

		/* LEAVE */
		else if (e.getSource() == btnLeave) {
			int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

			if (option == 0) {
				try {
					/**
					 * There's no way to edit a particular line of text of a file in Java. Thus, in
					 * order to edit the lifetime earnings of a specific user, the file contents
					 * must be copied, the lifetime earnings for the specific user must be updated,
					 * the file must be cleared, and the updated contents must be added to the
					 * cleared file. The lineCounter and lineNo variables are crucial to this
					 * process. A String array is used to store the String values of each line of
					 * the text file; the lineCounter variable is used to set the size of the array.
					 * For instance, if there are three users, the lineCounter variable is going to
					 * have a value of three, and thus the String array is going to have a size of
					 * three. Each location in the String array holds the text of each line in the
					 * file. The lineNo variable helps identify the line that needs to be updated.
					 * The variable was previously initialized to the line number at which the name
					 * inputted by the user matched a pre–existing user in the text file. For
					 * instance, if the user declares his name as "Diego" and such user is located
					 * in the second line in the text file, the lineNo variable is going to have a
					 * value of two. Once the program has reached the line that needs to be updated,
					 * the location in the String array at that line is going to be set to the
					 * user's name and the updated lifetime earnings variable. Notably, the program
					 * only updates the lifetime earnings once the LEAVE button is clicked.
					 */
					
					reader = new FileReader(file);
					in = new BufferedReader(reader);

					// Reads a line of text from the input stream and returns it as a String
					String line = in.readLine();

					// Initializes String array used to copy contents of file
					String[] originalFile = new String[lineCounter];

					// Resets line counter
					lineCounter = 0;

					// Executes as long as the end of the line has not been reached
					while (line != null) {
						// Initializes array element at line counter to content of the line
						originalFile[lineCounter] = line;

						lineCounter++;

						// Executes if line counter is equal to line number
						if (lineCounter == lineNo) {
							// Initializes array element at line counter to name and updated lifetime earnings
							originalFile[lineCounter - 1] = name + ": " + lifetimeEarnings;
						}

						// Reads a line of text from the input stream and returns it as a String
						line = in.readLine();
					}

					// Closes input file stream object
					in.close();

					// Clears contents of file
					file.delete();
					file.createNewFile();

					writer = new FileWriter(file, true);
					out = new BufferedWriter(writer);

					for (int counter = 0; counter < lineCounter; counter++) {
						// Writes updated lines to the output stream
						out.write(originalFile[counter]);
						out.newLine();
					}

					// Closes the output file stream object
					out.close();
				}

				catch (IOException a) {
					a.printStackTrace();
				}

				JOptionPane.showMessageDialog(null, "Thank you!\nHope to see you again.", "", JOptionPane.PLAIN_MESSAGE);
				System.exit(0);
			}
		}

		/* HIT */
		else if (e.getSource() == btnHit) {
			counter1++;

			// Draws a card for player
			playerHand.add(deck.drawCard());

			// Displays new card of player
			lblPlayerCard[counter1].setText("" + playerHand.get(counter1));
			lblPlayerCard[counter1].setVisible(true);

			// Calculates value of player's hand
			playerValue += playerHand.get(counter1).getValue();
			playerValue = calcHandValue(playerHand, playerValue);

			lblHandValue.setText("<html><div style='text-align: center;'>Rea: " + dealerValue + "<br>You: " + playerValue);

			// Executes if value of player's hand is more than 21, meaning the player lost
			if (playerValue > 21) {
				btnHit.setVisible(false);
				btnStand.setVisible(false);
				btnContinue.setVisible(true);

				lblWonLost.setVisible(true);
				lblWonLost.setText("You Lost!");

				// Displays hidden card of dealer and calculates value of dealer's hand
				lblDealerCard[1].setText("" + dealerHand.get(1));
				dealerValue += dealerHand.get(1).getValue();

				lblHandValue.setText("<html><div style='text-align: center;'>Rea: " + dealerValue + "<br><font color=red>You: <b>Bust!");

				lifetimeEarnings -= bet;
				bankBalance -= bet;
				sessionEarnings -= bet;
			}
		}

		/* STAND */
		else if (e.getSource() == btnStand) {
			btnHit.setEnabled(false);
			btnStand.setEnabled(false);

			// Displays hidden card of dealer
			lblDealerCard[1].setText("" + dealerHand.get(1));

			// Calculates value of dealer's hand
			dealerValue += dealerHand.get(1).getValue();
			dealerValue = calcHandValue(dealerHand, dealerValue);

			lblHandValue.setText("<html><div style='text-align: center;'>Rea: " + dealerValue + "<br>You: " + playerValue);

			// Executes while value of dealer's hand is less than 17 (according to Blackjack
			// rule, dealer HAS to hit until its hand value is more than or equal to 17)
			while (dealerValue < 17) {
				counter2++;

				// Draws a card for dealer
				dealerHand.add(deck.drawCard());

				// Displays new card of dealer
				lblDealerCard[counter2].setText("" + dealerHand.get(counter2));
				lblDealerCard[counter2].setVisible(true);

				// Calculates value of dealer's hand
				dealerValue += dealerHand.get(counter2).getValue();
				dealerValue = calcHandValue(dealerHand, dealerValue);

				lblHandValue.setText("<html><div style='text-align: center;'>Rea: " + dealerValue + "<br>You: " + playerValue);
			}
			
			// Executes if value of dealer's hand is more than 21, meaning the dealer bust 
			if (dealerValue > 21) {
				btnHit.setVisible(false);
				btnStand.setVisible(false);
				btnContinue.setVisible(true);

				lblWonLost.setVisible(true);
				lblWonLost.setText("You Won!");

				lblHandValue.setText("<html><div style='text-align: center;'><font color=red>Rea: <b>Bust!</b><br><font color=black>You: "
								+ playerValue);

				lifetimeEarnings += bet;
				bankBalance += bet;
				sessionEarnings += bet;
			}

			// Executes if value of player's hand is more than value of dealer's hand,
			// meaning the player won
			else if (playerValue > dealerValue) {
				btnHit.setVisible(false);
				btnStand.setVisible(false);
				btnContinue.setVisible(true);

				lblWonLost.setVisible(true);
				lblWonLost.setText("You Won!");

				lifetimeEarnings += bet;
				bankBalance += bet;
				sessionEarnings += bet;
			}

			// Executes if value of player's hand is less than value of dealer's hand,
			// meaning the player lost
			else if (playerValue < dealerValue) {
				btnHit.setVisible(false);
				btnStand.setVisible(false);
				btnContinue.setVisible(true);

				lblWonLost.setVisible(true);
				lblWonLost.setText("You Lost!");

				lifetimeEarnings -= bet;
				bankBalance -= bet;
				sessionEarnings -= bet;
			}

			// Executes if value of player's hand is equal to value of dealer's hand,
			// meaning the player and dealer pushed
			else if (playerValue == dealerValue) {
				btnHit.setVisible(false);
				btnStand.setVisible(false);
				btnContinue.setVisible(true);

				lblWonLost.setVisible(true);
				lblWonLost.setText("Push!");
			}
		}

		/* CONTINUE */
		else {
			// Sets main panel visible
			pnlWelcome.setVisible(false);
			pnlMain.setVisible(true);
			pnlGame.setVisible(false);

			// Sets properties of frame
			setContentPane(pnlMain);
			setSize(320, 352);
			setLocationRelativeTo(null);

			// Updates text of JLabel objects
			lblLifetimeEarnings.setText("<html><div style='text-align: center;'>You have lifetime casino<br>earnings of "
						+ df.format(lifetimeEarnings) + ".</html>");
			lblBankBalance.setText("<html><b>Bank Balance</b>: " + df.format(bankBalance) + "</html>");
			lblEarnings.setText("<html><b>Session Earnings</b>: " + df.format(sessionEarnings) + "</html>");
			
			// Clears text in JTextField and requests focus
			txtBet.setText("");
			txtBet.requestFocus();

			// Resets value of dealer's and player's hand, and updates text in JLabel
			dealerValue = 0;
			playerValue = 0;
			lblHandValue.setText("<html><div style='text-align: center;'>Rea: " + dealerValue + "<br>You: " + playerValue);
			
			// Resets value of counter variables
			counter1 = 1;
			counter2 = 1;
			
			// Resets JLabel arrays used for player's and dealer's hand
			for (int counter = 0; counter < 10; counter++) {
				lblPlayerCard[counter].setText("");
				lblDealerCard[counter].setText("");

				if (counter > 1) {
					lblPlayerCard[counter].setVisible(false);
					lblDealerCard[counter].setVisible(false);
				}
			}
		}
	}

	public void keyTyped(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
		// Executes when user presses ENTER key
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (pnlWelcome.isVisible())
				btnStart.doClick();

			else if (pnlMain.isVisible())
				btnPlay.doClick();
		}
	}
	
	public void readFile() {
		if (file.exists()) {
			try {
				reader = new FileReader(file);
				in = new BufferedReader(reader);
	
				// Reads a line of text from the input stream and returns it as a String
				String line = in.readLine();
	
				// Executes as long as the end of the line has not been reached
				while (line != null) {
					String data[] = line.split(": ");
	
					int i = Integer.parseInt(data[1]);
	
					if (i > 0)
						txtA.append(String.format("%-18s%-20s\n", data[0], "  " + data[1]));
	
					else
						txtA.append(String.format("%-18s%-20s\n", data[0], " " + data[1]));
	
					// Reads a line of text from the input stream and returns it as a String
					line = in.readLine();
				}
	
				// Closes input file stream object
				in.close();
			}
	
			catch (IOException a) {
				a.printStackTrace();
			}
		}
	}

	/**
	 * The following method is used to calculate the value of the player's or
	 * dealer's hand. The method calls two parameters: the hand, and the hand's
	 * value. First, the method checks if any of the cards in the hand is an ace
	 * (hand.get(counter).getValue() == 11). If such returns true, then the program
	 * checks if the hand value is over 21. If such also returns true, then the
	 * program sets the value of that ace to 1 (hand.get(counter).setValueOfAce()),
	 * and subtracts 10 from the hand value (11 – 1). In Blackjack, an ace can have
	 * a value of either 1 or 11. After the hand value has been calculated, the
	 * method returns the updated hand value.
	 */
	
	public int calcHandValue(ArrayList<Card> hand, int handValue) {
		for (int counter = 0; counter < hand.size(); counter++) {
			if (hand.get(counter).getValue() == 11 && handValue > 21) {
				hand.get(counter).setValueOfAce();
				handValue -= 10;
			}
		}

		return handValue;
	}

	public static void main(String[] args) {
		new Blackjack();
	}
}
