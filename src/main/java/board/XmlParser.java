package board;

import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import boardComponents.Condition;
import boardComponents.Entry;
import boardComponents.SymbolEnum;
import exceptions.BoardException;
import exceptions.ParseBoardXmlException;

public class XmlParser {

	private static Logger log = LogManager.getLogger(XmlParser.class);


	public static Board parseFile(String fileName) throws ParseBoardXmlException {
		Board board = null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;

		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException ex) {
			log.error(ex.getMessage(), ex);
			throw new ParseBoardXmlException("ParserConfigurationException: " + ex.getMessage());
		}

		Document doc;

		try {
			doc = dBuilder.parse(fileName);
		} catch (SAXException ex) {
			log.error(ex.getMessage(), ex);
			throw new ParseBoardXmlException("SAXException: " + ex.getMessage());
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
			throw new ParseBoardXmlException("IOException: " + ex.getMessage());
		}

		doc.getDocumentElement().normalize();
		NodeList boardNodes = doc.getElementsByTagName("Board");
		if (boardNodes.getLength() == 0) {
			String message = "unable to load nodes from element Board";
			log.error(message);
			throw new ParseBoardXmlException(message);
		}
		Node boardNode = boardNodes.item(0);
		Element eElement = (Element) boardNode;
		int boardSize = Integer.valueOf(eElement.getAttribute("size"));
		board = new Board(boardSize);
		try {
			String attribute = eElement.getAttribute("Print");
			if (!attribute.isEmpty()) {
				boolean printingEnabled = Boolean.valueOf(attribute);
				board.setPrinting(printingEnabled);
			}
		} catch(Exception ex) {
			log.warn("Printing enabled not found" , ex);
		}
		NodeList conditionNodes = eElement.getElementsByTagName("Condition");
		for (int i = 0; i<conditionNodes.getLength(); i++) {
			Element conditionNode = (Element) conditionNodes.item(i);
			SymbolEnum symbol = getSymbol(conditionNode.getAttribute("symbol"));

			int opValue = 0;
			try {
				opValue = Integer.valueOf(conditionNode.getAttribute("value"));
			} catch (Exception ex) {
				String message = "Error getting op Value from xml: " + ex;
				log.error(message, ex);
				throw new ParseBoardXmlException(message);
			}
			NodeList cellNodes = conditionNode.getElementsByTagName("Cell");
			LinkedList<Entry> entries = new LinkedList<Entry>();
			for (int j = 0; j<cellNodes.getLength(); j++) {
				String entryText = cellNodes.item(j).getTextContent();
				String[] entryInfo = entryText.split(",");
				int row = 0;
				int col = 0;
				try {
					row = Integer.valueOf(entryInfo[0]);
					col = Integer.valueOf(entryInfo[1]);
				} catch(Exception ex) {
					String message = "Error getting row and column for cell " + j + " in condition " + i;
					log.error(message);
					throw new ParseBoardXmlException(message);
				}
				try {
					entries.add(board.getEntry(row, col));
				} catch (BoardException ex) {
					String message = "Error adding entry to condition: row " + row + ", col " + col;
					log.error(message, ex);
					throw new ParseBoardXmlException(message);
				}
			}
			try {
				board.addCondition(new Condition(symbol, opValue, entries));
			} catch (BoardException e) {
				// TODO Auto-generated catch block
				throw new ParseBoardXmlException("Error adding condition in xml: " + e.getMessage());
			}
		}
		return board;
	}

	private static SymbolEnum getSymbol(String symbolText) throws ParseBoardXmlException {
		switch(symbolText) {
		case "=":
			return SymbolEnum.Equals;
		case "+":
			return SymbolEnum.Plus;
		case "-":
			return SymbolEnum.Minus;
		case "/":
			return SymbolEnum.Divide;
		case "*":
			return SymbolEnum.Times;
		default:
			throw new ParseBoardXmlException("Unknown Symbol in xml: " + symbolText);
		}
	}

}
