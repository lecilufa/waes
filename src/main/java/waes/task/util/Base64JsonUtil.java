package waes.task.util;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Base64JsonUtil {
	
	/**
	 * decode json String that is encoded by Base64,transfering it to target java bean given by target class @param c
	 * 
	 * @param jsonStr	json String that is encoded by Base64
	 * @param c			Target class this method transfer to
	 * @return
	 * 		Instance of Target class this method transfer to
	 * @throws Exception
	 */
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
	
	/**
	 * transfer a javabean to json format that is encoded by base64
	 * 
	 * @param bean	javabean to transfer
	 * @return
	 * 		json string that is encoded by base64
	 * @throws Exception
	 */
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
	
	/**
	 * encode a plain text by base64
	 * 
	 * @param text	plain text
	 * @return
	 * 		base64 string
	 */
	public static String encode(String text){
		byte[] encodedText = Base64.encodeBase64(text.getBytes());
		return new String(encodedText);
	}
	
	/**
	 * decode base64 string to a plain text
	 * 
	 * @param text	base64 string
	 * @return
	 * 		plain text
	 */
	public static String decode(String text){
		byte[] decodedText = Base64.decodeBase64(text.getBytes());
		return new String(decodedText);
	}
	

}
