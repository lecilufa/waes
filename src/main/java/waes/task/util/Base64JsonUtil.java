package waes.task.util;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Base64JsonUtil {
	
	public static <T> T decodeToBean(String jsonStr,Class<T> c) throws Exception{
	
		T t = null;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			t = mapper.readValue(decode(jsonStr), c);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return t;
	}
	
	public static String encodeToString(Object bean) throws Exception{
		
		String json = null;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(bean);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return encode(json);
	}
	
	public static String encode(String text){
		byte[] encodedText = Base64.encodeBase64(text.getBytes());
		return new String(encodedText);
	}
	
	public static String decode(String text){
		byte[] decodedText = Base64.decodeBase64(text.getBytes());
		return new String(decodedText);
	}
	

}
