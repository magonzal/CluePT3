package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Control extends JPanel {
	private JTextField title;
	
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
		title = new JTextField(15);
		title.setEditable(false);
		panel.add(titleLabel);
		panel.add(title);
		JButton nextPlayer = new JButton("Next player");
		JButton accusation = new JButton("Make an accusation");
		panel.add(nextPlayer);
		panel.add(accusation);
		return panel;
	}
	
	private JPanel createDiePanel(){
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 10));
		panel.setLayout(new GridLayout(2,0));
		JLabel rollLabel  = new JLabel("Roll");
		title = new JTextField(5);
		title.setEditable(false);
		panel.add(rollLabel);
		panel.add(title);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		return panel;
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
