package eg.edu.alexu.csd.oop.db.cs01;

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
