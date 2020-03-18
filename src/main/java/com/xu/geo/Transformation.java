package com.xu.geo;

/**
 * TransFormation
 */
public class Transformation {
    private double _a;
    private double _b;
    private double _c;
    private double _d;

    Transformation(double a, double b, double c, double d){
        this._a = a;
        this._b = b;
        this._c = c;
        this._d = d;
    }


    public Point transform(Point point, double scale) {
        point.x = scale * (this._a *point.x + this._b);
        point.y = scale * (this._c * point.y + this._d);
        return point;
    }

    public Point untransform(Point point, double scale) {
		return new Point(
		        (point.x / scale - this._b) / this._a,
		        (point.y / scale - this._d) / this._c);
    }
    
    public static Transformation toTransformation(double a, double b, double c, double d) {
        return new Transformation(a, b, c, d);
    }
    
}