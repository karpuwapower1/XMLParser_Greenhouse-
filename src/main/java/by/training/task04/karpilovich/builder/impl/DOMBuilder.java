package by.training.task04.karpilovich.builder.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import by.training.task04.karpilovich.builder.AbstractBuilder;
import by.training.task04.karpilovich.builder.constant.FlowersTag;
import by.training.task04.karpilovich.entity.Flower;
import by.training.task04.karpilovich.entity.GrowingTip;
import by.training.task04.karpilovich.entity.Multiplying;
import by.training.task04.karpilovich.entity.Soil;
import by.training.task04.karpilovich.entity.VisualParameter;
import by.training.task04.karpilovich.exception.BuilderException;

public class DOMBuilder extends AbstractBuilder {

	private DocumentBuilder documentBuilder;
	private static final Logger LOGGER = LogManager.getLogger(DOMBuilder.class);

	private DOMBuilder() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			documentBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			LOGGER.fatal("ParserConfigurationException while DOMBuilder was created \n" + e);
		}
	}

	public static final class DOMBuilderInstanceHolder {
		private static final DOMBuilder INSTANCE = new DOMBuilder();
	}

	public static DOMBuilder getInstance() {
		return DOMBuilderInstanceHolder.INSTANCE;
	}

	@Override
	public void buildSetFlowers(String fileName) throws BuilderException {
		Element flowerElement;
		try {
			Document document = documentBuilder.parse(fileName);
			Element element = document.getDocumentElement();
			NodeList flowerList = element.getElementsByTagName(FlowersTag.FLOWER.getTagName());
			for (int i = 0; i < flowerList.getLength(); i++) {
				flowerElement = (Element) flowerList.item(i);
				flowers.add(buildFlower(flowerElement));
			}
		} catch (IOException e) {
			LOGGER.error("IOException with file " + fileName + "\n" + e);
			throw new BuilderException(e);
		} catch (SAXException e) {
			LOGGER.error("SAXExceptoin while building a flowers set\n " + e);
			throw new BuilderException(e);
		}
	}

	private Flower buildFlower(Element flowerElement) throws BuilderException {
		Flower flower = new Flower();
		NamedNodeMap flowerAttributes = flowerElement.getAttributes();
		setAttributesToFlower(flower, flowerAttributes);
		setParametersToFlower(flower, flowerElement);
		return flower;
	}

	private void setAttributesToFlower(Flower flower, NamedNodeMap flowerAttributes) {
		if (flowerAttributes == null) {
			return;
		}
		Node node;
		for (int i = 0; i < flowerAttributes.getLength(); i++) {
			node = flowerAttributes.item(i);
			setFlowerAttribute(flower, node.getNodeName(), node.getNodeValue());
		}
	}

	private void setParametersToFlower(Flower flower, Element flowerElements) throws BuilderException {
		String soil = getElementTextContent(flowerElements, FlowersTag.SOIL);
		if (!soil.isEmpty()) {
			flower.setSoil(Soil.valueOf(soil.trim().toUpperCase()));
		}
		String multiplying = getElementTextContent(flowerElements, FlowersTag.MULTIPLYING);
		if (!multiplying.isEmpty()) {
			flower.setMultiplying(Multiplying.valueOf(multiplying.trim().toUpperCase()));
		}
		flower.setParameters(buildSetVisualParameter(flowerElements));
		flower.setTips(buildSetGrowingTip(flowerElements));
		String date = getElementTextContent(flowerElements, FlowersTag.PLANTING_DATE);
		if (!date.isEmpty()) {
			flower.setPlantingDate(parseDate(date));
		}
	}

	private String getElementTextContent(Element element, FlowersTag tag) {
		NodeList list = element.getElementsByTagName(tag.getTagName());
		if (list.item(0) == null) {
			return new String();
		}
		return list.item(0).getTextContent();
	}

	private Set<VisualParameter> buildSetVisualParameter(Element flowerElements) {
		Set<VisualParameter> parameters = new HashSet<>();
		NodeList nodes = flowerElements.getElementsByTagName(FlowersTag.PARAMETERS.getTagName());
		for (int i = 0; i < nodes.getLength(); i++) {
			Element parameter = (Element) nodes.item(i);
			parameters.add(buildVisualParameter(parameter));
		}
		return parameters;
	}

	private VisualParameter buildVisualParameter(Element element) {
		VisualParameter parameter = new VisualParameter();
		String param = getElementTextContent(element, FlowersTag.PARAMETER_NAME);
		if (!param.isEmpty()) {
			parameter.setParameter(param);
		}
		param = getElementTextContent(element, FlowersTag.SIZE);
		if (!param.isEmpty()) {
			parameter.setValue(param);
		}
		param = getElementTextContent(element, FlowersTag.COLOR);
		if (!param.isEmpty()) {
			parameter.setValue(param);
		}
		return parameter;
	}

	private Set<GrowingTip> buildSetGrowingTip(Element flowerElements) {
		Set<GrowingTip> tips = new HashSet<>();
		NodeList nodes = flowerElements.getElementsByTagName(FlowersTag.TIPS.getTagName());
		for (int i = 0; i < nodes.getLength(); i++) {
			Element tip = (Element) nodes.item(i);
			tips.add(buildGrowingTip(tip));
		}
		return tips;
	}

	private GrowingTip buildGrowingTip(Element element) {
		GrowingTip tip = new GrowingTip();
		String param = getElementTextContent(element, FlowersTag.TIP_NAME);
		if (!param.isEmpty()) {
			tip.setName(param);
		}
		param = getElementTextContent(element, FlowersTag.NECESSITY);
		if (!param.isEmpty()) {
			tip.setValue(param);
		}
		param = getElementTextContent(element, FlowersTag.QUANTITY);
		if (!param.isEmpty()) {
			tip.setValue(param);
		}
		return tip;
	}

	
}