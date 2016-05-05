package escuelita.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import escuelita.response.ErrorResponse;
import escuelita.response.Response;
import escuelita.servicios.ResponseService;

@Controller
public class AirportController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AirportController.class);

	private ResponseService responseGenerator;

	@RequestMapping(value = { "/airport-info/{iata}" }, method = RequestMethod.GET, produces = { "application/json" })
	@ResponseBody
	public Response getAirportInfo(@PathVariable String iata,
			@RequestParam(value = "language", required = false, defaultValue = "ES") String language) {
		Response toReturn = null;
		if (iata.length() != 3) {
			LOGGER.error("Usted a ingresado un codigo IATA invalido.");
			return new ErrorResponse(400, "Long IATA code error. IATA code must be 3 digits long. Invalid request for IATA code " + iata);
		} else {
			LOGGER.info("Generando respuesta para el aeropuerto " + iata);
			toReturn = responseGenerator.generate(iata, language.toUpperCase());
		}
		LOGGER.info(toReturn.toString());
		return toReturn;
	}

	@Required
	public void setResponseGenerator(ResponseService responseGenerator) {
		this.responseGenerator = responseGenerator;
	}

}
