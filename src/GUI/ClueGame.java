package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import Game.Board;
import Players.Player;
import GUI.*;


public class ClueGame extends JFrame{
	private static Control control;
	private static Board board;
	private DetectiveNotes dnDialog;
	private HumanHand humanCards;
	
	public ClueGame(){
		setSize(1100, 650);
		setTitle("Clue");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board = new Board();
		board.initialize();
		control = new Control();
		humanCards = new HumanHand();
		add(board, BorderLayout.CENTER);
		add(control, BorderLayout.SOUTH);
		add(humanCards, BorderLayout.WEST);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
	}

	private JMenu createFileMenu()
	{
		JMenu menu = new JMenu("File"); 
		menu.add(createDetectiveNotes());
		menu.add(createFileExitItem());
		return menu;
	}
	
	private JMenuItem createDetectiveNotes(){
		JMenuItem detectiveNotes = new JMenuItem("Detective Notes");
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				dnDialog = new DetectiveNotes();
				dnDialog.setVisible(true);
			}
		}
		detectiveNotes.addActionListener(new MenuItemListener());
		return detectiveNotes;
	}
	
	private JMenuItem createFileExitItem()
	{
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());

		return item;
	}
	
	public static void nextPlayer(){
		int counter = 0;
		Random rand = new Random();
		int dieRoll =rand.nextInt(6) + 1;
		String  number =  Integer.toString(dieRoll);
		control.setDie(number);
		LinkedList<Player> players = new LinkedList<Player>();
		players.add(board.getHuman());
		players.addAll(board.getComputerPlayers());
		control.setName(players.getFirst().getName());
		board.nextPlayer(players.get(0), dieRoll, board.getGraphics());
		counter++;
		
		
	}
	public static void main(String[] args) {
		ClueGame clue = new ClueGame();
		JOptionPane.showMessageDialog(clue, "You are Miss Scarlet! Press Next Player to begin playing.", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		clue.setVisible(true);
	}
}
