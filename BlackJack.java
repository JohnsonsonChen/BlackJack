import java.util.Scanner;

public class BlackJack {
	private static int myMoney = 20000;
	static Scanner keyboard = new Scanner(System.in);
	
	public static void main(String[] args) {
		//Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Welcome to Blackjack");
		System.out.print("Please input your name: ");
		String player = keyboard.nextLine();
		
		
		System.out.println();
		System.out.println("Hi," + player);
		System.out.println("Your Money: " + "$" + myMoney);
		System.out.println();
		
		int playerBet = betMoney(myMoney);
		
		
		
		
		DeckOfCards allCards = new DeckOfCards();
		allCards.shuffle();
		
		boolean gameOver = false;
		boolean playerTurn = true;
		boolean hiddenCardShown = false;
		boolean endOfGame = false;
		
		
		Card cardOne = allCards.deal();
		Card cardTwo = allCards.deal();
		int total = cardOne.getValue() + cardTwo.getValue();
		System.out.println("You got a " + cardOne.getSuit() + " " + cardOne.getValue() + " and a " + cardTwo.getSuit() + " " + cardTwo.getValue());
		System.out.println("Your total is: " + total);
		System.out.println();
		
		Card dealerCardOne = allCards.deal();
		Card dealerCardTwo = allCards.deal();
		int dealerTotal = dealerCardOne.getValue() + dealerCardTwo.getValue();
		System.out.println("The dealer has a " + dealerCardOne.getSuit() + " " + dealerCardOne.getValue() + " showing, and a hidden card.");
		System.out.println("His total is hidden");
		System.out.println();
		
		while(!gameOver) {
			if(endOfGame) {
				computeResult(total, dealerTotal, playerBet);
				System.out.print("Wanna play aagin?(y/n): ");
				String yOrn = keyboard.nextLine();
				if(yOrn.equals("n")) {
					gameOver = true;
					break;
				}
				else if(yOrn.equals("y")) {
					allCards.restart();
					
					playerBet = betMoney(myMoney);
					
					cardOne = allCards.deal();
					cardTwo = allCards.deal();
					total = cardOne.getValue() + cardTwo.getValue();
					System.out.println("You got a " + cardOne.getSuit() + " " + cardOne.getValue() + " and a " + cardTwo.getSuit() + " " + cardTwo.getValue());
					System.out.println("Your total is: " + total);
					System.out.println();
					
					dealerCardOne = allCards.deal();
					dealerCardTwo = allCards.deal();
					dealerTotal = dealerCardOne.getValue() + dealerCardTwo.getValue();
					System.out.println("The dealer has a " + dealerCardOne.getSuit() + " " + dealerCardOne.getValue() + " showing, and a hidden card.");
					System.out.println("His total is hidden");
					System.out.println();
					
					gameOver = false;
					playerTurn = true;
					hiddenCardShown = false;
					endOfGame = false;
					
					continue;
				}
			}
			if(playerTurn) {
			  System.out.println("Would you like to \"hit\" or \"stay\"?");
			  String response = keyboard.nextLine();
			  if(response.equalsIgnoreCase("hit")) {
			    Card cardThree = allCards.deal();
			    total += cardThree.getValue();
			    System.out.println("You drew a " + cardThree.getSuit() + " " + cardThree.getValue());
			    System.out.println("Your total is: " + total);
			    System.out.println();
			    
			    if(total > 21) {
			    	endOfGame = true;
			    }
			  }
			  else if(response.equalsIgnoreCase("stay")) {
				  System.out.println("Okay, dealer's turn");
				  playerTurn = false;
			  }
			}
			else {
				if(!hiddenCardShown) {
					System.out.println("Dealer's Hidden Card is: " + dealerCardTwo.getSuit() + " " + dealerCardTwo.getValue());
					System.out.println("His total is " + dealerTotal);
					hiddenCardShown = true;
					System.out.println();
				}
				if(dealerTotal <= 16) {
				  System.out.println("Dealer chooses to hit.");
				  Card dealerCardThree = allCards.deal();
				  dealerTotal += dealerCardThree.getValue();
				  System.out.println("He draws a " + dealerCardThree.getSuit() + " " + dealerCardThree.getValue());
				  System.out.println("His total is " + dealerTotal);
				  System.out.println();
					  
				  if(dealerTotal > 21) {
					  endOfGame = true;
				  }
				}
				else{
				  System.out.println("Dealer chooses to stay.");
				  System.out.println();
				  endOfGame = true;
				}
					
			}
		}
	}//main
	
	private static void computeResult(int total, int dealerTotal, int playerBet) {
		if(total > 21) {
			System.out.println("You \"busts\"");
			System.out.println("Dealer Win!");
			myMoney -= playerBet;
			System.out.println();
		}
		else if(dealerTotal > 21) {
			System.out.println("Dealer \"busts\"");
			System.out.println("You Win!");
			myMoney += playerBet * 2;
			System.out.println();
		}
		
		else if(total > dealerTotal) {
			System.out.println("You have " + total + " points, and dealer has " + dealerTotal + " points");
			System.out.println("You Win!");
			myMoney += playerBet * 2;
			System.out.println();
		}
		else if(total < dealerTotal) {
			System.out.println("You have " + total + " points, and dealer has " + dealerTotal + " points");
			System.out.println("Dealer Win!");
			myMoney -= playerBet;
			System.out.println();
		}
		else {
			System.out.println("You have " + total + " points, and dealer has " + dealerTotal + " points");
			System.out.println("Draw!");
			System.out.println();
		}
		System.out.println("You have: " + "$" + myMoney);
		System.out.println();
	}
	
	private static int betMoney(int myMoney) {
		if(myMoney == 0) {
			return 0;
		}
		boolean betted = false;
		int bet = 0;
		while(!betted) {
			System.out.println("How much do you wanna bet?");
		    bet = keyboard.nextInt();
		    if(keyboard.hasNextLine()) {
		    	keyboard.nextLine();
		    }
			
			if(bet <= myMoney) {
				break;
			}
			else {
				System.out.println("You don't have enough money!");
			}
		}
		return bet;
	}
}