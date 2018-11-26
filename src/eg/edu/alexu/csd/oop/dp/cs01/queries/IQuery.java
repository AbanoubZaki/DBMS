package eg.edu.alexu.csd.oop.dp.cs01.queries;

public interface IQuery {

	//Builder pattern i think.
	
	/**
	 * sets the name of the table.
	 * @param table
	 */
	public void setTableName(String tableName);

	/**
	 * gets the name of the table.
	 * @return
	 */
	public String getTableName();
	
	/**
	 * Executes the query.
	 * action that the query performs
	 * returns true if it is successfully executed.
	 * returns false if a problem occurred. 
	 * @return
	 */
	public boolean execute ();
}
