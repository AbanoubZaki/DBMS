package eg.edu.alexu.csd.oop.db.cs01.queries;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.cs01.condition.LogicalCondition;
import eg.edu.alexu.csd.oop.db.cs01.condition.LogicalSolver;
import eg.edu.alexu.csd.oop.db.cs01.jdbc.OurLogger;
import eg.edu.alexu.csd.oop.db.cs01.modules.Row;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class DeleteFrom extends OurQuery {

	public DeleteFrom(LogicalCondition condition) {
		setCondition(condition);
		setUpdatedRows(0);
	}
	
	@Override
	public boolean execute() throws SQLException {
		if (Table.getInstance() == null || Table.getInstance().getColumnNamesAsGiven().size() == 0) {
			OurLogger.error(this.getClass(), "Table not found.");
			throw new SQLException("Table not found.");
		}
		/**
		 * check if table exists.
		 */
		String pathTable = Table.getInstance().getDatabaseName();
		if(!pathTable.contains(System.getProperty("file.separator")))
			pathTable="databases"+System.getProperty("file.separator")+pathTable;
		pathTable+=System.getProperty("file.separator")+ Table.getInstance().getTableName();
		File tableFile = new File(pathTable+".Xml");
		File DTDFile = new File(pathTable+".dtd");
		if(!tableFile.exists() || !DTDFile.exists()) {
			OurLogger.error(this.getClass(), "Table not found.");
			throw new SQLException("Table not found.");
		}
		
		int effectedRows = Table.getInstance().getRows().size();
		if (getCondition().getStringCondition() == null) {
			Table.getInstance().getRows().clear();
			setUpdatedRows(effectedRows);
			return true;
		} else if (getCondition().getStringCondition() != null) {
			ArrayList<Row> remainingRows = new ArrayList<>();
			for (Row r:Table.getInstance().getRows()) {
				if (!LogicalSolver.getInstance().isRowSolvingCondition(r, getCondition())) {
					remainingRows.add(r);
				}
			}
			Table.getInstance().setRows(remainingRows);
			 effectedRows -= Table.getInstance().getRows().size();
		}
		setUpdatedRows(effectedRows);
		return true;
	}

}
