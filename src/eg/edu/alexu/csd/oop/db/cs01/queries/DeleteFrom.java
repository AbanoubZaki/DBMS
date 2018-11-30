package eg.edu.alexu.csd.oop.db.cs01.queries;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.cs01.condition.LogicalCondition;
import eg.edu.alexu.csd.oop.db.cs01.condition.LogicalSolver;
import eg.edu.alexu.csd.oop.db.cs01.modules.Row;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class DeleteFrom extends OurQuery {

	public DeleteFrom(Table table, LogicalCondition condition) {
		setTable(table);
		setCondition(condition);
	}
	
	@Override
	public int execute2() throws SQLException {
		/**
		 * check if table exists.
		 */
		String pathTable = getTable().getDatabaseName();
		if(!pathTable.contains(System.getProperty("file.separator")))
			pathTable="databases"+System.getProperty("file.separator")+pathTable;
		pathTable+=System.getProperty("file.separator")+ getTable().getTableName();
		File tableFile = new File(pathTable+".Xml");
		File DTDFile = new File(pathTable+".dtd");
		if(!tableFile.exists() || !DTDFile.exists()) {
			System.out.println("Table not found.");
			return 0;
		}
		
		int effectedRows = getTable().getRows().size();
		if (getCondition().getStringCondition() == null) {
			getTable().getRows().clear();
			return effectedRows;
		}else if (getCondition().getStringCondition() != null) {
			ArrayList<Row> remainingRows = new ArrayList<>();
			for (Row r:getTable().getRows()) {
				if (!LogicalSolver.getInstance().isRowSolvingCondition(r, getCondition())) {
					remainingRows.add(r);
				}
			}
			getTable().setRows(remainingRows);
			 effectedRows -= getTable().getRows().size();
		}
		
		return effectedRows;
	}

}
