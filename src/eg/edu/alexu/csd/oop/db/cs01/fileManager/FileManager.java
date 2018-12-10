package eg.edu.alexu.csd.oop.db.cs01.fileManager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import eg.edu.alexu.csd.oop.db.cs01.jdbc.OurLogger;
import eg.edu.alexu.csd.oop.db.cs01.modules.Cell;
import eg.edu.alexu.csd.oop.db.cs01.modules.Row;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class FileManager {

	// singleton pattern
	private static FileManager instance;

	private FileManager() {
	}

	public static FileManager getInstance() {
		if (instance == null) {
			instance = new FileManager();
		}
		return instance;
	}

	public boolean createDB(String databaseName) {
		if (!databaseName.contains(System.getProperty("file.separator")))
			databaseName = "databases" + System.getProperty("file.separator") + databaseName;
		File db = new File(databaseName);
		db.mkdirs();
		return true;
	}

	/**
	 * it tries to fetch for the db if it's not found it returns false else it drops
	 * it returning true
	 * 
	 * @param databaseName
	 */
	public boolean dropDB(String databaseName) {
		if (!databaseName.contains(System.getProperty("file.separator")))
			databaseName = "databases" + System.getProperty("file.separator") + databaseName;
		File db = new File(databaseName);
		if (!db.exists())
			return false;
		File[] tableFiles = db.listFiles();
		for (File f : tableFiles) {
			f.delete();
		}
		db.delete();
		Table.setTables(null);
		return true;
	}

	public boolean createTable(Table table) {
		if (table.getDatabaseName() == null) {
			OurLogger.error(this.getClass(), "Database is unknown.");
			System.out.println("Database is unknown.");
			return false;
		}
		String pathTable = table.getDatabaseName();
		if (!pathTable.contains(System.getProperty("file.separator")))
			pathTable = "databases" + System.getProperty("file.separator") + pathTable;
		pathTable += System.getProperty("file.separator") + table.getTableName();
		File tableFile = new File(pathTable + ".Xml");
		File DTDFile = new File(pathTable + ".dtd");
		if (tableFile.exists() && DTDFile.exists()) {
			OurLogger.error(this.getClass(), "Failed to create table, Table already exists.");
			System.out.println("Failed to create table, Table already exists.");
			return false;
		}
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.newDocument();
			Element rootEle = dom.createElement("table");
			Element typesElement = dom.createElement("dataTypes");
			for (String col : table.getColumnNamesAsGiven()) {
				Element typeElement = dom.createElement(col);
				typeElement.appendChild(dom.createTextNode(table.getColumnTypes().get(col.toLowerCase())));
				typesElement.appendChild(typeElement);
			}
			rootEle.appendChild(typesElement);
			for (Row r : table.getRows()) {
				Element e = dom.createElement("row");
				for (String col : table.getColumnNamesAsGiven()) {
					Element elementCell = dom.createElement(col);
					if (r.getCellByColumn(col) != null)
						elementCell.appendChild(dom.createTextNode(r.getCellByColumn(col)));
					else
						elementCell.appendChild(dom.createTextNode("null"));

					e.appendChild(elementCell);
				}
				rootEle.appendChild(e);
			}
			dom.appendChild(rootEle);
			Transformer tr = TransformerFactory.newInstance().newTransformer();
			tr.setOutputProperty(OutputKeys.INDENT, "yes");
			tr.setOutputProperty(OutputKeys.METHOD, "xml");
			tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			tr.transform(new DOMSource(dom), new StreamResult(tableFile));
			createDTD(table);

		} catch (Exception e) {
			OurLogger.error(this.getClass(), "Error happened bs msh ha2olak 3aleh.");
			System.out.println("Error happened bs msh ha2olak 3aleh.");
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * it tries to fetch for the table if it's not found it returns false else it
	 * drops it returning true
	 * 
	 * @param table
	 * @return
	 */
	public boolean dropTable(Table table) {
		if (table.getDatabaseName() == null) {
			OurLogger.error(this.getClass(), "Database is unknown.");
			System.out.println("Database is unknown.");
			return false;
		}
		String pathTable = table.getDatabaseName();
		if (!pathTable.contains(System.getProperty("file.separator")))
			pathTable = "databases" + System.getProperty("file.separator") + pathTable;
		pathTable += System.getProperty("file.separator") + table.getTableName();
		File tableFile = new File(pathTable + ".Xml");
		File DTDFile = new File(pathTable + ".dtd");
		if (!tableFile.exists() || !DTDFile.exists())
			return false;
		tableFile.delete();
		DTDFile.delete();
		return true;
	}

	private void createDTD(Table table) {
		String pathTable = table.getDatabaseName();
		if (!pathTable.contains(System.getProperty("file.separator")))
			pathTable = "databases" + System.getProperty("file.separator") + pathTable;
		pathTable += System.getProperty("file.separator") + table.getTableName();
		File DTDFile = new File(pathTable + ".dtd");
		try {
			PrintWriter pw = new PrintWriter(DTDFile);
			StringBuilder sb = new StringBuilder();
			sb.append("<?xml encoding=\"UTF-8\"?>\n");
			sb.append("<!ELEMENT table (rows*)>\n");
			sb.append("<!ELEMENT row (");
			for (String s : table.getColumnNamesAsGiven()) {
				sb.append(s + ",");
			}
			sb.delete(sb.length() - 1, sb.length());
			sb.append(")>\n");
			for (String s : table.getColumnNamesAsGiven()) {
				sb.append("<!ELEMENT " + s + " (#PCDATA)>\n");
			}
			sb.delete(sb.length() - 1, sb.length());
			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean readTable(Table table) {
		if (table.getDatabaseName() == null) {
			OurLogger.error(this.getClass(), "Database is unknown.");
			System.out.println("Database is unknown.");
			return false;
		}
		table.setRows(new ArrayList<Row>());
		String pathTable = table.getDatabaseName();
		if (!pathTable.contains(System.getProperty("file.separator")))
			pathTable = "databases" + System.getProperty("file.separator") + pathTable;
		pathTable += System.getProperty("file.separator") + table.getTableName();
		File tableFile = new File(pathTable + ".Xml");
		if (!tableFile.exists()) {
			table = null;
			return false;
		}
		try {
			Map<String, ArrayList<String>> map = parseXml(tableFile);
			int numberOfRow = map.get(map.keySet().toArray()[0]).size();
			ArrayList<String> columns = readDTD(new File(tableFile.getAbsolutePath().replaceAll(".Xml", ".dtd")));
			ArrayList<String> types = new ArrayList<String>();
			for (String s : columns) {
				types.add(map.get(s).get(0));
			}
			table.setAllColumnNamesAndTypes(columns, types);
			for (int i = 1; i < numberOfRow; i++) {
				Row r = new Row(table);
				for (Entry<String, ArrayList<String>> e : map.entrySet()) {
					if (!e.getValue().get(i).equals("null"))
						r.updateCell(e.getKey(), new Cell(e.getValue().get(i)));
					else
						r.updateCell(e.getKey(), null);
				}
				table.addRow(r);
			}
		} catch (Exception e) {
			e.printStackTrace();
			OurLogger.error(this.getClass(), "error bs msh han2olak 3aleh");
			System.out.println("error bs msh han2olak 3aleh");
		}
		return true;

	}

	public boolean writeTable(Table table) {
		if (table.getDatabaseName() == null) {
			OurLogger.error(this.getClass(), "Database is unknown.");
			System.out.println("Database is unknown.");
			return false;
		}
		String pathTable = table.getDatabaseName();
		if (!pathTable.contains(System.getProperty("file.separator")))
			pathTable = "databases" + System.getProperty("file.separator") + pathTable;
		pathTable += System.getProperty("file.separator") + table.getTableName();
		File tableFile = new File(pathTable + ".Xml");
		if (!tableFile.exists()) {
			tableFile.delete();
		}
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.newDocument();
			Element rootEle = dom.createElement("table");
			Element typesElement = dom.createElement("dataTypes");
			for (String col : table.getColumnNamesAsGiven()) {
				Element typeElement = dom.createElement(col);
				typeElement.appendChild(dom.createTextNode(table.getColumnTypes().get(col.toLowerCase())));
				typesElement.appendChild(typeElement);
			}
			rootEle.appendChild(typesElement);
			for (Row r : table.getRows()) {
				Element e = dom.createElement("row");
				for (String col : table.getColumnNamesAsGiven()) {
					Element elementCell = dom.createElement(col);
					if (r.getCellByColumn(col) != null)
						elementCell.appendChild(dom.createTextNode(r.getCellByColumn(col)));
					else
						elementCell.appendChild(dom.createTextNode("null"));
					e.appendChild(elementCell);
				}
				rootEle.appendChild(e);
			}
			dom.appendChild(rootEle);
			Transformer tr = TransformerFactory.newInstance().newTransformer();
			tr.setOutputProperty(OutputKeys.INDENT, "yes");
			tr.setOutputProperty(OutputKeys.METHOD, "xml");
			tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			tr.transform(new DOMSource(dom), new StreamResult(tableFile));
		} catch (Exception e) {
			OurLogger.error(this.getClass(), "Error happened bs msh ha2olak 3aleh.");
			System.out.println("Error happened bs msh ha2olak 3aleh.");
			e.printStackTrace();
		}
		return true;
	}

	private static Map<String, ArrayList<String>> parseXml(File tableFile) throws XMLStreamException, IOException {
		StringBuilder content = null;
		Map<String, ArrayList<String>> dataMap = new HashMap<>();
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream stream = new ByteArrayInputStream(Files.readAllBytes(tableFile.toPath()));
		XMLStreamReader reader = factory.createXMLStreamReader(stream);

		while (reader.hasNext()) {
			int event = reader.next();

			switch (event) {
			case XMLStreamConstants.START_ELEMENT:
				content = new StringBuilder();
				break;

			case XMLStreamConstants.CHARACTERS:
				if (content != null) {
					content.append(reader.getText().trim());
				}
				break;

			case XMLStreamConstants.END_ELEMENT:
				if (content != null) {
					String leafText = content.toString();
					if (dataMap.get(reader.getLocalName()) == null) {
						ArrayList<String> values = new ArrayList<>();
						values.add(leafText);
						dataMap.put(reader.getLocalName(), values);
					} else {
						dataMap.get(reader.getLocalName()).add(leafText);
					}
				}
				content = null;
				break;

			case XMLStreamConstants.START_DOCUMENT:
				break;
			}

		}
		stream.close();
		reader.close();
		return dataMap;
	}

	private ArrayList<String> readDTD(File tableFile) {
		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get(tableFile.getAbsolutePath()), StandardCharsets.UTF_8);
		} catch (IOException e) {

		}

		ArrayList<String> cols = new ArrayList<String>();
		for (int i = 3; i < lines.size(); i++) {
			String s = lines.get(i);
			s = s.replace(" (#PCDATA)>", "");
			s = s.replace("<!ELEMENT ", "");
			cols.add(s);
		}
		return cols;
	}
}
