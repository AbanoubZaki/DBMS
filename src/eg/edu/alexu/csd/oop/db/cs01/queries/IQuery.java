package eg.edu.alexu.csd.oop.db.cs01.queries;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.cs01.condition.LogicalCondition;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public interface IQuery {

	// Builder pattern i think.

	/**
	 * Executes the query. action that the query performs returns true if it is
	 * successfully executed. returns false if a problem occurred.
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public boolean execute1() throws SQLException;
	
	
	public int execute2() throws SQLException;

	/**
	 * reads the table from files in the folder of the chosen database.
	 * 
	 * @return
	 */
	public void setColumn(String column);

	public String getColumn();

	public void setCondition(LogicalCondition condition);

	public LogicalCondition getCondition();
	
	public void setSelected(Object[][] selected);
	
	public Object[][] getSelected();
		
}
