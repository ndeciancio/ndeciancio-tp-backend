package escuelita.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import escuelita.domain.*;

public class AfirmativeResponse extends Response {

	@JsonProperty("airport_code")
	private String airportCode;
	@JsonProperty("airport_description")
	private String airportDescription;
	@JsonProperty("city_code")
	private String cityCode;
	@JsonProperty("city_description")
	private String cityDescription;
	@JsonProperty("country_code")
	private String countryCode;
	@JsonProperty("country_description")
	private String countryDescription;
	private List<String> currencies;

	public AfirmativeResponse() {
		super();
	}

	public AfirmativeResponse(Airport airport, City city, Currency currency, Country country, String language) {
		super();
		this.airportCode = airport.getIATACode();
		this.airportDescription = airport.getDescription().getOfLanguage(language);
		this.cityCode = city.getCode();
		this.cityDescription = city.getDescriptions().getOfLanguage(language);
		this.countryCode = country.getIata();
		this.countryDescription = country.getDescription().getOfLanguage(language);
		this.currencies = currency.getCurrencies();
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public void setAirportDescription(String airportDescription) {
		this.airportDescription = airportDescription;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public void setCityDescription(String cityDescription) {
		this.cityDescription = cityDescription;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public void setCountryDescription(String countryDescription) {
		this.countryDescription = countryDescription;
	}

	public void setCurrencies(List<String> currencies) {
		this.currencies = currencies;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public String getAirportDescription() {
		return airportDescription;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getCityDescription() {
		return cityDescription;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getCountryDescription() {
		return countryDescription;
	}

	public List<String> getCurrencies() {
		return currencies;
	}

	@Override
	public String toString() {
		return "AfirmativeResponse [airportCode=" + airportCode + ", airportDescription=" + airportDescription
				+ ", cityCode=" + cityCode + ", cityDescription=" + cityDescription + ", countryCode=" + countryCode
				+ ", countryDescription=" + countryDescription + ", currencies=" + currencies + "]";
	}

}
