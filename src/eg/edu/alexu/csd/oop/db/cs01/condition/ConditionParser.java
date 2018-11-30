package eg.edu.alexu.csd.oop.db.cs01.condition;

import java.util.ArrayList;
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
			if(op!=null) {
				if(op.equals("="))
					op+=op;
				if(op.equals("<>"))
					op="!=";
			}
			RelationalOperand leftAgrument = null;
			RelationalOperand rightAgrument = null;
			if(theMatcher.group(1)!=null) {
				if(!table.getColumnNamesToLowerCase().contains(theMatcher.group(1).toLowerCase())) {
					leftAgrument = new RelationalOperand(theMatcher.group(1), !table.getColumnNamesToLowerCase().contains(theMatcher.group(1).toLowerCase()),dataChecker.getInstance().checkType(theMatcher.group(1)));
				}else {
					leftAgrument = new RelationalOperand(theMatcher.group(1), !table.getColumnNamesToLowerCase().contains(theMatcher.group(1).toLowerCase()),table.getColumnTypes().get(theMatcher.group(1).toLowerCase()));
				}
			}
			if(theMatcher.group(3)!=null) {
				if(!table.getColumnNamesToLowerCase().contains(theMatcher.group(3).toLowerCase())) {
					rightAgrument = new RelationalOperand(theMatcher.group(3), !table.getColumnNamesToLowerCase().contains(theMatcher.group(3).toLowerCase()),dataChecker.getInstance().checkType(theMatcher.group(3)));
				}else {
					rightAgrument = new RelationalOperand(theMatcher.group(3), !table.getColumnNamesToLowerCase().contains(theMatcher.group(3).toLowerCase()),table.getColumnTypes().get(theMatcher.group(3).toLowerCase()));
				}
			}
			return new RelationalCondition(leftAgrument, rightAgrument,op );
		}
		return null;
	}
	
	public ArrayList<RelationalCondition> stringToLogicalCondition(String logicalCondition) {
			// regex
		ArrayList<RelationalCondition>conditions = new ArrayList<RelationalCondition>();
		if(logicalCondition==null)
			return null;
		final String relationlConditionPattern = "(([^;\\s<>!=()]+) ?(([!=><]{1,2}) ?([^;\\s<>!=()]+)?)?)";
		logicalCondition = logicalCondition.replaceAll(" +", " ");
		Pattern p = Pattern.compile(relationlConditionPattern);
		Matcher operation = p.matcher(logicalCondition);
		while (operation.find()) {
//			String s = operation.group(1).replaceAll(" ", "");
			String s = operation.group(1);
			if(!s.replaceAll(" ", "").equals("or") && !s.replaceAll(" ", "").equals("and") && !s.replaceAll(" ", "").equals("not")) {
				conditions.add(new RelationalCondition(s));
			}
			
		}
			return conditions ;
	}
}
