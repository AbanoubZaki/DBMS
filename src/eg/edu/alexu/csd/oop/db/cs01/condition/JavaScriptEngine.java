package eg.edu.alexu.csd.oop.db.cs01.condition;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import eg.edu.alexu.csd.oop.db.cs01.jdbc.OurLogger;

public class JavaScriptEngine {
	
	private static JavaScriptEngine instance;
	private ScriptEngineManager sem;
	private ScriptEngine sm ;
	private JavaScriptEngine() {
		sem = new ScriptEngineManager();
		sm = sem.getEngineByName("JavaScript");
	}
	public static JavaScriptEngine getInstance() {
		if(instance==null) {
			instance = new JavaScriptEngine();
		}
		return instance;
	}
	public boolean getResult(String condition) {
		try {
			return (boolean) sm.eval(condition);
		} catch (Exception e) {
			OurLogger.error(this.getClass(), "Error in script");
			System.out.println("Error in script");
		}
		return false;
	}
}
