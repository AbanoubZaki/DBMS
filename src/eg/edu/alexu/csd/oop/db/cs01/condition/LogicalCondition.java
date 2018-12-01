package eg.edu.alexu.csd.oop.db.cs01.condition;

import java.util.ArrayList;

public class LogicalCondition {
	
	private ArrayList<RelationalCondition> relationalConditions;
	
	/**
	 * @return the relationalConditions
	 */
	public ArrayList<RelationalCondition> getRelationalConditions() {
		return relationalConditions;
	}
	
	/**
	 * @param relationalConditions the relationalConditions to set
	 */
	public void setRelationalConditions(ArrayList<RelationalCondition> relationalConditions) {
		this.relationalConditions = relationalConditions;
	}
	
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
	
	public LogicalCondition(String stringCondition) {
		
		this.stringCondition=stringCondition;
		setRelationalConditions(ConditionParser.getInstance().stringToLogicalCondition(stringCondition));
	}
		
	
}
