package escuelita.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Country {
	
	@JsonProperty("iata_code")
	private String iata;
	private Description description;
	
	
	public Country() {
		super();
	}
	public Country(String iata, Description description) {
		super();
		this.iata = iata;
		this.description = description;
	}
	public String getIata() {
		return iata;
	}
	public void setIata(String iata) {
		this.iata = iata;
	}
	public Description getDescription() {
		return description;
	}
	public void setDescription(Description description) {
		this.description = description;
	}
	
}
