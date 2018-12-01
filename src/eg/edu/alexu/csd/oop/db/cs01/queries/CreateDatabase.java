package eg.edu.alexu.csd.oop.db.cs01.queries;

import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;

public class CreateDatabase extends OurQuery{
	
	private String databaseName;
			
	public CreateDatabase(String databaseName) {
		super();
		this.databaseName = databaseName;
	}
	
	/**
	 * creates a data base and sets databasePath to its path. 
	 * @return
	 */
	@Override
	public boolean execute() {
		if (databaseName == null) {
			return false;
		}
		return 	FileManager.getInstance().createDB(databaseName); 
	}

}
