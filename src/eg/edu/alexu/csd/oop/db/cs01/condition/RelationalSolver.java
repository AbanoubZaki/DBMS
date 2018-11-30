package eg.edu.alexu.csd.oop.db.cs01.condition;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import eg.edu.alexu.csd.oop.db.cs01.modules.Row;

public class RelationalSolver {

	private static RelationalSolver solver;
	private ScriptEngineManager sem;
	private ScriptEngine sm ;
	private RelationalSolver() {
		sem = new ScriptEngineManager();
		sm = sem.getEngineByName("JavaScript");
	}
	public static RelationalSolver getInstance() {
		if(solver==null) {
			solver = new RelationalSolver();
		}
		return solver;
	}
	public boolean isRowSolvingCondition(Row row , RelationalCondition condition){
		// value , value
		if (condition.getLeftAgrument() == null || condition.getRightAgrument() == null) {
			System.out.println("Condition error.");
			return false;
		}
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
		if(var1.getOperand()==null||var1.getOperand()==null)
			return false;
		try {
			return (boolean) sm.eval(var1.getOperand().toLowerCase()+operation+var2.getOperand().toLowerCase());
		} catch (ScriptException e) {
			System.out.println("error");
		}
		return false;
	}
	private boolean compareValueCol(Row row , RelationalCondition condition){
		if(!condition.getLeftAgrument().isVariable()) {
			RelationalOperand rp = new RelationalOperand(row.getCellByColumn(condition.getLeftAgrument().getOperand()), false,condition.getLeftAgrument().getType());
			return getRelationResult(rp,condition.getRightAgrument(), condition.getOperation());

		}else {
			RelationalOperand rp = new RelationalOperand(row.getCellByColumn(condition.getRightAgrument().getOperand()), false,condition.getRightAgrument().getType());
			return getRelationResult(rp,condition.getRightAgrument(), condition.getOperation());		
		}			
	}
	private boolean compareTwoCol(Row row , RelationalCondition condition) {
		RelationalOperand lrp = new RelationalOperand(row.getCellByColumn(condition.getLeftAgrument().getOperand()), false,condition.getLeftAgrument().getType());
		RelationalOperand rrp = new RelationalOperand(row.getCellByColumn(condition.getRightAgrument().getOperand()), false,condition.getRightAgrument().getType());
		return getRelationResult(lrp,rrp, condition.getOperation());
		
	}
}
