package eg.edu.alexu.csd.oop.db.cs01;

public class dataChecker {

	private static dataChecker instance = new dataChecker();

	private dataChecker() {
	}

	public static dataChecker getInstance() {
		return instance;
	}

	public String checkType(String dataWord) {
		try {
			Integer.parseInt(dataWord);
			return "int";
		} catch (NumberFormatException e) {
			return "varchar";
		}
	}

}
