package com.xu.geo;

/**
 * SphericalMercator
 */
public class SphericalMercator {

    public static double R = 6378137;
    public static double MAX_LATITUDE = 85.0511287798;
    public Bounds bounds;

    public SphericalMercator(){
		bounds = new Bounds(new Point(-R * Math.PI, -R * Math.PI), new Point(R * Math.PI, R * Math.PI));
    }

    public Point project(LngLat lnglat) {
        double d = Math.PI / 180;
		double max = MAX_LATITUDE;
		double lat = Math.max(Math.min(max, lnglat.lat), -max);
		double sin = Math.sin(lat * d);

		return new Point(
			R * lnglat.lng * d,
		    R * Math.log((1 + sin) / (1 - sin)) / 2);
    }

    public LngLat unproject(Point point) {
		double d = 180 / Math.PI;

		return new LngLat(
			(2 * Math.atan(Math.exp(point.y / R)) - Math.PI / 2) * d,
			point.x * d / R);
	}
    
}