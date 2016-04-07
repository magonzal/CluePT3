package Game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDir;
	
	
	public BoardCell(int row, int column, char initial, DoorDirection d) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.doorDir = d;
	}
	public BoardCell(int row, int column, char initial, char direction) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
		switch (direction)
		{
			case 'L': doorDir = DoorDirection.LEFT;
						break;
			case 'U': doorDir = DoorDirection.UP;
						break;
			case 'R': doorDir = DoorDirection.RIGHT;
						break;
			case 'D': doorDir = DoorDirection.DOWN;
						break;
			default: doorDir = DoorDirection.NONE;
		}
	}
	public char getInitial() {
		return initial;
	}

	public DoorDirection getDoorDirection() {
		return doorDir;
	}

	public boolean isWalkway() {
		return getInitial() == 'W';
	}
	
	public boolean isRoom() {
		if (getInitial() != 'W' && getInitial() != 'X') {
			return true;
		}
		return false;
	}
	
	public boolean isDoorway() {
		if (doorDir != DoorDirection.NONE)
			return true;
		return false;
	}
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + ", initial=" + initial + ", doorDir=" + doorDir + "]";
	}
	
	public int getRow(){
		return row;
	}
	
	public int getColumn(){
		return column;
	}
	public void draw(Graphics g, int r, int c, int w, int h){
		int x = r;
		int y = c;
		g.setColor(Color.gray);
		g.fillRect(x, y, w, h);
		if(isWalkway()){
			g.setColor(Color.orange);
			g.fillRect(x, y, w-1, h-1);

		}
		else if(isDoorway()){
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.BLUE);
			g2.setStroke(new BasicStroke(3));
			switch(getDoorDirection()){
				case UP: g2.drawLine(x,y,x+20,y);
					break;
				case DOWN: g2.drawLine(x+20, y+18, x, y+18);
					break;
				case LEFT: g2.drawLine(x, y, x, y+20);
					break;
				case RIGHT: g2.drawLine(x+18, y, x+18, y+20);
					break;
			
			}
	
		}
	}
}

	