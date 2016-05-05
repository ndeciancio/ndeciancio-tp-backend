package escuelita.domain;

public class Airport {
	
	private String iataCode;
	private int cityOid;
	private Description description;
	
	public Airport() {
		super();
	}

	public Airport(String iataCode, int cityOid, Description description){
		this.iataCode = iataCode;
		this.cityOid = cityOid;
		this.description = description;
	}
	
	public String getIATACode(){
		return this.iataCode;
	}
	
	public int getCityOid(){
		return this.cityOid;
	}
	
	public Description getDescription(){
		return this.description;
	}

	public String getIataCode() {
		return iataCode;
	}

	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}

	public void setCityOid(int cityOid) {
		this.cityOid = cityOid;
	}

	public void setDescription(Description description) {
		this.description = description;
	}
}
