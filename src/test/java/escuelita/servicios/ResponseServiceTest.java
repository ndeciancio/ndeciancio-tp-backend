package escuelita.servicios;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.junit.Before;
import org.junit.Test;

import escuelita.domain.Airport;
import escuelita.domain.City;
import escuelita.domain.Country;
import escuelita.domain.Currency;
import escuelita.domain.Description;
import escuelita.exception.ClientRequestException;
import escuelita.exception.ServerResponseException;
import escuelita.response.AfirmativeResponse;
import escuelita.response.ErrorResponse;


public class ResponseServiceTest {
	
	private ResponseService responseService;
    private AirportService airportService;
    private CityService cityService;
    private CurrencyService currencyService;
    
    private Airport airport;
    private City city;
    private Currency currency;
    private Country country;

	
	@Before
    public void setUp(){
		
		responseService= new ResponseService();
        cityService = mock(CityService.class);
        responseService.setCityService(cityService);

        airportService = mock(AirportService.class);
        responseService.setAirportService(airportService);

        currencyService= mock(CurrencyService.class);
        responseService.setCurrencyService(currencyService);
        
        Description cityDesc = new Description();
		cityDesc.setEsUpper("Buenos Aires");
		this.city = new City(cityDesc,"BUE",20010);
        
        Description airportDesc = new Description();
        airportDesc.setEsUpper("Aeropuerto Buenos Aires Ministro Pistarini Ezeiza");
		this.airport = new Airport("EZE",982,airportDesc);
        
        Description countryDesc = new Description();
        countryDesc.setEsUpper("Argentina");
		this.country = new Country("AR",countryDesc);
		
		List<String> currencies = new ArrayList<String>();
		currencies.add("ARS");
		this.currency = new Currency(currencies,country);
		

    }

	@Test
	public void generateAfirmativeResponse() throws ClientProtocolException, IOException, ClientRequestException, ServerResponseException {
		when(airportService.generateAirport("EZE")).thenReturn(this.airport);
		when(cityService.generateCity(982,"ES")).thenReturn(this.city);
		when(currencyService.generateCurrency(20010)).thenReturn(this.currency);
		
		AfirmativeResponse afirmativeResponse = this.responseService.createAfirmativeResponse("EZE", "ES");
		
		assertEquals("EZE", afirmativeResponse.getAirportCode());
		assertEquals("Aeropuerto Buenos Aires Ministro Pistarini Ezeiza", afirmativeResponse.getAirportDescription());
		assertEquals("BUE",afirmativeResponse.getCityCode());
		assertEquals("Buenos Aires",afirmativeResponse.getCityDescription());
		assertEquals("AR",afirmativeResponse.getCountryCode());
		assertEquals("Argentina",afirmativeResponse.getCountryDescription());
		assertEquals("ARS",afirmativeResponse.getCurrencies().get(0));
	}
	
	@Test
	public void generateErrorResponse() throws ClientProtocolException, IOException, ClientRequestException, ServerResponseException{
		when(airportService.generateAirport("xxx")).thenThrow(new ClientRequestException("Bad request",400));
		
		ErrorResponse errorResponse = (ErrorResponse) this.responseService.generate("xxx", "ES");
		
		assertEquals(400,errorResponse.getCode());
		assertEquals("Bad request",errorResponse.getMessage());
	}
	

}
