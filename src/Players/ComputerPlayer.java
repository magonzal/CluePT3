package Players;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import javax.swing.JPanel;

import Game.*;

public class ComputerPlayer extends Player{
	LinkedList<Card> seen;
	
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		seen = new LinkedList<Card>();
	}

	//Picks a proper location for the computer location to move to
	public BoardCell pickLocation(Set<BoardCell> targets){
		for(BoardCell cell: targets){
			if(cell.isRoom() && cell.getColumn() != getColumn() && cell.getRow() != getRow() && cell.getInitial() != getLastroom())
				return cell;
		}
		LinkedList<BoardCell> cellTargets = new LinkedList<BoardCell>(targets);
		Collections.shuffle(cellTargets);
		return cellTargets.getFirst();
		
	}
	
	public void makeAccusation(){
		
	}
	
	//Allows the computer player to make a suggestion with known cards
	public Solution makeSuggestion(Board board, BoardCell location){
		LinkedList<Card> deckBeforeHand = board.getDeckBeforeDeal();
		LinkedList<Card> hand = new LinkedList<Card>(getHand());
		LinkedList<Card> temp = new LinkedList<Card>();
		Solution suggestion = new Solution("","","");
		for(Card c: deckBeforeHand){
			if(!hand.contains(c)){
				temp.add(c);
			}
		}

		LinkedList<Card> notSeen = new LinkedList<Card>();
		for(Card c: temp){
			if(!seen.contains(c)){
				notSeen.add(c);
			}
		}
		Map<Character,String> rooms = Board.getRooms();
		if(location.getRow() == getRow() && location.getColumn() == getColumn()){
			suggestion.setRoom(rooms.get(location.getInitial()));
		}
		else{
			Collections.shuffle(notSeen);
			for(Card c: notSeen){
				if(c.getCardType().equals(CardType.ROOM)){
					suggestion.setRoom(c.getCardName());
				}
			}
		}
		Collections.shuffle(notSeen);
		for(Card c: notSeen){
			if(c.getCardType().equals(CardType.WEAPON)){
				suggestion.setWeapon(c.getCardName());
			}
			else if(c.getCardType().equals(CardType.PERSON)){
				suggestion.setPerson(c.getCardName());
			}
		}
		return suggestion;
	}
	
	//Getter/Setter methods
	public void addSeen(Card card){
		seen.add(card);
	}
	
	public LinkedList<Card> getSeen(){
		return seen;
	}
	
	public void makeMove(Set<BoardCell> targets, Graphics g){
		BoardCell loc = pickLocation(targets);
		this.draw(g, loc.getColumn()*20, loc.getRow()*20, 20, 20);

	}
}
