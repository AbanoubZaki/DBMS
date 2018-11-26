package eg.edu.alexu.csd.oop.db.cs01.modules;

public class Cell {
	
	private String value;
	
	public Cell() {
	}
	public Cell(String value) {
		this.value = new String(value);
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
