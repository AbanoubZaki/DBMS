package eg.edu.alexu.csd.oop.dp.cs01.queries;

public abstract class OurQuery implements IQuery{

	private String tableName = new String();
	
	@Override
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public abstract boolean execute();


}
