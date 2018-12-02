package eg.edu.alexu.csd.oop.db.cs01.queries;

import java.sql.SQLException;
import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.cs01.condition.LogicalCondition;
import eg.edu.alexu.csd.oop.db.cs01.condition.LogicalSolver;
import eg.edu.alexu.csd.oop.db.cs01.modules.Row;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class SelectFrom extends OurQuery {

	private Object[][] selected;

	ArrayList<String> columns;

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
	public SelectFrom(ArrayList<String> columns, LogicalCondition condition) {
		setCondition(condition);
		this.columns = columns;
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
		if (Table.getInstance().getData() == null || Table.getInstance().getData().length == 0) {
			System.out.println("Table is empty.");
		}
		if (columns == null && getCondition().getStringCondition() == null) {
			// 1st constructor
			setSelected(Table.getInstance().getData());
			return true;

		} else if (columns != null && getCondition().getStringCondition() == null) {
			// 2nd constructor
			// return a whole column.
			/**
			 * if its a imaginary column the return false.
			 */
			for (String s : columns) {
				if (!Table.getInstance().getColumnNamesToLowerCase().contains(s.toLowerCase())) {
					throw new SQLException("Column \"" + s + "\" not found.");
				}
			}
			selected = new Object[Table.getInstance().getRows().size()][columns.size()];
			for (int i = 0; i < Table.getInstance().getRows().size(); i++) {
				for (int j = 0; j < columns.size(); j++) {
					// row of data to be filled with objects.
					if (Table.getInstance().getRow(i).getCells().get(columns.get(j).toLowerCase()) != null) {
						if (Table.getInstance().getRow(i).getCells().get(columns.get(j).toLowerCase())
								.getValue() != null) {
							if (Table.getInstance().getColumnTypes().get(columns.get(j).toLowerCase())
									.equals("varchar")) {
								selected[i][j] = Table.getInstance().getRow(i).getCells()
										.get(columns.get(j).toLowerCase()).getValue();
							} else {
								selected[i][j] = Integer.parseInt(Table.getInstance().getRow(i).getCells()
										.get(columns.get(j).toLowerCase()).getValue());
							}
						} else {
							selected[i][j] = null;
						}
					} else {
						selected[i][j] = null;
					}
				}
			}
			setSelected(selected);
			return true;

		} else if (columns == null && getCondition().getStringCondition() != null) {
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
						if (r.getCells().get(Table.getInstance().getColumnNamesToLowerCase().get(j)) == null) {
							selected[i][j] = null;
						} else {
							selected[i][j] = r.getCells().get(Table.getInstance().getColumnNamesToLowerCase().get(j))
									.getValue();
						}
					}
				}
			}
			setSelected(selected);

			return true;
		} else if (columns != null && getCondition().getStringCondition() != null) {
			// 4th constructor
			// returns a part of one column only which matches the condition.
			/**
			 * if its a imaginary column the return false.
			 */
			for (String s : columns) {
				if (!Table.getInstance().getColumnNamesToLowerCase().contains(s.toLowerCase())) {
					throw new SQLException("Column \"" + s + "\" not found.");
				}
			}
			ArrayList<ArrayList<Object>> selectedPartOfTable = new ArrayList<>();

			for (int i = 0; i < Table.getInstance().getRows().size(); i++) {
				Row r = Table.getInstance().getRows().get(i);
				ArrayList<Object> selectedPartOfRow = new ArrayList<>();
				for (int j = 0; j < columns.size(); j++) {
					if (LogicalSolver.getInstance().isRowSolvingCondition(r, getCondition())) {
						if (r.getCells().get(columns.get(j).toLowerCase()) == null) {
							selectedPartOfRow.add(null);
						} else if (Table.getInstance().getColumnTypes().get(columns.get(j).toLowerCase())
								.equals("varchar")) {
							selectedPartOfRow.add(Table.getInstance().getRow(i).getCells()
									.get(columns.get(j).toLowerCase()).getValue());
						} else {
							if (Table.getInstance().getRow(i).getCells().get(columns.get(j).toLowerCase()).getValue() != null) {
								selectedPartOfRow.add(Integer.parseInt(Table.getInstance().getRow(i).getCells()
										.get(columns.get(j).toLowerCase()).getValue()));
							}
						}
					}
				}
				if (!selectedPartOfRow.isEmpty()) {
					selectedPartOfTable.add(selectedPartOfRow);
				}
			}
			selected = new Object[selectedPartOfTable.size()][columns.size()];
			for (int i = 0; i < selected.length; i++) {
				for (int j = 0; j < columns.size(); j++) {
					selected[i][j] = selectedPartOfTable.get(i).get(j);
				}
			}
			setSelected(selected);
			return true;
		}
		return false;
	}

}
