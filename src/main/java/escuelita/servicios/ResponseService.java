package escuelita.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import escuelita.domain.*;
import escuelita.exception.ClientRequestException;
import escuelita.exception.ServerResponseException;
import escuelita.response.AfirmativeResponse;
import escuelita.response.ErrorResponse;
import escuelita.response.Response;

public class ResponseService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseService.class);


	private AirportService airportService;
	private CityService cityService;
	private CurrencyService currencyService;

	public Response generate(String iata, String language) {

		try {
			return this.createAfirmativeResponse(iata,language);
		} catch (ClientRequestException e) {
			LOGGER.error(e.getMessage(),e);
			return this.createErrorResponse(e.getStatusCode(),e);
		} catch (ServerResponseException e) {
			LOGGER.error(e.getMessage(),e);
			return this.createErrorResponse(e.getStatusCode(),e);
		}
	}

	public AfirmativeResponse createAfirmativeResponse(String iata, String language) {
		Airport airport = this.airportService.generateAirport(iata);
		City city = this.cityService.generateCity(airport.getCityOid(),language);
		Currency currency = this.currencyService.generateCurrency(city.getCountryId());
		Country country = currency.getCountry();
		LOGGER.info("Generando respuesta afirmativa.");
		return new AfirmativeResponse(airport, city, currency, country, language);
	}

	public ErrorResponse createErrorResponse(int statusCode, Exception e) {
		return new ErrorResponse(statusCode, e.getMessage());
	}

	@Required
	public void setAirportService(AirportService airportService) {
		this.airportService = airportService;
	}

	@Required
	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	@Required
	public void setCurrencyService(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

}
