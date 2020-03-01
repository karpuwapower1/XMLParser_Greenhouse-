package by.training.task04.karpilovich.parser;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import by.training.task04.karpilovich.builder.SAXBuilder;
import by.training.task04.karpilovich.builder.constant.Constant;
import by.training.task04.karpilovich.entity.Flower;
import by.training.task04.karpilovich.entity.GrowingTip;
import by.training.task04.karpilovich.entity.Multiplying;
import by.training.task04.karpilovich.entity.Soil;
import by.training.task04.karpilovich.entity.VisualParameter;
import by.training.task04.karpilovich.exception.BuilderException;

public class TestSAXBuilder {
	
	private Set<Flower> initFlower() throws BuilderException {
		Set<Flower> flowers = new HashSet<>();
		Set<VisualParameter> param = new HashSet<>();
		param.add(new VisualParameter("leaf", "green"));
		param.add(new VisualParameter("steam", "green"));
		param.add(new VisualParameter("flower", "red"));
		param.add(new VisualParameter("size", "20"));

		Set<GrowingTip> tips = new HashSet<>();
		tips.add(new GrowingTip("temperature", "15"));
		tips.add(new GrowingTip("light", "true"));	
		Calendar date = new GregorianCalendar();
		try {
		
		date.setTime(Constant.FORMAT.parse("2020-01-01"));
		} catch (ParseException e) {
			throw new BuilderException(e);
		}
		Flower flower = new Flower("rose", 0, "the netherlands", Soil.UNDERGROUND, param, tips, Multiplying.ROOT, date);
		flowers.add(flower);
		return flowers;
	}

	@Test
	public void testBuildSetFlower() throws BuilderException {
		SAXBuilder builder = SAXBuilder.getInstance();
		builder.buildSetFlowers(getClass().getClassLoader().getResource("flowers.xml").toString());
		Set<Flower> actual = builder.getFlowers();
		Set<Flower> expected = initFlower();
		Assert.assertEquals(expected, actual);
	}

}
