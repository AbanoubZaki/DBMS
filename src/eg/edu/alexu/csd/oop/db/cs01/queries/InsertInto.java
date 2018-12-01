package eg.edu.alexu.csd.oop.db.cs01.queries;

import java.sql.SQLException;
import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.cs01.dataChecker;
import eg.edu.alexu.csd.oop.db.cs01.modules.Cell;
import eg.edu.alexu.csd.oop.db.cs01.modules.Row;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class InsertInto extends OurQuery {

	private ArrayList<String> columnNames;

	private ArrayList<String> values;

	public InsertInto(ArrayList<String> values) {
		this.columnNames = null;
		this.values = values;
	}

	public InsertInto(ArrayList<String> columnNames, ArrayList<String> values) {
		this.columnNames = columnNames;
		this.values = values;
	}

	/**
	 * after the table has been read in the constructor add the row then write the
	 * table in the file using file manager.
	 * @throws SQLException 
	 */
	@Override
	public int execute2() throws SQLException {
		if (Table.getInstance() == null || Table.getInstance().getColumnNamesAsGiven().size() == 0) {
			throw new SQLException("Table not found.");
		}
		if (Table.getInstance().getColumnTypes() == null) {
			System.out.println("Datatype mismatch happened.");
			return 0;
		}
		if (columnNames == null || columnNames.isEmpty()) {
			columnNames = Table.getInstance().getColumnNamesToLowerCase();
			// no of values must be equal to no of columns.
			if (values.size() != columnNames.size()) {
				System.out.println(
						"you have entered " + values.size() + " but table needs " + columnNames.size() + " values.");
				return 0;
			}
		}
		for (int i = 0; i < columnNames.size(); i++) {
			if (!Table.getInstance().getColumnNamesToLowerCase().contains(columnNames.get(i).toLowerCase())) {
				System.out.println("Column names not found.");
				return 0;
			}
		}
		Row insertedRow = new Row(Table.getInstance());
		for (int i = 0; i < values.size(); i++) {
			if (Table.getInstance().getColumnTypes().get(columnNames.get(i).toLowerCase()).equals("int")) {
				if (Table.getInstance().getColumnTypes().get(columnNames.get(i).toLowerCase())
						.equals(dataChecker.getInstance().checkType(values.get(i)))) {
					insertedRow.updateCell(columnNames.get(i).toLowerCase(), new Cell(values.get(i)));
				} else
					return 0;
			} else
				insertedRow.updateCell(columnNames.get(i).toLowerCase(), new Cell(values.get(i)));
		}
		Table.getInstance().addRow(insertedRow);
		return 1;
	}
}
