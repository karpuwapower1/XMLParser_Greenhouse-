package by.training.task04.karpilovich.builder.constant;

import java.util.Optional;

public enum FlowersTag {

	FLOWER("flower"), 
	SOIL("soil"), PARAMETERS("parameter"), TIPS("tip"), MULTIPLYING("multiplying"), PLANTING_DATE("planting"),
	TIP_NAME("tipName"), QUANTITY("quantity"), NECESSITY("necessity"),
	PARAMETER_NAME("parameterName"), SIZE("size"), COLOR("color");

	private String tagName;

	private FlowersTag(String tagName) {
		this.tagName = tagName;
	}

	public String getTagName() {
		return tagName;
	}
	
	public static Optional<FlowersTag> getFlowersTag(String tagName) {
		for (FlowersTag tag : FlowersTag.values()) {
			if (tag.getTagName().equals(tagName)) {
				return Optional.of(tag);
			}
		}
		return Optional.empty();
	}

}
