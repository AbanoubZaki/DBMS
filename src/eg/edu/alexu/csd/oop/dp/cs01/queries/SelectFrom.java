package eg.edu.alexu.csd.oop.dp.cs01.queries;

import eg.edu.alexu.csd.oop.db.cs01.Condition;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class SelectFrom extends OurQuery {

	/**
	 * return all the table.
	 * @param table
	 */
	public SelectFrom(Table table) {
		setTable(table);
	}

	/**
	 * return one column (whole column)
	 * @param table
	 * @param column
	 */
	public SelectFrom(Table table, String column) {
		setTable(table);
		setColumn(column);
	}
	
	/**
	 * fetch all the table for the condition returning the selected rows.
	 * @param table
	 * @param condition
	 */
	public SelectFrom(Table table, Condition condition) {
		setTable(table);
		setCondition(condition);
		
	}

	/**
	 * fetch a certain column for the condition returning the selected rows.
	 * @param table
	 * @param column
	 * @param condition
	 */
	public SelectFrom(Table table, String column, Condition condition) {
		setTable(table);
		setCondition(condition);
		setColumn(column);
	}

	Object [][] selected;
	
	public void setSelected(Object[][] selected) {
		this.selected = selected;
	}
	
	public Object[][] getSelected() {
		return selected;
	}
	
	@Override
	public boolean execute() {
		if (getColumn() == null && getCondition() == null) {
			//1st constructor
			/**
			 * if its a imaginary column the return false.
			 */
			if (!getTable().getColumnNames().contains(getColumn())) {
				return false;
			}
			for (int i = 0; i < getTable().getRows().size(); i++) {
				
			}
			return true;
		} else if (getCondition() == null) {
			//2nd constructor
			
			return true;
		} else if (getColumn() == null) {
			//3rd constructor
			
			return true;
		} else if (getColumn() != null && getCondition() != null) {
			//4th constructor
			
			return true;
		}
		return false;
	}

}
