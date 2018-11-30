package eg.edu.alexu.csd.oop.db.cs01.condition;

import eg.edu.alexu.csd.oop.db.cs01.modules.Row;

public class LogicalSolver {
	
	private static LogicalSolver instance;
	private LogicalSolver() {
	}
	public static LogicalSolver getInstance() {
		if(instance==null)
			instance=new LogicalSolver();
		return instance;
	}
	
	private boolean getLogicalResult(LogicalCondition logicalCondition) {
		String logicalString = logicalCondition.getStringCondition();
		for(RelationalCondition relationalCondition:logicalCondition.getRelationalConditions()) {
			logicalString.replace(relationalCondition.getStringCondition(), Boolean.toString(relationalCondition.isTrueValue()));
		}
		logicalString = logicalString.toLowerCase();
		logicalString.replaceAll("not", "!");
		logicalString.replaceAll("and", "&&");
		logicalString.replaceAll("or", "||");
		return JavaScriptEngine.getInstance().getResult(logicalString);
		
	}
	public boolean isRowSolvingCondition(Row row , LogicalCondition condition) {
		for(RelationalCondition relationalCondition:condition.getRelationalConditions()) {
			relationalCondition.setTrueValue(RelationalSolver.getInstance().isRowSolvingCondition(row, relationalCondition));
		}
		return getLogicalResult(condition);
		
	}
}
