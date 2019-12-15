package kenken.analyzer;

import board.Board;
import board.XmlParser;
import exceptions.BoardException;
import exceptions.MissingPossibilityException;
import exceptions.ParseBoardXmlException;
import exceptions.ValueOutOfRangeException;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main( String[] args )
	{
		long startTime = System.currentTimeMillis();
		if (args.length < 1) {
			System.out.println("Need to input board xml file");
			return;
		}
		try {
			Board board = XmlParser.parseFile(args[0]);
			int count = board.countTotalBoardCombinations();
			System.out.println(count + " board(s) found");
			System.out.println("Calculation time: " + (System.currentTimeMillis() - startTime) + " ms");
		} catch (ParseBoardXmlException e) {
			System.out.println("Error found when parsing board. See log for more info");
			System.out.println(e.getMessage());
		} catch (BoardException e) {
			System.out.println("Error found when parsing board. See log for more info");
			System.out.println(e.getMessage());
		} catch (MissingPossibilityException e) {
			System.out.println("Error found when parsing board. See log for more info");
			System.out.println(e.getMessage());
		} catch (ValueOutOfRangeException e) {
			System.out.println("Error found when parsing board. See log for more info");
			System.out.println(e.getMessage());
		}
	}

}
