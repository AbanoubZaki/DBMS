package eg.edu.alexu.csd.oop.db.cs01.condition;

public class RelationalCondition {
	
	private RelationalOperand leftAgrument;
	private RelationalOperand rightAgrument;
	private String operation;
	
	/**
	 * @param leftAgrument
	 * @param rightAgrument
	 * @param operation
	 */
	public RelationalCondition(RelationalOperand leftAgrument, RelationalOperand rightAgrument, String operation) {
		super();
		this.leftAgrument = leftAgrument;
		this.rightAgrument = rightAgrument;
		this.operation = operation;
	}
	/**
	 * @return the leftAgrument
	 */
	public RelationalOperand getLeftAgrument() {
		return leftAgrument;
	}
	/**
	 * @param leftAgrument the leftAgrument to set
	 */
	public void setLeftAgrument(RelationalOperand leftAgrument) {
		this.leftAgrument = leftAgrument;
	}
	/**
	 * @return the rightAgrument
	 */
	public RelationalOperand getRightAgrument() {
		return rightAgrument;
	}
	/**
	 * @param rightAgrument the rightAgrument to set
	 */
	public void setRightAgrument(RelationalOperand rightAgrument) {
		this.rightAgrument = rightAgrument;
	}
	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}
	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
}
