package escuelita.servicios;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.eq;

import java.io.IOException;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;

import escuelita.domain.City;
import escuelita.domain.Description;
import escuelita.exception.ClientRequestException;
import escuelita.exception.ServerResponseException;
import escuelita.servicios.json.JsonFactory;

public class CityServiceTest {

	private static final String ALTERNATIVE_CITY_URL_WITHOUT_LANGUAGE = "http://api.despegar.it/v3/cities/982";
	private static final String ALTERNATIVE_CITY_URL_WITH_LANGUAGE = "?product=FLIGHTS&language=ES";
	private static final String ALTERNATIVE_CITY_BS_AS_RESPONSE = "{\"id\":\"982\",\"descriptions\":{\"es\":\"Buenos Aires\"},\"last_update_date\":\"2015-06-03T22:30:35Z\",\"code\":\"BUE\",\"location\":{\"type\":\"POLYGON\",\"latitude\":-34.60546023079388,\"longitude\":-58.381991406249995,\"coordinates\":[{\"type\":\"POINT\",\"latitude\":-34.697025716452536,\"longitude\":-58.530006408691406},{\"type\":\"POINT\",\"latitude\":-34.697025716452536,\"longitude\":-58.30615997314453},{\"type\":\"POINT\",\"latitude\":-34.525792894271376,\"longitude\":-58.30615997314453},{\"type\":\"POINT\",\"latitude\":-34.525792894271376,\"longitude\":-58.530006408691406},{\"type\":\"POINT\",\"latitude\":-34.697025716452536,\"longitude\":-58.530006408691406}]},\"pictures\":[],\"timezone\":\"America/Argentina/Buenos_Aires\",\"timezone_offset\":-3.0,\"administrative_division_id\":\"31655\",\"country_id\":\"20010\"}";
	private static final String ALTERNATIVE_CITY_BS_AS_RESPONSE_WITHOUT_LANGUAGE = "{\"id\":\"982\",\"descriptions\":{},\"last_update_date\":\"2015-06-03T22:30:35Z\",\"code\":\"BUE\",\"location\":{\"type\":\"POLYGON\",\"latitude\":-34.60546023079388,\"longitude\":-58.381991406249995,\"coordinates\":[{\"type\":\"POINT\",\"latitude\":-34.697025716452536,\"longitude\":-58.530006408691406},{\"type\":\"POINT\",\"latitude\":-34.697025716452536,\"longitude\":-58.30615997314453},{\"type\":\"POINT\",\"latitude\":-34.525792894271376,\"longitude\":-58.30615997314453},{\"type\":\"POINT\",\"latitude\":-34.525792894271376,\"longitude\":-58.530006408691406},{\"type\":\"POINT\",\"latitude\":-34.697025716452536,\"longitude\":-58.530006408691406}]},\"pictures\":[],\"timezone\":\"America/Argentina/Buenos_Aires\",\"timezone_offset\":-3.0,\"administrative_division_id\":\"31655\",\"country_id\":\"20010\"}";

	private CloseableHttpResponse closeableHttpResponse;
	private StatusLine fakeStatusLine;
	private CloseableHttpClient fakeCloseableHttpClient;
	
	private HttpService fakeHttpService;
	
	private String language;
	private String iataCity;
	private Integer cityOid;
	private int countryOid;
	
	private JsonFactory jsonFactory;
	
	private HttpService httpService;
	
	private CityService cityService;
	
	@Before
	public void setUp() {
		
		this.httpService = new HttpService();
		
		this.fakeStatusLine = mock(StatusLine.class);
		this.fakeCloseableHttpClient = mock(CloseableHttpClient.class);
		this.closeableHttpResponse = mock(CloseableHttpResponse.class);
		
		this.httpService.setHttpclient(this.fakeCloseableHttpClient);
		
		Description cityDesc = new Description();
		cityDesc.setEsUpper("Buenos Aires");
		
		this.language = "ES";
		this.iataCity = "BUE";
		this.cityOid = 982;
		this.countryOid = 20010;
		
		this.jsonFactory = new JsonFactory();
		
		this.fakeHttpService = mock(HttpService.class);
		
		this.cityService = new CityService();
		this.cityService.setHttpService(this.fakeHttpService);
		this.cityService.setJasonFactory(this.jsonFactory);
	}

	@Test
	public void testCreateCity() throws ClientProtocolException, IOException, ClientRequestException, ServerResponseException {
		when(fakeHttpService.getHttpResponse(eq(ALTERNATIVE_CITY_URL_WITHOUT_LANGUAGE + ALTERNATIVE_CITY_URL_WITH_LANGUAGE))).thenReturn(ALTERNATIVE_CITY_BS_AS_RESPONSE);
		City generatedCity = cityService.generateCity(982,this.language);
		assertEquals(iataCity, generatedCity.getCode());
		assertEquals("Buenos Aires",generatedCity.getDescriptions().getOfLanguage(this.language));
		assertEquals(countryOid, generatedCity.getCountryId());
	}
	
	@Test
	public void shouldNotHaveDescriptionOfUnspecifiedLanguage() throws ClientProtocolException, IOException, ClientRequestException, ServerResponseException{
		when(fakeHttpService.getHttpResponse(eq(ALTERNATIVE_CITY_URL_WITHOUT_LANGUAGE + ALTERNATIVE_CITY_URL_WITH_LANGUAGE))).thenReturn(ALTERNATIVE_CITY_BS_AS_RESPONSE);
		City generatedCity = cityService.generateCity(982,this.language);
		assertNull(generatedCity.getDescriptions().getOfLanguage("EN"));
	}
	
	@Test
	public void testBuildCityWithNoDescription() throws ClientProtocolException, IOException, ClientRequestException, ServerResponseException{
		when(fakeHttpService.getHttpResponse(eq(ALTERNATIVE_CITY_URL_WITHOUT_LANGUAGE))).thenReturn(ALTERNATIVE_CITY_BS_AS_RESPONSE_WITHOUT_LANGUAGE);
		City generatedCity = cityService.generateCity(this.cityOid,"");
		assertEquals(this.iataCity, generatedCity.getCode());
		assertNull(generatedCity.getDescriptions().getOfLanguage(language));
		assertEquals(this.countryOid, generatedCity.getCountryId());
	}
	
	@Test(expected=ClientRequestException.class)
	public void mustRiseOnClientRequestException() throws IOException{
		when(this.fakeCloseableHttpClient.execute(any(HttpGet.class))).thenReturn(this.closeableHttpResponse);
		when(this.closeableHttpResponse.getStatusLine()).thenReturn(this.fakeStatusLine);
		when(this.fakeStatusLine.getStatusCode()).thenReturn(400);
		
		this.cityService.setHttpService(this.httpService);
		this.cityService.generateCity(13243124,language);
	}
	
	@Test(expected=ServerResponseException.class)
	public void mustRiseOnServerResponseException() throws IOException{
		when(this.fakeCloseableHttpClient.execute(any(HttpGet.class))).thenReturn(this.closeableHttpResponse);
		when(this.closeableHttpResponse.getStatusLine()).thenReturn(this.fakeStatusLine);
		when(this.fakeStatusLine.getStatusCode()).thenReturn(500);
		
		this.cityService.setHttpService(this.httpService);
		this.cityService.generateCity(982,this.language);
	}

}
