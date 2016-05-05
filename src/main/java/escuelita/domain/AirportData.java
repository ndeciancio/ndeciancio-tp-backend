package escuelita.domain;

public class AirportData {
	
	Airport data;

	public AirportData() {
		super();
	}

	public Airport getData() {
		return data;
	}

	public void setData(Airport data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "AirportData [data=" + data + "]";
	}
}
