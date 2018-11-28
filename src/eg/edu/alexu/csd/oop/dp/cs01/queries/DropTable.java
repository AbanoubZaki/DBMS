package eg.edu.alexu.csd.oop.dp.cs01.queries;

import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class DropTable extends OurQuery {
	
	public DropTable(Table table) {
		super(table);
	}
	
	@Override
	public boolean execute1() {
		if(getTable()==null)
			return false;
		return FileManager.getInstance().dropTable(getTable());
	}

}
