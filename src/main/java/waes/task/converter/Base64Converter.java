package waes.task.converter;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import waes.task.model.Text;

public class Base64Converter implements Converter<String, Text> {

	@Override
	public Text convert(String base64Text) {
		
		String json = base64Text;
		Text text = null;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			text = mapper.readValue(json, Text.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return text;
	}

}
