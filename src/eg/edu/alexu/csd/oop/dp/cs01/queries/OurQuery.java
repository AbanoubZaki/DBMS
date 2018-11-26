package eg.edu.alexu.csd.oop.dp.cs01.queries;

public abstract class OurQuery implements IQuery{

	private String table = new String();
	
	@Override
	public void setTable(String table) {
		this.table = table;
	}

	@Override
	public String getTable() {
		return table;
	}

	@Override
	public abstract boolean execute();


}
