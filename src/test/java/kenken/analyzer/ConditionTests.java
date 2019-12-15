package kenken.analyzer;


import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;


import board.Board;
import boardComponents.Condition;
import boardComponents.Entry;
import boardComponents.SymbolEnum;
import exceptions.BoardException;
import exceptions.MissingPossibilityException;
import exceptions.ValueOutOfRangeException;
import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class ConditionTests extends TestCase{

	
	public void testEquals() throws ValueOutOfRangeException, BoardException, MissingPossibilityException {
		Board board = new Board(2);
		LinkedList entries = new LinkedList<Entry>();
		entries.add(board.getEntry(1,1));
		board.addCondition(new Condition(SymbolEnum.Equals, 2, entries));
		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertEquals(total, 1);
	}

	
	public void testEquals3by3() throws ValueOutOfRangeException, BoardException, MissingPossibilityException {
		Board board = new Board(3);
		LinkedList entries = new LinkedList<Entry>();
		entries.add(board.getEntry(1,0));
		board.addCondition(new Condition(SymbolEnum.Equals, 2, entries));
		LinkedList entries2 = new LinkedList<Entry>();
		entries2.add(board.getEntry(1,1));
		board.addCondition(new Condition(SymbolEnum.Equals, 3, entries2));
		LinkedList entries3 = new LinkedList<Entry>();
		entries3.add(board.getEntry(1,2));
		board.addCondition(new Condition(SymbolEnum.Equals, 1, entries3));
		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertEquals(total, 2);
	}

	
	public void testEquals5by5() throws ValueOutOfRangeException, BoardException, MissingPossibilityException {
		Board board = new Board(5);
		LinkedList entries = new LinkedList<Entry>();
		entries.add(board.getEntry(1,0));
		board.addCondition(new Condition(SymbolEnum.Equals, 2, entries));
		
		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertEquals(total, 161280/5);
	}

	
	
	public void testAdd2by2() throws ValueOutOfRangeException, BoardException, MissingPossibilityException {
		Board board = new Board(2);
		LinkedList<Entry> entries = new LinkedList<Entry>();
		entries.add(board.getEntry(0, 0));
		entries.add(board.getEntry(1, 1));

		board.addCondition(new Condition(SymbolEnum.Plus, 4, entries));
		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertEquals(total, 1);
	}

	
	
	
	
	public void testAdd3by3() throws ValueOutOfRangeException, BoardException, MissingPossibilityException {
		Board board = new Board(3);
		LinkedList<Entry> entries = new LinkedList<Entry>();
		entries.add(board.getEntry(0, 0));
		entries.add(board.getEntry(0, 1));

		board.addCondition(new Condition(SymbolEnum.Plus, 4, entries));
		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertEquals(total, 4);
	}


	
	public void testAdd5by5() throws ValueOutOfRangeException, BoardException, MissingPossibilityException {
		Board board = new Board(5);
		LinkedList<Entry> entries = new LinkedList<Entry>();
		entries.add(board.getEntry(1, 3));
		entries.add(board.getEntry(1, 2));
		entries.add(board.getEntry(1, 1));
		board.addCondition(new Condition(SymbolEnum.Plus, 12, entries));
		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertTrue(total < 161280);
	}

	
	public void testotherCageValuesNotFull() throws Exception {
		Board board = new Board(2);
		LinkedList<Entry> entries = new LinkedList<Entry>();
		entries.add(board.getEntry(0, 0));
		entries.add(board.getEntry(1, 1));
		Condition condition1 = new Condition(SymbolEnum.Plus, 4, entries);
		board.addCondition(condition1);
		board.getEntry(0,0).setValue(2);
		assertFalse(condition1.allOtherCageValuesFull(board.getEntry(0, 0)));
	}
	

	public void testotherCageValuesFull() throws Exception {
		Board board = new Board(2);
		LinkedList<Entry> entries = new LinkedList<Entry>();
		entries.add(board.getEntry(0, 0));
		entries.add(board.getEntry(1, 1));
		Condition condition1 = new Condition(SymbolEnum.Plus, 4, entries);
		board.addCondition(condition1);
		board.getEntry(1,1).setValue(2);
		assertTrue(condition1.allOtherCageValuesFull(board.getEntry(0, 0)));
	}

	
	public void testProduct2by2() throws ValueOutOfRangeException, BoardException, MissingPossibilityException {
		Board board = new Board(2);
		LinkedList<Entry> entries = new LinkedList<Entry>();
		entries.add(board.getEntry(0, 0));
		entries.add(board.getEntry(1, 1));

		board.addCondition(new Condition(SymbolEnum.Times, 4, entries));
		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertEquals(total, 1);
	}

	
	public void testProduct3by3() throws ValueOutOfRangeException, BoardException, MissingPossibilityException {
		Board board = new Board(3);
		LinkedList<Entry> entries = new LinkedList<Entry>();
		entries.add(board.getEntry(0, 2));
		entries.add(board.getEntry(0, 1));

		board.addCondition(new Condition(SymbolEnum.Times, 3, entries));
		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertEquals(total, 4);
	}


	
	public void testMinus3by3() throws ValueOutOfRangeException, BoardException, MissingPossibilityException {
		Board board = new Board(3);
		LinkedList<Entry> entries = new LinkedList<Entry>();
		entries.add(board.getEntry(0, 2));
		entries.add(board.getEntry(0, 1));

		board.addCondition(new Condition(SymbolEnum.Minus, 2, entries));
		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertEquals(total, 4);
	}

	
	public void testMinus5by5TooBig() throws ValueOutOfRangeException, BoardException, MissingPossibilityException {
		Board board = new Board(5);
		LinkedList<Entry> entries = new LinkedList<Entry>();
		entries.add(board.getEntry(0, 2));
		entries.add(board.getEntry(0, 1));

		board.addCondition(new Condition(SymbolEnum.Minus, 5, entries));
		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertEquals(total, 0);
	}	
	
	
	public void testProduct3by3Add() throws ValueOutOfRangeException, BoardException, MissingPossibilityException {
		Board board = new Board(3);
		LinkedList<Entry> entries = new LinkedList<Entry>();
		entries.add(board.getEntry(0, 2));
		entries.add(board.getEntry(0, 1));

		board.addCondition(new Condition(SymbolEnum.Times, 3, entries));
		LinkedList<Entry> entries2 = new LinkedList<Entry>();
		entries2.add(board.getEntry(1, 1));
		entries2.add(board.getEntry(0, 1));
		board.addCondition(new Condition(SymbolEnum.Plus, 4, entries2));

		
		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertEquals(total, 2);
	}


	
	public void testDivide2by2() throws ValueOutOfRangeException, BoardException, MissingPossibilityException {
		Board board = new Board(2);
		LinkedList<Entry> entries = new LinkedList<Entry>();
		entries.add(board.getEntry(0, 0));
		entries.add(board.getEntry(0, 1));

		board.addCondition(new Condition(SymbolEnum.Divide, 2, entries));
		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertEquals(total, 2);
	}

	
	public void testDivide3by3() throws ValueOutOfRangeException, BoardException, MissingPossibilityException {
		Board board = new Board(3);
		LinkedList<Entry> entries = new LinkedList<Entry>();
		entries.add(board.getEntry(0, 0));
		entries.add(board.getEntry(0, 1));
		board.addCondition(new Condition(SymbolEnum.Divide, 3, entries));

		LinkedList<Entry> entries2 = new LinkedList<Entry>();
		entries2.add(board.getEntry(0, 1));
		entries2.add(board.getEntry(0, 2));
		board.addCondition(new Condition(SymbolEnum.Divide, 2, entries2));

		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertEquals(total, 2);
	}
	
	
	public void testSudoku() throws Exception {
		Board board = new Board(9);
		for (int i=0; i<9; i=i+3) {
			for (int j=0; j<9; j=j+3) {
				LinkedList<Entry> squareEntries = new LinkedList<Entry>();
				for(int r=0; r<3; r++) {
					for(int c=0; c<3; c++) {
						squareEntries.add(board.getEntry(i+r, j+c));
					}
				}
				//Condition condition = new Condition(SymbolEnum.Plus, 45, squareEntries);
				Condition prodCondition = new Condition(SymbolEnum.Times, 362880, squareEntries);

				//board.addCondition(condition);
				board.addCondition(prodCondition);
			}
		}
		board.addCondition(new Condition(8, board.getEntry(0, 3)));
		board.addCondition(new Condition(2, board.getEntry(0, 4)));
		board.addCondition(new Condition(4, board.getEntry(0, 7)));
		board.addCondition(new Condition(2, board.getEntry(1, 0)));
		board.addCondition(new Condition(4, board.getEntry(1, 1)));
		board.addCondition(new Condition(3, board.getEntry(1, 2)));
		board.addCondition(new Condition(1, board.getEntry(1, 5)));
		board.addCondition(new Condition(8, board.getEntry(2, 0)));
		board.addCondition(new Condition(9, board.getEntry(2, 2)));
		board.addCondition(new Condition(5, board.getEntry(2, 4)));
		board.addCondition(new Condition(7, board.getEntry(3, 0)));
		board.addCondition(new Condition(5, board.getEntry(3, 1)));
		board.addCondition(new Condition(8, board.getEntry(3, 2)));
		board.addCondition(new Condition(1, board.getEntry(5, 6)));
		board.addCondition(new Condition(6, board.getEntry(5, 7)));
		board.addCondition(new Condition(8, board.getEntry(5, 8)));
		board.addCondition(new Condition(9, board.getEntry(6, 4)));
		board.addCondition(new Condition(6, board.getEntry(6, 6)));
		board.addCondition(new Condition(7, board.getEntry(6, 8)));
		board.addCondition(new Condition(4, board.getEntry(7, 3)));
		board.addCondition(new Condition(5, board.getEntry(7, 6)));
		board.addCondition(new Condition(2, board.getEntry(7, 7)));
		board.addCondition(new Condition(1, board.getEntry(7, 8)));
		board.addCondition(new Condition(1, board.getEntry(8, 1)));
		board.addCondition(new Condition(6, board.getEntry(8, 4)));
		board.addCondition(new Condition(2, board.getEntry(8, 5)));
		assertEquals(1, board.countTotalBoardCombinations());
	}

	public void testSudoku2() throws Exception {
		Board board = new Board(9);
		for (int i=0; i<9; i=i+3) {
			for (int j=0; j<9; j=j+3) {
				LinkedList<Entry> squareEntries = new LinkedList<Entry>();
				for(int r=0; r<3; r++) {
					for(int c=0; c<3; c++) {
						squareEntries.add(board.getEntry(i+r, j+c));
					}
				}
				Condition condition = new Condition(SymbolEnum.Plus, 45, squareEntries);
				Condition prodCondition = new Condition(SymbolEnum.Times, 362880, squareEntries);

				board.addCondition(condition);
				board.addCondition(prodCondition);
			}
		}
		board.addCondition(new Condition(3, board.getEntry(0, 3)));
		board.addCondition(new Condition(9, board.getEntry(0, 6)));
		board.addCondition(new Condition(7, board.getEntry(0, 7)));
		board.addCondition(new Condition(7, board.getEntry(1, 0)));
		board.addCondition(new Condition(4, board.getEntry(1, 4)));
		board.addCondition(new Condition(5, board.getEntry(1, 8)));
		board.addCondition(new Condition(5, board.getEntry(2, 5)));
		board.addCondition(new Condition(2, board.getEntry(2, 6)));
		board.addCondition(new Condition(9, board.getEntry(3, 0)));
		board.addCondition(new Condition(5, board.getEntry(3, 3)));
		board.addCondition(new Condition(1, board.getEntry(4, 0)));
		board.addCondition(new Condition(8, board.getEntry(4, 1)));
		board.addCondition(new Condition(6, board.getEntry(4, 7)));
		board.addCondition(new Condition(9, board.getEntry(4, 8)));
		board.addCondition(new Condition(7, board.getEntry(5, 5)));
		board.addCondition(new Condition(3, board.getEntry(5, 8)));
		board.addCondition(new Condition(4, board.getEntry(6, 2)));
		board.addCondition(new Condition(9, board.getEntry(6, 3)));
		board.addCondition(new Condition(3, board.getEntry(7, 0)));
		board.addCondition(new Condition(6, board.getEntry(7, 4)));
		board.addCondition(new Condition(7, board.getEntry(7, 8)));
		board.addCondition(new Condition(5, board.getEntry(8, 1)));
		board.addCondition(new Condition(6, board.getEntry(8, 2)));
		board.addCondition(new Condition(2, board.getEntry(8, 5)));
		
		assertEquals(1, board.countTotalBoardCombinations());
	}

	public void testKenKen() throws Exception {
		Board board = new Board(4);
		LinkedList<Entry> cage1Entries = new LinkedList<Entry>();
		cage1Entries.add(board.getEntry(0, 0));
		cage1Entries.add(board.getEntry(0, 1));
		board.addCondition(new Condition(SymbolEnum.Minus, 1, cage1Entries));

		LinkedList<Entry> cage2Entries = new LinkedList<Entry>();
		cage2Entries.add(board.getEntry(0, 2));
		cage2Entries.add(board.getEntry(1, 2));
		cage2Entries.add(board.getEntry(2, 2));
		board.addCondition(new Condition(SymbolEnum.Times, 6, cage2Entries));

		LinkedList<Entry> cage3Entries = new LinkedList<Entry>();
		cage3Entries.add(board.getEntry(0, 3));
		cage3Entries.add(board.getEntry(1, 3));
		cage3Entries.add(board.getEntry(2, 3));
		board.addCondition(new Condition(SymbolEnum.Times, 12, cage3Entries));

		LinkedList<Entry> cage4Entries = new LinkedList<Entry>();
		cage4Entries.add(board.getEntry(1, 0));
		cage4Entries.add(board.getEntry(2, 0));
		cage4Entries.add(board.getEntry(2, 1));
		board.addCondition(new Condition(SymbolEnum.Plus, 10, cage4Entries));
		
		board.addCondition(new Condition(1, board.getEntry(1, 1)));

		LinkedList<Entry> cage5Entries = new LinkedList<Entry>();
		cage5Entries.add(board.getEntry(3, 0));
		cage5Entries.add(board.getEntry(3, 1));
		board.addCondition(new Condition(SymbolEnum.Minus, 2, cage5Entries));

		LinkedList<Entry> cage6Entries = new LinkedList<Entry>();
		cage6Entries.add(board.getEntry(3, 2));
		cage6Entries.add(board.getEntry(3, 3));
		board.addCondition(new Condition(SymbolEnum.Divide, 2, cage6Entries));


		
		assertEquals(1, board.countTotalBoardCombinations());
	}

	
}
