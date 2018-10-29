package com.utils;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionUtil {
//	@Autowired
//	ProxyDao proxyDao;

	private static SqlSessionFactory sessionFactory;

	public static SqlSession getSqlSession() throws Exception{

		if(sessionFactory==null){
			String resource = "conf.xml";
			//浣跨敤绫诲姞杞藉櫒鍔犺浇mybatis鐨勯厤缃枃浠讹紙瀹冧篃鍔犺浇鍏宠仈鐨勬槧灏勬枃浠讹級
			InputStream is = Resources.getResourceAsStream(resource);
			//鏋勫缓sqlSession鐨勫伐鍘�
			 sessionFactory = new SqlSessionFactoryBuilder().build(is);
			//鍒涘缓鑳芥墽琛屾槧灏勬枃浠朵腑sql鐨剆qlSession
		}
		SqlSession sqlSession = sessionFactory.openSession();
		return sqlSession;
	}
	//缁忔娴嬶紝閲嶅鏋勫缓SqlSessionFactory鐨勬晥鐜囧姣旓紝鍑犱箮鏄�5鍊�
	//public static void main(String[] args) {
		/*long start_Time=System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			System.out.println("娓告垙寮�濮�");
			ProxyDao proxyDao=new ProxyDaoImpl();
			List<Proxy> a=proxyDao.getAllProxys(null, null, -1, -1);
			System.out.println(a.size());
		}
		long end_Time=System.currentTimeMillis();	
		
		
		System.out.println("闂撮殧鏃堕棿"+(end_Time-start_Time));*/
//		String[] aa=new String[]{"1"};
//		List<String> bb=Arrays.asList(aa);
//		if(Arrays.asList(aa).contains("1")){
//			System.out.println("1");
//		}else{
//		System.out.println("0");
//		}
	//}
}
