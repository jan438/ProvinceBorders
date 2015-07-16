package com.mylab;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ProvinceBorders {

	public static void main(final String argv[]) throws IOException {

		PrintWriter out = null;
		out = new PrintWriter(
				new BufferedWriter(
						new FileWriter(
								"/home/jan/git/BedAndBreakfast/json/province_borders.js",
								false)));

		out.print("var province_borders = { 'type': 'FeatureCollection','features': [ ");
		out.close();

		for (int index = 0; index < argv.length; index++) {
			System.out.println(argv[index]);
			Province province = new Province();
			province.process_borders(argv[index],index);
			out = new PrintWriter(
					new BufferedWriter(
							new FileWriter(
									"/home/jan/git/BedAndBreakfast/json/province_borders.js",
									true)));
			if (index < argv.length - 1) out.print(",");
			out.close();

		}

		out = new PrintWriter(
				new BufferedWriter(
						new FileWriter(
								"/home/jan/git/BedAndBreakfast/json/province_borders.js",
								true)));
		out.print("]}");
		out.close();
	}

}