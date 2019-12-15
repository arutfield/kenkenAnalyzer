package boardComponents;

import java.util.LinkedList;

import exceptions.MissingPossibilityException;
import exceptions.ValueOutOfRangeException;

/**
 * @author Alex
 *
 */
public class Entry {
	private final LinkedList<Integer> possibilities;
	private int value = 0; //default to 0 while unknown
	private final int size;
	private final int index;
	
	
	/** constructor
	 * @param size of one side of the board
	 */
	public Entry(int size, int row, int column) {
		this.size = size;
		possibilities = new LinkedList<Integer>();
		index = row*size + column;
		//when first created, all numbers are possible
		for (int i=1; i<=size; i++) {
			possibilities.add(i);
		}
	}
	
	//copy constructor
	public Entry(Entry prevEntry) throws ValueOutOfRangeException {
		this.size = prevEntry.size;
		possibilities = new LinkedList<Integer>();
		for (int possible: prevEntry.possibilities) {
			possibilities.add(possible);
		}
		int value = prevEntry.getValue();
		if (value != 0) {
			setValue(value);
		}
		index = prevEntry.getIndex();
		
	}
	
	public void removePossibility(int value) throws MissingPossibilityException, ValueOutOfRangeException {
			int location = findPossibilityPosition(value);
			possibilities.remove(location);
	}
	
	
	private int findPossibilityPosition(int value) throws MissingPossibilityException{
		for (int i=0; i<possibilities.size(); i++) {
			if (possibilities.get(i) == value) {
				return i;
			}
		}
		throw new MissingPossibilityException(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) throws ValueOutOfRangeException {
		if (value < 1 || value > size) {
			System.out.println("value: " + value);
			throw new ValueOutOfRangeException(value);
		}
		this.value = value;
		for (int i=0; i<possibilities.size(); i++) {
			if (possibilities.get(i)==value) {
				continue;
				// keep remaining possibility as only possibility
			}
			possibilities.remove(i);
			i--;
		}
	}

	public LinkedList<Integer> getPossibilities() {
		return possibilities;
	}
	
	public int getMinimumPossibleValue() {		
		if (value != 0) {
			return value;
		} else {
			int minimum = size;
			for (int possible : possibilities) {
				if (possible < minimum) {
					minimum = possible;
				}
			}
			return minimum;
		}
	}


	public int getIndex() {
		return index;
	}

		
}
