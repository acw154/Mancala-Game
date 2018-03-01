package mancala;

import java.util.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * MVC Model class for MancalaGUI project
 * @author TeamNemo
 *
 */
public class DataModel {
	// Stores the quantities of stones in 1 array.
	// data[0]: mancala_A, data[1..6]: second row left to right
	// data[7]: mancala_B, data[8..13]: first row right to left
	private int[] data;
	private int[] last;
	private boolean isPlayerA; // boolean to store which player is currently playing: 1 for playerA, 0 for playerB.
	private boolean allowUndo; // if the current status is after undo, then it is 0; otherwise it is 1.
	private static final int UNDO_LIMIT = 3; // the maximum undo limit
	private int undoTimes; // how many times the player have used undo within a round
	private List<ChangeListener> listeners;
	private boolean turnEnd;
	
	/**
	 * Constructor for DataModel class
	 * Initializes int arrays for the board and sets all variables to their default state
	 */
	public DataModel() {
		listeners = new ArrayList<>();
		data = new int[14];
		last = new int[14];
		isPlayerA = false;
		allowUndo = false;
		undoTimes = UNDO_LIMIT;
		turnEnd = false;
	}
	
	/**
	 * Initializes the board based on the number of stones inputed by the players and notifies view class
	 * @param numStones the number of stones that will placed in each pit at the start of the game
	 */
	public void init(int numStones) {
		for (int i = 0; i < 14; i++) {
			if (i == 0 || i == 7) {
				data[i] = 0;
			} else {
				data[i] = numStones;
			}
		}
		notifyListeners();
	}

	/**
	 * Attach the given listener.
	 * @param listener the given listener to listen the change.
	 */
	public void attach(ChangeListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Notifies all registered listeners.
	 */
	public void notifyListeners() {
		ChangeEvent ce = new ChangeEvent(this);
		for (ChangeListener listener : listeners) {
			listener.stateChanged(ce);
		}
	}
	
	/**
	 * Moves the stones on the board according to the choice of the player and notifies the viewer
	 * @param x the row number that indicates playerA or playerB to be chosen
	 * @param y the line number that indicates the pit location of a player
	 * @return true if the move has been completed, false if the player has another move or the move was invalid
	 */
	public boolean distributeStones(int x, int y) {
		if (x < 0 || x >= 2 || y < 0 || y >= 6) return false;
		if (turnEnd) return false;
		if (isPlayerA && x == 1) return false;
		if (!isPlayerA && x == 0) return false;
		int i = (x == 1 ? y + 1 : 13 - y);
		if (data[i] <= 0) return false;
		saveData();
		
		int n = data[i];
		data[i] = 0;
		turnEnd = true;
		while (n > 0) {
			i = (i+1) % 14;
			if (i == 0 && !isPlayerA) continue;
			if (i == 7 && isPlayerA) continue;
			n--;
			if (n == 0) {
				if (i == 0 || i == 7) {
					turnEnd = false;
				} else if (data[i] == 0) {
					int k = isPlayerA ? 0 : 7;
					data[k] += 1 + data[14 - i];
					data[14 - i] = 0;
					// Will added to 0 later.
					data[i] = -1;
				}
			}
			data[i]++;
		}
	
		if (checkGameEnd()) {
			for (i = 1; i <= 6; i++) {
				data[7] += data[i];
				data[i] = 0;
			}
			for (i = 8; i <= 13; i++) {
				data[0] += data[i];
				data[i] = 0;
			}
		}
		notifyListeners();
		return true;
	}
	
	/**
	 * Saves the current setup of the board and sets allowUndo to true if there are undoTimes is not zero
	 */
	private void saveData() {
		for (int i = 0; i < 14; i++) {
			last[i] = data[i];
		}
		if (undoTimes > 0) {
			allowUndo = true;
		}
	}
	
	/**
	 * Changes player and resets the undoTimes, allowUndo, and turnEnd before notifying the viewer
	 */
	public void nextPlayer() {
		isPlayerA = !isPlayerA;
		allowUndo = false;
		undoTimes = UNDO_LIMIT;
		turnEnd = false;
		notifyListeners();
	}
	
	/**
	 * Returns boolean turnEnd which tells if the current turn has come to an end
	 * @return true if there are no moves left and the player has clicked next
	 */
	public boolean isTurnEnd() {
		return turnEnd;
	}
	
	/**
	 * Returns the number of marbles in the leftmost pit
	 * @return data[0] the index of the array associated with the leftmost pit
	 */
	public int getMancalaA() {
		return data[0];
	}
	
	/**
	 * Returns the number of marbles in the rightmost pit 
	 * @return data[7] the index of the array associated with the rightmost pit 
	 */
	public int getMancalaB() {
		return data[7];
	}
	
	/**
	 * Returns an int array that has been set to the state of rowA
	 * @return a the int array equal to the data of rowA
	 */
	public int[] getRowA() {
		int[] a = new int[6];
		for (int i = 13; i >= 8; i--) {
			a[13 - i] = data[i];
		}
		return a;
	}
	
	/**
	 * Returns an int array that has been set to the state of rowB
	 * @return a the int array equal to the data of rowB
	 */
	public int[] getRowB() {
		int[] a = new int[6];
		for (int i = 1; i <= 6; i++) {
			a[i - 1] = data[i];
		}
		return a;
	}
	
	/**
	 * Checks if the game has ended by checking each row of pits to see if the entire row is empty.
	 * @return true if a row of pits is empty, false if neither row is empty
	 */
	public boolean checkGameEnd() {
		boolean isEmpty = true;
		for (int i = 1; i <= 6; i++) {
			if (data[i] > 0) {
				isEmpty = false;
				break;
			}
		}
		if (isEmpty) return true;
		isEmpty = true;
		for (int i = 8; i <= 13; i++) {
			if (data[i] > 0) {
				isEmpty = false;
				break;
			}
		}
		if (isEmpty) return true;
		return false;
	}
	
	/**
	 * Checks if the player can undo. 
	 * If the player can undo, the board will revert to its previous state and notify changeListeners
	 * @return true if the undo was allowed, false if boolean allowUndo is equal to false
	 */
	public boolean undoATurn() {
		if (!allowUndo) return false;
		allowUndo = false;
		undoTimes--;
		turnEnd = false;
		for (int i = 0; i < 14; i++) {
			data[i] = last[i];
		}
		notifyListeners();
		return true;
	}
	
	/**
	 * Returns the number of undos left for the current player's turn
	 * @return undoTimes the number of undos that are left for the players turn (max 3)
	 */
	public int getUndoTimes() {
		return undoTimes;
	}
	
	/**
	 * Returns boolean isPlayerA which will return true if the player is playerA
	 * @return true if the current player is playerA
	 */
	public boolean getIsPlayerA() {
		return isPlayerA;
	}
	
	/**
	 * Returns boolean allowUndo which will return true if the current player has not pressed undo 3 times
	 * @return true if the current player still has an undo left
	 */
	public boolean allowUndo() {
		return allowUndo;
	}
}