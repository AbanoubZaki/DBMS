package eg.edu.alexu.csd.oop.db.cs01;

public class Condition {
	private String condition = new String();
	/**
	 * empty constructor to just create.
	 */
	public Condition() {
	}
	/**
	 * constructor with giving the condition.
	 * @param condition
	 */
	public Condition(String condition) {
		this.setCondition(condition);
	}
	/**
	 * getter of the condition.
	 * @return
	 */
	public String getCondition() {
		return condition;
	}
	/**
	 * setter of the condition.
	 * @param condition
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}
}
