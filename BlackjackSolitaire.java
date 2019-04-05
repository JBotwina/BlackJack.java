
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BlackjackSolitaire {
	//create a deck 
		Deck gameDeck;
		//create a board
		Board gameBoard;
		//create an arrayList to hold the positions on the board that have been already chosen so that the user can not replace a placed card.
		ArrayList<String> assignedBoardPositions; 
		//create an array list populated with 4 positions 
		ArrayList<String> trash = new ArrayList<String>(Arrays.asList("17","18","19","20"));
		//create a variable that counts the number of times you place a card in the discard pile (trash).
		int discardCounter = 0;
		//create scanner class to receive input
		Scanner scan;
		//create int variable that will be assigned to userInput
		int playerInput;
		//create variable to hold current card.
		Card currentCard = null;
		
		//constructor for game
		public BlackjackSolitaire() {
		//create deck and board to play the game
		gameDeck = new Deck();
		gameBoard = new Board();
		//assign an array list to hold the assigned positions while playing
		assignedBoardPositions = new ArrayList<String>();
		//assign scanner for user input
		scan = new Scanner(System.in);
		}
		
		public void play() {
			//We want to to continue play by repeating these steps until all the board positions are filled. Limit is 15 because we zero-index. 
			while(assignedBoardPositions.size() < 16) {
			//show the state
			gameBoard.show();
			//show the discard pile.
			showTrash();
			//deal a card
			currentCard = gameDeck.dealCard();
			//print out the card's display icon
			System.out.println("Your card is: " + currentCard.getCardIcon());
			//prompt the user to make a move
			System.out.println("Pick a position");
			//receive that input and assign it to a variable
			int playerInput = scan.nextInt();
			//evaluate the input according to the rules of the game.
			evaluateInput(playerInput);
			}
			gameBoard.show();
			score();
		}
		
		//quarantine the possibilities of userInput 
		public void evaluateInput(int input) {
			playerInput = input;
			/*
			 *  Only allow input for a board position that is:
			 *  (1) not already used.
			 *  (2) between 0 and 21, not inclusive.
			 *  prompt user with appropriate directions
			 */
			while( !( (checkIfAcceptableRange(playerInput) && checkIfNotPlayed(playerInput)) ) ) {
				if(!(checkIfAcceptableRange(playerInput))) {
					System.out.println("That position doesn't exist. Pick again");
					playerInput = scan.nextInt();
				}
				else if(!(checkIfNotPlayed(playerInput))) {
					System.out.println("You already placed a card there. Pick another please!");
					playerInput = scan.nextInt();
				} 
			}
			/*if player wants to place the card in the discard pile, see if the trash is full 
			 * if it isn't, then move to trash
			 */
			if((playerInput > 16) && (playerInput < 21) ) {
				evaluateTrashInput(playerInput);
			}
			else {
			String stringInput = Integer.toString(playerInput);
			makeMove(stringInput, currentCard);
			assignedBoardPositions.add(stringInput);		
			}
		}
		
		//check if the input is within acceptable range.
		public boolean checkIfAcceptableRange(int choice) {
			int intInput = choice;
			if((intInput > 0) && (intInput < 21)) {
				return true;
			}
			return false;
		}
		
		//check if input is already played
		public boolean  checkIfNotPlayed(int input) {
			String stringInput = Integer.toString(input);
			if(assignedBoardPositions.contains(stringInput)) {
				return false;
			}
			return true;
		}
		//evaluate discard function
		public void evaluateTrashInput(int playerInput) {
			int intInput = playerInput;
			String stringedInput = Integer.toString(playerInput);
			//Allow a maximum of 4 cards to be added to the trash. Check if trash is full.
			if( (4 - discardCounter > 0)) {
				//if not full, check to see if the discard position is available
				if( trash.contains(stringedInput) ) {
					//move to that position
					moveToTrash(stringedInput);
				}
				//make the user chose an empty position in the trash
				else {
					while(!trash.contains(stringedInput)) {
						System.out.println("You can't change your mind. Input another space in the trash");
						stringedInput = Integer.toString(scan.nextInt());
					}
					moveToTrash(stringedInput);
				}
			}
			else {
				//when the trash is full, make the user input a valid position on the board.
				while( (!checkIfNotPlayed(intInput) || !(intInput < 17) )) {
					System.out.println("Make a valid move on board");	
					intInput = scan.nextInt();
				}
				String forcedInput = Integer.toString(intInput);
				makeMove(forcedInput, currentCard);
				assignedBoardPositions.add(forcedInput);
			}
		}
		//take an input and move the current card to that position in the trash.
		public void moveToTrash(String stringedInput) {
			trash.set(trash.indexOf(stringedInput), currentCard.getCardIcon());
			discardCounter++;
		}
		
		//show the trash contents.
		public void showTrash() {
			System.out.println("\n");
			//loop through trash
			for(int i = 0; i < trash.size(); i++) {
				//print out what we have
				System.out.print(trash.get(i)+ " ");
			}
			//print out the number of choices we have left. There is a max of 4.
			System.out.print("You have "+ (4 - discardCounter) + " available discard spaces left. \n");
		}
		
		//score the game when the board is full and print out score
		public void score() {
			int sum = 0;
			sum += scoreRows();
			sum += scoreCols();
			System.out.println("The score of the game is: " + sum);
		}
		//general function used to sum an array of cards
		public int sumArrayOfCards(Card[] cards) {
			int sum =0;
			for(Card card : cards) {
				if(card != null) {
					sum+= card.getValue();
				}
			}
			return sum;
		}
		//general function to check if there is an ace in an array of cards.
		public boolean checkForAce(Card[] cards) {
			for(Card card : cards) {
				if(card != null) {
					if(card.getCardIcon().charAt(0) == 'A') {
						return true;
					}
				}
			}
			return false;
		}
		
		
		//get the sum of the rows of the gameBoard.
		public int scoreRows() {
			int scoreOfAllRows = 0;
			int rowSum;
			boolean hasAce;
			for(int i = 0; i < 4; i++) {
				Card[] row = gameBoard.board[i];
				//sum the row
				rowSum = sumArrayOfCards(row);
				//check for ace
				hasAce = checkForAce(row);
				//if we do have an ace and the sum of the row is less than 12 we will always want to utilize the ace as 11 points, not 1 points
				if( (hasAce) && (rowSum < 12) ) {
					rowSum += 10;
				}
				//sanity check for proper scoring
				//System.out.println("The score of row " +i+ " is " + pointChecker(rowSum, false));
				
				//add it to row
				scoreOfAllRows +=pointChecker(rowSum, false);
			}
			
			return scoreOfAllRows;
		}
		
		// get sumCols individually. The commented code is sanity checking.
		public int scoreCols() {
			int scoreOfAllCols = 0;
			//column 0
			scoreOfAllCols += scoreEndCol(gameBoard.board[0][0], gameBoard.board[1][0]);
			//System.out.println("The score of col " +0+ " is " + scoreEndCol(gameBoard.board[0][0], gameBoard.board[1][0]));
			//column 4
			scoreOfAllCols += scoreEndCol(gameBoard.board[0][4], gameBoard.board[1][4]);
			//System.out.println("The score of col " +4+ " is " + scoreEndCol(gameBoard.board[0][4], gameBoard.board[1][4]));
			
			//column 1
			scoreOfAllCols += scoreMiddleCol(gameBoard.board[0][1], gameBoard.board[1][1], gameBoard.board[2][1], gameBoard.board[3][1]);
			//System.out.println("The score of col "+1+ " is "+scoreMiddleCol(gameBoard.board[0][1], gameBoard.board[1][1], gameBoard.board[2][1], gameBoard.board[3][1]));
			//column 2
			scoreOfAllCols += scoreMiddleCol(gameBoard.board[0][2], gameBoard.board[1][2], gameBoard.board[2][2], gameBoard.board[3][2]);
			
			//System.out.println("The score of col "+2+ " is "+scoreMiddleCol(gameBoard.board[0][2], gameBoard.board[1][2], gameBoard.board[2][2], gameBoard.board[3][2]));
			//column 3
			scoreOfAllCols += scoreMiddleCol(gameBoard.board[0][3], gameBoard.board[1][3], gameBoard.board[2][3], gameBoard.board[3][3]);
			//System.out.println("The score of col "+3+ " is "+scoreMiddleCol(gameBoard.board[0][3], gameBoard.board[1][3], gameBoard.board[2][3], gameBoard.board[3][3]));
			
			return scoreOfAllCols;
		}
		
		//take sum of an 2-card array used for the end columns.
		public int scoreEndCol(Card top, Card bottom) {
			Card[] topAndBottom = {top,bottom};
			int sum = top.getValue() + bottom.getValue();
			boolean hasAce = checkForAce(topAndBottom);
			if( (hasAce) && (sum < 12) ) {
				sum += 10;
			}
			int scoreOfEndCol = pointChecker(sum, true);
			return scoreOfEndCol;
		}
		//take sum of a 4-card array. Used for middle columns.
		public int scoreMiddleCol(Card first, Card second, Card third, Card fourth) {
			int scoreOfMiddleCol;
			int sum = 0;
			Card[] middleCol = {first, second, third, fourth};
			for(Card card : middleCol) {
				sum += card.getValue();
			}
			boolean hasAce = checkForAce(middleCol);
			if((hasAce) && (sum < 12)) {
				sum+= 10;
			}
			scoreOfMiddleCol = pointChecker(sum, false);
			return scoreOfMiddleCol;
		}
		
		//converts array sums to appropriate points
		public int pointChecker(int sum, boolean twoCards) {
			//allows for special point system when we have blackjack in two cards
			if(twoCards) {
				if(sum == 21) {
					return 10;
				}
			}
			if(sum == 21) {
				return 7;
			}
			if(sum == 20) {
				return 5;
			}
			if(sum == 19) {
				return 4;
			}
			if(sum == 18) {
				return 3;
			}
			if(sum == 17) {
				return 2;
			}
			if(sum < 17) {
				return 1;
			}
			return 0;
		}
		
		//place the card on the board
		public void makeMove(String input, Card currentCard) {
			//loop through board
				for(int i=0; i<4; i++) {
					for(int j=0; j<5; j++) {
						//pass the cards that are not displayed
						if(gameBoard.board[i][j] == null) {
							continue;
						}
						//if we have a display card that matches the user input, place the current card in that position of the board.
						if(gameBoard.board[i][j].getCardIcon().contentEquals(input)) {
							gameBoard.board[i][j] = currentCard;
						}
					}
				}
		}
}
