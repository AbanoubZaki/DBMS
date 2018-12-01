package eg.edu.alexu.csd.oop.db.cs01.queries;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class CreateTable extends OurQuery {

	@Override
	public boolean execute1() throws SQLException {
		if (Table.getInstance() == null || Table.getInstance().getColumnNamesAsGiven().size() == 0) {
			throw new SQLException("Table not found.");
		}
		Set<String> columnsSet = new HashSet<>(Table.getInstance().getColumnNamesToLowerCase()); 
		if(columnsSet.size() != Table.getInstance().getColumnNamesToLowerCase().size()) {
			System.out.println("Duplicates found in column names.");
			return false;
		}
		if(!FileManager.getInstance().createTable(Table.getInstance())) {
			FileManager.getInstance().readTable(Table.getInstance());
			return false;
		}
		return true;
	}

}
