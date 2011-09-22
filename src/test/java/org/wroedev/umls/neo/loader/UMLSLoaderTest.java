package org.wroedev.umls.neo.loader;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.kernel.impl.batchinsert.BatchInserter;
import org.neo4j.kernel.impl.batchinsert.BatchInserterImpl;

public class UMLSLoaderTest {
	private static String mrconsoFilePath;
	private static String basePath;
	private static String mrfilesFilePath;
	
	private static String mrconso="MRCONSO.RRF";
	private static String mrcolsFilePath;
	private UMLSLoader loader;
	private static String mrindexFilePath;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Properties props = new Properties();
		props.load(new FileInputStream("test.properties"));
		basePath = props.getProperty("base");
		mrconsoFilePath = basePath+"MRCONSO.RRF";
		mrfilesFilePath = basePath+"MRFILES.RRF";
		mrcolsFilePath = basePath+"MRCOLS.RRF";
		mrindexFilePath = "MRINDEX.ME";
	}

	

	@Before
	public void setUp() throws Exception {
		FileUtils.deleteDirectory(new File("neo4j-db/"));
		loader = new UMLSLoader();
		loader.setMrColsFilePath(mrcolsFilePath);
		loader.setMrFilesFilePath(mrfilesFilePath);
		loader.setMrIndexFilePath(mrindexFilePath);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReadColumnDefinition() throws Exception {
		String[] cols = loader.getColumnNamesForFile(mrconso);
		assertEquals(18,cols.length);
	}
	
	@Test
	public void testReadColumnType() throws Exception {
		String colType = loader.getColumnTypeForFile("CUI",mrconso);
		assertEquals("char(8)",colType);
	}
	
	@Test
	public void testGetColumnInfo() throws Exception {
		String[][] colTypes = loader.getPropertyTypesForFile(mrconso);
		assertEquals("CUI",colTypes[0][0]);
		assertEquals("char(8)",colTypes[1][0]);
	}
	
	@Test
	public void testGetIndexInfo() throws Exception {
		String indexType = loader.getIndexTypeForFile("CUI",mrconso);
		assertEquals("Exact",indexType);
		
	}
	
	@Test
	public void testLoadFiles() throws Exception {
		BatchInserter inserter = new BatchInserterImpl( "neo4j-db/" );
		loader.loadFile(mrconso, mrconsoFilePath,inserter);
		inserter.shutdown();
	}

}
