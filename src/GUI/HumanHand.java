package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import Game.Board;
import Players.Card;
import Players.CardType;

public class HumanHand extends JPanel {
	JPanel cardPanel;
	Board board;
	static final int dim = 10;
	private JTextField name;
	
	public HumanHand(){
		board = new Board();
		board.initialize();
		setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));
		setSize(new Dimension(800, 200));
		setLayout(new GridLayout(0, 1));
		add(peopleCards());
		add(weaponCards());
		add(roomCards());
	}
	
	private JPanel peopleCards(){
		JPanel people = new JPanel();
		people.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		JPanel card = new JPanel();
		card.setBorder(new EtchedBorder());
		name = new JTextField(15);
		name.setEditable(false);
		for(Card c: board.human.getHand()){
			if(c.getCardType() == CardType.PERSON){
				name.setText(c.getCardName());
				card.add(name);
				people.add(card);
			}
		}
		
		return people;
	}
	
	private JPanel weaponCards(){
		JPanel weapons = new JPanel();
		weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		JPanel card = new JPanel();
		card.setBorder(new EtchedBorder());
		name = new JTextField(15);
		name.setEditable(false);
		for(Card c: board.human.getHand()){
			if(c.getCardType() == CardType.WEAPON){
				name.setText(c.getCardName());
				card.add(name);
				weapons.add(card);
			}
		}
		
		return weapons;
	}
	
	private JPanel roomCards(){
		JPanel rooms = new JPanel();
		rooms.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		JPanel card = new JPanel();
		card.setBorder(new EtchedBorder());
		name = new JTextField(15);
		name.setEditable(false);
		for(Card c: board.human.getHand()){
			if(c.getCardType() == CardType.ROOM){
				name.setText(c.getCardName());
				card.add(name);
				rooms.add(card);
			}
		}
		
		return rooms;
	}
}
