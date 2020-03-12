package by.training.task04.karpilovich.builder;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.task04.karpilovich.builder.util.Constant;
import by.training.task04.karpilovich.builder.util.FlowerAttribute;
import by.training.task04.karpilovich.builder.util.FlowersTag;
import by.training.task04.karpilovich.entity.Flower;
import by.training.task04.karpilovich.entity.GrowingTip;
import by.training.task04.karpilovich.entity.Multiplying;
import by.training.task04.karpilovich.entity.Soil;
import by.training.task04.karpilovich.entity.VisualParameter;
import by.training.task04.karpilovich.exception.ParserException;
import by.training.task04.karpilovich.validator.XMLValidator;

public abstract class AbstractBuilder {

	protected Set<Flower> flowers;
	protected Flower currentFlower;
	protected GrowingTip currentTip;
	protected VisualParameter currentParameter;
	protected Optional<FlowersTag> currentTag;
	protected XMLValidator validator;
	
	private static final Logger LOGGER = LogManager.getLogger(AbstractBuilder.class);

	protected AbstractBuilder() {
		flowers = new HashSet<>();
		validator = new XMLValidator();
	}

	public Set<Flower> getFlowers() {
		return flowers;
	}

	public void reset() {
		flowers = new HashSet<>();
	}

	public abstract void buildSetFlowers(File file) throws ParserException;

	protected void setFlowerAttribute(String attributeName, String attributeValue) {
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
	
	protected void setParameter(String value, FlowersTag tag) throws ParserException {
		switch (tag) {
		case SOIL:
			currentFlower.setSoil(Soil.valueOf(value.toUpperCase()));
			break;
		case MULTIPLYING:
			currentFlower.setMultiplying(Multiplying.valueOf(value.toUpperCase()));
			break;
		case PLANTING_DATE:
			currentFlower.setPlantingDate(parseDate(value));
			break;
		case TIP_NAME:
			currentTip.setName(value);
			break;
		case QUANTITY:
		case NECESSITY:
			currentTip.setValue(value);
			break;
		case PARAMETER_NAME:
			currentParameter.setParameter(value);
			break;
		case SIZE:
		case COLOR:
			currentParameter.setValue(value);
			break;
		default:
			break;
		}
	}

	protected Calendar parseDate(String date) throws ParserException {
		Calendar calendar = new GregorianCalendar();
		try {
			calendar.setTime(Constant.FORMAT.parse(date));
			return calendar;
		} catch (ParseException e) {
			LOGGER.error("Error while parsing date " + date);
			throw new ParserException(e);
		}
	}
}
