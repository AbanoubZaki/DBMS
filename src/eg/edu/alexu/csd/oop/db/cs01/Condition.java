package eg.edu.alexu.csd.oop.db.cs01;

public class Condition {
	private String condition = new String();
	public Condition(String condition) {
		this.setCondition(condition);
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
}
