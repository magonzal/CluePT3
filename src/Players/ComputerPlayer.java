package Players;

import java.awt.Color;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import Game.*;

public class ComputerPlayer extends Player{
	LinkedList<Card> seen;
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		seen = new LinkedList<Card>();
	}

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
	
	public void addSeen(Card card){
		seen.add(card);
	}
	
	public LinkedList<Card> getSeen(){
		return seen;
	}
	


}
