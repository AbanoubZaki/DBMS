package eg.edu.alexu.csd.oop.db.cs01.queries;

import java.sql.SQLException;
import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.cs01.dataChecker;
import eg.edu.alexu.csd.oop.db.cs01.jdbc.OurLogger;
import eg.edu.alexu.csd.oop.db.cs01.modules.Cell;
import eg.edu.alexu.csd.oop.db.cs01.modules.Row;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class InsertInto extends OurQuery {

	private ArrayList<String> columnNames;

	private ArrayList<String> values;

	public InsertInto(ArrayList<String> values) {
		this.columnNames = null;
		this.values = values;
		setUpdatedRows(0);
	}

	public InsertInto(ArrayList<String> columnNames, ArrayList<String> values) {
		this.columnNames = columnNames;
		this.values = values;
		setUpdatedRows(0);
	}

	/**
	 * after the table has been read in the constructor add the row then write the
	 * table in the file using file manager.
	 * 
	 * @throws SQLException
	 */
	@Override
	public boolean execute() throws SQLException {
		if (Table.getInstance() == null || Table.getInstance().getColumnNamesAsGiven().size() == 0) {
			OurLogger.error(this.getClass(), "Table not found.");
			throw new SQLException("Table not found.");
		}
		if (Table.getInstance().getColumnTypes() == null) {
			OurLogger.error(this.getClass(), "Datatype mismatch happened.");
			throw new SQLException("Datatype mismatch happened.");
		}
		if (columnNames == null || columnNames.isEmpty()) {
			columnNames = Table.getInstance().getColumnNamesToLowerCase();
			// no of values must be equal to no of columns.
			if (values.size() != columnNames.size()) {
				throw new SQLException(
						"you have entered " + values.size() + " but table needs " + columnNames.size() + " values.");
			}
		}
		for (int i = 0; i < columnNames.size(); i++) {
			if (!Table.getInstance().getColumnNamesToLowerCase().contains(columnNames.get(i).toLowerCase())) {
				OurLogger.error(this.getClass(), "Column names not found.");
				throw new SQLException("Column names not found.");
			}
		}

		Row insertedRow = new Row(Table.getInstance());
		for (int i = 0; i < values.size(); i++) {
			if (Table.getInstance().getColumnTypes().get(columnNames.get(i).toLowerCase()).equals("int")) {
				if (Table.getInstance().getColumnTypes().get(columnNames.get(i).toLowerCase())
						.equals(dataChecker.getInstance().checkType(values.get(i)))) {
					insertedRow.updateCell(columnNames.get(i).toLowerCase(), new Cell(values.get(i)));
				} else {
					OurLogger.error(this.getClass(), "Datatype mismatch happened.");
					throw new SQLException("Datatype mismatch happened.");
				}
			} else {
				if (!values.get(i).contains("\"") && !values.get(i).contains("'")) {
					values.set(i, "'" + values.get(i) + "'");
				} else {
					values.set(i, values.get(i).replace("\"", "'"));
				}
				insertedRow.updateCell(columnNames.get(i).toLowerCase(), new Cell(values.get(i)));
			}
		}
		Table.getInstance().addRow(insertedRow);
		setUpdatedRows(1);
		return true;
	}
}
