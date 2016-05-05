package escuelita.domain;

import java.util.List;

public class Currency {
	private List<String> currencies;
	private Country parent;
	
	
	public Currency() {
		super();
	}
	public Currency(List<String> currencies, Country parent) {
		super();
		this.currencies = currencies;
		this.parent = parent;
	}
	public List<String> getCurrencies() {
		return currencies;
	}
	public void setCurrencies(List<String> currencies) {
		this.currencies = currencies;
	}
	public Country getCountry() {
		return parent;
	}
	public void setParent(Country parent) {
		this.parent = parent;
	}
	
}
