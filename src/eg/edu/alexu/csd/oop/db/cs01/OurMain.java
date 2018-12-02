package eg.edu.alexu.csd.oop.db.cs01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class OurMain {
	public static void main(String[] args) throws SQLException, IOException {

		System.out.println("Enter Queries :");
		
		while (true) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String query = br.readLine();
			if (query == null || query.equals("")) {
				continue;
			}
			if (query.equals("exit")) {
				break;
			}

			try {
				GeneralParser.getInstance().parse(query);
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}			
		}

	}
}
