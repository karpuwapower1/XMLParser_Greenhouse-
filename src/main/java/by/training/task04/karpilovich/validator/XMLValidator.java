package by.training.task04.karpilovich.validator;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class XMLValidator {
	
	public boolean validate() throws SAXException, IOException {
		SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		File fileSchema = new File(getClass().getClassLoader().getResource("flowers.xsd").getPath());
		Schema schema = factory.newSchema(fileSchema);
		Validator validator = schema.newValidator();
		Source source = new StreamSource(new File(getClass().getClassLoader().getResource("flowers.xml").getPath()));
		validator.validate(source);
		
		return true;
	}
	
	public static void main(String[] args) throws SAXException, IOException {
		XMLValidator v = new XMLValidator();
		v.validate();
		System.out.println("TRUE!!!");
	}
	
}
