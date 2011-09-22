package org.wroedev.umls.neo.loader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.neo4j.kernel.impl.batchinsert.BatchInserter;

public class UMLSLoader {
	
	private String mrColsFilePath,mrFilesFilePath,mrIndexFilePath;
	
	public String getMrIndexFilePath() {
		return mrIndexFilePath;
	}

	public void setMrIndexFilePath(String mrIndexFilePath) {
		this.mrIndexFilePath = mrIndexFilePath;
	}

	public String getMrColsFilePath() {
		return mrColsFilePath;
	}

	public void setMrColsFilePath(String mrColsFilePath) {
		this.mrColsFilePath = mrColsFilePath;
	}

	public String getMrFilesFilePath() {
		return mrFilesFilePath;
	}

	public void setMrFilesFilePath(String mrFilesFilePath) {
		this.mrFilesFilePath = mrFilesFilePath;
	}

	public String[] getColumnNamesForFile(String filename) throws FileNotFoundException, IOException {
		
		LineIterator iterator = IOUtils.lineIterator(new FileInputStream(mrFilesFilePath), "UTF-8");
		
		
		String[] fields;
		String cols= "";
		while (iterator.hasNext()) {
		     String line = iterator.nextLine();
		     fields = line.split("\\|");
		     
		     if (fields[0].equals(filename)) {
		    	 cols = fields[2];
		     }
		     
		   }
		return cols.split(",");
	}

	public String getColumnTypeForFile(String columnName, String fileName) throws FileNotFoundException, IOException {
LineIterator iterator = IOUtils.lineIterator(new FileInputStream(mrColsFilePath), "UTF-8");
		
		
		String[] fields;
		String colType= "";
		while (iterator.hasNext()) {
		     String line = iterator.nextLine();
		     fields = line.split("\\|");
		     
		     if (fields[0].equals(columnName) && fields[6].equals(fileName)) {
		    	 colType = fields[7];
		     }
		     
		   }
		return colType;
	}
	
	public String getIndexTypeForFile(String columnName, String fileName) throws FileNotFoundException, IOException {
		LineIterator iterator = IOUtils.lineIterator(new FileInputStream(mrIndexFilePath), "UTF-8");
				
				
				String[] fields;
				String indexType= "";
				while (iterator.hasNext()) {
				     String line = iterator.nextLine();
				     fields = line.split("\\|");
				     
				     if (fields[0].equals(columnName) && fields[1].equals(fileName)) {
				    	 indexType = fields[2];
				     }
				     
				   }
				return indexType;
			}
	
	public String[][] getPropertyTypesForFile(String fileName) throws FileNotFoundException, IOException {
		
		String[] cols = getColumnNamesForFile(fileName);
		String[] colTypes = new String[cols.length];
		for (int i = 0; i < cols.length; i++) {
			colTypes[i]=getColumnTypeForFile(cols[i], fileName);
		}
		
		String[][] result = {cols,colTypes};
		
		return result;
		
	}
	
	
	
	public LineIterator fileIterator(String filePath) throws FileNotFoundException, IOException {
		return IOUtils.lineIterator(new FileInputStream(filePath), "UTF-8");
	}
	
	private Map<String,Object> buildPropertyMap(String[] fields,String[][] def) {
		Map<String,Object> properties = new HashMap<String,Object>();
		for (int i = 0; i < fields.length; i++) {
			String property = fields[i];
			String propertyName = def[0][i];
			String propertyType = def[1][i];
			properties.put(propertyName, property);
		}
		return properties;
	}

	public void loadFile(String filename,String filepath, BatchInserter inserter) throws FileNotFoundException, IOException {
		String[][] def = getPropertyTypesForFile(filename);
		LineIterator it = fileIterator(filepath);
		String[] fields;
		int count = 0;
		
		while(count<100 & it.hasNext()) {
			String line = it.nextLine();
		    fields = line.split("\\|");
		    Map<String, Object> properties = buildPropertyMap(fields,def);
		    inserter.createNode(properties);
		    
		    
		    count++;
		}
				
	}
	
}
