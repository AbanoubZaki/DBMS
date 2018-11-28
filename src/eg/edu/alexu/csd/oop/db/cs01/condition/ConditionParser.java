package eg.edu.alexu.csd.oop.db.cs01.condition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.cs01.dataChecker;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class ConditionParser {

	private static ConditionParser parser;
	private ConditionParser() {
		// TODO Auto-generated constructor stub
	}
	public static ConditionParser getInstance() {
		if(parser==null)
			parser = new ConditionParser();
		return parser;
	}
	public RelationalCondition stringToRelationalCondition(String relationalCondition) {
		String stringConditionPattern = " ?(['\"]? ?\\w+ ?['\"]?) ?([><=!]{1,2})? ?(['\"]? ?\\w+['\"]?)? ?";
		Pattern thePattern = Pattern.compile(stringConditionPattern);
		Matcher theMatcher = thePattern.matcher(relationalCondition);
		Table table = Table.getInstance();
		if(table==null)
			return null;
		if (theMatcher.find()) {
			RelationalOperand leftAgrument = new RelationalOperand(theMatcher.group(1), !table.getColumnNamesToLowerCase().contains(theMatcher.group(1).toLowerCase()),dataChecker.getInstance().checkType(theMatcher.group(1)));
			RelationalOperand rightAgrument = new RelationalOperand(theMatcher.group(3), !table.getColumnNamesToLowerCase().contains(theMatcher.group(3).toLowerCase()),dataChecker.getInstance().checkType(theMatcher.group(3)));
			return new RelationalCondition(leftAgrument, rightAgrument, theMatcher.group(2));
		}
		return null;
	}
	
	public LogicalCondition stringToLogicalCondition(String logicalCondition) {
		Table table = Table.getInstance();
		if(table==null)
			return null;
		return null;
	}
}
