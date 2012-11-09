package heignamerican.gpsdistance;

import heignamerican.gpsdistance.hubeny.GeographicalPoint;
import heignamerican.gpsdistance.hubeny.HubenyDistance;
import heignamerican.myutils.IOUtil;
import heignamerican.myutils.StringUtil;
import heignamerican.myutils.Tuple2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class App {
	public static void main(String[] args) throws IOException {
		for (File tLogFile : new File("res/gpslogger").listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File aDir, String aName) {
				return aName.endsWith(".log");
			}
		})) {
			List<Tuple2<Date, GeographicalPoint>> tList = readLogs(tLogFile);

			System.out.println(tLogFile.getPath());
			if (tList.isEmpty()) {
				System.out.println("empty");
			} else {
				for (String tString : toStringList(tList)) {
					System.out.println(tString);
				}
			}
		}
	}

	private static List<String> toStringList(List<Tuple2<Date, GeographicalPoint>> aList) {
		List<String> tResult = new ArrayList<>();

		final String tSeparator = "\t";
		tResult.add(StringUtil.mkString(tSeparator, "epoch (ms)", "distance (m)"));

		Tuple2<Date, GeographicalPoint> tBefore = aList.get(0);
		for (int i = 0; i < aList.size(); i++) {
			Tuple2<Date, GeographicalPoint> tCurrent = aList.get(i);

			double tDistance = HubenyDistance.calcHubenyDistance(tBefore.get2(), tCurrent.get2());
			tResult.add(StringUtil.mkString(tSeparator, tCurrent.get1().getTime(), tDistance));

			tBefore = tCurrent;
		}

		return tResult;
	}

	/**
	 * <pre>
	 * ログの形式
	 * yyyy/MM/dd HH:mm:ss,epoch(long),longitude(double),latitude(double),altitude(double)
	 * </pre>
	 */
	private static List<Tuple2<Date, GeographicalPoint>> readLogs(File aFile) throws IOException {
		BufferedReader tBufferedReader = IOUtil.createBufferedReader(aFile, "UTF-8");
		try {
			List<Tuple2<Date, GeographicalPoint>> tResult = new ArrayList<>();

			for (String tLine = tBufferedReader.readLine(); tLine != null && !tLine.isEmpty(); tLine = tBufferedReader.readLine()) {
				String[] tSplitted = tLine.split(",");
				Date tDate = new Date(Long.valueOf(tSplitted[1]));
				double tLongitude = Double.parseDouble(tSplitted[2]);
				double tLatitude = Double.parseDouble(tSplitted[3]);
				GeographicalPoint tGeographicalPoint = GeographicalPoint.getFromDegree(tLongitude, tLatitude);

				tResult.add(Tuple2.create(tDate, tGeographicalPoint));
			}
			return tResult;
		} finally {
			IOUtil.closeQuietly(tBufferedReader);
		}
	}
}
