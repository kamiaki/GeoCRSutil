package com.xu.geo;

/**
 * EPSG3857
 */
public class EPSG3857 extends CRS {
    private volatile static EPSG3857 instance;
    public static String code = "EPSG:3857";

    public EPSG3857(){
        this.projection = new SphericalMercator();
        double scale = 0.5 / (Math.PI * SphericalMercator.R);
        this.transformation = new Transformation(scale, 0.5, -scale, 0.5);
    }

    public static EPSG3857 getInstance(){
        if(instance == null) {
            synchronized (EPSG3857.class) {
                if(instance == null) {
                    instance = new EPSG3857();
                }
            }
        }
        return instance;
    }

    
}