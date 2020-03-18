package com.xu.geo;
/**
 * 多边形或点集合的最小外接矩形
 * @author xuwei
 */
public class Bounds {
    public Point min;
    public Point max;

    Bounds(Point a, Point b) {
        if(a==null || b==null) return;
        Point[] points = {a, b};
        
        for(int i=0, len=points.length; i<len; i++) {
            this.extend(points[i]);
        }
    }

    private Bounds extend(Point point) { // (Point)
		if (this.min==null && this.max==null) {
			this.min = point.clone();
			this.max = point.clone();
		} else {
			this.min.x = Math.min(point.x, this.min.x);
			this.max.x = Math.max(point.x, this.max.x);
			this.min.y = Math.min(point.y, this.min.y);
			this.max.y = Math.max(point.y, this.max.y);
		}
		return this;
	}

    public Point getCenter() {
		return new Point(
		        (this.min.x + this.max.x) / 2,
		        (this.min.y + this.max.y) / 2);
	}

	public Point getBottomLeft() {
		return new Point(this.min.x, this.max.y);
	}

	public Point getTopRight() { // -> Point
		return new Point(this.max.x, this.min.y);
	}

	public Point getTopLeft() {
		return this.min; // left, top
	}

	public Point getBottomRight() {
		return this.max; // right, bottom
	}

	public Point getSize() {
		return this.max.subtract(this.min);
	}

	public boolean contains(Object obj) {
		Point min, max;

		if (obj instanceof Bounds) {
            Bounds b = (Bounds) obj;
			min = b.min;
			max = b.max;
		} else if(obj instanceof Point){
            Point p = (Point) obj;
			min = max = p;
		} else {
            throw new RuntimeException("Type error: obj must be instance of Bounds or Point");
        }

		return (min.x >= this.min.x) &&
		       (max.x <= this.max.x) &&
		       (min.y >= this.min.y) &&
		       (max.y <= this.max.y);
	}

	public boolean intersects(Bounds bounds) { // (Bounds) -> Boolean
		Point min = this.min;
		Point max = this.max;
		Point min2 = bounds.min;
		Point max2 = bounds.max;
		boolean xIntersects = (max2.x >= min.x) && (min2.x <= max.x);
		boolean yIntersects = (max2.y >= min.y) && (min2.y <= max.y);

		return xIntersects && yIntersects;
	}

	public boolean overlaps(Bounds bounds) { // (Bounds) -> Boolean
		Point min = this.min;
		Point max = this.max;
		Point min2 = bounds.min;
		Point max2 = bounds.max;
		boolean xOverlaps = (max2.x > min.x) && (min2.x < max.x);
		boolean yOverlaps = (max2.y > min.y) && (min2.y < max.y);

		return xOverlaps && yOverlaps;
	}

	public boolean isValid() {
		return this.min !=null && this.max != null;
	}
}