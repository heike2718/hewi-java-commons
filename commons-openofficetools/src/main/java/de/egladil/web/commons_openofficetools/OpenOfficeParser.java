// =====================================================
// Projekt: de.egladil.tools.parser
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_openofficetools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import de.egladil.web.commons_openofficetools.exceptions.ParserSecurityException;

/**
 * OpenOfficeParser
 */
public class OpenOfficeParser {

	private static final Logger LOG = LoggerFactory.getLogger(OpenOfficeParser.class);

	public static final String ENTRY_NAME_MIMETYPE = "mimetype";

	public static final String ENTRY_NAME_CONTENT = "content.xml";

	private final ZipParser zipParser = new ZipParser();

	/**
	 * Erwartet einen OpenOffice-Container und liest und prüft das content.xml.
	 *
	 * @param  in
	 *                            InputStream
	 * @param  maxLengthExtracted
	 *                            int maximale akzeptierte Länge des extrahierten content.xml
	 * @return                    String das xml
	 * @throws IOException
	 */
	public String getContentSafe(final InputStream in, final int maxLengthExtracted) throws IOException, ParserSecurityException {

		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

			IOUtils.copy(in, out);

			final byte[] bytes = out.toByteArray();
			final String content = zipParser.getContentOfEntrySafe(bytes, ENTRY_NAME_CONTENT, maxLengthExtracted, "utf-8");
			this.checkVulnerableXml(content);
			return content;
		}
	}

	/**
	 * Sammelt aus dem xml die für die Auswertung relevanten Informationen.
	 *
	 * @param  xml
	 *                                      String aus content.xml
	 * @return                              List
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws XMLStreamException
	 */
	public List<OpenOfficeTableElement> parseContent(final String xml) throws IOException, ParserConfigurationException, SAXException, XMLStreamException {

		final List<OpenOfficeTableElement> extrahierteElemente = new ArrayList<>();
		boolean tableCellOpened = false;
		boolean formulaOpened = false;
		boolean interestingContent = false;
		OpenOfficeTableElement rowElement = null;
		OpenOfficeTableElement cellElement = null;

		try (InputStreamReader contentIn = new InputStreamReader(new ByteArrayInputStream(xml.getBytes()))) {

			final XMLInputFactory factory = XMLInputFactory.newInstance();
			final XMLEventReader eventReader = factory.createXMLEventReader(contentIn);

			while (eventReader.hasNext()) {

				final XMLEvent event = eventReader.nextEvent();

				switch (event.getEventType()) {

				case XMLStreamConstants.START_ELEMENT:
					final StartElement startElement = event.asStartElement();
					final String qName = startElement.getName().getLocalPart();

					if (qName.equalsIgnoreCase("table-row")) {

						rowElement = new OpenOfficeTableElement(OOElementType.ROW);
					} else if (qName.equalsIgnoreCase("table-cell")) {

						cellElement = new OpenOfficeTableElement(OOElementType.CELL);
						final Iterator<Attribute> attributes = startElement.getAttributes();

						while (attributes.hasNext()) {

							final Attribute attr = attributes.next();
							final String attrName = attr.getName().getLocalPart();

							if ("formula".equalsIgnoreCase(attrName)) {

								formulaOpened = true;
								break;
							} else {

								formulaOpened = false;
							}
						}
						cellElement.setFormula(formulaOpened);
						tableCellOpened = true;
					} else if (qName.equalsIgnoreCase("p")) {

						interestingContent = true;
					}
					break;

				case XMLStreamConstants.CHARACTERS:
					final Characters characters = event.asCharacters();
					if (tableCellOpened) {

						cellElement.setContent(characters.getData());
						rowElement.addChild(cellElement);
						cellElement = null;
						tableCellOpened = false;
					}
					if (interestingContent) {

						interestingContent = false;
					}
					break;

				case XMLStreamConstants.END_ELEMENT:
					final EndElement endElement = event.asEndElement();

					if (endElement.getName().getLocalPart().equalsIgnoreCase("table-row")) {

						extrahierteElemente.add(OpenOfficeTableElement.createCopy(rowElement));
						rowElement = null;
					}
					break;
				}
			}
		}

		return extrahierteElemente;
	}

	/**
	 * @param  xml
	 *                     String
	 * @throws IOException
	 */
	void checkVulnerableXml(final String xml) throws IOException {

		String feature = null;

		try (InputStream in = new ByteArrayInputStream(xml.getBytes())) {

			final InputSource source = new InputSource(in);
			final SAXParserFactory spf = SAXParserFactory.newInstance();
			final SAXParser saxParser = spf.newSAXParser();
			final XMLReader reader = saxParser.getXMLReader();

			feature = "http://xml.org/sax/features/external-general-entities";
			spf.setFeature(feature, false);
			reader.setFeature(feature, false);

			feature = "http://apache.org/xml/features/disallow-doctype-decl";
			spf.setFeature(feature, true);
			reader.setFeature(feature, true);

			feature = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
			reader.setFeature(feature, false); // This may not be strictly required as DTDs shouldn't be allowed at all,
												// per previous line.

			feature = "http://xml.org/sax/features/external-parameter-entities";
			reader.setFeature(feature, false);

			reader.parse(source);

		} catch (final ParserConfigurationException e) {

			// This should catch a failed setFeature feature
			LOG.warn("ParserConfigurationException was thrown. The feature '" + feature
				+ "' is probably not supported by your XML processor.");
		} catch (final SAXException e) {

			throw new ParserSecurityException("XML enthält DOCTYPE");
		}
	}
}
