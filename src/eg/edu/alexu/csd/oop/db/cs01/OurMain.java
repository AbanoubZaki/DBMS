package eg.edu.alexu.csd.oop.db.cs01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;
import eg.edu.alexu.csd.oop.db.cs01.modules.Cell;
import eg.edu.alexu.csd.oop.db.cs01.modules.Row;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;
import eg.edu.alexu.csd.oop.dp.cs01.queries.InsertInto;

public class OurMain {
	public static void main(String[] args) {
		Database db = OurSql.getInstance();
		System.out.println(db.createDatabase("testDB", false));
		Table table = new Table("testDB","t1");
		ArrayList<String>columnNames = new ArrayList<String>();
		columnNames.add("c1");
		columnNames.add("c2");
		columnNames.add("c3");
		table.setColumnNames(columnNames);
		Map<String, String>columnTypes = new HashMap<String, String>();
		columnTypes.put("c1", "int");
		columnTypes.put("c2", "varchar");
		columnTypes.put("c3", "varchar");
		table.setColumnTypes(columnTypes);
		Row row = new Row(table);
		row.updateCell("c1", new Cell("1"));
		row.updateCell("c2", new Cell("a"));
		row.updateCell("c3", new Cell("b"));
		table.addRow(row);
		
		ArrayList<String> values = new ArrayList<>();
		values.add("2");
		values.add("c");
		values.add("d");
		InsertInto query = new InsertInto(table, values);
		query.execute();
		
		FileManager.getInstance().createTable(table);
		
//		table = FileManager.getInstance().readTable(table);
//		System.out.println(table.getColumnNames());
//		System.out.println(table.getColumnTypes());
		
		

	}
}
