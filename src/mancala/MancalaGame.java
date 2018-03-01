package mancala;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * MVC View Class for MancalaGUI
 * @author TeamNemo
 *
 */
public class MancalaGame implements ChangeListener{
	private static Font font = new Font("Arial", Font.BOLD, 18);
	private DataModel dataModel;
	private BoardPanel board;
	private JLabel msgLabel;
	private JButton nextButton;
	private JButton undoButton;
	private JLabel undoTimesLabel;
	
	/**
	 * Constructor for the Mancala GUI
	 * Initializes the board and dataModel
	 * DataModel attaches board as part of MVC strategy
	 * next and undo buttons are added to buttonPanel which is added to GUI Frame
	 */
	public MancalaGame() {
		dataModel = new DataModel();
		dataModel.attach(this);
		board = new BoardPanel(dataModel);
		dataModel.attach(board);
		
		JPanel buttonPanel = new JPanel();
		nextButton = new JButton("Next");
		nextButton.setFont(font);
		nextButton.addActionListener(e -> {
			dataModel.nextPlayer();
		});
		undoTimesLabel = new JLabel();
		undoTimesLabel.setFont(font);
		undoButton = new JButton("Undo");
		undoButton.addActionListener(e -> {
			dataModel.undoATurn();
		});
		undoButton.setFont(font);
		buttonPanel.add(nextButton);
		buttonPanel.add(undoButton);
		buttonPanel.add(undoTimesLabel);
		JPanel msgPanel = new JPanel();
		msgLabel = new JLabel();
		msgLabel.setFont(font);
		msgPanel.add(msgLabel);
		
		final JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.add(board, BorderLayout.NORTH);
		frame.add(buttonPanel, BorderLayout.EAST);
		frame.add(msgPanel, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		
		final ConfigDialog dialog = new ConfigDialog();
		dialog.attach(e -> {
			board.setFormat(dialog.getStyle());
			dataModel.init(dialog.getMarbleCount());
			frame.setVisible(true);
			update();
		});
		dialog.pack();
		dialog.setVisible(true);
	}
	
	/**
	 * Updates the dataModel and determines the number of undos left for the current player
	 * Checks if game has ended and gets the values of the pits and compares them to see which
	 * player won
	 */
	private void update() {
		int a = dataModel.getMancalaA();
		int b = dataModel.getMancalaB();
		nextButton.setEnabled(dataModel.isTurnEnd());
		undoButton.setEnabled(dataModel.allowUndo());
		undoTimesLabel.setText(String.format("%d times left", dataModel.getUndoTimes()));
		boolean end = dataModel.checkGameEnd();
		if (end) {
			msgLabel.setText(String.format("Game End: %d vs %d, Player %s win", a, b, a > b ? "A" : "B"));
		} else if (dataModel.isTurnEnd()) {
			msgLabel.setText(String.format("Player %s's turn ends, click Next or Undo", dataModel.getIsPlayerA() ? "A" : "B"));
		} else {
			msgLabel.setText(String.format("It's player %s's turn", dataModel.getIsPlayerA() ? "A" : "B"));
		}
	}

	/**
	 * If the data of the model class has been changed, view class
	 * will tell the board to update and update the GUI accordingly
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		board.stateChanged(e);
		update();
	}

	
	
}