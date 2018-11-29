package eg.edu.alexu.csd.oop.dp.cs01.queries;

import java.sql.SQLException;
import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.cs01.condition.RelationalCondition;
import eg.edu.alexu.csd.oop.db.cs01.condition.RelationalSolver;
import eg.edu.alexu.csd.oop.db.cs01.modules.Cell;
import eg.edu.alexu.csd.oop.db.cs01.modules.Row;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class UpdateSet extends OurQuery {

	private ArrayList<String> columnNames;

	private ArrayList<String> values;

	public UpdateSet(Table table, ArrayList<String> columnNames, ArrayList<String> values,
			RelationalCondition condition) {
		this.columnNames = columnNames;
		this.values = values;
		setTable(table);
		setCondition(condition);
	}

	@Override
	public int execute2()throws SQLException {
		
		if(getTable().getData()==null) {
			new SQLException("table not found");
			return 0;
		}
		for (int i = 0; i < columnNames.size(); i++) {
			if (!getTable().getColumnNamesToLowerCase().contains(columnNames.get(i).toLowerCase())) {
				System.out.println("Column names not found.");
				return 0;
			}
		}
		if(!getTable().checkDataTypeMatch(columnNames, values))
			return 0;

		int effectedRows = 0;
			if (getCondition().getStringCondition() == null) {
				for(Row r : getTable().getRows()){
					for (int i = 0; i < columnNames.size(); i++) {
						r.updateCell(columnNames.get(i).toLowerCase(), new Cell(values.get(i)));
					}
					effectedRows++;
				}		
			} else {
				for (Row r : getTable().getRows()) {
					if (RelationalSolver.getInstance().isRowSolvingCondition(r, getCondition())) {
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
