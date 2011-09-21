package org.wroedev.umls.neo.loader;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RRFLoadTest extends TestCase {

	private static String mrconsoFilePath;
	private static String basePath;
	private static String mrfilesFilePath;
	
	private static String mrconso="MRCONSO.RRF";
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		Properties props = new Properties();
		props.load(new FileInputStream("test.properties"));
		basePath = props.getProperty("base");
		mrconsoFilePath = basePath+"MRCONSO.RRF";
		mrfilesFilePath = basePath+"MRFILES.RRF";
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
	public void loadTenAUIs() {
		fail("Not yet implemented or is it?");
	}
	
	@Test
	public void testReadColumnDefinition() throws Exception {
		String[] cols = loader.getColumnNamesForFile(mrconso, mrfilesFilePath);
		assertEquals(19,cols.length);
		
	}

	@Test
	public void testIterateMrConSo() throws Exception {
		LineIterator iterator = IOUtils.lineIterator(new FileInputStream(mrconsoFilePath), "UTF-8");
		int count = 0;
		
		String[] fields;
		while (iterator.hasNext()) {
		     String line = iterator.nextLine();
		     fields = line.split("|");
		     count++;
		   }
		System.out.println(count);
	}

}
