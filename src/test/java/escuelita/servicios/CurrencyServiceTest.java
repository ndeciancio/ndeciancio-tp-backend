package escuelita.servicios;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.eq;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;

import escuelita.domain.Country;
import escuelita.domain.Currency;
import escuelita.domain.Description;
import escuelita.exception.ClientRequestException;
import escuelita.exception.ServerResponseException;
import escuelita.servicios.json.JsonFactory;

public class CurrencyServiceTest {

	private static final String COUNTRY_URL = "http://backoffice.despegar.com/v3/catalogs/country/20010";
	private static final String COUNTRY_ARG_RESPONSE = "{\"visaRequirements\":{\"es\":\"NO necesitan visado para estad\u00EDas menores a 90 d\u00EDas, los ciudadanos de la Uni\u00F3n Europea, de los Estados Unidos de Am\u00E9rica y de los siguientes pa\u00EDses latinoamericanos: Bolivia, Brasil, Chile, Paraguay, Uruguay, Colombia, Costa Rica, Ecuador, El Salvador, Guatemala, Honduras, M\u00E9xico, Nicaragua, Panam\u00E1, Per\u00FA y Venezuela.\",\"pt\":\"Em caso de estadias que n\u00E3o superam 90 dias, n\u00E3o precisam de visto cidad\u00E3os provenientes de: Uni\u00E3o Europ\u00E9ia, Estados Unidos da Am\u00E9rica e dos seguintes pa\u00EDses latinoamericanos: Bol\u00EDvia, Brasil, Chile, Paraguai, Uruguai, Col\u00F4mbia, Costa Rica, Equador, El Salvador, Guatemala, Honduras, M\u00E9xico, Nicar\u00E1gua, Panam\u00E1, Per\u00FA e Venezuela.\"},\"currencies\":[\"ARS\"],\"languages\":[\"es\"],\"phoneCode\":\"54\",\"intAccessPhoneCode\":\"00\",\"id\":\"20010\",\"parent\":{\"oid\":20010,\"iata_code\":\"AR\",\"description\":{\"PT\":\"Argentina\",\"EN\":\"Argentina\",\"ES\":\"Argentina\"}}}";
	
	private CloseableHttpResponse closeableHttpResponse;
	private StatusLine fakeStatusLine;
	private CloseableHttpClient fakeCloseableHttpClient;
	
	private HttpService fakeHttpService;
	
	private CurrencyService fakeCurrencyService;
	private ArrayList<String> currencies;
	private Currency nullCurrencies;
	private Country country;
	private Description descriptionCountry;
	
	private Integer countryOid;
	private String language;
	private String iataCountry;
	
	private JsonFactory jsonFactory;
	
	private HttpService httpService;
	
	private CurrencyService currencyService;
	
	
	@Before
	public void setUp() throws Exception {
		
		this.httpService = new HttpService();
		
		this.fakeStatusLine = mock(StatusLine.class);
		this.fakeCloseableHttpClient = mock(CloseableHttpClient.class);
		this.closeableHttpResponse = mock(CloseableHttpResponse.class);
		
		this.httpService.setHttpclient(this.fakeCloseableHttpClient);
		
		this.fakeCurrencyService = mock(CurrencyService.class);
		
		this.descriptionCountry = new Description();
		this.descriptionCountry.setPt("Argentina");
		this.descriptionCountry.setEs("Argentina");
		this.descriptionCountry.setEn("Argentina");
		this.currencies = new ArrayList<String>();
		this.currencies.add("ARS");
		this.country = new Country("AR", descriptionCountry);
		this.nullCurrencies = new Currency(null,this.country);
		
		this.language = "ES";
		this.iataCountry = "AR";
		this.countryOid = 20010;

		this.jsonFactory = new JsonFactory();
		
		this.fakeHttpService = mock(HttpService.class);
		
		this.currencyService = new CurrencyService();
		this.currencyService.setHttpService(fakeHttpService);
		this.currencyService.setJasonFactory(jsonFactory);
		
	}
	
	@Test
	public void testSetCurrenciesOnExistingCountry() throws ClientProtocolException, IOException, UnsupportedOperationException, ClientRequestException, ServerResponseException{
		when(fakeCurrencyService.generateCurrency(countryOid)).thenReturn(nullCurrencies);
		Currency currency = fakeCurrencyService.generateCurrency(countryOid);
		assertEquals(iataCountry, currency.getCountry().getIata());
		assertEquals(this.descriptionCountry.getOfLanguage(this.language), currency.getCountry().getDescription().getOfLanguage(this.language));
		assertNull(currency.getCurrencies());
	}
	
	@Test
	public void testBuildCountry() throws ClientProtocolException, IOException, ClientRequestException, ServerResponseException{
		when(fakeHttpService.getHttpResponse(eq(COUNTRY_URL))).thenReturn(COUNTRY_ARG_RESPONSE);
		Currency generatedCurrency = this.currencyService.generateCurrency(20010);
		assertEquals(iataCountry, generatedCurrency.getCountry().getIata());
		assertEquals(this.descriptionCountry.getOfLanguage(this.language), generatedCurrency.getCountry().getDescription().getOfLanguage(this.language));
		assertEquals(currencies, generatedCurrency.getCurrencies());
	}
	
	@Test(expected=ClientRequestException.class)
	public void mustRiseOnClientRequestException() throws IOException{
		when(this.fakeCloseableHttpClient.execute(any(HttpGet.class))).thenReturn(this.closeableHttpResponse);
		when(this.closeableHttpResponse.getStatusLine()).thenReturn(this.fakeStatusLine);
		when(this.fakeStatusLine.getStatusCode()).thenReturn(400);
		
		this.currencyService.setHttpService(this.httpService);
		this.currencyService.generateCurrency(13243124);
	}
	
	@Test(expected=ServerResponseException.class)
	public void mustRiseOnServerResponseException() throws IOException{
		when(this.fakeCloseableHttpClient.execute(any(HttpGet.class))).thenReturn(this.closeableHttpResponse);
		when(this.closeableHttpResponse.getStatusLine()).thenReturn(this.fakeStatusLine);
		when(this.fakeStatusLine.getStatusCode()).thenReturn(500);
		
		this.currencyService.setHttpService(this.httpService);
		this.currencyService.generateCurrency(20010);
	}

}
