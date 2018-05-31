package com.youlanw.common.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Type;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
/** 
 * 实现对象和json字符串之间的转换
 */  
public class JsonUtil {  	
	  private static Gson gson = new Gson();  
	
	   public static Gson getGsonInstance() {  
		    if(gson==null)gson= new Gson();
	        return gson;  
	    }  
	    /** 
	     * 对象转换成json字符串 
	     * @param obj  
	     * @return  
	     */  
	    public static String toJson(Object obj) {  
	        return getGsonInstance().toJson(obj);  
	    }  
	  
	    /** 
	     * json字符串转成对象 
	     * @param str   
	     * @param type 
	     * @return  
	     */  
	    public static <T> T fromJson(String str, Type type) {  
	        return getGsonInstance().fromJson(str, type);  
	    }  
	  
	    /** 
	     * json字符串转成对象 
	     * @param str   
	     * @param type  
	     * @return  
	     */  
	    public static <T> T fromJson(String str, Class<T> type) {  
	        return getGsonInstance().fromJson(str, type);  
	    }  
	    
		public static String bean2Json(Object obj) throws IOException {  
			if(obj==null)
				return null;
	        ObjectMapper mapper = new ObjectMapper();  
	        StringWriter sw = new StringWriter();  
	        JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);  
	        mapper.writeValue(gen, obj);  
	        gen.close();  
	        return sw.toString();  
	    }  
	  
	    public static <T> T json2Bean(String jsonStr, Class<T> objClass)  
	            throws JsonParseException, JsonMappingException, IOException {  
	    	if(jsonStr==null || "".equals(jsonStr))
	    		return null;
	        ObjectMapper mapper = new ObjectMapper(); 
	        //忽略无效属性
	        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	        return mapper.readValue(jsonStr, objClass);  
	    }  
	    
}
