package escuelita.servicios;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import escuelita.exception.ClientRequestException;
import escuelita.exception.ServerResponseException;
import escuelita.exception.ServiceException;

public class HttpService {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpService.class);

	private CloseableHttpClient httpclient = HttpClients.createDefault();
	private CloseableHttpResponse response;

	public String getHttpResponse(String url) {

		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("X-client", "escuelitaDespegar");
		try {
			this.response = httpclient.execute(httpGet);
		} catch (IOException e) {
			LOGGER.error("No se pudo conectar al servicio", e);
			throw new ServiceException("No se pudo conectar al servicio", e);
		}
		int statusCode = this.httpResponseGetStatusCode(this.response);

		if (statusCode >= 400 && statusCode <= 499 || statusCode == 4000) {
			throw new ClientRequestException(this.response.getStatusLine().getReasonPhrase(), statusCode);
		} else {
			if (statusCode >= 500) {
				throw new ServerResponseException(this.response.getStatusLine().getReasonPhrase()
						+ ". An error has ocurred while processing your request, try again later", statusCode);
			}
		}

		String json = this.httpResponseToString(this.response);

		try {
			LOGGER.info("Cerrando conexion con el servicio.");
			this.response.close();
		} catch (IOException e) {
			LOGGER.error("Error al intentar cerrar el servicio.", e);
		}

		return json;
	}

	public String httpResponseToString(CloseableHttpResponse httpResponse) {
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(httpResponse.getEntity().getContent(), writer, "UTF-8");
		} catch (IOException e) {
			LOGGER.error("Error al copiar entity content de " + httpResponse + " en " + writer, e);
			throw new ServiceException(e);
		}
		return writer.toString();
	}

	public int httpResponseGetStatusCode(CloseableHttpResponse httpResponse) {
		return httpResponse.getStatusLine().getStatusCode();
	}

	public void setHttpclient(CloseableHttpClient httpclient) {
		this.httpclient = httpclient;
	}
}
