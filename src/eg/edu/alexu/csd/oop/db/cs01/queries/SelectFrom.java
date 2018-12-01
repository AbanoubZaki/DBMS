package eg.edu.alexu.csd.oop.db.cs01.queries;

import java.sql.SQLException;
import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.cs01.condition.LogicalCondition;
import eg.edu.alexu.csd.oop.db.cs01.condition.LogicalSolver;
import eg.edu.alexu.csd.oop.db.cs01.modules.Row;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class SelectFrom extends OurQuery {

	private Object[][] selected;

	/**
	 * fetch all the table for the condition returning the selected rows. if
	 * condition is null return all the table.
	 * 
	 * @param condition
	 */
	public SelectFrom(LogicalCondition condition) {
		setCondition(condition);
	}

	/**
	 * fetch a certain column for the condition returning the selected rows. if
	 * condition is null return one column (whole column)
	 * 
	 * @param column
	 * @param condition
	 */
	public SelectFrom(String column, LogicalCondition condition) {
		setCondition(condition);
		setColumn(column.toLowerCase());
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
	public boolean execute() throws SQLException {
		if (Table.getInstance() == null || Table.getInstance().getColumnNamesAsGiven().size() == 0) {
			throw new SQLException("Table not found.");
		}
		if (getColumn() == null && getCondition().getStringCondition() == null) {
			// 1st constructor
			setSelected(Table.getInstance().getData());
			return true;

		} else if (getColumn() != null && getCondition().getStringCondition() == null) {
			// 2nd constructor
			// return a whole column.
			/**
			 * if its a imaginary column the return false.
			 */
			if (!Table.getInstance().getColumnNamesToLowerCase().contains(getColumn())) {
				throw new SQLException("Column \"" + getColumn() + "\" not found.");
			}
			selected = new Object[Table.getInstance().getRows().size()][1];
			for (int i = 0; i < Table.getInstance().getRows().size(); i++) {
				// row of data to be filled with objects.
				if (Table.getInstance().getColumnTypes().get(getColumn() ).equals("varchar")) {
					selected[i][0] = Table.getInstance().getRow(i).getCells().get(getColumn() ).getValue();
				} else {
					selected[i][0] = Integer.parseInt(
							Table.getInstance().getRow(i).getCells().get(getColumn() ).getValue());
				}
			}
			setSelected(selected);
			return true;

		} else if (getColumn() == null) {
			// 3rd constructor
			// fetches all the table using the condition.
			ArrayList<Row> rowsValidateCondition = new ArrayList<>();
			for (Row r : Table.getInstance().getRows()) {
				if (LogicalSolver.getInstance().isRowSolvingCondition(r, getCondition())) {
					rowsValidateCondition.add(r);
				}
			}
			selected = new Object[rowsValidateCondition.size()][Table.getInstance().getColumnNamesAsGiven().size()];
			for (int i = 0; i < rowsValidateCondition.size(); i++) {
				// row of data to be filled with objects.
				Row r = rowsValidateCondition.get(i);
				for (int j = 0; j < Table.getInstance().getColumnNamesToLowerCase().size(); j++) {
					if (Table.getInstance().getColumnTypes().get(Table.getInstance().getColumnNamesToLowerCase().get(j))
							.equals("int")) {
						if (r.getCells().get(Table.getInstance().getColumnNamesToLowerCase().get(j)) != null) {
							selected[i][j] = (Integer.parseInt(r.getCells()
									.get(Table.getInstance().getColumnNamesToLowerCase().get(j)).getValue()));
						} else {
							selected[i][j] = null;
						}
					} else if (Table.getInstance().getColumnTypes()
							.get(Table.getInstance().getColumnNamesToLowerCase().get(j)).equals("varchar")) {
						selected[i][j] = r.getCells().get(Table.getInstance().getColumnNamesToLowerCase().get(j))
								.getValue();
					}
				}
			}
			setSelected(selected);

			return true;
		} else if (getColumn() != null && getCondition().getStringCondition() != null) {
			// 4th constructor
			// returns a part of one column only which matches the condition.
			/**
			 * if its a imaginary column the return false.
			 */
			if (!Table.getInstance().getColumnNamesToLowerCase().contains(getColumn() )) {
				return false;
			}
			ArrayList<Object> selectedPartOfColumn = new ArrayList<>();

			for (int i = 0; i < Table.getInstance().getRows().size(); i++) {
				Row r = Table.getInstance().getRows().get(i);
				if (LogicalSolver.getInstance().isRowSolvingCondition(r, getCondition())) {
					if (Table.getInstance().getColumnTypes().get(getColumn() ).equals("varchar")) {
						selectedPartOfColumn.add(
								Table.getInstance().getRow(i).getCells().get(getColumn() ).getValue());
					} else {
						if (r.getCells().get(getColumn() ) != null)
							selectedPartOfColumn.add(Integer.parseInt(Table.getInstance().getRow(i).getCells()
									.get(getColumn() ).getValue()));
					}
				}
			}
			selected = new Object[selectedPartOfColumn.size()][1];
			for (int i = 0; i < selected.length; i++) {
				selected[i][0] = selectedPartOfColumn.get(i);
			}
			setSelected(selected);
			return true;
		}
		return false;
	}

}
