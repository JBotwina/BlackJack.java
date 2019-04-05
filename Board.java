//make a grid 4x5 2d array of which we will populate 16 spaces as needed for the game.
public class Board {
	//initialize dimensions
	public static final int[] dims = {4,5}; 
	// 2d array to play game
	Card[][] board;
	
	//constructor for Board
	public Board() {	
		//take in dimensions
		board = new Card[dims[0]][dims[1]];
		// create a counter variable k to keep track of the number of cards being created.
		int k = 0;
		//fill in the top 2x5 section of our desired board display cards
		for(int i=0; i<2; i++) {
			for(int j=0; j<5; j++) {
				//display cards only have a number value that is used to display their position on the board.
				Card c = new Card(null, k+1, null);
				//insert card in specified location
				board[i][j] = c;
				//record location
				int[] loc1 = {i,j};
				//set location to the location variable of the card. 
				c.setLocation(loc1);
				k++;
			}
		}
		// fill in the bottom 4x3 section of the display board.
		for(int m=2; m<4; m++){
			for(int n=1; n<4; n++) {
				Card c = new Card(null, k+1, null);
				board[m][n] = c;
				int[] loc2 = {m,n};
				c.setLocation(loc2);
				k++;
			}
		}
	}
	//display the state of the game! Loop through the board
	public void show() {
		for(int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				//We only put cards in the positions we wanted. All other positions will have null. If null, then skip by printing out " ".
				if(board[i][j] == null) {
					System.out.print("   ");
				}
				//Manage display spacing to look pretty.
				else if( (board[i][j].getCardIcon().length() < 2) || (board[i][j].getCardIcon().contentEquals("10"))){
					System.out.print(board[i][j].getCardIcon()+ "   ");
				}else {
					System.out.print(" " + board[i][j].getCardIcon()+ " ");
				}
			}
			System.out.println("");
		}
	}
}

	
	



	
	
