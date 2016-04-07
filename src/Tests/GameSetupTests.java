package Tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Collections;
import java.util.LinkedList;
import org.junit.Before;
import org.junit.Test;

import Game.*;
import Players.*;

public class GameSetupTests {
	
	private static Board board;
	private final static int DECK_SIZE = 21;
	private final static int NUMBER_OF_PERSONS = 6;
	private final static int NUMBER_OF_WEAPONS = 6;
	private final static int NUMBER_OF_ROOMS = 9;
	private final static int CARDS_DEALT = 18; //cards dealt for three players
	private static HumanPlayer human;
	private static ComputerPlayer compOne;
	private static ComputerPlayer compTwo;
	private static Card hallCard;
	private static Card mustardCard;
	private static Card knifeCard;
	
	
	@Before
	public void setUp(){
		board = new Board("ClueLayout.csv", "ClueLegend.txt", "Characters.txt", "Weapons.txt",3);
		board.initialize();
		//players used for loading test
		human = new HumanPlayer("Professor Plum", 0, 6, Color.BLACK);
		compOne = new ComputerPlayer("Mrs. Peacock", 24, 12, Color.blue);
		compTwo = new ComputerPlayer("Colonel Mustard", 4, 24, Color.yellow);
		
		//cards used for tests
		hallCard = new Card("Hall", CardType.ROOM);
		mustardCard = new Card("Colonel Mustard", CardType.PERSON);
		knifeCard = new Card("Knife", CardType.WEAPON);
		
		
	}
	//This test will test if the correct people have been loaded
	//It will check the correct name, color, and starting location
	//It will check the human player and 2 computer players
	@Test
	public void testLoading(){
		LinkedList<Player> players = board.getPlayers();
		assertTrue(players.contains(human));		
		assertTrue(players.contains(compOne));
		assertTrue(players.contains(compTwo));

	}
	
	//This test checks if loading the cards was done correctly
	//It will check if the deck contains the correct total number of cards
	//It will check if the deck contains the correct number of cards of each type
	//One room, one weapon, and one person are selected to ensure the deck contains each of those
	@Test
	public void testLoadingDeck(){
		assertEquals(DECK_SIZE, board.getDeckBeforeDeal().size());
		assertEquals(NUMBER_OF_PERSONS, board.getNumberPersons());
		assertEquals(NUMBER_OF_WEAPONS, board.getNumberWeapons());
		assertEquals(NUMBER_OF_ROOMS, board.getNumberRooms());
		assertTrue(board.getDeckBeforeDeal().contains(hallCard));
		assertTrue(board.getDeckBeforeDeal().contains(mustardCard));
		assertTrue(board.getDeckBeforeDeal().contains(knifeCard));
		
	}
	
	//This test of the cards are dealt correctly
	//It will check that all the cards are dealt
	//It will check that all the players have roughly the same number of cards
	//It will check that one card is not given to two different players
	@Test
	public void testDeal(){
		int difference = 1;
		assertEquals(CARDS_DEALT, board.getDealtCardSize());
		//difference player one and player two
		LinkedList<Player> players = board.getPlayable();
		int diffOne = Math.abs(players.get(0).getHand().size() -players.get(1).getHand().size());
		assertTrue(diffOne <= difference);
		
		//difference between player one and player three
		int diffTwo = Math.abs(players.get(0).getHand().size() - players.get(2).getHand().size());
		assertTrue(diffTwo <= difference);
		
		//difference between player two and player three
		int diffThree = Math.abs(players.get(1).getHand().size() - players.get(2).getHand().size());
		assertTrue(diffThree <= difference);
		
		//The following lines check that each hand is unique	
		assertTrue(Collections.disjoint(players.get(0).getHand(), players.get(1).getHand()));
		assertTrue(Collections.disjoint(players.get(0).getHand(), players.get(2).getHand()));
		assertTrue(Collections.disjoint(players.get(1).getHand(), players.get(2).getHand()));
		
	}
}
