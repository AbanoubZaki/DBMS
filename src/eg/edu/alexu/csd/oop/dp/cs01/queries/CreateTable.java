package eg.edu.alexu.csd.oop.dp.cs01.queries;

import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class CreateTable extends OurQuery {

	Table table;
	public CreateTable(Table table) {
		// TODO Auto-generated constructor stub
		this.table=table;
	}
	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		if(table==null)
		return false;
		return FileManager.getInstance().createTable(this.table);
	}

}
