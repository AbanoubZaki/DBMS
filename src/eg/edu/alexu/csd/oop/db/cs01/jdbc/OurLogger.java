package eg.edu.alexu.csd.oop.db.cs01.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OurLogger {

	private static Logger log;
	

	private OurLogger() {
	}

	public static void info(final Class<?> Class, final String message) {
		log = LogManager.getLogger(Class);
		log.info(message);
	}
	
	public static void error(final Class<?> Class, final String message) {
		log = LogManager.getLogger(Class);
		log.error(message);
	}
	
	public static void warn(final Class<?> Class, final String message) {
		log = LogManager.getLogger(Class);
		log.warn(message);
	}

}
