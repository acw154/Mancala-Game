package mancala;

import java.awt.*;
import java.awt.geom.*;

/**
 * Concrete Strategy class EasterBoardFormat implements BoardFormat strategy
 * @author TeamNemo
 *
 */
public class EasterBoardFormat implements BoardFormat {

	/**
	 * Returns the background image for this format
	 * @return the image for this format
	 */
	public Image backgroundImg() {
		Image img = Toolkit.getDefaultToolkit().getImage("easter_board.jpg");
        return img;
	}
	
	/**
	 * Sets pit dimensions
	 */
	@Override
	public Shape formatPits(PitShape ps) {
		return new RoundRectangle2D.Double(ps.getX(), ps.getY(), ps.getWidth(), ps.getHeight(), 15, 15);
	}
	
	/**
	 * Returns width of the board.
	 */
	@Override
	public int getWidth() {
		return 1280;
	}
	
	/**
	 * Returns the left position of mancala A.
	 */
	@Override
	public int getMancalaLeft() {
		return 50;
	}

	/**
	 * Returns the top position of the mancala.
	 */
	@Override
	public int getMancalaTop() {
		return 130;
	}

	/**
	 * Returns the width of the mancala.
	 */
	@Override
	public int getMancalaWidth() {
		return 110;
	}

	/**
	 * Returns the height of the mancala.
	 */
	@Override
	public int getMancalaHeight() {
		return 200;
	}

	/**
	 * Returns the left position of the first pit.
	 */
	@Override
	public int getPitLeft() {
		return 165;
	}

	/**
	 * Returns the top position of the first pit.
	 */
	@Override
	public int getPitTop() {
		return 60;
	}

	/**
	 * Returns the width of the pit.
	 */
	@Override
	public int getPitWidth() {
		return 155;
	}

	/**
	 * Returns the height of the pit.
	 */
	@Override
	public int getPitHeight() {
		return 160;
	}

	/**
	 * Returns the horizontal border of the pit.
	 */
	@Override
	public int getPitBorderX() {
		return 5;
	}

	/**
	 * Returns the vertical border of the pit.
	 */
	@Override
	public int getPitBorderY() {
		return 10;
	}
}


