package eg.edu.alexu.csd.oop.dp.cs01.queries;

import java.util.HashSet;
import java.util.Set;

import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class CreateTable extends OurQuery {

	public CreateTable(Table table) {
		super(table);
	}
	@Override
	public boolean execute1() {
		Set<String> columnsSet = new HashSet<>(super.getTable().getColumnNamesToLowerCase()); 
		if(getTable()==null) {
			return false;
		}
		if(columnsSet.size() != super.getTable().getColumnNamesToLowerCase().size()) {
			System.out.println("Duplicates found in column names.");
			return false;
		}
		if(!FileManager.getInstance().createTable(getTable())) {
			FileManager.getInstance().readTable(getTable());
			return false;
		}
		return true;
	}

}
