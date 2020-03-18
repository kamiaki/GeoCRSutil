package com.xu.geo;

/**
 * CRSutil
 * @autho xuwei
 */
public class CRSutil {
    private int zoom;
    private CRS crs;
    private Point pixelOrigin;

    /**
     * 
     * @param center [double lng, double lat], 画布的中心坐标
     * @param width  画布的宽
     * @param height 画布的高
     */
    public CRSutil(double[] center, double width, double height, int zoom){
        this.zoom = zoom;
        this.crs = EPSG3857.getInstance();
        Point size = new Point(width, height);
        Point viewHalf = size.divideBy(2);
        this.pixelOrigin = this.project(toLngLat(center[0], center[1]))
            .subtract(viewHalf).round();
    }
    
    public LngLat pixelPointToLngLat(Point point) {
        Point projectedPoint = point.clone().add(this.pixelOrigin);
        return this.unproject(projectedPoint);
    }

    public Point lngLatToPixelPoint(LngLat lnglat) {
		Point projectedPoint = this.project(lnglat).round();
		return projectedPoint.clone().subtract(this.pixelOrigin);
    }
    
    public Point project(LngLat lngLat) {
        return this.crs.LngLatToPoint(lngLat, this.zoom);
    }

    public LngLat unproject(Point point) {
        return this.crs.pointToLngLat(point,  this.zoom);
    }

    public static Point toPoint(double x, double y) {
        return new Point(x, y);
    }
    
    public static LngLat toLngLat(double a, double b) {
         return new LngLat(a, b);
     }
}