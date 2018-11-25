package eg.edu.alexu.csd.oop.db.cs01.modules;

public class Cell {
	
	private String vlaue;
	
	public Cell(String value) {
		value = new String(value);
	}
	
	public void setValue(String value) {
		this.vlaue = value;
	}
	
	public String getValue() {
		return vlaue;
	}
}
