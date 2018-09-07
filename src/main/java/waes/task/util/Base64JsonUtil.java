package waes.task.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import waes.task.vo.Diff;

public class Base64JsonUtil {
	
	public static <T> T toBean(String jsonStr,Class<T> c){
		
		T t = null;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			t = mapper.readValue(jsonStr, c);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("malform parameter format");
		}
		
		return t;
	}

	public static <T> T decodeToBean(String jsonStr,Class<T> c){
	
		T t = null;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			t = mapper.readValue(decode(jsonStr), c);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("malform parameter format");
		}
		
		return t;
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
