
public class Card {
	//suit value of H,C,S,D for heart, club, spade, diamond respectively
	private String suit;
	//value for display and scoring purposes
	private int value;
	//array that contains the row then column of the card on the game board
	private int[] location;
	/*two letter string for display purposes on the board.
	 * for cards 2-10, will contain first letter of suit and value.
	 * for face cards, will contain first letter of suit and first letter of the face card's name.
	 * for ace will contain first letter of suit and 'A.'
	 */
	private String cardIcon;
	

	

	/*constructor will create different types of cards as needed. The highCard parameter is used to create the cardIcon for cards the face cards
	 * and ace because their display on the board will not use their value as per instructions.
	 * There will be 4 types of cards needed:
	 * (1) Display cards for initial empty position display. Will have null suit, value will be the position i'th postion in 
	 * the board that the user chooses to make a move
	 * (2) Cards 2-10, whose value is part of their display.
	 * (3) Face cards, who have a value of 10 and display the first char of suit and name.
	 * (4) The ace, which will have default value of 1 and display the first char of suit and name.
	 */
	public Card(String s, int v, String highCard) {
		this.suit = s;
		this.value = v;	
		//check if display card.
		if((highCard == null) && s == null) {
			//set the display variable for display cards
			this.cardIcon = Integer.toString(v);
			//check if 2-10 card
		}else if((highCard == null) && (s != null)) {
			//set display variable to value and first letter of suit
			this.cardIcon = Integer.toString(v)+ s;
		}
		// otherwise, must be a face card or ace. 
		else {
			//set display variable to the highcard parameter that will be initialized during card construction.
			this.cardIcon = highCard;
		}
	}
	
	//getters and setters
		
	public String getCardIcon() {
		return cardIcon;
	}

	public void setCardIcon(String cardIcon) {
		this.cardIcon = cardIcon;
	}
	
	public int[] getLocation() {
		return location;
	}

	public void setLocation(int[] location) {
		this.location = location;
	}

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	
	
	
	
	
}
