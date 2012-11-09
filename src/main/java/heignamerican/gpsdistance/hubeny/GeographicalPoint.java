package heignamerican.gpsdistance.hubeny;

public class GeographicalPoint {
	public static GeographicalPoint getFromDegree(double aLongitude, double aLatitude) {
		return new GeographicalPoint(aLongitude * Math.PI / 180, aLatitude * Math.PI / 180);
	}

	/**
	 * 経度(ラジアン)
	 */
	private final double mLongitudeRadian;
	/**
	 * 緯度(ラジアン)
	 */
	private final double mLatitudeRadian;

	private GeographicalPoint(double aLongitudeRadian, double aLatitudeRadian) {
		mLongitudeRadian = aLongitudeRadian;
		mLatitudeRadian = aLatitudeRadian;
	}

	public double getLongitudeRadian() {
		return mLongitudeRadian;
	}

	public double getLatitudeRadian() {
		return mLatitudeRadian;
	}

	public double getLongitudeDegree() {
		return mLongitudeRadian * 180 / Math.PI;
	}

	public double getLatitudeDegree() {
		return mLatitudeRadian * 180 / Math.PI;
	}

	@Override
	public String toString() {
		return "GeographicalPoint [LongitudeRadian=" + mLongitudeRadian + ", LatitudeRadian=" + mLatitudeRadian + "]";
	}
}
