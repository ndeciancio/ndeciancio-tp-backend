package escuelita.servicios;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import escuelita.response.AfirmativeResponse;
import escuelita.response.ErrorResponse;
import escuelita.servicios.json.JsonFactory;

@Ignore
public class BackEndIntegrationTest {
	
	private static final String LAX_URL_ES = "http://localhost:8080/escuelita/airport-info/LAX?language=ES";
	private static final String EZE_URL_PT = "http://localhost:8080/escuelita/airport-info/EZE?language=PT";
	private static final String EZE_URL_ES = "http://localhost:8080/escuelita/airport-info/EZE?language=ES";
	private static final String EZE_URL_ES_LOWER_CASE = "http://localhost:8080/escuelita/airport-info/EZE?language=es";
	private static final String EZE_URL_NO_LANG = "http://localhost:8080/escuelita/airport-info/EZE?language=";
	private static final String BAD_REQUEST = "http://localhost:8080/escuelita/airport-info/ASDSAD";

	private CloseableHttpClient httpclient = HttpClients.createDefault();
	private JsonFactory jasonFactory;
	
	@Before
	public void setUp() {
		this.jasonFactory = new JsonFactory();
	}

	@Test
	public void testLaxAirportInfoEs() throws IOException {
		HttpGet httpGet = new HttpGet(LAX_URL_ES);
		httpGet.setHeader("X-client", "escuelitaDespegar");
		CloseableHttpResponse httpResponse = null;
		httpResponse = httpclient.execute(httpGet);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		
		assertEquals(200,statusCode);
		
		AfirmativeResponse response = this.jasonFactory.mapObject(this.httpResponseToString(httpResponse), AfirmativeResponse.class);
		
		assertEquals(response.getAirportCode(),"LAX");
		assertEquals(response.getAirportDescription(),"Aeropuerto Internacional Los Angeles");
		assertEquals(response.getCityCode(),"LAX");
		assertEquals(response.getCityDescription(),"Los Ángeles");
		assertEquals(response.getCountryCode(),"US");
		assertEquals(response.getCountryDescription(),"Estados Unidos");
		assertEquals(response.getCurrencies().get(0),"USD");
		
		closeService(httpResponse);
	}
	
	@Test
	public void shouldReturnErrorResponseInvalidIata() throws IOException {
		HttpGet httpGet = new HttpGet(BAD_REQUEST);
		httpGet.setHeader("X-client", "escuelitaDespegar");
		CloseableHttpResponse httpResponse = null;
		httpResponse = httpclient.execute(httpGet);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		
		assertEquals(200,statusCode);
		
		ErrorResponse response = this.jasonFactory.mapObject(this.httpResponseToString(httpResponse), ErrorResponse.class);
		
		assertEquals(response.getCode(),400);
		assertEquals(response.getMessage(),"Long IATA code error. IATA code must be 3 digits long. Invalid request for IATA code ASDSAD");
		
		closeService(httpResponse);
	}
	
	@Test
	public void testEzeAirportInfoPt() throws IOException {
		HttpGet httpGet = new HttpGet(EZE_URL_PT);
		httpGet.setHeader("X-client", "escuelitaDespegar");
		CloseableHttpResponse httpResponse = null;
		httpResponse = httpclient.execute(httpGet);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		
		assertEquals(200,statusCode);
		
		AfirmativeResponse response = this.jasonFactory.mapObject(this.httpResponseToString(httpResponse), AfirmativeResponse.class);
		
		assertEquals(response.getAirportCode(),"EZE");
		assertEquals(response.getAirportDescription(),"Aeroporto Buenos Aires Ministro Pistarini Ezeiza");
		assertEquals(response.getCityCode(),"BUE");
		assertEquals(response.getCityDescription(),"Buenos Aires");
		assertEquals(response.getCountryCode(),"AR");
		assertEquals(response.getCountryDescription(),"Argentina");
		assertEquals(response.getCurrencies().get(0),"ARS");
		
		closeService(httpResponse);
	}

	@Test
	public void testEzeAirportInfoEs() throws IOException {
		HttpGet httpGet = new HttpGet(EZE_URL_ES);
		httpGet.setHeader("X-client", "escuelitaDespegar");
		CloseableHttpResponse httpResponse = null;
		httpResponse = httpclient.execute(httpGet);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		
		assertEquals(200,statusCode);
		
		AfirmativeResponse response = this.jasonFactory.mapObject(this.httpResponseToString(httpResponse), AfirmativeResponse.class);
		
		assertEquals(response.getAirportCode(),"EZE");
		assertEquals(response.getAirportDescription(),"Aeropuerto Buenos Aires Ministro Pistarini Ezeiza");
		assertEquals(response.getCityCode(),"BUE");
		assertEquals(response.getCityDescription(),"Buenos Aires");
		assertEquals(response.getCountryCode(),"AR");
		assertEquals(response.getCountryDescription(),"Argentina");
		assertEquals(response.getCurrencies().get(0),"ARS");
		
		closeService(httpResponse);
	}
	
	@Test
	public void testEzeAirportInfoWithNoLanguageGetsNullDescription() throws IOException {
		HttpGet httpGet = new HttpGet(EZE_URL_NO_LANG);
		httpGet.setHeader("X-client", "escuelitaDespegar");
		CloseableHttpResponse httpResponse = null;
		httpResponse = httpclient.execute(httpGet);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		
		assertEquals(200,statusCode);
		
		AfirmativeResponse response = this.jasonFactory.mapObject(this.httpResponseToString(httpResponse), AfirmativeResponse.class);
		
		assertEquals(response.getAirportCode(),"EZE");
		assertEquals(response.getAirportDescription(),"Aeropuerto Buenos Aires Ministro Pistarini Ezeiza");
		assertEquals(response.getCityCode(),"BUE");
		assertNull(response.getCityDescription());
		assertEquals(response.getCountryCode(),"AR");
		assertEquals(response.getCountryDescription(),"Argentina");
		assertEquals(response.getCurrencies().get(0),"ARS");
		
		closeService(httpResponse);
	}
	
	@Test
	public void testEzeAirportInfoWitchLangLowerCase() throws IOException {
		HttpGet httpGet = new HttpGet(EZE_URL_ES_LOWER_CASE);
		httpGet.setHeader("X-client", "escuelitaDespegar");
		CloseableHttpResponse httpResponse = null;
		httpResponse = httpclient.execute(httpGet);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		
		assertEquals(200,statusCode);
		
		AfirmativeResponse response = this.jasonFactory.mapObject(this.httpResponseToString(httpResponse), AfirmativeResponse.class);
		
		assertEquals(response.getAirportCode(),"EZE");
		assertEquals(response.getAirportDescription(),"Aeropuerto Buenos Aires Ministro Pistarini Ezeiza");
		assertEquals(response.getCityCode(),"BUE");
		assertEquals(response.getCityDescription(),"Buenos Aires");
		assertEquals(response.getCountryCode(),"AR");
		assertEquals(response.getCountryDescription(),"Argentina");
		assertEquals(response.getCurrencies().get(0),"ARS");
		
		closeService(httpResponse);
	}
	
	private String httpResponseToString(CloseableHttpResponse httpResponse)
			throws UnsupportedOperationException, IOException {
		StringWriter writer = new StringWriter();
		IOUtils.copy(httpResponse.getEntity().getContent(), writer, "UTF-8");
		return writer.toString();
	}
	
	private void closeService(CloseableHttpResponse service){
		try {
			service.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
