package by.training.task04.karpilovich.builder;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

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

public abstract class AbstractBuilder {

	protected Set<Flower> flowers;

	private static final Logger LOGGER = LogManager.getLogger(AbstractBuilder.class);

	protected AbstractBuilder() {
		flowers = new HashSet<>();
	}

	public Set<Flower> getFlowers() {
		return flowers;
	}

	public void reset() {
		flowers = new HashSet<>();
	}

	public abstract void buildSetFlowers(String fileName) throws BuilderException;

	protected void setFlowerAttribute(Flower flower, String attributeName, String attributeValue) {
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
	
	protected void setParameter(Flower flower, VisualParameter parameter, GrowingTip tip, String value,
			FlowersTag tag) throws BuilderException {
		switch (tag) {
		case SOIL:
			flower.setSoil(Soil.valueOf(value.toUpperCase()));
			break;
		case MULTIPLYING:
			flower.setMultiplying(Multiplying.valueOf(value.toUpperCase()));
			break;
		case PLANTING_DATE:
			flower.setPlantingDate(parseDate(value));
			break;
		case TIP_NAME:
			tip.setName(value);
			break;
		case QUANTITY:
		case NECESSITY:
			tip.setValue(value);
			break;
		case PARAMETER_NAME:
			parameter.setParameter(value);
			break;
		case SIZE:
		case COLOR:
			parameter.setValue(value);
			break;
		default:
			break;
		}
	}

	protected Calendar parseDate(String date) throws BuilderException {
		Calendar calendar = new GregorianCalendar();
		try {
			calendar.setTime(Constant.FORMAT.parse(date));
			return calendar;
		} catch (ParseException e) {
			LOGGER.error("Error while parsing date " + date);
			throw new BuilderException(e);
		}
	}
}
