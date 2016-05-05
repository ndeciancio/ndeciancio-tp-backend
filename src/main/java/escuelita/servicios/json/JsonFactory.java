package escuelita.servicios.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import escuelita.exception.JsonMapperException;


public class JsonFactory {

	private ObjectMapper objectMapper;
	
	private static final String ERROR_CREATING_OBJECT = "Error creating an Object from Json";

	public JsonFactory() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public String toJson(Object object) {
		try {
			return this.objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new JsonMapperException("Error creating a json", e);
		}
	}
	
	public <T> T mapObject(String string, Class<T> class1){
			try {
				return (T) this.objectMapper.readValue(string, class1);
			} catch (JsonParseException e) {
				throw new JsonMapperException(ERROR_CREATING_OBJECT + e.toString(), e);
			} catch (JsonMappingException e) {
				throw new JsonMapperException(ERROR_CREATING_OBJECT + e.toString(), e);
			} catch (IOException e) {
				throw new JsonMapperException(ERROR_CREATING_OBJECT + e.toString(), e);
			} 
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

}
