package eg.edu.alexu.csd.oop.dp.cs01.queries;

import java.util.ArrayList;
import eg.edu.alexu.csd.oop.db.cs01.condition.RelationalCondition;
import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;
import eg.edu.alexu.csd.oop.db.cs01.modules.Row;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class SelectFrom extends OurQuery {

	private Object[][] selected;

	private String columnType;

	private int columnIndex = -1;

	/**
	 * fetch all the table for the condition returning the selected rows. if
	 * condition is null return all the table.
	 * 
	 * @param table
	 * @param condition
	 */
	public SelectFrom(Table table, RelationalCondition condition) {
		setTable(table);
		setCondition(condition);
	}

	/**
	 * fetch a certain column for the condition returning the selected rows. if
	 * condition is null return one column (whole column)
	 * 
	 * @param table
	 * @param column
	 * @param condition
	 */
	public SelectFrom(Table table, String column, RelationalCondition condition) {
		setTable(table);
		setCondition(condition);
		setColumn(column);
		setColumnIndexAndType();
	}
	
	@Override
	public void setSelected(Object[][] selected) {
		this.selected = selected;
	}
	
	@Override
	public Object[][] getSelected() {
		return selected;
	}

	@Override
	public void setColumnIndexAndType() {
		for (int i = 0; i < getTable().getColumnNamesToLowerCase().size(); i++) {
			if (getTable().getColumnNamesToLowerCase().contains(getColumn().toLowerCase())) {
				this.columnIndex = i;
				break;
			}
		}
		this.columnType  = getTable().getColumnTypes().get(getTable().getColumnNamesToLowerCase().get(getColumnIndex()));
	}

	@Override
	public String getColumnType() {
		return columnType;
	}

	@Override
	public int getColumnIndex() {
		return columnIndex;
	}

	@Override
	public boolean execute1() {
		if (getColumn() == null && getCondition() == null) {
			// 1st constructor
			setSelected(getTable().getData());
			return true;

		} else if (getCondition() == null) {
			// 2nd constructor
			/**
			 * if its a imaginary column the return false.
			 */
			if (!getTable().getColumnNamesToLowerCase().contains(getColumn().toLowerCase())) {
				return false;
			}
			FileManager.getInstance().readTable(getTable());
				
			ArrayList<ArrayList<Object>> data = new ArrayList<>();
			for (int i = 0; i < getTable().getRows().size(); i++) {
				// row of data to be filled with objects.
				ArrayList<Object> dataRow = new ArrayList<>();
				Row r = getTable().getRows().get(i);
				for (int j = 0; j <getTable().getColumnNamesToLowerCase().size() ; j++) {
					if (j == getColumnIndex()) {
						if (getColumnType().equals("int")) {
							dataRow.add((Integer.parseInt(r.getCells().get(getTable().getColumnNamesToLowerCase().get(j)).getValue())));
						} else if (getColumnType().equals("varchar")) {
							dataRow.add(r.getCells().get(getTable().getColumnNamesToLowerCase().get(j)).getValue());
						}
					}
				}
				data.add(dataRow);
			}

			return true;
		} else if (getColumn() == null) {
			// 3rd constructor

			return true;
		} else if (getColumn() != null && getCondition() != null) {
			// 4th constructor
			/**
			 * if its a imaginary column the return false.
			 */
			if (!getTable().getColumnNamesToLowerCase().contains(getColumn().toLowerCase())) {
				return false;
			}
			
			return true;
		}
		return false;
	}

}
