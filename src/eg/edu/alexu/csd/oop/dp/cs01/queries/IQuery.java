package eg.edu.alexu.csd.oop.dp.cs01.queries;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.cs01.condition.RelationalCondition;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public interface IQuery {

	// Builder pattern i think.

	/**
	 * sets the name of the table.
	 * 
	 * @param table
	 */
	public void setTable(Table table);

	/**
	 * gets the name of the table.
	 * 
	 * @return
	 */
	public Table getTable();

	/**
	 * Executes the query. action that the query performs returns true if it is
	 * successfully executed. returns false if a problem occurred.
	 * 
	 * @return
	 */
	public boolean execute1();
	
	
	public int execute2() throws SQLException;

	/**
	 * reads the table from files in the folder of the chosen database.
	 * 
	 * @return
	 */
	public boolean readTable();

	public void setColumn(String column);

	public String getColumn();

	public void setCondition(RelationalCondition condition);

	public RelationalCondition getCondition();
	
	public void setSelected(Object[][] selected);
	
	public Object[][] getSelected();
	
	public void setColumnIndexAndType();
	
	public String getColumnType();
	
	public int getColumnIndex();

	void setColumnType();
	
	
}
