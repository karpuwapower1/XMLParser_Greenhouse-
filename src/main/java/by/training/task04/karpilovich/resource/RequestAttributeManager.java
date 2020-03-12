package by.training.task04.karpilovich.resource;

public enum RequestAttributeManager {
	
	FLOWERS("flowers");
	
	private String attributeName;

	private RequestAttributeManager(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeName() {
		return attributeName;
	}	

}
