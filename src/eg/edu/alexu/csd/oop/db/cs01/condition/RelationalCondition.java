package eg.edu.alexu.csd.oop.db.cs01.condition;

public class RelationalCondition {
	
	private RelationalOperand leftAgrument;
	private RelationalOperand rightAgrument;
	private String operation;
	private String stringCondition;
	/**
	 * @return the stringCondition
	 */
	public String getStringCondition() {
		return stringCondition;
	}
	/**
	 * @param stringCondition the stringCondition to set
	 */
	public void setStringCondition(String stringCondition) {
		this.stringCondition = stringCondition;
	}
	/**
	 * @param leftAgrument
	 * @param rightAgrument
	 * @param operation
	 */
	public RelationalCondition(String stringCondition) {
		if(stringCondition==null)
			return;
		this.stringCondition=stringCondition;
		RelationalCondition r = ConditionParser.getInstance().stringToRelationalCondition(stringCondition);
		setLeftAgrument(r.getLeftAgrument());
		setRightAgrument(r.getRightAgrument());
		setOperation(r.getOperation());
	}
	public RelationalCondition(RelationalOperand leftAgrument, RelationalOperand rightAgrument, String operation){
		if(!leftAgrument.getType().equals(rightAgrument.getType()))
			return;
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
