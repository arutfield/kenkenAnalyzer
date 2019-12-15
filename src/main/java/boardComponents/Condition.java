package boardComponents;

import java.util.LinkedList;

import exceptions.BoardException;

public class Condition {
	private final SymbolEnum operation;

	private final LinkedList<Entry> cageEntries;
	
	private final int opValue;
	
	
	public Condition(SymbolEnum operation, int opValue, LinkedList<Entry> otherCageValues) {
		this.cageEntries = otherCageValues;
		this.operation = operation;
		this.opValue = opValue;
	}

	
	public Condition(Condition prevCondition, LinkedList<Entry> otherCageValues) throws BoardException {
		// TODO Auto-generated constructor stub
		this.operation = prevCondition.operation;
		this.opValue = prevCondition.opValue;
		this.cageEntries = otherCageValues;
	}


	public Condition(int opValue, Entry entry) {
		this.operation = SymbolEnum.Equals;
		this.opValue = opValue;
		this.cageEntries = new LinkedList<Entry>();
		this.cageEntries.add(entry);
	}


	public SymbolEnum getOperation() {
		return operation;
	}

	public LinkedList<Entry> getCageEntries() {
		return cageEntries;
	}


	public int getOpValue() {
		return opValue;
	}


	public boolean allOtherCageValuesFull(Entry mainEntry) {
		for (Entry entry: cageEntries) {
			if (entry.getIndex() == mainEntry.getIndex()) {
				continue; // ignore the entry you're currently looking at
			}
			if(entry.getValue() == 0) {
				return false;
			}
		}
		return true;
	}
	
	public int getMinimumTotalForOtherCageValues(Entry entryToSkip) {
		int total = 0;
		for (Entry cageEntry : cageEntries) {
			if (cageEntry.getIndex() == entryToSkip.getIndex()) {
				continue;
			}
			total += cageEntry.getMinimumPossibleValue();
		}
		return total;
	}


	public int getMinimumProductForOtherCageValues(Entry entryToSkip) {
		int product = 1;
		for (Entry cageEntry : cageEntries) {
			if (cageEntry.getIndex() == entryToSkip.getIndex()) {
				continue;
			}
			product *= cageEntry.getMinimumPossibleValue();
		}
		return product;
	}


	public Entry getOtherEntry(Entry entry) {
		for (Entry cageEntry : cageEntries) {
			if(entry.getIndex() != cageEntry.getIndex()) {
				return cageEntry;
			}
		}
		return null;
	}


}
