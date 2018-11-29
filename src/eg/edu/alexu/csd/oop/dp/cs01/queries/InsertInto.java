package eg.edu.alexu.csd.oop.dp.cs01.queries;

import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.cs01.dataChecker;
import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;
import eg.edu.alexu.csd.oop.db.cs01.modules.Cell;
import eg.edu.alexu.csd.oop.db.cs01.modules.Row;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class InsertInto extends OurQuery {

	private ArrayList<String> columnNames;

	private ArrayList<String> values;

	public InsertInto(Table table, ArrayList<String> values) {
		this.columnNames = null;
		this.values = values;
		setTable(table);
	}

	public InsertInto(Table table, ArrayList<String> columnNames, ArrayList<String> values) {
		this.columnNames = columnNames;
		this.values = values;
		setTable(table);
	}

	/**
	 * after the table has been read in the constructor add the row then write the
	 * table in the file using file manager.
	 */
	@Override
	public int execute2() {
		if (getTable()==null||getTable().getColumnTypes()==null) {
			System.out.println("Table not found.");
			return 0;
		}
		if (getTable().getColumnTypes() == null) {
			System.out.println("column types dismatch happened");
			return 0;
		}
		if (columnNames == null) {
			columnNames = getTable().getColumnNamesToLowerCase();
			// no of values must be equal to no of columns.
			if (values.size() != columnNames.size()) {
				System.out.println(
						"you have entered " + values.size() + " but table needs " + columnNames.size() + " values.");
				return 0;
			}
		}
		for (int i = 0; i < columnNames.size(); i++) {
			if (!getTable().getColumnNamesToLowerCase().contains(columnNames.get(i).toLowerCase())) {
				System.out.println("Column names not found.");
				return 0;
			}
		}
		Row insertedRow = new Row(getTable());
		for (int i = 0; i < values.size(); i++) {
			if (getTable().getColumnTypes().get(columnNames.get(i).toLowerCase()).equals("int")) {
				if (getTable().getColumnTypes().get(columnNames.get(i).toLowerCase())
						.equals(dataChecker.getInstance().checkType(values.get(i)))) {
					insertedRow.updateCell(columnNames.get(i).toLowerCase(), new Cell(values.get(i)));
				} else
					return 0;
			} else
				insertedRow.updateCell(columnNames.get(i).toLowerCase(), new Cell(values.get(i)));
		}
		getTable().addRow(insertedRow);
		return 1;
	}
}
