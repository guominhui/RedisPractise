package com.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class ConfigUtil {
	public static Properties prop=new Properties(); 
	public static Map<Integer, Integer> bonus_class_map;
	public static float[] bonus_class_ratio;
	public static int[] bonusClassKey;
	public static int[] bonusPrice;
	//总代账号关闭三级分销
	public static int[] threeLevelSellInstitutionIds;
	public static int three_level_institution_close;
	public static String[] priority_open_list;

	/** 
	 * 读取配置文件 
	 * LiChaofei 
	 * 2013-1-31 上午9:10:07 
	 * @return 
	 */  
	@PostConstruct
	private Properties loadProperty() {  
		prop=new Properties();  
		// try {  
		//InputStream is=this.getClass().getResourceAsStream("/ipRemote.properties");
		// OutputStream fos=new FileOutputStream(PathUtil.relativePath+"\\ipRemote.properties");  
		InputStream is=null;	
		try {
			is=this.getClass().getResourceAsStream("/config.properties");
			System.setProperty("config.properties", "config.properties");
			prop.load(is);
			handleBonusClassArray();
			//
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally{
			try {
				if (is!=null) {
					is.close();  	
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		//           
		//        } catch (IOException e) {  
		//            e.printStackTrace();  
		//        }  
		return prop; 
	}  
	/* *//** 
	 * 保存修改后的配置文件 
	 * LiChaofei 
	 * 2013-1-31 上午9:09:51 
	 * @throws IOException  
	 *//*  
    public void saveProperty(String ip,String port) throws IOException{  
            //OutputStream fos=new FileOutputStream(this.getClass().getResource("/ipRemote.properties").getPath());  
            OutputStream fos=new FileOutputStream(PathUtil.relativePath+"\\ipRemote.properties");  
            prop.setProperty("ip",ip);  
            DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            prop.setProperty("port",port);  
            prop.store(fos, df.format(new Date()));  

    }  */
	public static String getIp_info(){
		return prop.getProperty("server_ip")+":"+prop.getProperty("server_port");
	}
	public static String getSignature(){
		return prop.getProperty("server_signature");
	}
	public static String getBaseUri(){
		return prop.getProperty("server_pc_dateUri");
	}
	/* public static void main(String[] args) {
    	//URL absoluteUrl=null;
    //	String url=IpInfoUtil.class.getResource("/").getPath();
    //	String dir = System.getProperty("user.dir");// 当前启动路径
    	//URL parseUrl=null;
//    	try { 
//    	 absoluteUrl = new URL(url);   
//         
//			// parseUrl = new URL(absoluteUrl ,"../../" );
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
    	//System.out.println(url.toString());
    	//String aa=new File(dir).getParent();

    	for(File file :files){
    		System.out.println(file.getPath());// 当前启动路径);	
    	}

	//System.out.println(aa+\webapps\);
    }*/
	/* *//**
	 * 
	 * 加载配置文件   
	 * @param path
	 * @return
	 * @throws IOException
	 */
	/*private static Properties loadProperties(String path){
		Properties p=null;
		InputStream in=null;
		try {
			in = new BufferedInputStream(new FileInputStream(path));
			p = new Properties();
			p.load(in);
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return p;
	}*/
	/*    public static void main(String[] args) {
		ConfigUtil configUtil=new ConfigUtil();
		configUtil.loadProperty();
		String aa=(String) ConfigUtil.prop.get("bonus_class");
		String[] bb=aa.split(",");
		int[] finalValue=new int[bb.length];
		for(int i=0;i<finalValue.length;i++){
			finalValue[i]=Integer.parseInt(bb[i]);
		}

		for(int cc:finalValue){
			System.out.println(cc);	
		}

	}*/

	public void handleBonusClassArray(){
		//处理三级分销比率
		String aaString=(String) ConfigUtil.prop.get("bonus_class_ratio");
		String[] bbString=aaString.split(",");
		float[] bonusClassRatio=new float[bbString.length];
		if(bbString.length<3){
			System.err.println("请检查三级分销比率");
		}

		for(int i=0;i<bonusClassRatio.length;i++){
			bonusClassRatio[i]=Float.parseFloat(bbString[i]);
		}
		bonus_class_ratio=bonusClassRatio;
		//三级分销价格
		String aaString5=(String) ConfigUtil.prop.get("bonus_price");
		String[] bbString5=aaString5.split(",");
		int[] bonus_price=new int[bbString5.length];
		if(bbString5.length<3){
			System.err.println("请检查三级价格");
		}

		for(int i=0;i<bbString5.length;i++){
			bonus_price[i]=Integer.parseInt(bbString5[i]);
		}
		bonusPrice=bonus_price;

		//解析三级分销的档位标识
		String aa=(String) ConfigUtil.prop.get("bonus_class_key");
		String[] bb=aa.split(",");
		if(bb.length<=0){
			System.err.println("请检查三级分销比率");
		}

		int[] bonusClassKey1=new int[bb.length];
		for(int i=0;i<bonusClassKey1.length;i++){
			bonusClassKey1[i]=Integer.parseInt(bb[i]);
		}
		bonusClassKey=bonusClassKey1;
		//解析三级分销的档位信息值
		String aa1=(String) ConfigUtil.prop.get("bonus_class_value");
		String[] bb1=aa1.split(",");
		if(bb1.length<=0||bb.length!=bb1.length){
			System.err.println("请检查三级分销比率");
		}
		int[] bonusClassValue=new int[bb1.length];
		for(int i=0;i<bonusClassValue.length;i++){
			bonusClassValue[i]=Integer.parseInt(bb1[i]);
		}
		//填值
		bonus_class_map=new HashMap<Integer, Integer>();
		for (int i = 0; i < bonusClassKey1.length; i++) {
			bonus_class_map.put(bonusClassKey1[i], bonusClassValue[i]);
		}
		//三级分销关闭的总代Id集合
		String dd=(String) ConfigUtil.prop.get("three_level_institution");
		String[] ee=dd.split(",");
		if(ee.length<=0){
			System.err.println("请检查三级分销比率");
		}
		threeLevelSellInstitutionIds=new int[ee.length];
		for(int i=0;i<threeLevelSellInstitutionIds.length;i++){
			threeLevelSellInstitutionIds[i]=Integer.parseInt(ee[i]);
		}
		//三级分销方案2
		String ff=(String) ConfigUtil.prop.get("three_level_institution_close");
		try {
			three_level_institution_close=Integer.parseInt(ff);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//游戏回访记录查询权限列表
		String gameRecordListStr=(String) ConfigUtil.prop.get("priority_open_list");
		String[] gameRecordList=gameRecordListStr.split(",");
		if(ee.length<=0){
			System.err.println("请检查三级分销比率");
		}
		priority_open_list=new String[gameRecordList.length];
		for(int i=0;i<gameRecordList.length;i++){
			priority_open_list[i]=gameRecordList[i];
		}


	}
	public static Map<Integer, Integer> getBonus_class_map() {
		return bonus_class_map;
	}
	public static void setBonus_class_map(Map<Integer, Integer> bonus_class_map) {
		ConfigUtil.bonus_class_map = bonus_class_map;
	}
	public static float[] getBonus_class_ratio() {
		return bonus_class_ratio;
	}
	public static void setBonus_class_ratio(float[] bonus_class_ratio) {
		ConfigUtil.bonus_class_ratio = bonus_class_ratio;
	}
	public static int[] getBonusClassKey() {
		return bonusClassKey;
	}
	public static void setBonusClassKey(int[] bonusClassKey) {
		ConfigUtil.bonusClassKey = bonusClassKey;
	}
	public static int[] getBonusPrice() {
		return bonusPrice;
	}
	public static void setBonusPrice(int[] bonusPrice) {
		ConfigUtil.bonusPrice = bonusPrice;
	}





}
