package com.xu.geo;

/**
 * 平面像素坐标点对象
 * @author xuwei
 */
public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;      
    }

    public Point clone(){
        return new Point(this.x, this.y);
    }

    public Point add(Point point) {
        this.x += point.x;
        this.y += point.y;
        return this;
    }

    public Point subtract(Point point) {
        this.x -= point.x;
        this.y -= point.y;
        return this;
    }

	public Point divideBy(double num) {
		this.x /= num;
		this.y /= num;
		return this;
	}

	public Point multiplyBy(double num) {
		this.x *= num;
		this.y *= num;
		return this;
	}

	public Point scaleBy(Point point) {
		return new Point(this.x * point.x, this.y * point.y);
	}
	public Point unscaleBy(Point point) {
		return new Point(this.x / point.x, this.y / point.y);
    }

    public Point round() {
        this.x = Math.round(this.x);
		this.y = Math.round(this.y);
		return this;
	}
    
    public double distanceTo(Point point) {
		double x = point.x - this.x;
		double y = point.y - this.y;

		return Math.sqrt(x * x + y * y);
    }
    
    @Override
    public boolean equals(Object obj) {
        Point point = (Point) obj;
        return point.x == this.x &&
               point.y == this.y;
    }

    public boolean contains(Point point){
        return Math.abs(point.x) <= Math.abs(this.x) &&
		       Math.abs(point.y) <= Math.abs(this.y); 
    }

    @Override
    public String toString() {
        return "Point(" + this.x+ ", " +this.y + ")";
    }


}