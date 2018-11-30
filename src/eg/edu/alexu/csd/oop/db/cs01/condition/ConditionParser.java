package eg.edu.alexu.csd.oop.db.cs01.condition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.cs01.dataChecker;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class ConditionParser {

	private static ConditionParser parser;
	private ConditionParser() {
	}
	public static ConditionParser getInstance() {
		if(parser==null)
			parser = new ConditionParser();
		return parser;
	}
	public RelationalCondition stringToRelationalCondition(String relationalCondition) {
		//remove all spaces to increase the speed of regex matching decreasing steps of matching
		relationalCondition = relationalCondition.replace(" ", "");
		relationalCondition = " " + relationalCondition;
		String stringConditionPattern = " \\s* ?(['\\\"]? ?\\w+['\\\"]?)\\s* ?([><=!]{1,2})?\\s* ?(['\\\"]? ?\\w+['\\\"]?)? ?";
		Pattern thePattern = Pattern.compile(stringConditionPattern);
		Matcher theMatcher = thePattern.matcher(relationalCondition);
		Table table = Table.getInstance();
		if(table==null)
			return null;
		if (theMatcher.find()) {
			String op = theMatcher.group(2);
			if(op.equals("="))
				op+=op;
			if(op.equals("<>"))
				op="!=";
			RelationalOperand leftAgrument = null;
			RelationalOperand rightAgrument = null;
			if(!table.getColumnNamesToLowerCase().contains(theMatcher.group(1).toLowerCase())) {
				leftAgrument = new RelationalOperand(theMatcher.group(1), !table.getColumnNamesToLowerCase().contains(theMatcher.group(1).toLowerCase()),dataChecker.getInstance().checkType(theMatcher.group(1)));
			}else {
				leftAgrument = new RelationalOperand(theMatcher.group(1), !table.getColumnNamesToLowerCase().contains(theMatcher.group(1).toLowerCase()),table.getColumnTypes().get(theMatcher.group(1).toLowerCase()));
			}
			if(!table.getColumnNamesToLowerCase().contains(theMatcher.group(3).toLowerCase())) {
				 rightAgrument = new RelationalOperand(theMatcher.group(3), !table.getColumnNamesToLowerCase().contains(theMatcher.group(3).toLowerCase()),dataChecker.getInstance().checkType(theMatcher.group(3)));
			}else {
				rightAgrument = new RelationalOperand(theMatcher.group(3), !table.getColumnNamesToLowerCase().contains(theMatcher.group(3).toLowerCase()),table.getColumnTypes().get(theMatcher.group(3).toLowerCase()));
			}
			return new RelationalCondition(leftAgrument, rightAgrument,op );
		}
		return null;
	}
	
	public LogicalCondition stringToLogicalCondition(String logicalCondition) {
			// regex
			return null;
	}
}
