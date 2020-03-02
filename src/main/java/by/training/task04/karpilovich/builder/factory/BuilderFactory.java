package by.training.task04.karpilovich.builder.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.task04.karpilovich.builder.AbstractBuilder;
import by.training.task04.karpilovich.builder.impl.DOMBuilder;
import by.training.task04.karpilovich.builder.impl.SAXBuilder;
import by.training.task04.karpilovich.builder.impl.StAXBuilder;
import by.training.task04.karpilovich.exception.ParserException;

public class BuilderFactory {

	private static final Logger LOGGER = LogManager.getLogger(BuilderFactory.class);

	private enum BuilderType {
		DOM, SAX, STAX;
	}

	private BuilderFactory() {
	}

	public static final class BuilderFactoryInstanceHolder {
		private static final BuilderFactory INSTANCE = new BuilderFactory();
	}

	public static BuilderFactory getInstance() {
		return BuilderFactoryInstanceHolder.INSTANCE;
	}

	public AbstractBuilder getBuilder(String type) throws ParserException {
		try {
			BuilderType factoryType = BuilderType.valueOf(type.toUpperCase());
			switch (factoryType) {
			case DOM:
				return DOMBuilder.getInstance();
			case SAX:
				return SAXBuilder.getInstance();
			case STAX:
				return StAXBuilder.getInstance();
			default:
				LOGGER.warn("Illegal type " + type);
				throw new ParserException("Illegal type " + type);
			}
		} catch (IllegalArgumentException e) {
			LOGGER.warn("Illegal type " + type);
			throw new ParserException(e);
		}
	}
}
