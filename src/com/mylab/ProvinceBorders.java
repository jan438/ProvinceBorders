package com.mylab;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.util.*;
import java.io.*;

public class ProvinceBorders {

	public static void main(String argv[]) {
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

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

/*					System.out.println("Start Element :" + qName); */

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

/*					System.out.println("End Element :" + qName); */

				}

				public void characters(char ch[], int start, int length)
						throws SAXException {

					if (bSimpleData) {
						System.out.println("SimpleData : "
								+ new String(ch, start, length));
						bSimpleData = false;
					}

					if (bMultiGeometry) {
/*						System.out.println("MultiGeometry : "
								+ new String(ch, start, length)); */
						bMultiGeometry = false;
					}

					if (bPolygon) {
/*						System.out.println("Polygon : "
								+ new String(ch, start, length)); */
						bPolygon = false;
					}

					if (bouterBoundaryIs) {
/*						System.out.println("bouterBoundaryIs : "
								+ new String(ch, start, length)); */
						bouterBoundaryIs = false;
					}

					if (bLinearRing) {
/*						System.out.println("LinearRing : "
								+ new String(ch, start, length)); */
						bLinearRing = false;
					}

					if (bcoordinates) {
/*						System.out.println("coordinates : "
								+ new String(ch, start, length)); */
						bcoordinates = false;
					}

				}

			};

			saxParser.parse("/home/jan/Downloads/geoserver-GetFeature.kml", handler);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
