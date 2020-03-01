package by.training.task04.karpilovich.builder.impl;

import java.io.IOException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import by.training.task04.karpilovich.builder.AbstractBuilder;
import by.training.task04.karpilovich.builder.constant.FlowersTag;
import by.training.task04.karpilovich.entity.Flower;
import by.training.task04.karpilovich.entity.GrowingTip;
import by.training.task04.karpilovich.entity.VisualParameter;
import by.training.task04.karpilovich.exception.BuilderException;

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

	private static final class SAXBuilderInstanceHandler {
		private static final SAXBuilder INSTANCE = new SAXBuilder();
	}

	public static SAXBuilder getInstance() {
		return SAXBuilderInstanceHandler.INSTANCE;
	}

	public void buildSetFlowers(String fileName) throws BuilderException {
		try {
			reader.parse(fileName);
		} catch (IOException e) {
			LOGGER.error("IOException with file " + fileName + "\n" + e);
			throw new BuilderException(e);
		} catch (SAXException e) {
			LOGGER.error("SAXException with file " + fileName + "\n" + e);
			throw new BuilderException(e);
		}
	}

	private class FlowerHandler extends DefaultHandler {

		private Flower currentFlower;
		private GrowingTip currentTip;
		private VisualParameter currentParameter;
		private Optional<FlowersTag> currentTag;

		private FlowerHandler() {
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			if (FlowersTag.FLOWER.getTagName().equals(localName)) {
				currentFlower = new Flower();
				for (int i = 0; i < attributes.getLength(); i++) {
					setFlowerAttribute(currentFlower, attributes.getLocalName(i), attributes.getValue(i));
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
				setParameter(currentFlower, currentParameter, currentTip, value, currentTag.get());
			} catch (BuilderException e) {
				LOGGER.error(e);
			}
		}
	}

}