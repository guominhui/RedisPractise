package com.utils.redis;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pojo.Person;



public class JsonUtil {

	static Logger log=LoggerFactory.getLogger(JsonUtil.class);

	private static ObjectMapper objectMapper=new ObjectMapper();

	static{
		//����������ֶ�ȫ������
		objectMapper.setSerializationInclusion(Inclusion.ALWAYS);
		//ȡ��Ĭ��ת��timestamps��ʽ
		objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);
		//���Կ�beanתjson�Ĵ���
		objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);
		//���е����ڶ�ͳһΪһ�µ���ʽ
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		//����   ��json�ַ��д��ڣ�������java�����в����ڶ�Ӧ���Ե��������ֹ����
		objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_NULL_FOR_PRIMITIVES,false);
	}


	public static <T> String obj2String(T obj){

		if(obj==null){
			return null;
		}

		try {
			return obj instanceof String ?(String)obj:objectMapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			log.warn("JsonGenerationException",e);
			e.printStackTrace();
		} catch (JsonMappingException e) {
			log.warn("JsonMappingException",e);
			e.printStackTrace();
		} catch (IOException e) {
			log.warn("IOException",e);
			e.printStackTrace();
		}
		return null;
	}

	public static <T> String obj2StringPretty(T obj){

		if(obj==null){
			return null;
		}

		try {
			return obj instanceof String ?(String)obj:objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			log.warn("JsonGenerationException",e);
			e.printStackTrace();
		} catch (JsonMappingException e) {
			log.warn("JsonMappingException",e);
			e.printStackTrace();
		} catch (IOException e) {
			log.warn("IOException",e);
			e.printStackTrace();
		}
		return null;
	}


	public static <T> T string2Obj(String str,Class<T> clazz){

		if(StringUtils.isEmpty(str)||clazz==null){
			return null;
		}
		try {
			return clazz.equals(String.class)?(T)str:objectMapper.readValue(str,clazz);
		} catch (JsonParseException e) {
			log.warn("JsonParseException");
			e.printStackTrace();
		} catch (JsonMappingException e) {
			log.warn("JsonMappingException");
			e.printStackTrace();
		} catch (IOException e) {
			log.warn("IOException");
			e.printStackTrace();
		}
		return null;
	}


	public static <T> T string2Obj(String str,TypeReference<T> typeReference){
		
		if(StringUtils.isEmpty(str)||typeReference==null){
			return null;
		}
		
		try {
			return typeReference.getType().equals(String.class)?(T)str:(T)objectMapper.readValue(str, typeReference);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T string2Obj(String str,Class<?> collectionClass,Class<?>... elementClass){
		
		JavaType javaType=objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClass);
		
		try {
			return objectMapper.readValue(str, javaType);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		Person p=new Person();
		p.setUserId(111);
		p.setPassword("hello");
		
		Person p1=new Person();
		p1.setUserId(222);
		p1.setPassword("sss");
		
		String user1Json=JsonUtil.obj2String(p);
		String user1JsonPretty=JsonUtil.obj2StringPretty(p);
		
		System.out.println(user1Json);
		log.info("123");
		System.out.println("================");
		System.out.println(user1JsonPretty);
		
		Person user=JsonUtil.string2Obj(user1Json, Person.class);
		System.out.println("-----------");
		
		
		List<Person> list=new ArrayList<Person>();
		list.add(p);
		list.add(p1);
		String userListStr=JsonUtil.obj2StringPretty(list);
		System.out.println(userListStr);
		
		System.out.println("##########");
		List<Person> listResult=JsonUtil.string2Obj(userListStr, new TypeReference<List<Person>>() {
			
		});
		List<Person> list2=JsonUtil.string2Obj(userListStr, List.class, Person.class);
		System.out.println("end");
		
	}
}
