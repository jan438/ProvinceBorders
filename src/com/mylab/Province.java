package com.mylab;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Province {
	public void process_borders(final String province_name, int index,
			String representavive) {

		Reader reader;
		StringBuilder line = new StringBuilder(64);
		File file;
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(
					ProvinceBorders.js_file, true)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
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
							if (province.equals(province_name)) {
								file = new File(
										"/home/jan/Downloads/geoserver-GetFeature.tmp");
								try {
									file.createNewFile();
									writer = new FileWriter(file);
								} catch (IOException e) {
									e.printStackTrace();
								}
								System.out.println(province_name);
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
						if (province.equals(province_name) && (writer != null)) {
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

			saxParser.parse(ProvinceBorders.kml_file, handler);

			file = new File("/home/jan/Downloads/geoserver-GetFeature.tmp");
			InputStream in = new FileInputStream(file);
			reader = new InputStreamReader(in);

			int c, count = 0, modulus = 100, linecount = 0;

			out.write("{ 'type': 'Feature', 'id': "
					+ index
					+ ", 'properties': { 'ID': 128058.000000, 'AREA': 54.130000, 'DATA': 7.000000, 'DISTRICT': \'"
					+ province_name
					+ "\', 'MEMBERS': 1.000000, 'LOCKED': 'Y', 'NAME': '', 'POP': 259008.000000, 'COLORING': 0, 'IDEAL_VALU': 252841.000000, 'DEVIATION': 6167.000000, 'F_DEVIATIO': 0.024400, 'REPRESENTATIVE': \'"
					+ representavive
					+ "\' }, 'geometry': { 'type': 'Polygon', 'coordinates': [[\n");

			try {
				while ((c = reader.read()) != -1) {
					if ((char) c == ' ') {
						if ((count % modulus) == 0) {
							String s = "[" + line.toString() + "],\n";
							out.write(s);
							linecount++;
						}
						count++;
						line.setLength(0);
					} else {
						line.append((char) c);
					}
				}
			} finally {
				String s = "[" + line.toString() + "]\n]]}}";
				out.write(s);
				linecount++;
				count++;
				line.setLength(0);
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			}
			System.out.println("Total count of coordicates: " + count
					+ " Total count of lines:" + linecount);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}