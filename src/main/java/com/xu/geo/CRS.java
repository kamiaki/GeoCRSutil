package com.xu.geo;

/**
 * 坐标引用系统
 * @author xuwei
 */
public abstract class CRS {
	public static int[] wrapLng = {-180, 180};
    public static int R = 6371000;
	protected Transformation transformation;
	public SphericalMercator projection;
	public boolean infinite = false;

    public Point LngLatToPoint(LngLat lnglat, int zoom){
		Point projectedPoint = this.projection.project(lnglat);
		double scale = this.scale(zoom);
		return this.transformation.transform(projectedPoint.clone(), scale);
	}

	public LngLat pointToLngLat(Point point, int zoom) {
		double scale = this.scale(zoom);
		Point untransformedPoint = this.transformation.untransform(point, scale);
		return this.projection.unproject(untransformedPoint);
	}

	public Point project(LngLat lnglat){
		return this.projection.project(lnglat);
	}

	public LngLat unproject(Point point) {
		return this.projection.unproject(point);
	}

	public double scale(int zoom) {
		return 256 * Math.pow(2, zoom);
	}

	public double zoom(double scale) {
		return Math.log(scale / 256) / Math.log(2);
	}

	public Bounds getProjectedBounds(int zoom) {
		if (this.infinite) return null;
		Bounds b = this.projection.bounds;
		double s = this.scale(zoom);
		Point min = this.transformation.transform(b.min, s);
		Point max = this.transformation.transform(b.max, s);

		return new Bounds(min, max);
	}

	public static double distance(LngLat lnglat1, LngLat lngLat2) {
        double rad = Math.PI / 180;
		double lat1 = lnglat1.lat * rad;
		double lat2 = lngLat2.lat * rad;
		double sinDLat = Math.sin((lngLat2.lat - lnglat1.lat) * rad / 2);
		double sinDLon = Math.sin((lngLat2.lng - lnglat1.lng) * rad / 2);
		double a = sinDLat * sinDLat + Math.cos(lat1) * Math.cos(lat2) * sinDLon * sinDLon;
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return R * c;
    }


}