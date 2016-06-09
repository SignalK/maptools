package org.signalk.maptools;

import java.awt.Point;



public class MapReference {


	public MapReference(Position pos, Point p) {
		this.point=p;
		this.position=pos;
	}
	public Point point;
	public Position position;

	public String toString(){
		return String.format("x=%4d y=%4d lat=%f lon%f", point.x, point.y, position.getLatitude(), position.getLongitude());
	}
}
