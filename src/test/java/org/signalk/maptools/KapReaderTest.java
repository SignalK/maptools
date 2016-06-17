package org.signalk.maptools;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.After;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class KapReaderTest {
	private static Logger logger = Logger.getLogger(KapReaderTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shouldExtractImage() throws Exception {
		KapProcessor processor = new KapProcessor();
		String kapName = "NZ61401";
		File mapPath = new File("./src/test/resources/");
		File kapFile = new File(mapPath, kapName+".KAP");
		
		File png = new File(mapPath, kapName+".png");
		if(png.exists())png.delete();
		processor.extractImage(kapFile);
		assertTrue(png.exists());
		assertTrue(png.length()>1400000);
	}

	@Test
	public void shouldCreateTiles() throws Exception {
		// 1 minute of lat ~= 1Nm = 1852 M
		// 1 sec of lat ~= 30M
		//String kapName = "US50_1";
		//String kapName = "18649_1";
		// String kapName = "US411_1";
		//String kapName = "NZ61401";
		//String kapName = "UK_6868_1";
		//String kapName = "11535_1";
		String kapName = "NZ61401";
		KapProcessor processor = new KapProcessor();
		
		File mapPath = new File("./src/test/resources/");
		File kapFile = new File(mapPath, kapName+".KAP");
		File target = new File(mapPath, kapName);
		FileUtils.deleteDirectory(target);
		processor.createTilePyramid(kapFile, mapPath);
		//should be there now
		assertTrue(target.exists());
		assertTrue(new File(target,KapProcessor.TILEMAPRESOURCE_XML).exists());
		assertTrue(new File(target,KapProcessor.OPENLAYERS_HTML).exists());
		assertEquals(7, target.listFiles().length);
		
	}

	

}
