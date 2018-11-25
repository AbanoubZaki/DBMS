package eg.edu.alexu.csd.oop.db.cs01.modules;

public class Cell {
	private String type = new String();
	
	private String vlaue = new String();
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public void setValue(String value) {
		this.vlaue = value;
	}
	
	public String getValue() {
		return vlaue;
	}
}
