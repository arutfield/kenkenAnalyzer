package board;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import boardComponents.Condition;
import boardComponents.Entry;
import boardComponents.SymbolEnum;
import exceptions.BoardException;
import exceptions.MissingPossibilityException;
import exceptions.ValueOutOfRangeException;

public class Board {

	private Entry[][] entries;
	private final int boardSize;
	private static boolean printingEnabled = true;
	private static Logger log = LogManager.getLogger(Board.class);
	private LinkedList<Condition> conditionsList = new LinkedList<Condition>();


	/** Constructor
	 * @param size number of entries for one side of board
	 */
	public Board(int size) {
		conditionsList.clear();
		entries = new Entry[size][size];
		boardSize = size;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				entries[i][j] = new Entry(size, i, j);
			}
		}

	}

	// copy constructor
	/** copy board
	 * @param board to copy
	 * @throws BoardException error
	 * @throws ValueOutOfRangeException error
	 */
	public Board(Board board) throws BoardException, ValueOutOfRangeException {
		boardSize = board.getSize();
		entries = new Entry[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				entries[i][j] = new Entry(board.getEntry(i, j));
			}
		}
		conditionsList = new LinkedList<Condition>();
		for (Condition prevCondition : board.getConditions()) {
			LinkedList<Entry> cageEntries = new LinkedList<Entry>();
			for (Entry prevConditionEntry : prevCondition.getCageEntries()) {
				int index = prevConditionEntry.getIndex();
				cageEntries.add(getEntry(index / boardSize, index % boardSize));
			}
			conditionsList.add(new Condition(prevCondition, cageEntries));

		}
	}


	private LinkedList<Condition> getConditions() {
		return conditionsList;
	}

	public void addCondition(Condition condition) throws BoardException {
		try {
			if (condition.getOperation() == SymbolEnum.Equals) {
				for (int i=0; i<condition.getCageEntries().size(); i++) {
					int index = condition.getCageEntries().get(i).getIndex();
					getEntry(index / getSize(), index % getSize()).setValue(condition.getOpValue());
				}
			} else {
				conditionsList.add(condition);
			}
		} catch (ValueOutOfRangeException e) {
			throw new BoardException("Error adding condition: " + e.getMessage());
		}
	}

	/** toggle if board should be printed to log
	 * @param enable true if should print
	 */
	public void setPrinting(boolean enable) {
		printingEnabled = enable;
	}


	/** get entry in particle row or column
	 * @param row of entry
	 * @param column of entry
	 * @return entry
	 * @throws BoardException error
	 */
	public Entry getEntry(int row, int column) throws BoardException {
		if (row > entries.length || column > entries.length) {
			throw new BoardException(
					"requesting position outside of range. Searching for position [" + row + "," + column + "]");
		}
		try {
			return entries[row][column];
		} catch (ArrayIndexOutOfBoundsException ex) {
			throw new BoardException("out of bounds getting row "+ row + " and column " + column);
		}
	}

	private void filterEntryPossibilities(int row, int column)
			throws BoardException, MissingPossibilityException, ValueOutOfRangeException {
		Entry mainEntry = getEntry(row, column);
		if (mainEntry.getValue() != 0) {
			return;
		}
		//check conditions
		checkConditions(mainEntry);


		//check row and column to avoid duplicates
		for (int i = 0; i < entries.length; i++) {
			if (i == row) {
				continue;
			}
			int entryValue = getEntry(i, column).getValue();
			if (entryValue != 0 && mainEntry.getPossibilities().contains(entryValue)) {
				// remove if it is in the column already but is still considered possible
				mainEntry.removePossibility(entryValue);
			}
		}

		for (int i = 0; i < entries.length; i++) {
			if (i == column) {
				continue;
			}
			int entryValue = getEntry(row, i).getValue();
			if (entryValue != 0 && mainEntry.getPossibilities().contains(entryValue)) {
				// remove if it is in the row already but is still considered possible
				mainEntry.removePossibility(entryValue);
			}
		}
	}


	private void checkConditions(Entry entry) throws ValueOutOfRangeException {
		LinkedList<Condition> conditions = getConditionsWithEntry(entry);
		for (Condition condition : conditions) {
			switch(condition.getOperation()) {
			case Equals:
				throw new ValueOutOfRangeException("Equals should not be in condition list");
			case Plus:
				int minimumTotalOfOtherEntries = condition.getMinimumTotalForOtherCageValues(entry);
				if (condition.allOtherCageValuesFull(entry)) {
					//all cage values are full. only one possibility
					if (entry.getPossibilities().contains(condition.getOpValue() - minimumTotalOfOtherEntries)) {
						entry.setValue(condition.getOpValue() - minimumTotalOfOtherEntries);
					} else { // if only option is not possible, there are no possibilities
						entry.getPossibilities().clear();
					}
					return;
				}
				for(int i = 0; i<entry.getPossibilities().size(); i++) {
					int possible = entry.getPossibilities().get(i);
					//possible value must be less than or equal to the op value minus the minimum possible total of other cage values
					if (possible > condition.getOpValue() - minimumTotalOfOtherEntries) {
						entry.getPossibilities().remove(i);
						i--;
						continue;
					}
				}
				break;
			case Minus:
				Entry otherEntry = condition.getOtherEntry(entry);
				for (int i = 0; i<entry.getPossibilities().size(); i++) {
					boolean couldBeValue = false;
					int currentPossible = entry.getPossibilities().get(i);
					for (int otherPossible : otherEntry.getPossibilities()) {
						if (Math.abs(otherPossible - currentPossible) == condition.getOpValue()) {
							couldBeValue = true;
							break;
						}
					}
					if (!couldBeValue) {
						entry.getPossibilities().remove(i);
						i--;
					}
				}
				break;
			case Times:
				int minimumProductOfOtherEntries = condition.getMinimumProductForOtherCageValues(entry);
				if (condition.allOtherCageValuesFull(entry)) {
					//all cage values are full. only one possibility
					if (entry.getPossibilities().contains(condition.getOpValue() / minimumProductOfOtherEntries)) {
						entry.setValue(condition.getOpValue() / minimumProductOfOtherEntries);
					} else { // if only option is not possible, there are no possibilities
						entry.getPossibilities().clear();
					}
					return;
				}
				for(int i = 0; i<entry.getPossibilities().size(); i++) {
					int possible = entry.getPossibilities().get(i);
					//possible value must be less than or equal to the op value divided by minimum possible total of other cage values
					// possible must also be a factor of opValue
					if ((condition.getOpValue() < possible * minimumProductOfOtherEntries) || !isFactorOf(condition.getOpValue(), possible)) {
						entry.getPossibilities().remove(i);
						i--;
						continue;
					}
				}
				break;
			case Divide:
				Entry otherEntryDivide = condition.getOtherEntry(entry);
				for (int i = 0; i<entry.getPossibilities().size(); i++) {
					boolean couldBeValue = false;
					int currentPossible = entry.getPossibilities().get(i);
					for (int otherPossible : otherEntryDivide.getPossibilities()) {
						if (currentPossible * condition.getOpValue() == otherPossible ||
								otherPossible * condition.getOpValue() == currentPossible) {
							couldBeValue = true;
							break;
						}
					}
					if (!couldBeValue) {
						entry.getPossibilities().remove(i);
						i--;
					}
				}
				break;
			}
		}
	}

	private boolean isFactorOf(int opValue, int possible) {
		return ((opValue / possible) * possible == opValue);
	}

	private LinkedList<Condition> getConditionsWithEntry(Entry entry) {
		LinkedList<Condition> conditionsWithEntry = new LinkedList<Condition>();
		for (Condition condition : conditionsList) {
			for (Entry conditionEntry : condition.getCageEntries()) {
				if (entry.getIndex() == conditionEntry.getIndex()) {
					conditionsWithEntry.add(condition);
				}
			}
		}
		return conditionsWithEntry;
	}

	/** filters all entries removing impossible options
	 * @throws BoardException error
	 * @throws MissingPossibilityException error
	 * @throws ValueOutOfRangeException error
	 */
	public void filterAllEntries() throws BoardException, MissingPossibilityException, ValueOutOfRangeException {

			for (int r = 0; r < boardSize; r++) {
				for (int c = 0; c < boardSize; c++) {
					filterEntryPossibilities(r, c);
				}
			}
	}

	/** count total possible board combinations with this inital board
	 * @return total possible combinations
	 * @throws BoardException error
	 * @throws MissingPossibilityException error
	 * @throws ValueOutOfRangeException error
	 */
	public int countTotalBoardCombinations()
			throws BoardException, MissingPossibilityException, ValueOutOfRangeException {

		filterAllEntries();
		// check if board is valid (all entries have possibilities)
		if(!isBoardValid()) {
			return 0;
		}
		if (isBoardFilled()) {
			return 1; // this board is filled, return it as one filled board
		}
		int sum = 0;
		for (int r = 0; r < boardSize; r++) {
			for (int c = 0; c < boardSize; c++) {
				// only act on first empty
				if (getEntry(r, c).getValue() == 0) {
					LinkedList<Integer> possibles = getEntry(r, c).getPossibilities();

					for (int possibleValue : possibles) {
						Board possibleBoard = new Board(this);
						possibleBoard.getEntry(r, c).setValue(possibleValue);
						sum += possibleBoard.countTotalBoardCombinations();
					}
					return sum;
				}

			}
		}
		throw new BoardException("should not reach here");
	}

	private boolean isBoardValid() throws BoardException {
		for (int r=0; r<boardSize; r++) {
			for (int c=0; c<boardSize; c++) {
				if (getEntry(r, c).getPossibilities().isEmpty())
					return false;
			}
		}
		return true;

	}

	/** check if board is filled
	 * @return true if filled
	 * @throws BoardException error
	 */
	public boolean isBoardFilled() throws BoardException {
		for (int r = 0; r < boardSize; r++) {
			for (int c = 0; c < boardSize; c++) {
				if (getEntry(r, c).getValue() == 0) {
					return false;
				}
			}
		}
		printBoard();
		return true;
	}


	private void printBoard() throws BoardException {
		if (!printingEnabled) {
			return;
		}
		String boardString = "";
		for (int r = 0; r < boardSize; r++) {
			for (int c = 0; c < boardSize; c++) {
				boardString += getEntry(r, c).getValue();
				if (c == boardSize - 1)
					boardString += "|";
				else {
					boardString += ", ";
				}
			}

			boardString += "\n";
		}
		log.debug(boardString);
	}

	public int getSize() {
		return boardSize;
	}

}
