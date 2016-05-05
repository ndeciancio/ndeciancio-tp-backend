package escuelita.servicios;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.io.IOException;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;

import escuelita.domain.Airport;
import escuelita.domain.Description;
import escuelita.exception.ClientRequestException;
import escuelita.exception.ServerResponseException;
import escuelita.servicios.json.JsonFactory;

public class AirportServiceTest {

	private static final String AIRPORT_URL = "http://geo.despegar.com/geo-services-web/service/webcontext/getAirportByCode/EZE";
	private static final String AIRPORT_EZE_RESPONSE = "{\"data\":{\"oid\":192635,\"version\":0,\"iataCode\":\"EZE\",\"description\":{\"PT\":\"Aeroporto Buenos Aires Ministro Pistarini Ezeiza\",\"ES\":\"Aeropuerto Buenos Aires Ministro Pistarini Ezeiza\",\"EN\":\"Buenos Aires Ministro Pistarini Ezeiza Airport\"},\"code\":\"EZE\",\"cityOid\":982,\"latitude\":-34.81266,\"longitude\":-58.539761,\"type\":null,\"commercial\":true},\"meta\":{\"time\":\"4 ms\"}}";
	private static final String AIRPORT_BAD_URL = "http://geo.despegar.com/geo-services-web/service/webcontext/getAirportByCode/xxx";

	private JsonFactory jsonFactory;

	private CloseableHttpResponse closeableHttpResponse;
	private StatusLine fakeStatusLine;
	private CloseableHttpClient fakeCloseableHttpClient;
	
	private HttpService fakeHttpService;
	private HttpService httpService;

	private AirportService airportService;
	private AirportService mockAirporService;
	
	String esDescription;
	String enDescription;
	String ptDescription;

	private Airport airport;
	private Description descriptionAirport;

	private String iataAirport;
	private Integer cityOid;

	@Before
	public void setUp() {
		
		this.httpService = new HttpService();
		
		this.fakeStatusLine = mock(StatusLine.class);
		this.fakeCloseableHttpClient = mock(CloseableHttpClient.class);
		this.closeableHttpResponse = mock(CloseableHttpResponse.class);
		
		this.httpService.setHttpclient(this.fakeCloseableHttpClient);

		this.jsonFactory = new JsonFactory();

		this.fakeHttpService = mock(HttpService.class);
		
		this.airportService = new AirportService();
		this.mockAirporService = mock(AirportService.class);
		
		this.airportService.setHttpService(this.fakeHttpService);
		this.airportService.setJasonFactory(this.jsonFactory);

		this.descriptionAirport = new Description();
		this.ptDescription = "Aeroporto Buenos Aires Ministro Pistarini Ezeiza";
		this.esDescription = "Aeropuerto Buenos Aires Ministro Pistarini Ezeiza";
		this.enDescription = "Buenos Aires Ministro Pistarini Ezeiza Airport";
		this.descriptionAirport.setPtUpper(this.ptDescription);
		this.descriptionAirport.setEsUpper(this.esDescription);
		this.descriptionAirport.setEnUpper(this.enDescription);
		
		this.airport = new Airport("EZE", 982, this.descriptionAirport);

		this.iataAirport = "EZE";
		this.cityOid = 982;
		
		

	}
	
	@Test
	public void testAirport() {
		when(this.mockAirporService.generateAirport(eq("EZE"))).thenReturn(this.airport);
		Airport generatedAirport = this.mockAirporService.generateAirport("EZE");
		assertEquals(this.iataAirport, generatedAirport.getIataCode());
		assertEquals(this.cityOid.intValue(), generatedAirport.getCityOid());
		assertEquals(this.esDescription,generatedAirport.getDescription().getEs());
		assertEquals(this.enDescription,generatedAirport.getDescription().getEn());
		assertEquals(this.ptDescription,generatedAirport.getDescription().getPt());
	}

	@Test
	public void testBuildAirportFromJson() {
		when(fakeHttpService.getHttpResponse(eq(AIRPORT_URL))).thenReturn(AIRPORT_EZE_RESPONSE);
		Airport generatedAirport = this.airportService.generateAirport("EZE");
		assertEquals(iataAirport, generatedAirport.getIataCode());
		assertEquals(cityOid.intValue(), generatedAirport.getCityOid());
		assertEquals(this.esDescription,generatedAirport.getDescription().getEs());
		assertEquals(this.enDescription,generatedAirport.getDescription().getEn());
		assertEquals(this.ptDescription,generatedAirport.getDescription().getPt());
	}
	
	@Test(expected=ClientRequestException.class)
	public void mustRiseOnClientRequestException() throws IOException{
		when(this.fakeCloseableHttpClient.execute(any(HttpGet.class))).thenReturn(this.closeableHttpResponse);
		when(this.closeableHttpResponse.getStatusLine()).thenReturn(this.fakeStatusLine);
		when(this.fakeStatusLine.getStatusCode()).thenReturn(400);
		
		this.airportService.setHttpService(this.httpService);
		this.airportService.generateAirport(AIRPORT_BAD_URL);
	}
	
	@Test(expected=ServerResponseException.class)
	public void mustRiseOnServerResponseException() throws IOException{
		when(this.fakeCloseableHttpClient.execute(any(HttpGet.class))).thenReturn(this.closeableHttpResponse);
		when(this.closeableHttpResponse.getStatusLine()).thenReturn(this.fakeStatusLine);
		when(this.fakeStatusLine.getStatusCode()).thenReturn(500);
		
		this.airportService.setHttpService(this.httpService);
		this.airportService.generateAirport(AIRPORT_BAD_URL);
	}

}
