package mancala;

import java.awt.Image;
import java.awt.Shape;

/**
 * Strategy class that defines the methods needed for other BoardFormats
 * @author TeamNemo
 *
 */
public interface BoardFormat {

	/**
	 * Gets the shape of the formatted Pit
	 * @param ps the Pit to be formatted
	 * @return the shape of the formatted Pit
	 */
	Shape formatPits(PitShape ps);
	
	/**
	 * Gets the image of the background of the board
	 * @return the image of the background of the board
	 */
	Image backgroundImg();

	/*
	 * @return the width of the board.
	 */
	int getWidth();
	
	/*
	 * @return the left position of mancala A.
	 */
	int getMancalaLeft();
	
	/*
	 * @return the top position of the mancala.
	 */
	int getMancalaTop();
	
	/*
	 * @return the width of the mancala.
	 */
	int getMancalaWidth();
	
	/*
	 * @return the height of the mancala.
	 */
	int getMancalaHeight();
	
	/*
	 * @return the left position of first pit.
	 */
	int getPitLeft();
	
	/*
	 * @return the top position of first pit.
	 */
	int getPitTop();
	
	/*
	 * @return the width of the pit.
	 */
	int getPitWidth();
	
	/*
	 * @return the height of the pit.
	 */
	int getPitHeight();
	
	/*
	 * @return the horizontal border of the pits.
	 */
	int getPitBorderX();
	
	/*
	 * @return the vertical border of the pits.
	 */
	int getPitBorderY();
}