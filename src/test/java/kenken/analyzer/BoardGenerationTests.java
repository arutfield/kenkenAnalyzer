package kenken.analyzer;


import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import board.Board;
import boardComponents.Entry;
import exceptions.BoardException;
import exceptions.MissingPossibilityException;
import exceptions.ValueOutOfRangeException;
import junit.framework.TestCase;


public class BoardGenerationTests extends TestCase{

	public void testBoardFilled() throws ValueOutOfRangeException, BoardException {
		Board fullBoard = new Board(4);
		for (int i=0; i<4; i++) {
        	for (int j=0; j<4; j++) {
        		fullBoard.getEntry(i, j).setValue(1);
        	}
        }
		assertTrue(fullBoard.isBoardFilled());
	}

	public void testBoardNotFilled() throws ValueOutOfRangeException, BoardException {
		Board fullBoard = new Board(4);
		for (int i=0; i<4; i++) {
        	for (int j=0; j<4; j++) {
        		if(i==2 && j==2) {
        			continue;
        		}
        		fullBoard.getEntry(i, j).setValue(1);
        	}
        }
		assertFalse(fullBoard.isBoardFilled());
	}
	
    public void testGenerateNewBoard() throws BoardException {
        Board emptyBoard = new Board(4); 
        for (int i=0; i<4; i++) {
        	for (int j=0; j<4; j++) {
        		assertTrue(emptyBoard.getEntry(i, j).getValue() == 0);
        	}
        }
    }

	public void testBoardFilledOneCombination() throws ValueOutOfRangeException, BoardException, MissingPossibilityException {
		Board fullBoard = new Board(4);
		for (int i=0; i<4; i++) {
        	for (int j=0; j<4; j++) {
        		fullBoard.getEntry(i, j).setValue(1);
        	}
        }
		assertTrue(fullBoard.isBoardFilled());
		assertEquals(fullBoard.countTotalBoardCombinations(), 1);
	}

	public void test3by3Combinations() throws Exception {
		Board board = new Board(3);
		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertEquals(total, 12);
		
	}

	public void test4by4Combinations() throws Exception {
		Board board = new Board(4);
		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertEquals(total, 576);
		
	}

	
	
	public void test4by4CombinationsTwoLeft() throws Exception {
		Board board = new Board(4);
		board.getEntry(0, 0).setValue(1);
		board.getEntry(0, 1).setValue(2);
		board.getEntry(0, 2).setValue(3);
		board.getEntry(0, 3).setValue(4);
		
		board.getEntry(1, 0).setValue(2);
		board.getEntry(1, 1).setValue(1);
		board.getEntry(1, 2).setValue(4);
		board.getEntry(1, 3).setValue(3);
		
		board.getEntry(2, 0).setValue(4);
		board.getEntry(2, 1).setValue(3);
		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertEquals(total, 2);
		
	}
	
	
	public void test4by4CombinationsOneLeft() throws Exception {
		Board board = new Board(4);
		board.getEntry(0, 0).setValue(1);
		board.getEntry(0, 1).setValue(2);
		board.getEntry(0, 2).setValue(3);
		board.getEntry(0, 3).setValue(4);
		board.getEntry(1, 0).setValue(2);
		board.getEntry(1, 1).setValue(4);
		board.getEntry(1, 2).setValue(1);
		board.getEntry(1, 3).setValue(3);
		board.getEntry(2, 0).setValue(4);
		board.getEntry(2, 1).setValue(3);
		board.getEntry(2, 2).setValue(2);
		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertEquals(total, 1);
		
	}

	
	
	public void test5by5CombinationsOneLeft() throws Exception {
		Board board = new Board(5);
		board.getEntry(0, 0).setValue(1);
		board.getEntry(0, 1).setValue(2);
		board.getEntry(0, 2).setValue(3);
		board.getEntry(0, 3).setValue(4);
		board.getEntry(0, 4).setValue(5);
		
		board.getEntry(1, 0).setValue(2);
		board.getEntry(1, 1).setValue(4);
		board.getEntry(1, 2).setValue(1);
		board.getEntry(1, 3).setValue(5);
		board.getEntry(1, 4).setValue(3);
		
		board.getEntry(2, 0).setValue(3);
		board.getEntry(2, 1).setValue(5);
		board.getEntry(2, 2).setValue(4);
		board.getEntry(2, 3).setValue(2);
		board.getEntry(2, 4).setValue(1);
		
		board.getEntry(3, 0).setValue(4);
		board.getEntry(3, 1).setValue(3);
		board.getEntry(3, 2).setValue(5);
		board.getEntry(3, 3).setValue(1);
		
		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertEquals(total, 1);
		
	}
	
	
	
	public void test5by5Combinations() throws Exception {
		Board board = new Board(5);
		//Board.setPrinting(false);
		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertEquals(total, 161280);
		
	}

	
	public void test6by6CombinationsFixedFirstRow() throws Exception {
		Board board = new Board(6);
		//Board.setPrinting(false);
		for (int i=1; i<7; i++) {
			board.getEntry(0, i-1).setValue(i);
		}
		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertEquals(total, 1128960);
		
	}

	
	public void test6by6CombinationsFixedFirstRowRegardless() throws Exception {
		Board board = new Board(6);
		//Board.setPrinting(false);
		for (int i=1; i<7; i++) {
			board.getEntry(0, i-1).setValue(i);
		}
		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertEquals(total, 1128960);
		
		Board board2 = new Board(6);
		LinkedList<Integer> numbersLeft = new LinkedList<>();
		for (int i=1; i<7; i++) {
			numbersLeft.add(i);
		}
		//Board.setPrinting(false);
		for (int i=1; i<7; i++) {
			int index = ThreadLocalRandom.current().nextInt(0, numbersLeft.size());
			int value = numbersLeft.get(index);
			board2.getEntry(0, i-1).setValue(value);
			numbersLeft.remove(index);

		}
		int total2 = board2.countTotalBoardCombinations();
		assertEquals(total, total2);
	}
	

	/*
	public void test6by6CombinationsFixedFirstRowNotLastThree() throws Exception {
		Board board = new Board(6);
		//Board.setPrinting(false);
		for (int i=1; i<4; i++) {
			board.getEntry(0, i-1).setValue(i);
		}
		int total = board.countTotalBoardCombinations();
		System.out.println("total: " + total);
		assertEquals(total, 1128960*6);
		
	}*/
	
	
	
	public void testBoardOneSpot2by2OneCombination() throws ValueOutOfRangeException, BoardException, MissingPossibilityException {
		Board board = new Board(2);
		board.getEntry(0, 0).setValue(1);
		assertEquals(board.countTotalBoardCombinations(), 1);
	}

	

	public void test1by1Combinations() throws BoardException, MissingPossibilityException, ValueOutOfRangeException {
		Board startBoard = new Board(1); 
        assertEquals(startBoard.countTotalBoardCombinations(), 1);
	}

	
	public void test2by2Combinations() throws BoardException, MissingPossibilityException, ValueOutOfRangeException {
		Board startBoard = new Board(2); 
        assertEquals(startBoard.countTotalBoardCombinations(), 2);
	}

	
	
	public void testGenerateNewEntry() throws MissingPossibilityException, ValueOutOfRangeException {
		Entry entry = new Entry(4, 0, 0);
		for (int i=1; i<5; i++) {
			entry.removePossibility(i);
		}
		assertTrue(entry.getPossibilities().isEmpty());
		
	}
	
	
	public void testNotPossible() throws ValueOutOfRangeException {
		Entry entry = new Entry(4, 0, 0);
        try {
			entry.removePossibility(5);
	        assertTrue(false);

        } catch (MissingPossibilityException e) {
			assertTrue(true);
		}
	}

	
	public void testSetValue() throws ValueOutOfRangeException {
		Entry entry = new Entry(4, 0, 0);
		int value = 3;
		entry.setValue(value);
		assertEquals(entry.getValue(), value);
		assertEquals(entry.getPossibilities().size(), 1);
		assertEquals(entry.getPossibilities().get(0).intValue(), value);
	}
	
	

}
