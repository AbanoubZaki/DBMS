package eg.edu.alexu.csd.oop.db.cs01.fileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class FileManager {
	
	private static FileManager instance;
	
	public static FileManager getInstance() {
		
		if(instance==null) {
			instance = new FileManager();
		}
		return instance;
	}
	
	public String createDB(String databaseName, boolean dropIfExists) {
		File db = new File("databases"+System.getProperty("file.separator")+databaseName);
		boolean isExists = !db.mkdirs();
		if(isExists&&dropIfExists) {
			dropDB(databaseName);
			db.mkdirs();
		}
		return db.getAbsolutePath();
		
	}
	public void dropDB(String databaseName) {
	File db = new File("databases"+System.getProperty("file.separator")+databaseName);
	File[]tableFiles = db.listFiles();
		for(File f:tableFiles) {
		f.delete();
		}
		db.delete();
	}
	public void createTable(Table table) {
		String pathTable = "databases"+System.getProperty("file.separator")+table.getDataBaseName();
		pathTable+=System.getProperty("file.separator")+table.getTableName();
		File tableFile = new File(pathTable+".Xml");
		try {
			JAXBContext jc = JAXBContext.newInstance(table.getClass());
			Marshaller jaxbMarshaller =jc.createMarshaller();
  	  		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
  	  		jaxbMarshaller.marshal(table, tableFile);
  	  	jaxbMarshaller.marshal(table, System.out);
  	  	createDTD(table);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void dropTable(Table table) {
		String pathTable = "databases"+System.getProperty("file.separator")+table.getDataBaseName();
		pathTable+=System.getProperty("file.separator")+table.getTableName();
		File tableFile = new File(pathTable+".Xml");
		File DTDFile = new File(pathTable+".dtd");
		tableFile.delete();
		DTDFile.delete();
	}
	public void createDTD(Table table) {
		String pathTable = "databases"+System.getProperty("file.separator")+table.getDataBaseName();
		pathTable+=System.getProperty("file.separator")+table.getTableName();
		File DTDFile = new File(pathTable+".dtd");
		try {
			PrintWriter pw = new PrintWriter(DTDFile);
			StringBuilder sb = new StringBuilder();
			sb.append("<?xml encoding=\"UTF-8\"?>\n");
			sb.append("<!ELEMENT table (columnNames*,dataBaseName,rows*)>\n");
			sb.append("<!ELEMENT columnNames (#PCDATA)>\n");
			sb.append("<!ELEMENT dataBaseName (#PCDATA)>\n");
			sb.append("<!ELEMENT tableName (#PCDATA)>\n");
			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
