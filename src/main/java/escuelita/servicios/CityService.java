package escuelita.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import escuelita.domain.City;
import escuelita.exception.ClientRequestException;
import escuelita.servicios.interfaces.CityServiceInterface;
import escuelita.servicios.json.JsonFactory;

public class CityService implements CityServiceInterface{

	private static final Logger LOGGER = LoggerFactory.getLogger(CityService.class);

	private JsonFactory jasonFactory;
	private HttpService httpService;

	private static final String URL_CITY = "http://api.despegar.it/v3/cities/";
	private static final String LANG_PATH = "?product=FLIGHTS&language=";

	@Override
	public City generateCity(int cityOid,String language){
		String languagePath = "";
		if (!language.isEmpty()){
			languagePath = LANG_PATH + language;
		}
		Integer oid = cityOid;
		String url = URL_CITY + oid.toString() + languagePath;
		LOGGER.info("Haciendo request al servicio: " + url);
		
		try {
			String json = this.httpService.getHttpResponse(url);
			LOGGER.info("Json a parsear para Ciudades: " + json);
			return jasonFactory.mapObject(json, City.class);

		} catch (ClientRequestException e) {
			LOGGER.error(e.getMessage() + ". No matching results for City Oid " + cityOid, e);
			throw new ClientRequestException(e.getMessage() + ". No matching results for City Oid " + cityOid,
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
