package by.training.task04.karpilovich.validator;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import by.training.task04.karpilovich.exception.ParserException;

public class XMLValidator {

	private static final String SCHEMA_FILE_NAME = "flowers.xsd";
	private static final String SCHEMA_LANGUAGE = "http://www.w3.org/2001/XMLSchema";
	private static final Logger LOGGER = LogManager.getLogger(XMLValidator.class);

	public void validate(File file) throws ParserException {
		try {
			SchemaFactory factory = SchemaFactory.newInstance(SCHEMA_LANGUAGE);
			URL url = getClass().getClassLoader().getResource(SCHEMA_FILE_NAME);
			LOGGER.debug(url == null);
			Schema schema = factory.newSchema(url);
			Validator validator = schema.newValidator();
			Source source = new StreamSource(file);
			validator.validate(source);
		} catch (SAXException | IOException e) {
			LOGGER.warn("Illegal file " + e);
			throw new ParserException("Illegal file ", e);
		}
	}
}
