package eg.edu.alexu.csd.oop.db.cs01.condition;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import eg.edu.alexu.csd.oop.db.cs01.modules.Row;

public class RelationalSolver {

	private static RelationalSolver solver;
	private RelationalSolver() {
	}
	public static RelationalSolver getInstance() {
		if(solver==null) {
			solver = new RelationalSolver();
		}
		return solver;
	}
	public boolean isRowSolvingCondition(Row row , RelationalCondition condition){
		// value , value
		if(condition.getLeftAgrument().isVariable()&&condition.getRightAgrument().isVariable())
			return compareTwoValue(condition);
		else if(!condition.getLeftAgrument().isVariable()&&!condition.getRightAgrument().isVariable())
			return compareTwoCol(row, condition);
		else
			return compareValueCol(row, condition);
		
	}
	public boolean compareTwoValue(RelationalCondition condition) {
		if(!condition.getLeftAgrument().getType().equals(condition.getRightAgrument().getType())) {
			System.out.println("error");
		}
		return getRelationResult(condition.getLeftAgrument(), condition.getRightAgrument(), condition.getOperation());
		
	}
	public boolean getRelationResult(RelationalOperand var1 ,RelationalOperand var2 ,String operation) {
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine sm = sem.getEngineByName("JavaScript");
		try {
			return (boolean) sm.eval(var1.getOperand()+operation+var2.getOperand());
		} catch (ScriptException e) {
			System.out.println("error");
			
		}
		return false;
	}
	private boolean compareValueCol(Row row , RelationalCondition condition){
		if(!condition.getLeftAgrument().isVariable()) {
			condition.getLeftAgrument().setOperand(row.getCellByColumn(condition.getLeftAgrument().getOperand()));
			return getRelationResult(condition.getLeftAgrument(),condition.getRightAgrument(), condition.getOperation());

		}else {
			condition.getRightAgrument().setOperand(row.getCellByColumn(condition.getRightAgrument().getOperand()));
			return getRelationResult(condition.getLeftAgrument(),condition.getRightAgrument(), condition.getOperation());
		}			
	}
	private boolean compareTwoCol(Row row , RelationalCondition condition) {
		condition.getLeftAgrument().setOperand(row.getCellByColumn(condition.getLeftAgrument().getOperand()));
		condition.getRightAgrument().setOperand(row.getCellByColumn(condition.getRightAgrument().getOperand()));
		return getRelationResult(condition.getLeftAgrument(),condition.getRightAgrument(), condition.getOperation());
		
	}
}
