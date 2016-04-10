package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import Game.Board;
import Players.Card;
import Players.Player;

public class DetectiveNotes extends JFrame{
	private Board board;
	private JCheckBox box;
	private JComboBox<String> word;

	public DetectiveNotes(){
		board = new Board();
		board.initialize();
		
		setLayout(new GridLayout(2,3));
		setSize(900,600);
		setTitle("Detective Notes");
		
		add(people());
		add(rooms());
		add(weapons());
		add(personCombo());
		add(roomCombo());
		add(weaponCombo());
		
	}
	
	public JPanel people(){
		JPanel panel = new JPanel();
		for(Player p: board.getPlayers()){
			box = new JCheckBox(p.getName());
			panel.add(box);
		}
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		panel.setLayout(new GridLayout(3,2));
		return panel;
	}
	
	public JPanel rooms(){
		JPanel panel = new JPanel();
		for(String s: Board.getRooms().values()){
			panel.add(new JCheckBox(s));
		}
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		panel.setLayout(new GridLayout(4,2));
		return panel;
	}
	
	public JPanel weapons(){
		JPanel panel = new JPanel();
		for(Card c: board.getWeapons()){
			panel.add(new JCheckBox(c.getCardName()));
		}
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		panel.setLayout(new GridLayout(3,2));
		return panel;
	}
	
	public JPanel east(){
		JPanel east = new JPanel();
		east.add(people());
		east.add(rooms());
		east.add(weapons());
		east.setLayout(new GridLayout(3,1));
		return east;
	}
	
	public JComboBox<String> personCombo(){
		JComboBox<String> box = new JComboBox<String>();
		for(Player p: board.getPlayers()){
			box.addItem(p.getName());
		}
		box.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		return box;
	}
	
	public JComboBox<String> roomCombo(){
		JComboBox<String> box = new JComboBox<String>();
		for(String s: Board.getRooms().values()){
			box.addItem(s);
		}
		box.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		return box;
	}
	
	public JComboBox<String> weaponCombo(){
		JComboBox<String> box = new JComboBox<String>();
		for(Card c: board.getWeapons()){
			box.addItem(c.getCardName());
		}
		box.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		return box;
	}

}
