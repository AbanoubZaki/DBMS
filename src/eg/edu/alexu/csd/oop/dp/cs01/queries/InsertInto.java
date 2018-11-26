package eg.edu.alexu.csd.oop.dp.cs01.queries;

import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.cs01.dataChecker;
import eg.edu.alexu.csd.oop.db.cs01.modules.Cell;
import eg.edu.alexu.csd.oop.db.cs01.modules.Row;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class InsertInto extends OurQuery {

	private ArrayList<String> columnNames;

	private ArrayList<String> values;

	public InsertInto(Table table, ArrayList<String> values) {
		this.columnNames = null;
		this.values = new ArrayList<>(values);
		this.readTable();
		// then read the table delegating the filemanager.
	}

	public InsertInto(Table table, ArrayList<String> columnNames, ArrayList<String> values) {
		this.columnNames = new ArrayList<>(columnNames);
		this.values = new ArrayList<>(values);
		// then read the table delegating the filemanager.
	}

	/**
	 * after the table has been read in the constructor add the row then write the
	 * table in the file using file manager.
	 */
	@Override
	public boolean execute() {
		if (columnNames == null) {
			columnNames = getTable().getColumnNames();
		}
		Row insertedRow = new Row();
		for (int i = 0; i < columnNames.size(); i++) {
			if (columnNames.get(i) != null && getTable().getColumnTypes().get(columnNames.get(i))
					.equals(dataChecker.getInstance().checkType(values.get(i)))) {
				insertedRow.updateCell(columnNames.get(i), new Cell(values.get(i)));
			} else {
				return false;
			}
		}
		return true;
	}
}
