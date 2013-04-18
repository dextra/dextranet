package br.com.dextra.dextranet.rest.config;

import java.text.SimpleDateFormat;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import br.com.dextra.dextranet.utils.TimeMachine;

@Provider
@Produces(Application.JSON_UTF8)
public class JacksonConfig implements ContextResolver<ObjectMapper> {

	private final ObjectMapper objectMapper;

	public JacksonConfig() {
		objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat(TimeMachine.DATA_HORA));
		objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	@Override
	public ObjectMapper getContext(Class<?> objectType) {
		return objectMapper;
	}

}
