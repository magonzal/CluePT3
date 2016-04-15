package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import Game.Board;

public class Control extends JPanel {
	private JTextField title;
	private String number = "";
	private JTextArea display;
	private JTextArea dispName;
	private JButton nextPlayer;
	private String namePlayer= "";
	
	public Control() {
		setLayout(new GridLayout(2, 0));
		JPanel panel = createFirstPanel();
		add(panel, BorderLayout.CENTER);
		panel = createDiePanel();
		add(panel, BorderLayout.SOUTH);
		panel = createGuessPanel();
		add(panel, BorderLayout.SOUTH);
		panel = createGuessResultPanel();
		add(panel, BorderLayout.SOUTH);
	}

	private JPanel createFirstPanel(){
		JPanel panel = new JPanel();
		setLayout(new GridLayout(1, 0));
		JLabel titleLabel = new JLabel("Whose turn?");
		panel.add(titleLabel);
		dispName = new JTextArea(2,20);
		dispName.setText(namePlayer);
		updateDisplayTwo();
		panel.add(dispName);
			
		nextPlayer = new JButton("Next player");
		nextPlayer.addActionListener(new ButtonListener());
		JButton accusation = new JButton("Make an accusation");
		panel.add(nextPlayer);
		panel.add(accusation);
		return panel;
	}
	
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			ClueGame.nextPlayer();
		}
	}
	
	
	private JPanel createDiePanel(){
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 10));
		panel.setLayout(new GridLayout(2,0));
		JLabel rollLabel  = new JLabel("Roll");
		panel.add(rollLabel);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		display = new JTextArea(2,20);
		updateDisplay();
		panel.add(display);
		return panel;
	}
	public void updateDisplay(){
		display.setText(number);
	}
	public void setDie(String number){
		this.number = number;
		updateDisplay();
	}
	
	public void updateDisplayTwo(){
		dispName.setText(namePlayer);
	}
	
	public void setName(String name){
		this.namePlayer = name;
		updateDisplayTwo();
	}
	
	private JPanel createGuessPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,0));
		JLabel guessLabel = new JLabel("Guess");
		title = new JTextField(35);
		title.setEditable(false);
		panel.add(guessLabel);
		panel.add(title);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		return panel;
	}
	
	private JPanel createGuessResultPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 0));
		JLabel guessResultLabel = new JLabel("Response");
		title = new JTextField(15);
		title.setEditable(false);
		panel.add(guessResultLabel);
		panel.add(title);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		return panel;
	}
	
	

}
