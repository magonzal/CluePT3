package Players;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private Set<Card> hand;
	private char lastRoom;

	
	public Player(){
		super();
	}
	
	public Player(String playerName, int row, int column, Color color) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		hand = new HashSet<Card>();
	}


	public Card disproveSuggestion(Solution suggestion){
		LinkedList<Card> matches = new LinkedList<Card>();
		for(Card c: hand){
			if(c.getCardName().equals(suggestion.person) || c.getCardName().equals(suggestion.room) || c.getCardName().equals(suggestion.weapon)){
				matches.add(c);
			}
		
		}
		if(matches.size() == 0){
			return null;
		}
		else if(matches.size() == 1) {
			return matches.get(0);
		} 
		else {
			Collections.shuffle(matches);
			return matches.get(0);
		}
		
	}
	
	@Override
	public boolean equals(Object other) {
		if( this == other ) return true;
		if(!(other instanceof Player)) return false;
		
		Player otherOne = (Player) other;
		return this.playerName.equals(otherOne.playerName) &&
				this.row == otherOne.row &&
				this.column == otherOne.column &&
				this.color.equals(otherOne.color);
	}
	
	public void draw(Graphics g, int x, int y, int w, int h){
		g.setColor(getColor());
		g.fillOval(x, y, w-2, h-2);
	}
	
	public void setHand(Card card){
		hand.add(card);
	}
	
	public void setTotalHand(Set<Card> cards){
		hand.addAll(cards);
	}
	public Set<Card> getHand(){
		return hand;
	}

	@Override
	public String toString() {
		return "Player [playerName=" + playerName + ", row=" + row + ", column=" + column + ", color=" + color + "]";
	}
	
	public Color getColor(){
		return color;
	}
	
	public String getName(){
		return playerName;
	}
	
	public int getColumn(){
		return column;
	}
	
	public int getRow(){
		return row;
	}

	
	public void setLastRoom(char room){
		lastRoom = room;
	}
	
	public char getLastroom(){
		return lastRoom;
	}

}
