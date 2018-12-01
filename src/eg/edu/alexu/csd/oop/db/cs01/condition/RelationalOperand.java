package eg.edu.alexu.csd.oop.db.cs01.condition;

public class RelationalOperand {

	private String operand;
	private boolean variable;
	private String type;
	public RelationalOperand(String operand , boolean isVariable,String type) {
		
		this.operand=operand;
		this.variable=isVariable;
		this.type=type;
	}
	/**
	 * @param operand the operand to set
	 */
	public void setOperand(String operand) {
		this.operand = operand;
	}
	
	/**
	 * @param variable the variable to set
	 */
	public void setVariable(boolean variable) {
		this.variable = variable;
	}
	
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return the operand
	 */
	public String getOperand() {
		return operand;
	}
	
	/**
	 * @return the variable
	 */
	public boolean isVariable() {
		return variable;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
}
