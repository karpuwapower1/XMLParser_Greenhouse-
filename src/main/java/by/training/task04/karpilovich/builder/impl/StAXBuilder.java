package by.training.task04.karpilovich.builder.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.task04.karpilovich.builder.AbstractBuilder;
import by.training.task04.karpilovich.builder.constant.FlowersTag;
import by.training.task04.karpilovich.entity.Flower;
import by.training.task04.karpilovich.entity.GrowingTip;
import by.training.task04.karpilovich.entity.VisualParameter;
import by.training.task04.karpilovich.exception.BuilderException;

public class StAXBuilder extends AbstractBuilder {

	private XMLInputFactory factory;
	
	private static final Logger LOGGER = LogManager.getLogger(StAXBuilder.class);

	private StAXBuilder() {
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
						buildFlower(reader);
						flowers.add(currentFlower);
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

	private void buildFlower(XMLStreamReader reader) throws XMLStreamException, BuilderException {
		currentFlower = new Flower();
		for (int i = 0; i < reader.getAttributeCount(); i++) {
			setFlowerAttribute(reader.getAttributeLocalName(i), reader.getAttributeValue(i));
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
					return;
				} else if (FlowersTag.PARAMETERS.getTagName().equals(name)) {
					currentFlower.addVisualParamenter(currentParameter);
				} else if (FlowersTag.TIPS.getTagName().equals(name)) {
					currentFlower.addGrowingTip(currentTip);
				}
				break;
			}

		}
	}

	private void setParameter(String name, XMLStreamReader reader) throws XMLStreamException, BuilderException {
		Optional<FlowersTag> currentTag = FlowersTag.getFlowersTag(name);
		String value = getTextParameter(reader);
		if (!currentTag.isPresent() || value.isEmpty()) {
			return;
		}
		setParameter(value, currentTag.get());
	}

	private String getTextParameter(XMLStreamReader reader) throws XMLStreamException {
		String text = new String();
		if (reader.hasNext()) {
			reader.next();
			text = reader.getText().trim();
		}
		return text;
	}
}
