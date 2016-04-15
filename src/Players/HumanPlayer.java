package Players;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Set;

import Game.*;

public class HumanPlayer extends Player{

	public HumanPlayer(){
		super();
	}
	
	public HumanPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
	}
	
	public void makeMove(Set<BoardCell> targets, Graphics g){
		for(BoardCell c: targets){
			g.setColor(Color.cyan);
			g.fillRect(c.getColumn()*20, c.getRow()*20, 18, 18);
		}
	}

}
