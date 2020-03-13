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
import by.training.task04.karpilovich.command.constant.PageManager;
import by.training.task04.karpilovich.command.constant.RequestAttributeManager;
import by.training.task04.karpilovich.command.constant.RequestParameterManager;
import by.training.task04.karpilovich.entity.Flower;
import by.training.task04.karpilovich.exception.ParserException;
import by.training.task04.karpilovich.factory.BuilderFactory;

public class ParserCommand implements Command {

	private static final String ILLEGAL_FILE_MESSAGE = "File is not valid";
	private static final Logger LOGGER = LogManager.getLogger(ParserCommand.class);
	
	@Override
	public PageManager execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String parserType = request.getParameter(RequestParameterManager.PARSER.getParameterName());
		BuilderFactory factory = BuilderFactory.getInstance();
		File file = upload(request);
		try {		
			AbstractBuilder builder = factory.getBuilder(parserType);
			builder.reset();
			builder.buildSetFlowers(file);
			Set<Flower> flowers = builder.getFlowers();
			request.setAttribute(RequestAttributeManager.FLOWERS.getAttributeName(), flowers);
			return PageManager.RESULT;
		} catch (ParserException e) {
			request.setAttribute( RequestAttributeManager.ILLEGAL_FILE_MESSAGE_ATTRIBUTE.getAttributeName(), ILLEGAL_FILE_MESSAGE);
			return PageManager.WELCOME;
		} finally {
			if (file.exists()) {
				file.delete();
				LOGGER.debug("file was deleted " + file.getName());
			}
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
