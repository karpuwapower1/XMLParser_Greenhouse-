package by.training.task04.karpilovich.builder.constant;

public enum FlowerAttribute {
	
	NAME("name"), QUANTITY("quantity"), ORIGIN("origin");
	
	private String tagName;

	private FlowerAttribute(String tagName) {
		this.tagName = tagName;
	}

	public String getTagName() {
		return tagName;
	}


}
