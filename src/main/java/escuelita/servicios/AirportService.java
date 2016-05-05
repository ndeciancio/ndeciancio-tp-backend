package escuelita.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import escuelita.domain.Airport;
import escuelita.domain.AirportData;
import escuelita.exception.ClientRequestException;
import escuelita.servicios.interfaces.AirportServiceInterface;
import escuelita.servicios.json.JsonFactory;

public class AirportService implements AirportServiceInterface {
	private static final Logger LOGGER = LoggerFactory.getLogger(AirportService.class);

	private JsonFactory jasonFactory;
	private HttpService httpService;

	private static final String URL_AIRPORT = "http://geo.despegar.com/geo-services-web/service/webcontext/getAirportByCode/";

	@Override
	public Airport generateAirport(String iata) {
		String url = URL_AIRPORT + iata;
		LOGGER.info("Haciendo request al servicio: " + url);
		
		try {
			String json = this.httpService.getHttpResponse(url);
			LOGGER.info("Json a parsear para Aeropuertos: " + json);
			AirportData airportData = jasonFactory.mapObject(json, AirportData.class);
			return airportData.getData();

		} catch (ClientRequestException e) {
			LOGGER.error(e.getMessage() + ". Invalid request for IATA code " + iata, e);
			throw new ClientRequestException(e.getMessage() + ". Invalid request for IATA code " + iata,
					e.getStatusCode());
		}
	}

	public void setJasonFactory(JsonFactory jasonFactory) {
		this.jasonFactory = jasonFactory;
	}

	public void setHttpService(HttpService httpService) {
		this.httpService = httpService;
	}

}
