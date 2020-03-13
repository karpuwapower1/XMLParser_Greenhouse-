package by.training.task04.karpilovich.command.constant;

public enum RequestAttributeManager {
	
	FLOWERS("flowers"), ILLEGAL_FILE_MESSAGE_ATTRIBUTE("illegalFileMessage");
	
	private String attributeName;

	private RequestAttributeManager(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeName() {
		return attributeName;
	}	

}
