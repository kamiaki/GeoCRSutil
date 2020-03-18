package com.xu.geo;

/**
 * LngLatBounds
 */
public class LngLatBounds {
    public LngLat southWest;
    public LngLat northEast;


    LngLatBounds(LngLat[] lnglats) {
        for(int i=0, len=lnglats.length; i<len; i++) {
            this.extend(lnglats[i]);
        }       
    }

    public LngLatBounds extend(Object obj) {
		LngLat sw = this.southWest;
		LngLat ne = this.northEast;
        LngLat sw2, ne2;

		if (obj instanceof LngLat) {
			sw2 = (LngLat)obj;
			ne2 = (LngLat)obj;

		} else if (obj instanceof LngLatBounds) {
			sw2 = ((LngLatBounds)obj).southWest;
			ne2 = ((LngLatBounds)obj).northEast;

			if (sw2 == null || ne2 == null) { return this; }

		} else {
			return this;
		}

		if (sw == null && ne == null) {
			this.southWest = new LngLat(sw2.lat, sw2.lng);
			this.northEast = new LngLat(ne2.lat, ne2.lng);
		} else {
			sw.lat = Math.min(sw2.lat, sw.lat);
			sw.lng = Math.min(sw2.lng, sw.lng);
			ne.lat = Math.max(ne2.lat, ne.lat);
			ne.lng = Math.max(ne2.lng, ne.lng);
		}

		return this;
    }
    
    public LngLatBounds pad(double bufferRatio) {
		LngLat sw = this.southWest;
		LngLat ne = this.northEast;
		double heightBuffer = Math.abs(sw.lat - ne.lat) * bufferRatio;
        double widthBuffer = Math.abs(sw.lng - ne.lng) * bufferRatio;
        
        LngLat[] lngLats = { new LngLat(sw.lat - heightBuffer, sw.lng - widthBuffer),
            new LngLat(ne.lat + heightBuffer, ne.lng + widthBuffer) };

		return new LngLatBounds(lngLats);
	}

	public LngLat getCenter() {
		return new LngLat(
		        (this.southWest.lat + this.northEast.lat) / 2,
		        (this.southWest.lng + this.northEast.lng) / 2);
	}

	public LngLat getSouthWest() {
		return this.southWest;
	}

	public LngLat getNorthEast() {
		return this.northEast;
	}

	public LngLat getNorthWest() {
		return new LngLat(this.getNorth(), this.getWest());
	}

	public LngLat getSouthEast() {
		return new LngLat(this.getSouth(), this.getEast());
	}

	public double getWest() {
		return this.southWest.lng;
	}

	public double getSouth() {
		return this.southWest.lat;
	}

	public double getEast() {
		return this.northEast.lng;
	}

	public double getNorth() {
		return this.northEast.lat;
	}

	public boolean contains(Object obj) {
		LngLat sw = this.southWest;
		LngLat ne = this.northEast;
		LngLat sw2, ne2;

		if (obj instanceof LngLatBounds) {
            LngLatBounds b = (LngLatBounds) obj;
			sw2 = b.getSouthWest();
			ne2 = b.getNorthEast();
		} else if(obj instanceof LngLat){
			sw2 = ne2 = (LngLat)obj;
		} else {
            return false;
        }

		return (sw2.lat >= sw.lat) && (ne2.lat <= ne.lat) &&
		       (sw2.lng >= sw.lng) && (ne2.lng <= ne.lng);
	}

	public boolean intersects(LngLatBounds bounds) {
		LngLat sw = this.southWest;
		LngLat ne = this.northEast;
		LngLat sw2 = bounds.getSouthWest();
		LngLat ne2 = bounds.getNorthEast();

		boolean latIntersects = (ne2.lat >= sw.lat) && (sw2.lat <= ne.lat);
		boolean lngIntersects = (ne2.lng >= sw.lng) && (sw2.lng <= ne.lng);

		return latIntersects && lngIntersects;
	}

	public boolean overlaps(LngLatBounds bounds) {
		LngLat sw = this.southWest;
		LngLat ne = this.northEast;
		LngLat sw2 = bounds.getSouthWest();
		LngLat ne2 = bounds.getNorthEast();

        boolean latOverlaps = (ne2.lat > sw.lat) && (sw2.lat < ne.lat);
        boolean lngOverlaps = (ne2.lng > sw.lng) && (sw2.lng < ne.lng);

		return latOverlaps && lngOverlaps;
	}

    @Override
	public boolean equals(Object bounds) {
        if(bounds instanceof LngLatBounds) {
            LngLatBounds b = (LngLatBounds) bounds;
            return this.southWest.equals(b.getSouthWest()) &&
                this.northEast.equals(b.getNorthEast());
        }
        return false;
    }
    
	public boolean isValid() {
		return this.southWest != null && this.northEast != null;
	}

}