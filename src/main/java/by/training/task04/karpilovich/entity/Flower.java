package by.training.task04.karpilovich.entity;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class Flower {

	private String name;
	private int quantity;
	private String origin;
	private Soil soil;
	private Set<VisualParameter> parameters;
	private Set<GrowingTip> tips;
	private Multiplying multiplying;
	private Calendar plantingDate;

	public Flower() {
		parameters = new HashSet<>();
		tips = new HashSet<>();
	}

	public Flower(String name, int quantity, String origin, Soil soil, Set<VisualParameter> parameters,
			Set<GrowingTip> tips, Multiplying multiplying, Calendar plantingDate) {
		this.name = name;
		this.origin = origin;
		this.quantity = quantity;
		this.soil = soil;
		this.parameters = parameters;
		this.tips = tips;
		this.multiplying = multiplying;
		this.plantingDate = plantingDate;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Soil getSoil() {
		return soil;
	}

	public void setSoil(Soil soil) {
		this.soil = soil;
	}

	public Set<VisualParameter> getParamenters() {
		return parameters;
	}

	public void setParameters(Set<VisualParameter> parameters) {
		this.parameters = parameters;
	}

	public Set<GrowingTip> getTips() {
		return tips;
	}

	public void setTips(Set<GrowingTip> tips) {
		this.tips = tips;
	}

	public Multiplying getMultiplying() {
		return multiplying;
	}

	public void setMultiplying(Multiplying multiplying) {
		this.multiplying = multiplying;
	}

	public Calendar getPlantingDate() {
		return plantingDate;
	}

	public void setPlantingDate(Calendar plantingDate) {
		this.plantingDate = plantingDate;
	}

	public void addGrowingTip(GrowingTip tip) {
		tips.add(tip);
	}

	public void addVisualParamenter(VisualParameter parameter) {
		parameters.add(parameter);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((multiplying == null) ? 0 : multiplying.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result + ((plantingDate == null) ? 0 : plantingDate.hashCode());
		result = prime * result + quantity;
		result = prime * result + ((soil == null) ? 0 : soil.hashCode());
		result = prime * result + ((tips == null) ? 0 : tips.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Flower other = (Flower) obj;
		if (multiplying != other.multiplying)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		if (plantingDate == null) {
			if (other.plantingDate != null)
				return false;
		} else if (!plantingDate.equals(other.plantingDate))
			return false;
		if (quantity != other.quantity)
			return false;
		if (soil != other.soil)
			return false;
		if (tips == null) {
			if (other.tips != null)
				return false;
		} else if (!tips.equals(other.tips))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [name=" + name + ", quantity=" + quantity + ", origin=" + origin
				+ ", soil=" + soil + ", paramenters=" + parameters + ", tips=" + tips + ", multiplying=" + multiplying
				+ ", plantingDate=" + plantingDate + "]";
	}

}
