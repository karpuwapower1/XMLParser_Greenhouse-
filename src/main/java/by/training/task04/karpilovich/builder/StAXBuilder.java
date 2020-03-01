package by.training.task04.karpilovich.builder;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.task04.karpilovich.builder.constant.Constant;
import by.training.task04.karpilovich.builder.constant.FlowerAttribute;
import by.training.task04.karpilovich.builder.constant.FlowersTag;
import by.training.task04.karpilovich.entity.Flower;
import by.training.task04.karpilovich.entity.GrowingTip;
import by.training.task04.karpilovich.entity.Multiplying;
import by.training.task04.karpilovich.entity.Soil;
import by.training.task04.karpilovich.entity.VisualParameter;
import by.training.task04.karpilovich.exception.BuilderException;

public class StAXBuilder {

	private Set<Flower> flowers;
	private XMLInputFactory factory;
	private Flower currentFlower;
	private GrowingTip currentTip;
	private VisualParameter currentParameter;

	private static final Logger LOGGER = LogManager.getLogger(StAXBuilder.class);

	private StAXBuilder() {
		flowers = new HashSet<>();
		factory = XMLInputFactory.newInstance();
	}

	private static final class StAXBuilderInstanceHolder {
		private static final StAXBuilder INSTANCE = new StAXBuilder();
	}

	public static StAXBuilder getInstance() {
		return StAXBuilderInstanceHolder.INSTANCE;
	}

	public Set<Flower> getFlowers() {
		return flowers;
	}

	public void buildSetFlowers(String fileName) throws BuilderException {
		try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
			XMLStreamReader reader = factory.createXMLStreamReader(fileInputStream);
			String name;
			while (reader.hasNext()) {
				if (reader.next() == XMLStreamConstants.START_ELEMENT) {
					name = reader.getLocalName();
					if (FlowersTag.FLOWER.getTagName().equals(name)) {
						flowers.add(buildFlower(reader));
					}
				}
			}
		} catch (IOException e) {
			LOGGER.error("IOException while working with file " + fileName + "\n" + e);
			throw new BuilderException(e);
		} catch (XMLStreamException e) {
			LOGGER.error("XMLStreamException while working with file " + fileName + "\n" + e);
			throw new BuilderException(e);
		}
	}

	private Flower buildFlower(XMLStreamReader reader) throws XMLStreamException {
		currentFlower = new Flower();
		for (int i = 0; i < reader.getAttributeCount(); i++) {
			setAttribute(reader.getAttributeLocalName(i), reader.getAttributeValue(i));
		}
		String name;
		while (reader.hasNext()) {
			switch (reader.next()) {
			case XMLStreamConstants.START_ELEMENT:
				name = reader.getLocalName();
				if (FlowersTag.PARAMETERS.getTagName().equals(name)) {
					currentParameter = new VisualParameter();
				} else if (FlowersTag.TIPS.getTagName().equals(name)) {
					currentTip = new GrowingTip();
				} else {
					setParameter(name, reader);
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				name = reader.getLocalName();
				if (FlowersTag.FLOWER.getTagName().equals(name)) {
					return currentFlower;
				} else if (FlowersTag.PARAMETERS.getTagName().equals(name)) {
					currentFlower.addVisualParamenter(currentParameter);
				} else if (FlowersTag.TIPS.getTagName().equals(name)) {
					currentFlower.addGrowingTip(currentTip);
				}
				break;
			}

		}
		return currentFlower;
	}

	private void setAttribute(String attributeName, String attributeValue) {
		switch (FlowerAttribute.valueOf(attributeName.trim().toUpperCase())) {
		case NAME:
			currentFlower.setName(attributeValue);
			break;
		case QUANTITY:
			currentFlower.setQuantity(Integer.parseInt(attributeValue));
			break;
		case ORIGIN:
			currentFlower.setOrigin(attributeValue);
			break;
		default:
			break;
		}
	}

	private void setParameter(String name, XMLStreamReader reader) throws XMLStreamException {
		Optional<FlowersTag> optional = FlowersTag.getFlowersTag(name);
		String parameter = getParameter(reader);
		if (!optional.isPresent() || parameter.isEmpty()) {
			return;
		}
		switch (optional.get()) {
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
	}

	private String getParameter(XMLStreamReader reader) throws XMLStreamException {
		String text = new String();
		if (reader.hasNext()) {
			reader.next();
			text = reader.getText();
		}
		return text;
	}
}
