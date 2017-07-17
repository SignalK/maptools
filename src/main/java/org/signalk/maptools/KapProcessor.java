package org.signalk.maptools;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.imgscalr.Scalr;

/**
 * Provides methods to process BSB/KAP files into images and tile pyramids
 * compatible with Leaflet and OpenLayers.
 *
 * @author robert
 *
 */
public class KapProcessor {

	public static final String TILEMAPRESOURCE_XML = "tilemapresource.xml";
	public static final String OPENLAYERS_HTML = "openlayers.html";
	private static Logger logger = Logger.getLogger(KapProcessor.class);
	private CoordsPolynomTrans coordTranslator;
	private KAPParser parser;
	private int zMin;
	private int zMax;
	private KapObserver observer;

	/**
	 * Extracts the image from a BSB/KAP file and saves it as a png. Saves the
	 * png with the same filename and path as the original KAP file, but with
	 * .png extension
	 *
	 * @param kapFile
	 *
	 * @throws Exception
	 */
	public void extractImage(File kapFile, boolean fixTransparentBlack) throws Exception {
		String fileName = kapFile.getAbsolutePath();
		KAPParser parser = new KAPParser(fileName,fixTransparentBlack);
		logger.debug(parser.getName());
		logger.debug("x=" + parser.getBounds().x + ", y=" + parser.getBounds().y);
		logger.debug(parser.getDatum());
		logger.debug(parser.getMapFileScale());
                logger.debug(parser.getSkew());
//		logger.debug(parser.getRawMapHeightPixels());
//		logger.debug(parser.getRawMapWidthPixels());
		logger.debug(parser.getMapHeightPixels());
		logger.debug(parser.getMapWidthPixels());
		logger.debug(parser.getProjectionName());
		logger.debug(parser.getProjectionParameter());
		logger.debug(parser.getSoundingReference());

		parser.saveAsPNG(parser.getImage(), new File(fileName.substring(0, fileName.lastIndexOf(".")) + ".png"));

	}

	private void log(Object msg) {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stacktrace[2];//maybe this number needs to be corrected
		String methodName = e.getMethodName();
		if (logger.isDebugEnabled()) {
			logger.debug(methodName + " " + msg);
		}
		if (observer != null) {
			observer.appendMsg(msg.toString() + "\n");
		}
	}

	/**
	 * For KAP file kapFile produce a tile pyramid in pyramidPath
	 * <br/>
	 * eg for KAP file '.../mapDir/chart_name.KAP' create a tile pyramid in
	 * pyramid path '.../mapCache/' such that the result is
	 * '.../mapCache/chart_name/2/4/123.png'
	 *
	 * @param kapFile
	 * @param pyramidPath
	 * @param fixTransparentBlack 
	 *
	 * @throws Exception
	 */
	public void createTilePyramid(File kapFile, File pyramidPath, boolean fixTransparentBlack) throws Exception {
		// 1 minute of lat ~= 1Nm = 1852 M
		// 1 sec of lat ~= 30M

		String kapName = kapFile.getName();
		kapName = kapName.substring(0, kapName.lastIndexOf("."));
		StringBuffer tileRes = new StringBuffer();
		tileRes.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		tileRes.append("    <TileMap version=\"1.0.0\" tilemapservice=\"http://tms.osgeo.org/1.0.0\">\n");

		parser = new KAPParser(kapFile.getAbsolutePath(),fixTransparentBlack);

		logger.debug(parser.getName());
		logger.debug("x=" + parser.getBounds().x + ", y=" + parser.getBounds().y + ", height=" + parser.getBounds().height + ", width="
				  + parser.getBounds().width);

		// take the & out of the title or get SAXE Parse Errors
                tileRes.append("      <Title>" + parser.getMapName().replace("&", "and") + "</Title>\n");
		tileRes.append("      <Abstract></Abstract>\n");
		//scale is an integer, larger is a larger area
		tileRes.append("      <Metadata scale=\""+parser.getMapFileScale()+"\" mime-type=\"text/xml\" />\n");
		tileRes.append("      <SRS>EPSG:900913</SRS>\n");

		Position center = parser.getMapUseableSector().getCenter();
		Position nw = parser.getMapUseableSector().getNorthWest();
		Position se = parser.getMapUseableSector().getSouthEast();

		logger.debug("nw, lat=" + nw.getLatitude() + ", lon=" + nw.getLongitude());
		logger.debug("center, lat=" + center.getLatitude() + ", lon=" + center.getLongitude());
		logger.debug("se, lat=" + se.getLatitude() + ", lon=" + se.getLongitude());

		tileRes.append("      <BoundingBox minx=\"" + nw.getLongitude() + "\" miny=\"" + se.getLatitude() + "\" maxx=\"" + se.getLongitude() + "\" maxy=\""
				  + nw.getLatitude() + "\"/>\n");
		tileRes.append("      <Origin x=\"" + nw.getLongitude() + "\" y=\"" + nw.getLatitude() + "\" />\n");
		tileRes.append("      <TileFormat width=\"256\" height=\"256\" mime-type=\"image/png\" extension=\"png\"/>\n");
		tileRes.append("      <TileSets profile=\"mercator\">\n");

		logger.debug("getMapWidthPixels:" + parser.getMapWidthPixels() + ", getMapHeightPixels:" + parser.getMapHeightPixels());

		// double xToPixels=StrictMath.abs(Double.valueOf(parser.getBounds().width)/parser.getMapWidthPixels());
		coordTranslator = parser.getCoordTranslator();
		// logger.debug("xToPixels:"+xToPixels);
		zMin = Sector.getMinUsefulZoom(nw, se);
		zMax = zMin + 4;
		for (int zoom = zMin; zoom < zMax; zoom++) {

			double unitsPerPixel = 156543.0339 / Math.pow(2.0, zoom);
			tileRes.append("        <TileSet href=\"" + zoom + "\" units-per-pixel=\"" + unitsPerPixel + "\" order=\"" + zoom + "\"/>\n");

			int xTile = Sector.getTileX(nw.getLongitude(), zoom);
			int yTile = Sector.getTileY(nw.getLatitude(), zoom);
			int XTile = Sector.getTileX(se.getLongitude(), zoom);
			int YTile = Sector.getTileY(se.getLatitude(), zoom);
			logger.info("Zoom = "+ zoom+" Tiles:x=" + xTile + "=>" + XTile + ", y=" + YTile + "=>" + yTile);
			Point [] tileBounds;
			//crossing 180 deg
			if(parser.isDateLine()){
				logger.info("Across dateline");
				//XTile to max
				int dz = ((1<<zoom)-1);
				logger.info("Tile "+XTile+" to "+dz);
				for (int i = XTile; i <= dz; i++) {
					for (int y = YTile - 1; y <= yTile; y++) {
						tileBounds = getTilePixBounds(zoom, i, y);
						logger.debug(String.format("zoom = %2d i = %6d y = %6d top = %5d bottom %5d", zoom, i, y, tileBounds[0].y, tileBounds[1].y));
						createTiles(pyramidPath, kapName, zoom, i, y);
					}
				}
				//and xtile to 0
				logger.info("Tile "+xTile+" to 0");
				for (int i = 0; i <= xTile; i++) {
					for (int y = YTile - 1; y <= yTile; y++) {
						tileBounds = getTilePixBounds(zoom, i, y);
						logger.debug(String.format("zoom = %2d i = %6d y = %6d top = %5d bottom %5d", zoom, i, y, tileBounds[0].y, tileBounds[1].y));
						createTiles(pyramidPath, kapName, zoom, i, y);
					}
				}
			}else{
				for (int i = xTile; i <= XTile; i++) {
					for (int y = YTile - 1; y <= yTile; y++) {
						tileBounds = getTilePixBounds(zoom, i, y);
						logger.debug(String.format("zoom = %2d i = %6d y = %6d top = %5d bottom %5d", zoom, i, y, tileBounds[0].y, tileBounds[1].y));
						createTiles(pyramidPath, kapName, zoom, i, y);
					}
				}
			}
		}

		tileRes.append("   </TileSets>\n");
		tileRes.append("</TileMap>\n");
		logger.debug(tileRes.toString());

		// write out
		File tileResFile = new File(pyramidPath, kapName + "/" + TILEMAPRESOURCE_XML);
		FileUtils.writeStringToFile(tileResFile, tileRes.toString(), StandardCharsets.UTF_8);
		// sort out the openlayers.html
		writeOpenlayersHtml(pyramidPath, kapName, nw, se, zMin, zMax);
	}

	

	public Point[] getTilePixBounds(int zoom, int i, int y){
		// get lat/lon box
		double south = Sector.tile2lat(y, zoom);
		double north = Sector.tile2lat(y + 1, zoom);
		double west = Sector.tile2lon(i, zoom);
		double east = Sector.tile2lon(i + 1, zoom);
		logger.debug("north:" + north + ", south:" + south + ", east:" + east + ", west:" + west);

		// convert to pixels
		Point nWest = coordTranslator.getAbsolutePointFromPosition(west, north);
		Point sEast = coordTranslator.getAbsolutePointFromPosition(east, south);
		int pixelY = nWest.y;
		int pixely = sEast.y;
		int pixelx = nWest.x;
		int pixelX = sEast.x;
		return new Point[]{nWest, sEast};
	}

	/**
	 * Creates tiles for a given zoom layer
	 *
	 * @param pyramidPath
	 * @param kapName
	 * @param zoom
	 * @param i
	 * @param y
	 */
	private void createTiles(File pyramidPath, String kapName, int zoom, int i, int y) {

		String url = "" + zoom + "/" + i + "/" + y;
		logger.debug("url=" + url);

		// get lat/lon box
		double south = Sector.tile2lat(y, zoom);
		double north = Sector.tile2lat(y + 1, zoom);
		double west = Sector.tile2lon(i, zoom);
		double east = Sector.tile2lon(i + 1, zoom);
		logger.debug("north:" + north + ", south:" + south + ", east:" + east + ", west:" + west);

		// convert to pixels
		Point nWest = coordTranslator.getAbsolutePointFromPosition(west, north);
		Point sEast = coordTranslator.getAbsolutePointFromPosition(east, south);
		int pixelY = StrictMath.min(nWest.y,sEast.y);
		int pixely = StrictMath.max(nWest.y,sEast.y);
		int pixelx = StrictMath.min(nWest.x,sEast.x);
		int pixelX = StrictMath.max(nWest.x,sEast.x);
		logger.debug(" nWestY:" + nWest.y + ", sEasty:" + sEast.y + ", sEastX:" + sEast.x + ", nWestx:" + nWest.x);
		logger.debug(" pixelY:" + pixelY + ", pixely:" + pixely + ", pixelX:" + pixelX + ", pixelx:" + pixelx);

		// skip if out of frame
		if (pixelY < 0 && pixely < 0) {
			return;
		}
		if (pixelY > parser.getMapHeightPixels() && pixely > parser.getMapHeightPixels()) {
			return;
		}

		if (pixelX < 0 && pixelx < 0) {
			return;
		}
		if (pixelX > parser.getMapWidthPixels() && pixelx > parser.getMapWidthPixels()) {
			return;
		}
		
		int maxHeight = StrictMath.abs(pixely - pixelY);
		int maxWidth = StrictMath.abs(pixelX - pixelx);

		int height = (int) StrictMath.abs((pixelY > 0 ? (pixely - pixelY) : pixely));
		if (height > parser.getMapHeightPixels()) {
			height = parser.getMapHeightPixels();
		}
		int width = (int) StrictMath.abs((pixelx > 0 ? (pixelX - pixelx) : pixelX));
		if (width > parser.getMapWidthPixels()) {
			width = parser.getMapWidthPixels();
		}
		logger.debug("Image: aTop:" + pixelY + ", aLeft:" + pixelx + ", width:" + width + ", height:" + height);

                // parser.getImage will fail if width or height is zero
                if ((width <= 0)||(height<=0)){
                    return;
                }

                File png = new File(pyramidPath, kapName + "/" + url + ".png");
		png.getParentFile().mkdirs();
		try {
			BufferedImage mapImage = parser.getImage((int) pixelx, (int) pixelY, width, height);
			if(mapImage==null)return;
			//if(mapImage.getWidth()<=0 || mapImage.getHeight()<=0)return;
			logger.debug("Clipped Image: x:" + mapImage.getWidth() + ", y:" + mapImage.getHeight());
			
			// if image still greater than 512, keep going another level
			if (zoom + 1 == zMax 
					&& mapImage.getWidth()< parser.getMapWidthPixels()/2
					&& zMax < 20 
					&& (mapImage.getHeight() > 256 || mapImage.getWidth() > 256)) {
				logger.info("zMax Image: aTop:" + pixelY + ", aLeft:" + pixelx + ", width:" + width + ", height:" + height);
				logger.info(" zMax="+zMax+", maxWidth="+maxWidth);
				zMax=zoom + 2;
				logger.info(" Check zMax height="+mapImage.getHeight()+", width="+mapImage.getWidth());
				logger.info(" Incremented zMax to "+zMax);
			}
			
			createImage(mapImage, png, zoom, maxWidth, maxHeight, pixelY, pixelx);
		} catch (Exception e) {
			logger.error("\tError", e);
		}
	}

	/**
	 * Creates an individual tile. Scales and pads it if needed to cover the
	 * 256x256 tile area.
	 *
	 * @param mapImage
	 * @param png
	 * @param zoom
	 * @param maxWidth
	 * @param maxHeight
	 * @param pixelY
	 * @param pixelx
	 *
	 * @throws IOException
	 */
	private void createImage(BufferedImage mapImage, File png, int zoom, int maxWidth, int maxHeight, int pixelY, int pixelx) throws IOException {

		
		double widthRatio = maxWidth / (double) mapImage.getWidth();
		double heightRatio = maxHeight / (double) mapImage.getHeight();
		// double widthScale = maxWidth/(double)width;
		// double heightScale = maxHeight/(double)height;
		// scale to 256x256 size
		mapImage = Scalr.resize(mapImage, Scalr.Mode.FIT_TO_WIDTH, (int) Math.round(256 / widthRatio), (int) Math.round(256 / heightRatio), Scalr.OP_ANTIALIAS);
		logger.debug("  tile image " + mapImage.getWidth() + "x" + mapImage.getHeight());

		// image may need padding to be square
		if (mapImage.getHeight() < 256 || mapImage.getWidth() < 256) {
			logger.debug("  Padding tile image " + mapImage.getWidth() + "x" + mapImage.getHeight());

			WritableRaster raster = mapImage.getColorModel().createCompatibleWritableRaster(256, 256);
			// create the associate image
			BufferedImage fullImage = new BufferedImage(mapImage.getColorModel(), raster, false, null);
			int offsetY = (pixelY < 0 ? 256 - mapImage.getHeight() : 0);
			int offsetX = (pixelx < 0 ? 256 - mapImage.getWidth() : 0);
			logger.debug("  Padding offset x:" + offsetX + ", y:" + offsetY);

			addImage(fullImage, mapImage, 1, offsetX, offsetY);
			parser.saveAsPNG(fullImage, png);
			fullImage.flush();
		} else {
			parser.saveAsPNG(mapImage, png);
		}
		mapImage.flush();

	}

	/**
	 * Creates an openlayers.html file in the root tiles directory that can be
	 * opened in a browser to check the chart Returns the contents for
	 * convienence.
	 *
	 * @param pyramidPath
	 * @param kapName
	 * @param nw
	 * @param se
	 * @param zMin
	 * @param zMax
	 *
	 * @return
	 * @throws IOException
	 */
	public String writeOpenlayersHtml(File pyramidPath, String kapName, Position nw, Position se, int zMin, int zMax) throws IOException {
		//get the bas openlayers.html
		String openlayers = IOUtils.toString(getClass().getClassLoader().getResourceAsStream(OPENLAYERS_HTML));
		// <BoundingBox minx="-98.631865777" miny="16.961576213198796" maxx="-98.631865777" maxy="34.209845576"/>
		// var mapBounds = new OpenLayers.Bounds( -98.631865777, 16.961576213198796, -75.31470489696913, 34.209845576);
		openlayers = openlayers.replace("var mapBounds = new OpenLayers.Bounds( -180.0, -90.0, 180.0, 90.0);",
				  "var mapBounds = new OpenLayers.Bounds(  " + nw.getLongitude() + ", " + se.getLatitude() + "," + se.getLongitude() + ", " + nw.getLatitude()
				  + ");");
		// var mapMinZoom = 0;
		openlayers = openlayers.replace("var mapMinZoom = 0;", "var mapMinZoom = " + zMin + ";");
		// var mapMaxZoom = 20;
		openlayers = openlayers.replace("var mapMaxZoom = 20;", "var mapMaxZoom = " + (zMax - 1) + ";");
		FileUtils.writeStringToFile(new File(pyramidPath, kapName + "/" + OPENLAYERS_HTML), openlayers, StandardCharsets.UTF_8);
		return openlayers;
	}

	/**
	 * prints the contents of buff2 on buff1 with the given opaque value.
	 *
	 * @param fullImage buffer
	 * @param mapImage buffer
	 * @param opaque how opaque the second buffer should be drawn
	 * @param x x position where the second buffer should be drawn
	 * @param y y position where the second buffer should be drawn
	 */
	private void addImage(BufferedImage fullImage, BufferedImage mapImage, float opaque, int x, int y) {

		Graphics2D g2d = fullImage.createGraphics();
		// g2d.setBackground(new Color(128,128,128,10));
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
		g2d.fillRect(0, 0, fullImage.getWidth(), fullImage.getHeight());
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opaque));
		// See sum bug https://bugs.openjdk.java.net/browse/JDK-6575367
		// causes transparent background to be black on scaling
		g2d.drawImage(mapImage, x, y, mapImage.getWidth(), mapImage.getHeight(), null);
		g2d.dispose();

	}

	public KapObserver getObserver() {
		return observer;
	}

	public void setObserver(KapObserver observer) {
		this.observer = observer;
	}

}
