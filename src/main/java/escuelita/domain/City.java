package escuelita.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class City {
	private Description descriptions;
	private String code;
	@JsonProperty("country_id")
	private int countryId;
	
	
	public City() {
		super();
	}

	public City(Description descriptions, String code, int countryId) {
		super();
		this.descriptions = descriptions;
		this.code = code;
		this.countryId = countryId;
	}
	
	public Description getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(Description descriptions) {
		this.descriptions = descriptions;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	
}
