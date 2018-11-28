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
		if (columnNames == null) {
			columnNames = getTable().getColumnNamesToLowerCase();
		}
		Row insertedRow = new Row(getTable());
		for (int i = 0; i < values.size(); i++) {
			if(getTable().getColumnTypes().get(columnNames.get(i)).equals("int")) {
				if (getTable().getColumnTypes().get(columnNames.get(i)).equals(dataChecker.getInstance().checkType(values.get(i)))) {
				insertedRow.updateCell(columnNames.get(i), new Cell(values.get(i)));
				}else
					return 0;
			}else
				insertedRow.updateCell(columnNames.get(i), new Cell(values.get(i)));	
		}
		getTable().addRow(insertedRow);
		return 1;
	}
}
