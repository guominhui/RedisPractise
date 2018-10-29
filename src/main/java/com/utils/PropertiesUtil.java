package com.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class PropertiesUtil {

	private static Properties props;
	
	static{
		String fileName= "config.properties";
		props=new Properties();
		try {
			props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String getProperty(String key){
		String value=props.getProperty(key.trim());
		if(StringUtils.isEmpty(value)){
			return null;
		}
		return value.trim();
	}
	
	public static String getProperty(String key,String defaultValue){
		String value=props.getProperty(key.trim());
		if(StringUtils.isEmpty(value)){
			return defaultValue;
		}
		return value.trim();
	}
	
	
}
