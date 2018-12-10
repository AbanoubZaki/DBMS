package eg.edu.alexu.csd.oop.db.cs01.queries;

import java.sql.SQLException;
import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.cs01.dataChecker;
import eg.edu.alexu.csd.oop.db.cs01.condition.LogicalCondition;
import eg.edu.alexu.csd.oop.db.cs01.condition.LogicalSolver;
import eg.edu.alexu.csd.oop.db.cs01.jdbc.OurLogger;
import eg.edu.alexu.csd.oop.db.cs01.modules.Cell;
import eg.edu.alexu.csd.oop.db.cs01.modules.Row;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class UpdateSet extends OurQuery {

	private ArrayList<String> columnNames;

	private ArrayList<String> values;

	public UpdateSet(ArrayList<String> columnNames, ArrayList<String> values, LogicalCondition condition) {
		this.columnNames = columnNames;
		this.values = values;
		setCondition(condition);
		setUpdatedRows(0);
	}

	@Override
	public boolean execute() throws SQLException {
		if (Table.getInstance() == null || Table.getInstance().getColumnNamesAsGiven().size() == 0) {
			OurLogger.error(this.getClass(), "Table not found.");
			throw new SQLException("Table not found.");
		}
		if (Table.getInstance().getRows().size() == 0) {
			setUpdatedRows(0);
			return false;
		}
		for (int i = 0; i < columnNames.size(); i++) {
			if (!Table.getInstance().getColumnNamesToLowerCase().contains(columnNames.get(i).toLowerCase())) {
				OurLogger.error(this.getClass(), "Column names not found.");
				throw new SQLException("Column names not found.");
			}
		}
		if (!Table.getInstance().checkDataTypeMatch(columnNames, values)) {
			OurLogger.error(this.getClass(), "Datatype mismatch happened.");
			throw new SQLException("Datatype mismatch happened.");
		}

		int effectedRows = 0;
		for (String s : values) {
			if ((!s.contains("\"") && !s.contains("'")) && dataChecker.getInstance().checkType(s).equals("varchar")) {
				values.set(values.indexOf(s), "'" + s + "'");
			} else {
				values.set(values.indexOf(s), s.replace("\"", "'"));
			}
		}
		if (getCondition().getStringCondition() == null) {
			for (Row r : Table.getInstance().getRows()) {
				for (int i = 0; i < columnNames.size(); i++) {
					r.updateCell(columnNames.get(i).toLowerCase(), new Cell(values.get(i)));
				}
				effectedRows++;
			}
		} else {
			for (Row r : Table.getInstance().getRows()) {
				if (LogicalSolver.getInstance().isRowSolvingCondition(r, getCondition())) {
					for (int i = 0; i < columnNames.size(); i++) {
						r.updateCell(columnNames.get(i).toLowerCase(), new Cell(values.get(i)));
					}
					effectedRows++;
				}
			}
		}
		setUpdatedRows(effectedRows);
		return true;
	}

}
