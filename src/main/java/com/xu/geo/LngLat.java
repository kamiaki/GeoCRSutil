package com.xu.geo;
/**
 * 经纬度对象（经度lng, 纬度lat)
 * @author xuwei
 */
public class LngLat {
    public double lng;
    public double lat;

    public LngLat(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof LngLat)) {
            return false;
        }
        LngLat lnglat = (LngLat)obj;
        Double margin = Math.max(
            Math.abs(this.lng - lnglat.lng),
            Math.abs(this.lat - lnglat.lat)
        );
        return margin < 1.0E-9;
    }

	public double distanceTo(LngLat other) {
		return CRS.distance(this, other);
	}

	public LngLatBounds toBounds(double sizeInMeters) {
		double latAccuracy = 180 * sizeInMeters / 40075017;
		double lngAccuracy = latAccuracy / Math.cos((Math.PI / 180) * this.lat);

        LngLat[] lnglats = {
            new LngLat(this.lng - lngAccuracy,  this.lat - latAccuracy), 
            new LngLat(this.lng + lngAccuracy, this.lat + latAccuracy)
        };
		return new LngLatBounds(lnglats);
	}

	public LngLat clone() {
		return new LngLat(this.lat, this.lng);
    }

    public static LngLat toLngLat(double lng, double lat) {
        return new LngLat(lng, lat);
    }
    
}