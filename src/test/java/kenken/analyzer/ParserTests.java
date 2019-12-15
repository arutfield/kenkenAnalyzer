package kenken.analyzer;



import board.Board;
import board.XmlParser;
import exceptions.BoardException;
import exceptions.MissingPossibilityException;
import exceptions.ParseBoardXmlException;
import exceptions.ValueOutOfRangeException;
import junit.framework.TestCase;

public class ParserTests extends TestCase{
	
	
	public void testReadEmptyBoard2() throws ValueOutOfRangeException, ParseBoardXmlException {
		Board board = XmlParser.parseFile("src/emptyBoard2.xml");
		assertEquals(board.getSize(), 2);
	}

	public void testReadEmptyBoard4() throws ValueOutOfRangeException, ParseBoardXmlException {
		Board board = XmlParser.parseFile("src/emptyBoard4.xml");
		assertEquals(board.getSize(), 4);
	}
	
	
	public void testReadMissingFile() {
		boolean success = false;
		try {
			XmlParser.parseFile("missingFile.xml");
			success = true;
		} catch (ParseBoardXmlException e) {
			System.out.println(e.getMessage());
		}
		assertFalse(success);
	}

	
	public void testReadOneConditionBoard() throws ValueOutOfRangeException, ParseBoardXmlException, MissingPossibilityException, BoardException {
		Board board = XmlParser.parseFile("src/TwoBoardOneCondition.xml");
		assertEquals(board.countTotalBoardCombinations(), 1);
	}

	
	public void testReadSudokuBoard() throws ValueOutOfRangeException, ParseBoardXmlException, MissingPossibilityException, BoardException {
		Board board = XmlParser.parseFile("src/SudokuBoard.xml");
		assertEquals(board.countTotalBoardCombinations(), 1);
	}
	
	public void testKenKen() throws ParseBoardXmlException, BoardException, MissingPossibilityException, ValueOutOfRangeException {
		Board board = XmlParser.parseFile("src/nyTimes.xml");
		assertEquals(board.countTotalBoardCombinations(), 1);
	}

	
}
