package org.wroedev.umls.neo.loader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

public class UMLSLoader {
	
	public String[] getColumnNamesForFile(String filename, String definitionFilePath) throws FileNotFoundException, IOException {
		
		LineIterator iterator = IOUtils.lineIterator(new FileInputStream(definitionFilePath), "UTF-8");
		
		
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

	public String getColumnTypeForFile(String columnName, String fileName,
			String mrcolsFilePath) throws FileNotFoundException, IOException {
LineIterator iterator = IOUtils.lineIterator(new FileInputStream(mrcolsFilePath), "UTF-8");
		
		
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
	
	public String[][] getPropertyTypesForFile(String fileName,String mrColsPath,String mrFilesPath) throws FileNotFoundException, IOException {
		
		String[] cols = getColumnNamesForFile(fileName, mrFilesPath);
		String[] colTypes = new String[cols.length];
		for (int i = 0; i < cols.length; i++) {
			colTypes[i]=getColumnTypeForFile(cols[i], fileName, mrColsPath);
		}
		
		String[][] result = {cols,colTypes};
		
		return result;
		
	}

}
