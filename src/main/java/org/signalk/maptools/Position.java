package org.signalk.maptools;

public class Position {

	private double latitude;
	private double longitude;

	public Position(double latitude, double longitude) {
		this.latitude=latitude;
		this.longitude=longitude;
	}

	protected double getLatitude() {
		return latitude;
	}

	protected void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	protected double getLongitude() {
		return longitude;
	}

	protected void setLongitude(double longitude) {
		this.longitude = longitude;
	}
        
        public String toString(){
            return String.format("lat=%f lon=%f", latitude, longitude);
        }
 
}
