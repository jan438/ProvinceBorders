package com.mylab;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ProvinceBorders {

	public static final String js_file = "/home/jan/git/BedAndBreakfast/json/province_borders.js";
	public static final String kml_file = "/home/jan/Downloads/geoserver-GetFeature.kml";

	public static void main(final String argv[]) throws IOException {

		String representavive = null;
		PrintWriter out = null;
		out = new PrintWriter(
				new BufferedWriter(new FileWriter(js_file, false)));

		out.print("var province_borders = { 'type': 'FeatureCollection','features': [ ");
		out.close();

		for (int index = 0; index < argv.length; index++) {
			System.out.println(argv[index]);
			Province province = new Province();
			switch (index) {
			case 0:
				representavive = "J. Tichelaar";
				break;
			case 1:
				representavive = "Th.J.F.M. Bovens";
				break;
			case 2:
				representavive = "W. van Beek";
				break;
			default:
				representavive = "";
			}
			province.process_borders(argv[index], index, representavive);
			out = new PrintWriter(new BufferedWriter(new FileWriter(js_file,
					true)));
			if (index < argv.length - 1)
				out.print(",");
			out.close();

		}

		out = new PrintWriter(new BufferedWriter(new FileWriter(js_file, true)));
		out.print("]}");
		out.close();
	}

}