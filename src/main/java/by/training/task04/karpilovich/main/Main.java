package by.training.task04.karpilovich.main;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.task04.karpilovich.builder.DOMBuilder;
import by.training.task04.karpilovich.builder.SAXBuilder;
import by.training.task04.karpilovich.entity.Flower;
import by.training.task04.karpilovich.exception.BuilderException;

public class Main {

private static final Logger LOGGER = LogManager.getLogger(DOMBuilder.class);
	
	public static void main(String[] args) throws BuilderException {
		SAXBuilder builder = SAXBuilder.getInstance();
		builder.buildSetFlowers("flowers.xml");
		Set<Flower> flowers = builder.getFlowers();
		for (Flower flower : flowers) {
			LOGGER.debug(flower.toString());
		}
		
	}
}
