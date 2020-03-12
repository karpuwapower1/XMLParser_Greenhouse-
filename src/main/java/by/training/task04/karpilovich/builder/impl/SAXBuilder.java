package by.training.task04.karpilovich.builder.impl;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import by.training.task04.karpilovich.builder.AbstractBuilder;
import by.training.task04.karpilovich.builder.util.FlowersTag;
import by.training.task04.karpilovich.entity.Flower;
import by.training.task04.karpilovich.entity.GrowingTip;
import by.training.task04.karpilovich.entity.VisualParameter;
import by.training.task04.karpilovich.exception.ParserException;

public class SAXBuilder extends AbstractBuilder {

	private XMLReader reader;
	private FlowerHandler handler;

	private static final Logger LOGGER = LogManager.getLogger(SAXBuilder.class);

	private SAXBuilder() {
		handler = new FlowerHandler();
		try {
			reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(handler);
		} catch (SAXException e) {
			LOGGER.fatal("SAXException while initializing SAXBuilder\n " + e);
		}
	}

	public static SAXBuilder getInstance() {
		return new SAXBuilder();
	}

	public void buildSetFlowers(File file) throws ParserException {
		validator.validate(file);
		try {
			reader.parse(file.getAbsolutePath());
		} catch (IOException | SAXException e) {
			LOGGER.error("Illegal file \n" + e);
			throw new ParserException("Illegal file ", e);
		}
	}

	private class FlowerHandler extends DefaultHandler {

		public FlowerHandler() {
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			if (FlowersTag.FLOWER.getTagName().equals(localName)) {
				currentFlower = new Flower();
				for (int i = 0; i < attributes.getLength(); i++) {
					setFlowerAttribute(attributes.getLocalName(i), attributes.getValue(i));
				}
			} else if (FlowersTag.PARAMETERS.getTagName().equals(localName)) {
				currentParameter = new VisualParameter();
			} else if (FlowersTag.TIPS.getTagName().equals(localName)) {
				currentTip = new GrowingTip();
			} else {
				currentTag = FlowersTag.getFlowersTag(localName);
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (FlowersTag.FLOWER.getTagName().equals(localName)) {
				flowers.add(currentFlower);
			} else if (FlowersTag.PARAMETERS.getTagName().equals(localName)) {
				currentFlower.addVisualParamenter(currentParameter);
			} else if (FlowersTag.TIPS.getTagName().equals(localName)) {
				currentFlower.addGrowingTip(currentTip);
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			String value = new String(ch, start, length).trim();
			if (!currentTag.isPresent() || value.isEmpty()) {
				return;
			}
			try {
				setParameter(value, currentTag.get());
			} catch (ParserException e) {
				LOGGER.error(e);
				throw new SAXException(e);
			}
		}
	}

}