package escuelita.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import escuelita.domain.Currency;
import escuelita.exception.ClientRequestException;
import escuelita.servicios.interfaces.CurrencyServiceInterface;
import escuelita.servicios.json.JsonFactory;

public class CurrencyService implements CurrencyServiceInterface{
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyService.class);

	private JsonFactory jasonFactory;
	private HttpService httpService;

	private static final String URL_CURRENCY = "http://backoffice.despegar.com/v3/catalogs/country/";

	@Override
	public Currency generateCurrency(int countryId) {
		Integer oid = countryId;
		String url = URL_CURRENCY + oid.toString();
		LOGGER.info("Haciendo request al servicio: " + url);

		try {
			String json = this.httpService.getHttpResponse(url);
			LOGGER.info("Json a parsear para Monedas: " + json);
			return jasonFactory.mapObject(json, Currency.class);

		} catch (ClientRequestException e) {
			LOGGER.error(e.getMessage() + ". No matching results for Country Id " + countryId, e);
			throw new ClientRequestException(e.getMessage() + ". No matching results for Country Id " + countryId,
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
