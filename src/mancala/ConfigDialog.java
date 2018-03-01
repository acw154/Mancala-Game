package mancala;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Initial dialog that prompts user for number of stones
 * @author TeamNemo
 *
 */
public class ConfigDialog extends JDialog implements ActionListener {
	private List<ChangeListener> listeners; 
	private JTextField marbleCountField;
	private JRadioButton easterStyle;
	private JRadioButton goStyle;
	private static Font font = new Font("Arial", Font.BOLD, 18);
	
	/**
	 * Constructor for ConfigDialog class.
	 * Prompts users for number of stones and initializes changeListener array.
	 */
	public ConfigDialog() {
		listeners = new ArrayList<ChangeListener>();
		this.setLayout(new BorderLayout());
		// Blocks parent frame.
		this.setModal(true);
		
		JPanel topPanel = new JPanel();
		this.setPreferredSize(new Dimension(400, 150));
		JLabel label = new JLabel("Enter the marbles per pit:");
		label.setFont(font);
		topPanel.add(label);
		marbleCountField = new JTextField(2);
		marbleCountField.setText("3");
		marbleCountField.setFont(font);
		topPanel.add(marbleCountField);
		topPanel.setPreferredSize(new Dimension(400, 40));
		this.add(topPanel, BorderLayout.NORTH);
		
		ButtonGroup group = new ButtonGroup();
		easterStyle = new JRadioButton("Easter Style");
		easterStyle.setFont(font);
		goStyle = new JRadioButton("GO Style");
		goStyle.setFont(font);
		group.add(easterStyle);
		group.add(goStyle);
		easterStyle.setSelected(true);
		JPanel panel = new JPanel();
		panel.add(easterStyle);
		panel.add(goStyle);
		this.add(panel, BorderLayout.CENTER);
		panel.setPreferredSize(new Dimension(400, 60));
		
		JButton startButton = new JButton("Start");
		startButton.addActionListener(this);
		startButton.setFont(font);
		startButton.setPreferredSize(new Dimension(100, 45));
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(startButton);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Attaches changeListener to the JDialog to update the number of stones
	 * @param listener
	 */
	public void attach(ChangeListener listener) {
		this.listeners.add(listener);
	}
	
	/**
	 * Notifies changeListeners of ConfigDialog when an event occurs
	 */
	public void notifyListeners() {
		ChangeEvent ce = new ChangeEvent(this);
		for (ChangeListener listener : listeners) {
			listener.stateChanged(ce);
		}
	}
	
	/**
	 * Retrieves user input on how many marbles to initialize the board with 
	 * and returns the value as an integer
	 * @return an integer equal to the number of marbles inputed by the user
	 */
	public int getMarbleCount() {
		return Integer.valueOf(marbleCountField.getText().trim());
	}
	
	/**
	 * Returns the format of the board
	 * @return new boardFormat object depending on which boardFormat is in use
	 */
	public BoardFormat getStyle() {
		if (easterStyle.isSelected()) {
			return new EasterBoardFormat();
		} else {
			return new GOBoardFormat();
		}
	}
	
	/**
	 * Notifies changeListeners when the button is pressed and disposes of the JDialog frame
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		notifyListeners();
		this.dispose();
	}
}
