package eg.edu.alexu.csd.oop.dp.cs01.queries;

import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.CompoundInstruction;

import eg.edu.alexu.csd.oop.db.cs01.condition.ConditionParser;
import eg.edu.alexu.csd.oop.db.cs01.condition.RelationalCondition;
import eg.edu.alexu.csd.oop.db.cs01.condition.RelationalSolver;
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
	public void setColumnType() {
		this.columnType  = getTable().getColumnTypes().get(getColumn());
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
		if (getColumn() == null && getCondition().getStringCondition() == null) {
			// 1st constructor
			setSelected(getTable().getData());
			return true;

		} else if (getColumn() != null && getCondition().getStringCondition() == null) {
			// 2nd constructor
			//return a whole column.
			/**
			 * if its a imaginary column the return false.
			 */
			if (!getTable().getColumnNamesToLowerCase().contains(getColumn().toLowerCase())) {
				System.out.println("column \"" + getColumn() + "\" not found");
				return false;
			}
			selected = new Object[getTable().getRows().size()][1];
			for (int i = 0; i < getTable().getRows().size(); i++) {
				// row of data to be filled with objects.
				if (getColumnType().equals("varchar")) {
					selected[i][1] = getTable().getRow(i).getCells().get(getColumn().toLowerCase()).getValue();
				} else {
					selected[i][1] = Integer.parseInt(getTable().getRow(i).getCells().get(getColumn().toLowerCase()).getValue());
				}
			}
			setSelected(selected);
			return true;
			
		} else if (getColumn() == null) {
			// 3rd constructor
			ArrayList<Row> remainingRows = new ArrayList<>();
			for (Row r:getTable().getRows()) {
				if (RelationalSolver.getInstance().isRowSolvingCondition(r, getCondition())) {
					remainingRows.add(r);
				}
			}
			getTable().setRows(remainingRows);
			setSelected(getTable().getData());
			
			return true;
		} else if (getColumn() != null && getCondition().getStringCondition() != null) {
			// 4th constructor
			// returns a part of one column only which matches the condition.
			/**
			 * if its a imaginary column the return false.
			 */
			if (!getTable().getColumnNamesToLowerCase().contains(getColumn().toLowerCase())) {
				return false;
			}
			ArrayList<Object> selectedPartOfColumn = new ArrayList<>();
			for (int i = 0; i < getTable().getRows().size(); i++) {
				Row r = getTable().getRows().get(i);
				setCondition(ConditionParser.getInstance().stringToRelationalCondition(getCondition().getStringCondition()));
				if (RelationalSolver.getInstance().isRowSolvingCondition(r, getCondition())) {
					if (getColumnType().equals("varchar")) {
						selectedPartOfColumn.add(getTable().getRow(i).getCells().get(getColumn().toLowerCase()).getValue());
					} else {
						selectedPartOfColumn.add(Integer.parseInt(getTable().getRow(i).getCells().get(getColumn().toLowerCase()).getValue()));
					}
				}
			}
			selected = new Object[selectedPartOfColumn.size()][1];
			for (int i = 0; i < selected.length; i++) {
				selected[i][1] = selectedPartOfColumn.get(i);
			}
			setSelected(selected);
			return true;
		}
		return false;
	}

}
