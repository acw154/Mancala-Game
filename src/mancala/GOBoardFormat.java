package mancala;

import java.awt.*;
import java.awt.geom.*;

/**
 * Concrete Strategy class GOBoardFormat implements BoardFormat strategy
 * @author TeamNemo
 *
 */
public class GOBoardFormat implements BoardFormat {

	/**
	 * Returns the shape of the pit relative to this format
	 * @return the shape of the pit relative to this format
	 */
	public Shape formatPits(PitShape ps) {
		return new Ellipse2D.Double(ps.getX(), ps.getY(), ps.getWidth(), ps.getHeight());
	}

	/**
	 * Returns the background image for this format
	 * @return the image for this format
	 */
	public Image backgroundImg() {
		Image img = Toolkit.getDefaultToolkit().getImage("go_board.png");
        return img;
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
		return 75;
	}
	
	/**
	 * Returns the top position of the mancala.
	 */
	@Override
	public int getMancalaTop() {
		return 100;
	}
	
	/**
	 * Returns the width of the mancala.
	 */
	@Override
	public int getMancalaWidth() {
		return 100;
	}
	
	/**
	 * Returns the height of the mancala.
	 */
	@Override
	public int getMancalaHeight() {
		return 260;
	}
	
	/**
	 * Returns the left position of the first pit.
	 */
	@Override
	public int getPitLeft() {
		return 207;
	}
	
	/**
	 * Returns the top position of the first pit.
	 */
	@Override
	public int getPitTop() {
		return 92;
	}

	/**
	 * Returns the width of the pit.
	 */
	@Override
	public int getPitWidth() {
		return 110;
	}
	
	/**
	 * Returns the height of the pit.
	 */
	@Override
	public int getPitHeight() {
		return 110;
	}
	
	/**
	 * Returns the horizontal border of the pit.
	 */
	@Override
	public int getPitBorderX() {
		return 37;
	}
	
	/**
	 * Returns the vertical border of the pit.
	 */
	@Override
	public int getPitBorderY() {
		return 38;
	}
}

