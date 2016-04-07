package Game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class ClueGame extends JFrame{
	
	private Board board;
	private DetectiveNotes dnDialog;
	public ClueGame(){
		setSize(500,550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board = new Board();
		board.initialize();
		add(board, BorderLayout.CENTER);
		

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		

	}

	private JMenu createFileMenu()
	{
		JMenu menu = new JMenu("File"); 
		menu.add(createFileExitItem());
		menu.add(createDetectiveNotes());
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
		clue.setVisible(true);		
		
	}
}
