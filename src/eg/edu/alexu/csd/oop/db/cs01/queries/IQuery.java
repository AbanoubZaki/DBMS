package eg.edu.alexu.csd.oop.db.cs01.queries;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.cs01.condition.LogicalCondition;

public interface IQuery {

	// Builder pattern i think.

	/**
	 * Executes the query. action that the query performs returns true if it is
	 * successfully executed. returns false if a problem occurred.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public boolean execute() throws SQLException;

	public void setCondition(LogicalCondition condition);

	public LogicalCondition getCondition();

	public void setSelected(Object[][] selected);

	public Object[][] getSelected();

	public void setUpdatedRows(int updatedRows);

	public int getUpdatedRows();

}
