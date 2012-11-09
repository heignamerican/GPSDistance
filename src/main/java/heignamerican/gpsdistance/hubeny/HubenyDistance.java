package heignamerican.gpsdistance.hubeny;

public class HubenyDistance {
	private static final double a = 6377397.155; // 赤道半径(m)
	private static final double e2 = 0.00667436061028297; // 第一離心率の自乗

	/**
	 * ヒュベニの公式<br>
	 * 大変参考になったサイト <a href="http://yamadarake.jp/trdi/report000001.html">http://yamadarake.jp/trdi/report000001.html</a>
	 */
	public static double calcHubenyDistance(final GeographicalPoint aPoint1, final GeographicalPoint aPoint2) {
		final double x1 = aPoint1.getLongitudeRadian();
		final double y1 = aPoint1.getLatitudeRadian();
		final double x2 = aPoint2.getLongitudeRadian();
		final double y2 = aPoint2.getLatitudeRadian();

		final double dx = Math.abs(x1 - x2);
		final double dy = Math.abs(y1 - y2);

		final double muonY = (y1 + y2) / 2;

		final double W = Math.sqrt(1 - square(e2) * square(Math.sin(muonY)));
		final double N = a / W;
		final double M = a * (1 - e2) / cube(W);

		return Math.sqrt(square(dy * M) + square(dx * N * muonY));
	}

	private static double square(final double aValue) {
		return aValue * aValue;
	}

	private static double cube(final double aValue) {
		return aValue * aValue * aValue;
	}
}
