package eg.edu.alexu.csd.oop.db.cs01.condition;

import java.sql.SQLException;

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
			logicalString = logicalString.replace(relationalCondition.getStringCondition(), Boolean.toString(relationalCondition.isTrueValue()));
		}
		logicalString = logicalString.toLowerCase();
		logicalString = logicalString.replaceAll("not", "!");
		logicalString =	logicalString.replaceAll("and", "&&");
		logicalString = logicalString.replaceAll("or", "||");
		return JavaScriptEngine.getInstance().getResult(logicalString);
	}
	
	public boolean isRowSolvingCondition(Row row , LogicalCondition condition) throws SQLException {
		for(RelationalCondition relationalCondition:condition.getRelationalConditions()) {
			relationalCondition.setTrueValue(RelationalSolver.getInstance().isRowSolvingCondition(row, relationalCondition));
		}
		return getLogicalResult(condition);
	}
}
