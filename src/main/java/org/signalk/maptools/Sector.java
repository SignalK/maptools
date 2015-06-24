package org.signalk.maptools;


public class Sector {
	public static final int PIXEL_REF_SIZE = 4096*4;
	Position topLeft;
	Position bottomRight;
	private static final double originShift = (2 * StrictMath.PI * 6378137) / 2.0;
	private static final int tileSize = 256;
	private static final double initialResolution = 2 * StrictMath.PI * 6378137 / tileSize;

	public Sector(Position topLeft, Position bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;

	}

	public Position getCenter() {
		double midLat = (topLeft.getLatitude() + bottomRight.getLatitude()) / 2;
		double midLon = 0.0;
		if (topLeft.getLongitude() > bottomRight.getLongitude()) {
			// cross dateline
			midLon = ((topLeft.getLongitude() + bottomRight.getLongitude()) * 0.5) + 180;
		} else {
			midLon = (topLeft.getLongitude() + bottomRight.getLongitude()) * 0.5;
		}
		return new Position(midLat, midLon);
	}

	public Position getNorthWest() {
		return topLeft;
	}

	public Position getSouthEast() {
		return bottomRight;
	}

	/**
	 * Chart must need more than 1 tile for both x and y
	 * @param nw
	 * @param se
	 * @return
	 */
	public static int getMinUsefulZoom(Position nw, Position se){
		for(int zoom=1;zoom<20;zoom++){
			if(getTileX(nw.getLongitude(), zoom)!=getTileX(se.getLongitude(), zoom)
					&& getTileY(nw.getLatitude(), zoom)!=getTileY(se.getLatitude(), zoom)){
				return zoom;
			}
		}
		return 0;
	}
	
	
	public static int getTileX(final double lon, final int zoom) {
		int xtile = (int) StrictMath.floor((lon + 180) / 360 * (1 << zoom));
		if (xtile < 0)
			xtile = 0;
		if (xtile >= (1 << zoom))
			xtile = ((1 << zoom) - 1);
		return xtile;
		// return metersToTileX( lon2x(lon), zoom);
	}

	public static int getTileY(final double lat, final int zoom) {
		double rowIndex = StrictMath.log(StrictMath.tan(StrictMath.toRadians(lat)) + (1.0 / StrictMath.cos(StrictMath.toRadians(lat))));
		rowIndex = (1 - (rowIndex / StrictMath.PI)) / 2;
		int ytile = (int) StrictMath.floor(rowIndex * ((1 << zoom) - 1));
		// invert
		ytile = (1 << zoom) - 1 - ytile;
		if (ytile < 0)
			ytile = 0;
		if (ytile >= (1 << zoom))
			ytile = ((1 << zoom) - 1);
		return ytile;
		// return metersToTileY(lat2y(lat), zoom);

	}

	public static String getTileUrl(final double lat, final double lon, final int zoom) {
		int xtile = getTileX(lon, zoom);
		int ytile = getTileY(lat, zoom);

		return ("" + zoom + "/" + xtile + "/" + ytile);
	}

	public static double tile2lon(int x, int zoom) {
		return x / StrictMath.pow(2.0, zoom) * 360.0 - 180;
		// double mx = pixelsToMetersX( x*tileSize, zoom );
		// return metersXToLon(mx);
	}

	public static double tile2lat(int y, int zoom) {
		y = (1 << zoom) - y;
		double n = StrictMath.PI - (2.0 * StrictMath.PI * y) / StrictMath.pow(2.0, zoom);
		return StrictMath.toDegrees(StrictMath.atan(StrictMath.sinh(n)));
		// double my = pixelsToMetersY( y*tileSize,zoom );
		// return metersYToLat(my);
	}
	
	//double lon2 = (lon < 0) ? lon + cph : lon - cph;

	public static double lat2y(double lat) {
		double my = StrictMath.log(StrictMath.tan((90.0 + lat) * StrictMath.PI / 360.0)) / (StrictMath.PI / 180.0);
		my = my * originShift / 180.0;
		return my;
	}

	public static double lon2x(double lon) {
		return lon * originShift / 180.0;
	}

	public static double getResolution(int zoom) {
		// Resolution (meters/pixel) for given zoom level (measured at Equator)"

		return initialResolution / StrictMath.pow(2.0, zoom);
	}

	public static double latToPixel(double aLat) {

		double sinLat = StrictMath.sin(StrictMath.toRadians(aLat));
		double log = StrictMath.log((1.0 + sinLat) / (1.0 - sinLat));
		int mp = PIXEL_REF_SIZE; 
		double y = mp * (0.5 - (log / (4.0 * StrictMath.PI)));
		return StrictMath.min(y, mp - 1);
	}

	public static double pixelToLat(int aY) {
		aY += (-1 * PIXEL_REF_SIZE / 2);
		double radius = PIXEL_REF_SIZE / (2.0 * StrictMath.PI);
		double latitude = (StrictMath.PI / 2) - (2 * StrictMath.atan(StrictMath.exp(-1.0 * aY / radius)));
		return -1 * StrictMath.toDegrees(latitude);
	}

	/**
	 * Returns the absolut number of pixels in y or x, defined as: 2^Zoomlevel *
	 * tileSize where tileSize is the width of a tile in pixels
	 * 
	 * @param aZoomlevel
	 *            zoom level to request pixel data
	 * @return number of pixels
	 */
	public int getMaxPixels(int aZoomlevel) {
		return tileSize * (1 << aZoomlevel);
	}

}
