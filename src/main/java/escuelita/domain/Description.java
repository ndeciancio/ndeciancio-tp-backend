package escuelita.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Description {
	private String pt;
	@JsonProperty("PT")
	private String ptUpper;
	private String es;
	@JsonProperty("ES")
	private String esUpper;
	private String en;
	@JsonProperty("EN")
	private String enUpper;
	
	
	public Description() {
		super();
	}
	public Description(String pt, String ptUpper, String es, String esUpper, String en, String enUpper) {
		super();
		this.pt = pt;
		this.ptUpper = ptUpper;
		this.es = es;
		this.esUpper = esUpper;
		this.en = en;
		this.enUpper = enUpper;
	}
	public String getPt() {
		if( this.pt == null){
			return this.ptUpper;
		}
		return pt;
	}
	public void setPt(String pt) {
		this.pt = pt;
	}
	
	public void setPtUpper(String ptUpper) {
		this.ptUpper = ptUpper;
	}
	public String getEs() {
		if( this.es == null ){
			return this.esUpper;
		}
		return es;
	}
	public void setEs(String es) {
		this.es = es;
	}
	
	public void setEsUpper(String esUpper) {
		this.esUpper = esUpper;
	}
	public String getEn() {
		if( this.en == null ){
			return this.enUpper;
		}
		return en;
	}
	public void setEn(String en) {
		this.en = en;
	}
	
	public void setEnUpper(String enUpper) {
		this.enUpper = enUpper;
	}
	
	public String getOfLanguage(String language){
		if("ES".contains(language)){
			return this.getEs();
		}
		if("EN".contains(language)){
			return this.getEn();
		}
		if("PT".contains(language)){
			return this.getPt();
		}
		return "";
	}
	
	
}
