package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import Game.Board;


public class ClueGame extends JFrame{
	private Control control;
	private Board board;
	private DetectiveNotes dnDialog;
	public ClueGame(){
		setSize(1000, 800);
		setTitle("Clue");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board = new Board();
		board.initialize();
		control = new Control();
		add(board, BorderLayout.CENTER);
		add(control, BorderLayout.SOUTH);
		
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
	
	public static void main(String[] args) {
		ClueGame clue = new ClueGame();
		JOptionPane.showMessageDialog(clue, "You are Miss Scarlet! Press Next Player to begin playing.", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		clue.setVisible(true);
	}
}
