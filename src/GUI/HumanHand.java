package GUI;

import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import Game.Board;
import Players.Card;
import Players.CardType;

public class HumanHand extends JPanel {
	JPanel cardPanel;
	Board board;
	static final int dim = 10;
	
	public HumanHand(){
		board = new Board();
		board.initialize();
		setLayout(new GridLayout(0, 1));
	}
	
	private JPanel createPanel(){
		cardPanel = new JPanel();
		setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));
		
		for(Card c: board.human.getHand()){
			
		}
		
		return cardPanel;
	}
	
	private JPanel peopleCards(Graphics g){
		JPanel people = new JPanel();
		setBorder(new TitledBorder(new EtchedBorder(), "People"));
		
		for(Card c: board.human.getHand()){
			if(c.getCardType() == CardType.PERSON){
				g.drawRect(dim, dim, dim, dim);
				g.drawString(c.getCardName(), dim/2, dim);
			}
		}
		
		return people;
	}
	
	
}
