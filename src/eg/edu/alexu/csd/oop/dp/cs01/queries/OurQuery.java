package eg.edu.alexu.csd.oop.dp.cs01.queries;

import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public abstract class OurQuery implements IQuery{

	private Table table;
	public OurQuery() {
		// TODO Auto-generated constructor stub
	}
	public OurQuery(Table table) {
		// TODO Auto-generated constructor stub
		this.table = table;
	}
	/**
	 * @return the table
	 */
	public Table getTable() {
		return table;
	}


	/**
	 * @param table the table to set
	 */
	public void setTable(Table table) {
		this.table = table;
	}	
	@Override
	public abstract boolean execute();


}
