package eg.edu.alexu.csd.oop.dp.cs01.queries;

import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class CreateTable extends OurQuery {

	public CreateTable(Table table) {
		// TODO Auto-generated constructor stub
		super(table);
	}
	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		if(getTable()==null)
		return false;
		return FileManager.getInstance().createTable(getTable());
	}

}
