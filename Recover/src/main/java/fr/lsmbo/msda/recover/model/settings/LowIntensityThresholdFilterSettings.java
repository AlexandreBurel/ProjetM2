package fr.lsmbo.msda.recover.model.settings;

public class LowIntensityThresholdFilterSettings extends RecoverSetting {

	private Boolean selectedFilter;
	private String method;
	private Float emergence;
	private Integer minUPN;
	private Integer maxUPN;

	public LowIntensityThresholdFilterSettings() {
		this.initialize();
	}

	public LowIntensityThresholdFilterSettings(Boolean selectedFilter, String method, Float emergence, Integer minUPN,
			Integer maxUPN) {
		super();
		this.selectedFilter = selectedFilter;
		this.method = method;
		this.emergence = emergence;
		this.minUPN = minUPN;
		this.maxUPN = maxUPN;
		this.initialize();
	}

	private void initialize() {
		this.name = "Low intensity threshold";
		this.description = ""; // TODO write a proper description
	}

	public Boolean getSelectedFilter() {
		return selectedFilter;
	}

	public void setSelectedFilter(Boolean selectedFilter) {
		this.selectedFilter = selectedFilter;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Float getEmergence() {
		return emergence;
	}

	public void setEmergence(Float emergence) {
		this.emergence = emergence;
	}

	public Integer getMinUPN() {
		return minUPN;
	}

	public void setMinUPN(Integer minUPN) {
		this.minUPN = minUPN;
	}

	public Integer getMaxUPN() {
		return maxUPN;
	}

	public void setMaxUPN(Integer maxUPN) {
		this.maxUPN = maxUPN;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("selectedFilter: ").append(selectedFilter).append("\n").append("method: ").append(method)
				.append("\n").append("emergence: ").append(emergence).append("\n").append("minUPN: ").append(minUPN)
				.append("\n").append("maxUPN: ").append(maxUPN);
		return str.toString();

	}

}
