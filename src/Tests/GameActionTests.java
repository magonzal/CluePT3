package Tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import Clue.Board;
import Clue.BoardCell;
import Clue.Card;
import Clue.CardType;
import Clue.ComputerPlayer;
import Clue.DoorDirection;
import Clue.HumanPlayer;
import Clue.Player;
import Clue.Solution;

public class GameActionTests {
	
	private static Board board;
	@Before
	public void setUp(){
		board = new Board("ClueLayout.csv", "ClueLegend.txt", "Characters.txt", "Weapons.txt",3);
		board.initialize();
	}
	
	
	//This tests whether checkAccussation works properly
	//It tests if the accusation is correct containing the correct person, weapon, and room
	//It tests if the accusation is not correct if the room is wrong, or if the person is wrong, or weapon is wrong
	@Test
	public void testAccusation(){
		Solution solution = new Solution("Colonel Mustard", "Kitchen", "Knife");
		board.setSolution(solution);
		Solution accusation = new Solution("Colonel Mustard", "Kitchen", "Knife");
		
		assertTrue(board.checkAccusation(accusation));
		
		Solution falseAccussation = new Solution("Colonel Mustard", "Hall", "Knife");
		assertFalse(board.checkAccusation(falseAccussation));
		
		Solution falseAccusationTwo = new Solution("Professor Plum", "Kitchen", "Knife");
		assertFalse(board.checkAccusation(falseAccusationTwo));
		
		Solution falseAccusationThree = new Solution("Colonel Mustard", "Kitchen", "Revolver");
		assertFalse(board.checkAccusation(falseAccusationThree));	
	}
	
	//Created one player and dealt six specific cards to the player
	//Two of each type then made 4 calls to disproveSuggestoin
	//Then tested if the correct person, correct weapon, and correct room was returned
	//Testing for a null return was tested as well
	@Test
	public void testOneCard(){
		Card personOne = new Card("Mykell Allen", CardType.PERSON);
		Card personTwo = new Card("Cindy Rader", CardType.PERSON);
		Card weaponOne = new Card("Shotgun", CardType.WEAPON);
		Card weaponTwo = new Card("Uzi", CardType.WEAPON);
		Card roomOne = new Card("Hall", CardType.ROOM);
		Card roomTwo = new Card("Bathroom", CardType.ROOM);
		
		Player playerOne = new Player("Tommy", 0,0, Color.CYAN);
		playerOne.setHand(personOne);
		playerOne.setHand(personTwo);
		playerOne.setHand(weaponOne);
		playerOne.setHand(weaponTwo);
		playerOne.setHand(roomOne);
		playerOne.setHand(roomTwo);
		
		Solution suggestion = new Solution("Colonel Mustard", "Kitchen", "Knife");
		assertEquals(null, playerOne.disproveSuggestion(suggestion));
		
		suggestion = new Solution("Colonel Mustard", "Hall", "Knife");
		assertEquals(roomOne, playerOne.disproveSuggestion(suggestion));
		
		suggestion = new Solution("Colonel Mustard", "Kitchen", "Uzi");
		assertEquals(weaponTwo, playerOne.disproveSuggestion(suggestion));
		
		suggestion = new Solution("Cindy Rader", "Kitchen", "Knife");
		assertEquals(personTwo, playerOne.disproveSuggestion(suggestion));
		
	}
	//Test similar to one possible match
	//Created dummy variables inside the loop to make sure that the number
	//of times each card was returned was greater than zero
	//The player did not return a card that was not part of the suggestion
	@Test
	public void testMultipleCard(){
		int nameCount = 0;
		int weaponCount = 0;
		int roomCount = 0;
		int totalCount = 50;
		Card personOne = new Card("Mykell Allen", CardType.PERSON);
		Card personTwo = new Card("Cindy Rader", CardType.PERSON);
		Card weaponOne = new Card("Shotgun", CardType.WEAPON);
		Card weaponTwo = new Card("Uzi", CardType.WEAPON);
		Card roomOne = new Card("Hall", CardType.ROOM);
		Card roomTwo = new Card("Bathroom", CardType.ROOM);
		
		Player playerOne = new Player("Tommy", 0,0, Color.CYAN);
		playerOne.setHand(personOne);
		playerOne.setHand(personTwo);
		playerOne.setHand(weaponOne);
		playerOne.setHand(weaponTwo);
		playerOne.setHand(roomOne);
		playerOne.setHand(roomTwo);
		
		Solution suggestion = new Solution("Colonel Mustard", "Kitchen", "Knife");
		assertEquals(null, playerOne.disproveSuggestion(suggestion));
		
		suggestion = new Solution("Mykell Allen", "Hall", "Uzi");
		Card answer;
		for (int i = 0; i < totalCount; i++) {
			answer = playerOne.disproveSuggestion(suggestion);
			
			if (answer.equals(personOne)) {
				nameCount++;
			}
			else if (answer.equals(roomOne)) {
				roomCount++;
			}
			else if (answer.equals(weaponTwo)) {
				weaponCount++;
			}
		}

		assertTrue(totalCount == roomCount + nameCount + weaponCount);
		assertTrue(roomCount > 1);
		assertTrue(nameCount > 1);
		assertTrue(weaponCount > 1);	
	}
	//The following method makes sure that a suggestions was made
	//and no players could disprove so ensured that null was returned
	//made a suggestions that only the human could disprove
	//Then it was ensured that if the person who made the suggestion was the only one could disprove it
	//null was returned using the handleSuggestion method
	//Finally to test the order suggestions were set up so that two players
	//could disprove it so it ensures that the fir person disproves it.
	//Then the test was set up so that the furthest person from the accuser
	//is the one who can disprove.
	@Test
	public void playersQueried(){
		
		ArrayList<Player> players = new ArrayList<Player>();
		Player playerOne = new Player("Tommy", 4,0, Color.CYAN);
		Player playerTwo = new Player("Mykell", 10,10, Color.red);
		Player playerThree = new Player("Marcelo", 11,11, Color.DARK_GRAY);
		HumanPlayer human = new HumanPlayer("Cindy Rader",0,0,Color.BLUE);
		
		players.add(playerOne);
		players.add(playerTwo);
		players.add(playerThree);
		players.add(human);
		
		Card personOne = new Card("Mykell Allen", CardType.PERSON);
		Card personTwo = new Card("Cindy Rader", CardType.PERSON);
		Card personThree = new Card("Professor Plum", CardType.PERSON);
		Card personFour = new Card("Mrs. White", CardType.PERSON);
		Card personFive = new Card("Miss Scarlett", CardType.PERSON);
		Card personSix = new Card("Colonel Mustard", CardType.PERSON);
		Card weaponOne = new Card("Shotgun", CardType.WEAPON);
		Card weaponTwo = new Card("Uzi", CardType.WEAPON);
		Card weaponThree = new Card("Candlestick", CardType.WEAPON);
		Card weaponFour = new Card("Revolver", CardType.WEAPON);
		Card weaponSix = new Card("Wrench", CardType.WEAPON);
		Card weaponFive = new Card("Knife", CardType.WEAPON);
		Card roomOne = new Card("Hall", CardType.ROOM);
		Card roomTwo = new Card("Bathroom", CardType.ROOM);
		Card roomThree = new Card("Kitchen", CardType.ROOM);
		Card roomFour = new Card("Ballroom", CardType.ROOM);
		Card roomFive = new Card("Lounge", CardType.ROOM);
		Card roomSix = new Card("Billiard room", CardType.ROOM);
		
		playerOne.setHand(personOne);
		playerOne.setHand(personTwo);
		playerOne.setHand(weaponOne);
		playerOne.setHand(weaponTwo);
		playerOne.setHand(roomOne);
		
		playerTwo.setHand(personThree);
		playerTwo.setHand(personFour);
		playerTwo.setHand(weaponThree);
		playerTwo.setHand(roomTwo);
		playerTwo.setHand(roomThree);
		
		playerThree.setHand(personFive);
		playerThree.setHand(weaponFour);
		playerThree.setHand(weaponFive);
		playerThree.setHand(roomFour);
		
		human.setHand(personSix);
		human.setHand(weaponSix);
		human.setHand(roomSix);
		human.setHand(roomFive);
		
		board.clearPlayers();
		board.addPlayableCharacters(playerOne);
		board.addPlayableCharacters(playerTwo);
		board.addPlayableCharacters(playerThree);
		board.addPlayableCharacters(human);
		
		//Suggestion no players can disprove
		Solution suggestion = new Solution("Michael", "Closet","MiniGun");
		assertEquals(null,board.handleSuggestion(suggestion, playerOne.getName(), null));
		assertEquals(null,board.handleSuggestion(suggestion, playerTwo.getName(), null));
		assertEquals(null,board.handleSuggestion(suggestion, playerThree.getName(), null));
		assertEquals(null,board.handleSuggestion(suggestion, human.getName(), null));
		
		//Suggestion only human player can disprove
		suggestion = new Solution("Colonel Mustard","Office","MiniGun");
		assertEquals(null,board.handleSuggestion(suggestion, human.getName(), null));
		assertEquals(personSix,board.handleSuggestion(suggestion, playerOne.getName(), null));
		assertEquals(personSix,board.handleSuggestion(suggestion, playerTwo.getName(), null));
		assertEquals(personSix,board.handleSuggestion(suggestion, playerThree.getName(), null));
		
		suggestion = new Solution("Michael","Office", "Knife");
		assertEquals(weaponFive, board.handleSuggestion(suggestion, human.getName(), null));
		assertEquals(weaponFive,board.handleSuggestion(suggestion, playerOne.getName(), null));
		assertEquals(weaponFive,board.handleSuggestion(suggestion, playerTwo.getName(), null));
		assertEquals(null,board.handleSuggestion(suggestion, playerThree.getName(), null));
		
		suggestion = new Solution("Michael","Billiard room", "MiniGun");
		assertEquals(null, board.handleSuggestion(suggestion, human.getName(), null));
		assertEquals(roomSix,board.handleSuggestion(suggestion, playerOne.getName(), null));
		assertEquals(roomSix,board.handleSuggestion(suggestion, playerTwo.getName(), null));
		assertEquals(roomSix,board.handleSuggestion(suggestion, playerThree.getName(), null));
		

		suggestion = new Solution("Colonel Mustard","Office","Knife");
		assertEquals(weaponFive,board.handleSuggestion(suggestion, human.getName(), null));
		assertEquals(weaponFive,board.handleSuggestion(suggestion, playerOne.getName(), null));
		assertEquals(weaponFive,board.handleSuggestion(suggestion, playerTwo.getName(), null));
		assertEquals(personSix,board.handleSuggestion(suggestion, playerThree.getName(), null));
		
		
		
	}
	//The following three methods select a target where the
	//tests include a set of targets that include a room,
	//a random selection from a set of targets that don't include a room
	//and a test that includes the last visited room.
	@Test
	public void testLocation(){
		ComputerPlayer player = new ComputerPlayer("Joe", 0, 0, Color.blue);
		// Pick a location with no rooms in target, just three targets
		board.calcTargets(5, 0, 3);
		boolean loc_5_1 = false;
		boolean loc_5_3 = false;
		boolean loc_6_2 = false;
		boolean loc_6_0 = false;
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(5, 1))
				loc_5_1 = true;
			else if (selected == board.getCellAt(5, 3))
				loc_5_3 = true;
			else if (selected == board.getCellAt(6, 0))
				loc_6_0 = true;
			else if (selected == board.getCellAt(6, 2))
				loc_6_2 = true;
			else
				fail("Invalid target selected");
		}
		// Ensure each target was selected at least once
		assertTrue(loc_5_1);
		assertTrue(loc_5_3);
		assertTrue(loc_6_2);
		assertTrue(loc_6_0);
	}

	@Test
	public void testLocationRoom(){
		ComputerPlayer player = new ComputerPlayer("Joe", 0, 0, Color.blue);
		player.setLastRoom('C');

		board.calcTargets(18, 17, 3);
		boolean loc_21_17 = false;
		boolean loc_15_17 = false;
		boolean loc_20_18 = false;
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(21, 17))
				loc_21_17 = true;
			else if (selected == board.getCellAt(15, 17))
				loc_15_17 = true;
			else if (selected == board.getCellAt(20, 18))
				loc_20_18 = true;
			else
				fail("Invalid target selected");
		}

		assertFalse(loc_21_17);
		assertFalse(loc_15_17);
		assertTrue(loc_20_18);

	}
	
	@Test 
	public void testLastRoom(){
		ComputerPlayer player = new ComputerPlayer("Joe", 0, 0, Color.blue);
		player.setLastRoom('H');
	
		board.calcTargets(18, 17, 3);
		boolean loc_21_17 = false;
		boolean loc_15_17 = false;
		boolean loc_20_18 = false;
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(21, 17))
				loc_21_17 = true;
			else if (selected == board.getCellAt(15, 17))
				loc_15_17 = true;
			else if (selected == board.getCellAt(20, 18))
				loc_20_18 = true;
			else
				fail("Invalid target selected");
		}

		assertTrue(loc_21_17);
		assertTrue(loc_15_17);
		assertTrue(loc_20_18);
	}
	//Computer player makes suggestion where only one suggestion is possible
	//Computer makes multiple suggestions that include all the cards the computer
	//has not see i.e. the solution and the cards of the other computer or player.
	@Test
	public void suggestionTest(){
		//test only one possibility
		board.clearPlayers();
		ComputerPlayer player = new ComputerPlayer("Joe", 11, 21, Color.blue);
		board.addPlayableCharacters(player);
		BoardCell location = new BoardCell(11,21,'D', DoorDirection.DOWN);
		
		board.createDeck();
		board.selectAnswer();
		board.deal();
		Solution solution = board.getSolution();
		String person = solution.getPerson();
		String room = Board.getRooms().get(location.getInitial());
		String weap = solution.getWeapon();
		
		assertTrue(person.equals(player.makeSuggestion(board, location).getPerson()));
		assertTrue(room.equals(player.makeSuggestion(board, location).getRoom()));
		assertTrue(weap.equals(player.makeSuggestion(board, location).getWeapon()));
		
		//Test more than one possibility
		board.clearPlayers();
		ComputerPlayer playerOne = new ComputerPlayer("Joe", 11, 21, Color.blue);
		ComputerPlayer playerTwo = new ComputerPlayer("Michael", 13, 1, Color.cyan);
		board.addPlayableCharacters(playerOne);
		board.addPlayableCharacters(playerTwo);
		board.createDeck();
		board.selectAnswer();
		board.deal();
		solution = board.getSolution();
		person = solution.getPerson();
		room = Board.getRooms().get(location.getInitial());
		weap = solution.getWeapon();
		LinkedList<String> people= new LinkedList<String>();
		LinkedList<String> weapons = new LinkedList<String>();
		people.add(person);
		weapons.add(weap);
		for(Card c: playerTwo.getHand()){
			if(c.getCardType() == CardType.PERSON){
				people.add(c.getCardName());
			}
			else if(c.getCardType() == CardType.WEAPON){
				weapons.add(c.getCardName());
			}
		}
		assertTrue(people.contains(playerOne.makeSuggestion(board, location).getPerson()));
		assertTrue(weapons.contains(playerOne.makeSuggestion(board, location).getWeapon()));			
	}
}
