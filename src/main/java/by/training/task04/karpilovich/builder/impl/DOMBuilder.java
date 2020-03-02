package by.training.task04.karpilovich.builder.impl;

import java.io.IOException;

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
				buildFlower(flowerElement);
				flowers.add(currentFlower);
			}
		} catch (IOException e) {
			LOGGER.error("IOException with file " + fileName + "\n" + e);
			throw new BuilderException(e);
		} catch (SAXException e) {
			LOGGER.error("SAXExceptoin while building a flowers set\n " + e);
			throw new BuilderException(e);
		}
	}

	private void buildFlower(Element flowerElement) throws BuilderException {
		currentFlower = new Flower();
		NamedNodeMap flowerAttributes = flowerElement.getAttributes();
		setAttributesToFlower(flowerAttributes);
		setParametersToFlower(flowerElement.getChildNodes());
	}

	private void setAttributesToFlower(NamedNodeMap flowerAttributes) {
		if (flowerAttributes == null) {
			return;
		}
		Node node;
		for (int i = 0; i < flowerAttributes.getLength(); i++) {
			node = flowerAttributes.item(i);
			setFlowerAttribute(node.getNodeName(), node.getNodeValue());
		}
	}

	private void setParametersToFlower(NodeList nodes) throws BuilderException {
		Node node;
		
		FlowersTag tag;
		String value;
		for (int i = 0; i < nodes.getLength(); i++) {
			node = nodes.item(i);
			currentTag = FlowersTag.getFlowersTag(node.getNodeName());
			if (currentTag.isPresent()) {
				tag = currentTag.get();
				switch (tag) {
				case PARAMETERS:
					currentParameter = new VisualParameter();
					setParametersToFlower(node.getChildNodes());
					currentFlower.addVisualParamenter(currentParameter);
					break;
				case TIPS:
					currentTip = new GrowingTip();
					setParametersToFlower(node.getChildNodes());
					currentFlower.addGrowingTip(currentTip);
					break;
				default:
					value = node.getTextContent().trim();
					if (value != null) {
						setParameter(value, tag);
					}
				}
			}
			// <parameters>
			// <parameter> .... </parameter>
			// .....
			// </parameters>
			if (node.getChildNodes() != null) {
				setParametersToFlower(node.getChildNodes());
			}
		}
	}
}