package mancala;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class that defines the setup of the board for the use of the
 * MVC View class in the GUI
 * @author TeamNemo
 *
 */
public class BoardPanel extends JPanel implements ChangeListener, MouseListener {
	private DataModel data;
	private BoardFormat boardFormat;
	private PitShape[] pitInRowA;
	private PitShape[] pitInRowB;
	private PitShape mancalaA;
	private PitShape mancalaB;
	private List<PitShape> pits;
	
	/**
	 * Constructor for the BoardPanel class. Creates a board and initializes it to a specific format.
	 * BoardPanel initializes 6 pits per player
	 * @param data The data from the DataModel class 
	 */
	public BoardPanel(DataModel data) {
		setPreferredSize(new Dimension(1280, 500));
		this.data = data;
		boardFormat = new GOBoardFormat();
		pitInRowA = new PitShape[6];
		pitInRowB = new PitShape[6];
		pits = new ArrayList<PitShape>();
	}

	/**
	 * Sets the board format and dimensions 
	 * Initializes the pit dimensions and adds mouse listener
	 * @param format the layout and dimensions of the board 
	 */
	public void setFormat(BoardFormat format) {
		boardFormat = format;
		int width = format.getWidth();
		int mancalaLeft = format.getMancalaLeft();
		int mancalaTop = format.getMancalaTop();
		int mancalaWidth = format.getMancalaWidth();
		int mancalaHeight = format.getMancalaHeight();
		int pitLeft = format.getPitLeft();
		int pitTop = format.getPitTop();
		int pitWidth = format.getPitWidth();
		int pitHeight = format.getPitHeight();
		int pitBorderX = format.getPitBorderX();
		int pitBorderY = format.getPitBorderY();
		mancalaA = new PitShape(mancalaLeft, mancalaTop, mancalaWidth, mancalaHeight);
		mancalaA.setShape(format.formatPits(mancalaA));
		mancalaB = new PitShape(width - mancalaLeft - mancalaWidth, mancalaTop, mancalaWidth, mancalaHeight);
		mancalaB.setShape(format.formatPits(mancalaB));
		pits.clear();
		pits.add(mancalaA);
		pits.add(mancalaB);
		for (int i = 0; i < 6; i++) {
			int x = pitLeft + i * (pitWidth + pitBorderX);
			int y = pitTop;
			pitInRowA[i] = new PitShape(x, y, pitWidth, pitHeight);
			pitInRowA[i].setShape(format.formatPits(pitInRowA[i]));
			pitInRowB[i] = new PitShape(x, y + pitHeight + pitBorderY, pitWidth, pitHeight);
			pitInRowB[i].setShape(format.formatPits(pitInRowB[i]));
			pits.add(pitInRowA[i]);
			pits.add(pitInRowB[i]);
		}
		addMouseListener(this);
	}
	
	/**
	 * Controller method for updating the pits of the board
	 * Changes the number of marbles in each pit based on the data provided by the model
	 */
	public void updateGame() {
		int[] rowA = data.getRowA();
		int[] rowB = data.getRowB();
		mancalaA.setMarbles(data.getMancalaA());
		mancalaB.setMarbles(data.getMancalaB());
		for (int i = 0; i < 6; i++) {
			pitInRowA[i].setMarbles(rowA[i]);
			pitInRowB[i].setMarbles(rowB[i]);
		}
	}
	
	/**
	 * Viewer / Controller method response to Model notify method
	 * When viewer class is changed, it will call update game and repaint to 
	 * change the values of the pits and repaint the viewer accordingly
	 * @param e Viewer state changeEvent
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		updateGame();
		repaint();
	}
	/**
	 * Repaints the Mancala GUI 
	 * @param g a graphics object
	 */
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		g.drawImage(boardFormat.backgroundImg(), 0, 0, this);
		
		g.setColor(Color.BLUE);
		Font font = new Font("Futura", Font.BOLD, 100);
		g.setFont(font);
		g.drawString("A", 55, 100);
		g.drawString("B", 1155, 100);

		Graphics2D g2 = (Graphics2D) g;

		for (PitShape p : pits) {
			p.fill(g2);
		}
	}
	/**
	 * mouseClicked method will take the mouse area clicked on as an argument and distribute the stones
	 * from the pit that was clicked 
	 * @param e The part of the GUI that was clicked
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		PitShape[] pits;
		int x = 0;
		if (data.getIsPlayerA()) {
			x = 0;
			pits = pitInRowA;
		} else {
			x = 1;
			pits = pitInRowB;
		}
		for (int i = 0; i < 6; i++) {
			if (pits[i].contains(e.getPoint())) {
				data.distributeStones(x, i);
				break;
			}
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
