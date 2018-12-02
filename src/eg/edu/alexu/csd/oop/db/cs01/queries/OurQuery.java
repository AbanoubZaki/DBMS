package eg.edu.alexu.csd.oop.db.cs01.queries;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.cs01.condition.LogicalCondition;

public abstract class OurQuery implements IQuery {

	private LogicalCondition condition;
	private int updatedRows;
	
	@Override
	public abstract boolean execute() throws SQLException;

	@Override
	public void setCondition(LogicalCondition condition2) {
		this.condition = condition2;
	}

	@Override
	public LogicalCondition getCondition() {
		return condition;
	}

	@Override
	public void setSelected(Object[][] selected) {
	}

	@Override
	public Object[][] getSelected() {
		return null;
	}

	@Override
	public int getUpdatedRows() {
		return updatedRows;
	}

	@Override
	public void setUpdatedRows(int updatedRows) {
		this.updatedRows =  updatedRows;
	}

}
