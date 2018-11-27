package eg.edu.alexu.csd.oop.db.cs01.condition;

public class LogicalCondition {
	
	private RelationalCondition condition1;
	
	private RelationalCondition condition2;
	
	private String logicalSign;
	
	private String logicalOperation;

	public LogicalCondition(String logicalOperation) {
		setLogicalOperation(logicalOperation);
	}
	
	public RelationalCondition getCondition1() {
		return condition1;
	}

	public void setCondition1(RelationalCondition condition1) {
		this.condition1 = condition1;
	}

	public RelationalCondition getCondition2() {
		return condition2;
	}

	public void setCondition2(RelationalCondition condition2) {
		this.condition2 = condition2;
	}

	public String getLogicalSign() {
		return logicalSign;
	}

	public void setLogicalSign(String logicalSign) {
		this.logicalSign = logicalSign;
	}

	public String getLogicalOperation() {
		return logicalOperation;
	}

	public void setLogicalOperation(String logicalOperation) {
		this.logicalOperation = logicalOperation;
	}
		
	
}
