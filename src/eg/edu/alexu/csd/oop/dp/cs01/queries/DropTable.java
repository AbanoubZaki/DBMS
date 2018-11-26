package eg.edu.alexu.csd.oop.dp.cs01.queries;

import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class DropTable extends OurQuery {

	Table table;
	
	public DropTable(Table table) {
		this.table=table;
	}
	
	@Override
	public boolean execute() {
		if(table==null)
			return false;
		return FileManager.getInstance().dropTable(this.table);
	}

}
