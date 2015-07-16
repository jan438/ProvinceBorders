package com.mylab;

import javax.xml.parsers.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.io.*;

public class ProvinceBorders {

	public static void main(String argv[]) {

		Reader reader;
		FileWriter outputStream = null;
		StringBuilder line = new StringBuilder(64);
		File file;

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				boolean bSimpleData = false;
				boolean bMultiGeometry = false;
				boolean bPolygon = false;
				boolean bouterBoundaryIs = false;
				boolean bLinearRing = false;
				boolean bcoordinates = false;
				String province = null;
				File file = null;
				FileWriter writer = null;
				private final StringBuilder characters = new StringBuilder(64);

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					/* System.out.println("Start Element :" + qName); */

					if (qName.equalsIgnoreCase("SimpleData")) {
						bSimpleData = true;
					}

					if (qName.equalsIgnoreCase("MultiGeometry")) {
						bMultiGeometry = true;
					}

					if (qName.equalsIgnoreCase("Polygon")) {
						bPolygon = true;
					}

					if (qName.equalsIgnoreCase("outerBoundaryIs")) {
						bouterBoundaryIs = true;
					}

					if (qName.equalsIgnoreCase("LinearRing")) {
						bLinearRing = true;
					}

					if (qName.equalsIgnoreCase("coordinates")) {
						bcoordinates = true;
					}

				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					final String content = characters.toString().trim();

					if (bSimpleData) {
						if (!content.contains("provincies")) {
							province = content;
							if (province.equals("Drenthe")) {
								file = new File(
										"/home/jan/Downloads/geoserver-GetFeature.tmp");
								try {
									file.createNewFile();
									writer = new FileWriter(file);
								} catch (IOException e) {
									e.printStackTrace();
								}
								System.out.println("Drenthe");
							} else {
								if (writer != null) {
									try {
										writer.flush();
										writer.close();
										writer = null;
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}
						}
						bSimpleData = false;
					}

					if (bMultiGeometry) {
						bMultiGeometry = false;
					}

					if (bPolygon) {
						bPolygon = false;
					}

					if (bouterBoundaryIs) {
						bouterBoundaryIs = false;
					}

					if (bLinearRing) {
						bLinearRing = false;
					}

					if (bcoordinates) {
						if (province.equals("Drenthe") && (writer != null)) {
							try {
								writer.write(content);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						bcoordinates = false;
					}

					characters.setLength(0);
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {
					characters.append(new String(ch, start, length));
				}

			};

			saxParser.parse("/home/jan/Downloads/geoserver-GetFeature.kml",
					handler);

			file = new File("/home/jan/Downloads/geoserver-GetFeature.tmp");
			InputStream in = new FileInputStream(file);
			reader = new InputStreamReader(in);
			outputStream = new FileWriter(
					"/home/jan/git/BedAndBreakfast/json/drenthe_borders.js");

			int c, count = 0, modulus = 100, linecount = 0;

			outputStream.write("var drenthe_borders = [{ 'type': 'LineString', 'coordinates': [\n");
			
			try {
				while ((c = reader.read()) != -1) {
					if ((char) c == ' ') {
						if ((count % modulus) == 0) {
							String s = "[" + line.toString() + "],\n";
							outputStream.write(s);
							linecount++;
						}
						count++;
						line.setLength(0);
					} else {
						line.append((char) c);
					}
				}
			} finally {
				String s = "[" + line.toString() + "]\n]}];";
				outputStream.write(s);
				linecount++;
				count++;
				line.setLength(0);
				if (in != null) {
					in.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			}
			System.out.println("Total count of coordicates: " + count
					+ " Total count of lines:" + linecount);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
