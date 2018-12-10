package eg.edu.alexu.csd.oop.db.cs01.condition;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.cs01.jdbc.OurLogger;
import eg.edu.alexu.csd.oop.db.cs01.modules.Row;

public class RelationalSolver {

	private static RelationalSolver solver;

	private RelationalSolver() {
	}

	public static RelationalSolver getInstance() {
		if (solver == null) {
			solver = new RelationalSolver();
		}
		return solver;
	}

	public boolean isRowSolvingCondition(Row row, RelationalCondition condition) throws SQLException {
		// value , value
		if (condition.getLeftAgrument() == null || condition.getRightAgrument() == null) {
			if (condition.getOperation() == null)
				return true;
			OurLogger.error(this.getClass(), "incomplete condition");
			throw new SQLException("incomplete condition");
		}
		if (condition.getLeftAgrument().isVariable() && condition.getRightAgrument().isVariable())
			return compareTwoValue(condition);
		else if (!condition.getLeftAgrument().isVariable() && !condition.getRightAgrument().isVariable())
			return compareTwoCol(row, condition);
		else
			return compareValueCol(row, condition);

	}

	private boolean compareTwoValue(RelationalCondition condition) {
		if (!condition.getLeftAgrument().getType().equals(condition.getRightAgrument().getType())) {
			System.out.println("error");
		}
		return getRelationResult(condition.getLeftAgrument(), condition.getRightAgrument(), condition.getOperation());

	}

	private boolean getRelationResult(RelationalOperand var1, RelationalOperand var2, String operation) {

		if (var1.getOperand() == null || var2.getOperand() == null) {
			return true;
		}

		return JavaScriptEngine.getInstance()
				.getResult(var1.getOperand().toLowerCase() + operation + var2.getOperand().toLowerCase());

	}

	private boolean compareValueCol(Row row, RelationalCondition condition) {
		if (!condition.getLeftAgrument().isVariable()) {
			RelationalOperand rp = new RelationalOperand(row.getCellByColumn(condition.getLeftAgrument().getOperand()),
					false, condition.getLeftAgrument().getType());
			return getRelationResult(rp, condition.getRightAgrument(), condition.getOperation());

		} else {
			RelationalOperand rp = new RelationalOperand(row.getCellByColumn(condition.getRightAgrument().getOperand()),
					false, condition.getRightAgrument().getType());
			return getRelationResult(rp, condition.getLeftAgrument(), condition.getOperation());
		}
	}

	private boolean compareTwoCol(Row row, RelationalCondition condition) {
		RelationalOperand lrp = new RelationalOperand(row.getCellByColumn(condition.getLeftAgrument().getOperand()),
				false, condition.getLeftAgrument().getType());
		RelationalOperand rrp = new RelationalOperand(row.getCellByColumn(condition.getRightAgrument().getOperand()),
				false, condition.getRightAgrument().getType());
		return getRelationResult(lrp, rrp, condition.getOperation());

	}
}
