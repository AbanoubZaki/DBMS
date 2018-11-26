package eg.edu.alexu.csd.oop.dp.cs01.queries;

import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;

public class DropDatabase extends OurQuery{

	private String databaseName;
	public DropDatabase(String databasaNme) {
		// TODO Auto-generated constructor stub
		super();
		this.databaseName = databasaNme;
	}
	@Override
	public boolean execute() {
		if (databaseName == null) {
			return false;
		}
		return FileManager.getInstance().dropDB(this.databaseName);
	}

}
