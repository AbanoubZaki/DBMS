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
	public RelationalCondition stringToRelationalCondition(String stringCondition , Table table) {
		String stringConditionPattern = " ?(['\"]? ?\\w+ ?['\"]?) ?([><=!]{1,2})? ?(['\"]? ?\\w+['\"]?)? ?";
		Pattern thePattern = Pattern.compile(stringConditionPattern);
		Matcher theMatcher = thePattern.matcher(stringCondition);
		if (theMatcher.find()) {
			String op1  = theMatcher.group(1);
			op1 = op1.replaceAll("'", "");
			op1 = op1.replaceAll("\"", "");
			String op2  = theMatcher.group(3);
			op2 = op2.replaceAll("'", "");
			op2 = op2.replaceAll("\"", "");
			RelationalOperand leftAgrument = new RelationalOperand(op1, !table.getColumnNames().contains(theMatcher.group(1)),dataChecker.getInstance().checkType(theMatcher.group(1)));
			RelationalOperand rightAgrument = new RelationalOperand(op2, !table.getColumnNames().contains(theMatcher.group(3)),dataChecker.getInstance().checkType(theMatcher.group(1)));;
			return new RelationalCondition(leftAgrument, rightAgrument, theMatcher.group(2));
		}
		return null;
	}
}
