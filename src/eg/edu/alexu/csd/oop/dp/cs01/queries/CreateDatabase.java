package eg.edu.alexu.csd.oop.dp.cs01.queries;

import eg.edu.alexu.csd.oop.db.cs01.OurSql;

public class CreateDatabase extends OurQuery{
	
	String databaseName;
	
	boolean dropIfExists;
	
	public CreateDatabase(String databaseName, boolean dropIfExists) {
		databaseName = new String(databaseName);
		this.dropIfExists = dropIfExists;
	}
	
	@Override
	public boolean execute() {
		OurSql.getInstance().createDatabase(databaseName, dropIfExists);
		return false;
	}

}
