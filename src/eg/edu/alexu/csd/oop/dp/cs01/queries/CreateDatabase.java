package eg.edu.alexu.csd.oop.dp.cs01.queries;

import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;

public class CreateDatabase extends OurQuery{
	
	private String databaseName;
	
	private String databasePath;
	
	private boolean dropIfExists;
	
	public CreateDatabase(String databaseName, boolean dropIfExists) {
		super();
		this.databaseName = new String(databaseName);
		this.dropIfExists = dropIfExists;
		databasePath = new String();
	}
	
	/**
	 * creates a data base and sets databasePath to its path. 
	 * @return
	 */
	@Override
	public boolean execute1() {
		if (databaseName == null) {
			return false;
		}
		databasePath = FileManager.getInstance().createDB(databaseName, dropIfExists); 
		return true;
	}
	
	public String getDatabasePath() {
		return databasePath;
	}

}
