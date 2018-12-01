package eg.edu.alexu.csd.oop.db.cs01.queries;

import java.sql.SQLException;
import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.cs01.condition.LogicalCondition;
import eg.edu.alexu.csd.oop.db.cs01.condition.LogicalSolver;
import eg.edu.alexu.csd.oop.db.cs01.modules.Cell;
import eg.edu.alexu.csd.oop.db.cs01.modules.Row;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class UpdateSet extends OurQuery {

	private ArrayList<String> columnNames;

	private ArrayList<String> values;

	public UpdateSet(ArrayList<String> columnNames, ArrayList<String> values,
			LogicalCondition condition) {
		this.columnNames = columnNames;
		this.values = values;
		setCondition(condition);
	}

	@Override
	public int execute2()throws SQLException {
		if (Table.getInstance() == null || Table.getInstance().getColumnNamesAsGiven().size() == 0) {
			throw new SQLException("Table not found.");
		}
		if (Table.getInstance().getRows().size() == 0) {
			System.out.println("Update Failed, Table is empty.");
			return 0;
		}
		if(Table.getInstance().getData()==null) {
			new SQLException("Table not found.");
			return 0;
		}
		for (int i = 0; i < columnNames.size(); i++) {
			if (!Table.getInstance().getColumnNamesToLowerCase().contains(columnNames.get(i).toLowerCase())) {
				System.out.println("Column names not found.");
				return 0;
			}
		}
		if(!Table.getInstance().checkDataTypeMatch(columnNames, values))
			return 0;

		int effectedRows = 0;
			if (getCondition().getStringCondition() == null) {
				for(Row r : Table.getInstance().getRows()){
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
		return effectedRows;
	}

}
