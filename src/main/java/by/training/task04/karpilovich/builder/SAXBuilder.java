package by.training.task04.karpilovich.builder;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import by.training.task04.karpilovich.builder.constant.Constant;
import by.training.task04.karpilovich.builder.constant.FlowerAttribute;
import by.training.task04.karpilovich.builder.constant.FlowersTag;
import by.training.task04.karpilovich.entity.Flower;
import by.training.task04.karpilovich.entity.GrowingTip;
import by.training.task04.karpilovich.entity.Multiplying;
import by.training.task04.karpilovich.entity.Soil;
import by.training.task04.karpilovich.entity.VisualParameter;
import by.training.task04.karpilovich.exception.BuilderException;

public class SAXBuilder {

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

	public Set<Flower> getFlowers() {
		return handler.flowers;
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

		private Set<Flower> flowers;
		private Flower currentFlower;
		private GrowingTip currentTip;
		private VisualParameter currentParameter;
		private FlowersTag currentTag;

		private FlowerHandler() {
			flowers = new HashSet<>();
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			if (FlowersTag.FLOWER.getTagName().equals(localName)) {
				currentFlower = new Flower();
				for (int i = 0; i < attributes.getLength(); i++) {
					setAttribute(currentFlower, attributes.getLocalName(i), attributes.getValue(i));
				}
			} else if (FlowersTag.PARAMETERS.getTagName().equals(localName)) {
				currentParameter = new VisualParameter();
			} else if (FlowersTag.TIPS.getTagName().equals(localName)) {
				currentTip = new GrowingTip();
			} else {
				for (FlowersTag tag : FlowersTag.values()) {
					if (tag.getTagName().equals(localName)) {
						currentTag = tag;
					}
				}
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
			if (currentTag == null) {
				return;
			}
			String parameter = new String(ch, start, length).trim();
			LOGGER.debug(parameter);
			switch (currentTag) {
			case SOIL:
				currentFlower.setSoil(Soil.valueOf(parameter.toUpperCase()));
				break;
			case MULTIPLYING:
				currentFlower.setMultiplying(Multiplying.valueOf(parameter.toUpperCase()));
				break;
			case PLANTING_DATE:
				Calendar calendar = new GregorianCalendar();
				try {
					calendar.setTime(Constant.FORMAT.parse(parameter));
				} catch (ParseException e) {
					LOGGER.error("Illegal date " + parameter);
				}
				currentFlower.setPlantingDate(calendar);
				break;
			case TIP_NAME:
				currentTip.setName(parameter);
				break;
			case QUANTITY:
			case NECESSITY:
				currentTip.setValue(parameter);
				break;
			case PARAMETER_NAME:
				currentParameter.setParameter(parameter);
				break;
			case SIZE:
			case COLOR:
				currentParameter.setValue(parameter);
				break;
			default:
				break;
			}
			currentTag = null;
		}

		private void setAttribute(Flower flower, String attributeName, String attributeValue) {
			switch (FlowerAttribute.valueOf(attributeName.trim().toUpperCase())) {
			case NAME:
				flower.setName(attributeValue);
				break;
			case QUANTITY:
				flower.setQuantity(Integer.parseInt(attributeValue));
				break;
			case ORIGIN:
				flower.setOrigin(attributeValue);
				break;
			default:
				break;
			}
		}

	}

}