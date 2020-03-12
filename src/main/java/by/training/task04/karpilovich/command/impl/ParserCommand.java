package by.training.task04.karpilovich.command.impl;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.task04.karpilovich.builder.AbstractBuilder;
import by.training.task04.karpilovich.command.Command;
import by.training.task04.karpilovich.entity.Flower;
import by.training.task04.karpilovich.exception.ParserException;
import by.training.task04.karpilovich.factory.BuilderFactory;
import by.training.task04.karpilovich.resource.PageManager;
import by.training.task04.karpilovich.resource.RequestAttributeManager;
import by.training.task04.karpilovich.resource.RequestParameterManager;

public class ParserCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(ParserCommand.class);

	@Override
	public PageManager execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String parserType = request.getParameter(RequestParameterManager.PARSER.getParameterName());
		File file = upload(request);
		BuilderFactory factory = BuilderFactory.getInstance();
		try {
			AbstractBuilder builder = factory.getBuilder(parserType);
			builder.reset();
			builder.buildSetFlowers(file);
			Set<Flower> flowers = builder.getFlowers();
			request.setAttribute(RequestAttributeManager.FLOWERS.getAttributeName(), flowers);
			file.delete();
			LOGGER.debug("file was deleted " + file.getName());
			return PageManager.RESULT;
		} catch (ParserException e) {
			request.setAttribute("illegalFileMessage", new String("File is not valid"));
			return PageManager.WELCOME;
		}

	}

	private File upload(HttpServletRequest request) throws IOException, ServletException {
		File file = File.createTempFile("temp", ".xml");
		LOGGER.debug("file was created " + file.getName());
		Part part = request.getPart(RequestParameterManager.FILE.getParameterName());
		part.write(file.getAbsolutePath());
		return file;
	}

}
