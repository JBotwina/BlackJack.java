
public class Deck {
	//size of the deck
		public static final int DECK_SIZE=52;
		//array to hold the cards.
		Card[] cards;
		//array used as a temporary deck for shuffling the deck
		Card[] cards2;
		//keeps track of current card in the deck so the deal method can choose the next one.
		int currentCardCounter = 0;
		
		 // constructor for Deck. Construct an array of 52 unique cards.
		 public  Deck() { 
			 //deck will always have 52 card
			 cards = new Card[DECK_SIZE];
			 
			 // suits in the deck
			 String[] suits = {"H", "D", "C", "S"};
			 // values of the cards
			 int[] values = {2,3,4,5,6,7,8,9,10};
			 //Jack, Queen, King
			 String[] faces = {"J", "Q", "K"};
			 
			 //keep track of the number of cards created
			 int x = 0;
			 //do not make more than 52 cards
			 while(x < 52) {
				 //start with the numbered cards
				 for(int i = 0; i< suits.length; i++) {
					 for(int j = 0; j< values.length; j++) {
						 //create a card
						 Card c;
						 //give the card a suit and value.
						 c = new Card(suits[i%suits.length], values[j%values.length], null);
						 //place it in the deck
						 cards[x] = c;
						 //increment tracker so that the next card is placed at in the next position.
						 x++;
					 }
					 //do the face cards.
					 for(int k = 0; k < faces.length; k++) {
						 //declare and assign a highCard variable that combines the suit and name
						 String highCard = faces[k%faces.length] + suits[i%suits.length];
						 //create card
						 Card c;
						 //give card a suit, value of 10, and the special display variable for highCards.
						 c = new Card(suits[i%suits.length],10, highCard);
						 cards[x] = c;
						 x++;
					 }
					 //finish with the ace.
					 String highCard = "A" + suits[i];
					 Card c = new Card(suits[i], 1, highCard);
					 cards[x] = c;
					 x++;
					 
				 }
			 }
			 //Start with shuffled deck.
			 shuffle();
			
		 }
		 
		 //deal the card to the player
		 public Card dealCard() {
			 //make a boolean switch to toggle the while loop, start at true
			 boolean deal = true;
			 //declare current card to be dealt
			 Card currentCard = null;
			 //while deal is true, assign the next card in line to the current card position to be returned.
			 while(deal) {
				currentCard = cards[currentCardCounter];
				//increment counter so the next card can be chosen after this.
				currentCardCounter++;
				//toggle deal off so that we end the while loop.
				 deal = false;
			 }	 
			 return currentCard ;
		 }
		 
		 //shuffle the deck
		 public void shuffle() {
			 // create a temporary deck to hold a randomized ordering of the main deck.
			 cards2 = new Card[DECK_SIZE]; 
			 //create a random order of the from 1 to 52.
			 int[] randomOrder = RandomOrderGenerator.getRandomOrder(DECK_SIZE);
			 //replace the 52 with a 0 because we want to index from zero.
			 for(int b=0; b<DECK_SIZE; b++) {
				 if(randomOrder[b] == 52) {
					 randomOrder[b] = 0;
					 break;
				 }
			 }
			 //loop through the deck and match it with a new order
			 for(int i = 0; i< DECK_SIZE; i++) {
				 cards2[i] = cards[randomOrder[i]];
			 }
			 //fill the original deck with the randomized order
			 for(int j = 0; j< DECK_SIZE; j++) {
				 cards[j] = cards2[j];
			 }	 
		}
}
