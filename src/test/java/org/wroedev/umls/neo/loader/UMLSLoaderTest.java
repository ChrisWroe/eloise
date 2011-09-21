package org.wroedev.umls.neo.loader;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UMLSLoaderTest {
	private static String mrconsoFilePath;
	private static String basePath;
	private static String mrfilesFilePath;
	
	private static String mrconso="MRCONSO.RRF";
	private static String mrcolsFilePath;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Properties props = new Properties();
		props.load(new FileInputStream("test.properties"));
		basePath = props.getProperty("base");
		mrconsoFilePath = basePath+"MRCONSO.RRF";
		mrfilesFilePath = basePath+"MRFILES.RRF";
		mrcolsFilePath = basePath+"MRCOLS.RRF";
	}

	private UMLSLoader loader;

	@Before
	public void setUp() throws Exception {
		loader = new UMLSLoader();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReadColumnDefinition() throws Exception {
		String[] cols = loader.getColumnNamesForFile(mrconso, mrfilesFilePath);
		assertEquals(18,cols.length);
	}
	
	@Test
	public void testReadColumnType() throws Exception {
		String colType = loader.getColumnTypeForFile("CUI",mrconso, mrcolsFilePath);
		assertEquals("char(8)",colType);
	}
	
	@Test
	public void testGetColumnInfo() throws Exception {
		String[][] colTypes = loader.getPropertyTypesForFile(mrconso, mrcolsFilePath, mrfilesFilePath);
		assertEquals("CUI",colTypes[0][0]);
		assertEquals("char(8)",colTypes[1][0]);
	}

}
