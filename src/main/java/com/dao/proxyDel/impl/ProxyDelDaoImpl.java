package com.dao.proxyDel.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.dao.proxy.ProxyDao;
import com.dao.proxyDel.ProxyDelDao;
import com.dao.proxyDel.mapper.ProxyDelMapper;
import com.pojo.Proxy;
import com.utils.SqlSessionUtil;
import com.utils.ValidateUtils;

@Repository
public class ProxyDelDaoImpl implements ProxyDelDao{


	@Override
	public Proxy findProxyByID(int id) {
		try {
			SqlSession	sqlSession = SqlSessionUtil.getSqlSession();
			ProxyDelMapper proxyMapper = sqlSession.getMapper(ProxyDelMapper.class);
			Proxy result=proxyMapper.findProxyByID(id);
			sqlSession.commit();
			sqlSession.close();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public int addProxy(Proxy user) {
		try {
			SqlSession	sqlSession = SqlSessionUtil.getSqlSession();
			ProxyDelMapper proxyMapper = sqlSession.getMapper(ProxyDelMapper.class);
			int result=proxyMapper.addProxy(user);
			sqlSession.commit();
			sqlSession.close();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;

	}

	@Override
	public int deleteProxy(int userId) {
		try {
			SqlSession	sqlSession = SqlSessionUtil.getSqlSession();
			ProxyDelMapper proxyMapper = sqlSession.getMapper(ProxyDelMapper.class);
			int result=proxyMapper.deleteProxy(userId);
			sqlSession.commit();
			sqlSession.close();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;

	}

	@Override
	public int updateProxy(Proxy user) {
		try {
			SqlSession	sqlSession = SqlSessionUtil.getSqlSession();
			ProxyDelMapper proxyMapper = sqlSession.getMapper(ProxyDelMapper.class);
			int result=proxyMapper.updateProxy(user);
			sqlSession.commit();
			sqlSession.close();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;

	}

	@Override
	public int updateLCardCount(Proxy user) {
		try {
			SqlSession	sqlSession = SqlSessionUtil.getSqlSession();
			ProxyDelMapper proxyMapper = sqlSession.getMapper(ProxyDelMapper.class);
			int result=proxyMapper.updateLCardCount(user);
			sqlSession.commit();
			sqlSession.close();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;

	}

	@Override
	public int updateFCardCount(Proxy user) {
		try {
			SqlSession sqlSession = SqlSessionUtil.getSqlSession();
			ProxyDelMapper proxyMapper = sqlSession.getMapper(ProxyDelMapper.class);
			int result=proxyMapper.updateFCardCount(user);
			sqlSession.commit();
			sqlSession.close();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;

	}


	@Override
	public int getProxysCountByRecommendID(int recommendId) {
		try {
			SqlSession sqlSession = SqlSessionUtil.getSqlSession();
			ProxyDelMapper proxyMapper = sqlSession.getMapper(ProxyDelMapper.class);
			int result=proxyMapper.getProxysCountByRecommendID(recommendId);
			sqlSession.commit();
			sqlSession.close();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}
	
	@Override
	public int editPassword(Proxy person) {
		try {
			SqlSession 	sqlSession = SqlSessionUtil.getSqlSession();
			ProxyDelMapper proxyMapper = sqlSession.getMapper(ProxyDelMapper.class);
			int result=proxyMapper.editPassword(person);
			sqlSession.commit();
			sqlSession.close();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;

	}

	@Override
	public List<Integer> getProxyIdsByRecommendID(int recommendPerson) {
		try {
			SqlSession	sqlSession = SqlSessionUtil.getSqlSession();
			ProxyDelMapper proxyMapper = sqlSession.getMapper(ProxyDelMapper.class);
			List<Integer> list=proxyMapper.getProxyIdsByRecommendID(recommendPerson);
			sqlSession.commit();
			sqlSession.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Proxy> getPersonLikeUsername(String username) {
		try {
			SqlSession	sqlSession = SqlSessionUtil.getSqlSession();
			ProxyDelMapper proxyMapper = sqlSession.getMapper(ProxyDelMapper.class);
			List<Proxy> list=proxyMapper.getProxyLikeUsername(username);
			sqlSession.commit();
			sqlSession.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Proxy> getAllProxys(Date startTime,Date endTime,int startIndex,int length) {
		try {
			SqlSession sqlSession = SqlSessionUtil.getSqlSession();
			ProxyDelMapper proxyMapper = sqlSession.getMapper(ProxyDelMapper.class);
			Map<String, Object> paramMap=new HashMap<String, Object>();
			if (ValidateUtils.isValidatedIndex(startIndex,length)) {
				paramMap.put("startIndex", startIndex);
				paramMap.put("length", length);	
			}
			if (ValidateUtils.isValidatedDate(startTime,endTime)) {
				paramMap.put("startTime", startTime);
				paramMap.put("endTime", endTime);	
			}

			List<Proxy> list=proxyMapper.getAllProxys(paramMap);
			sqlSession.commit();
			sqlSession.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public int getAllProxysCount() {
		try {
			SqlSession sqlSession = SqlSessionUtil.getSqlSession();
			ProxyDelMapper proxyMapper = sqlSession.getMapper(ProxyDelMapper.class);
			int result=proxyMapper.getAllProxysCount();
			sqlSession.commit();
			sqlSession.close();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Proxy> getProxys(int id, Date crTime, Date enTime,int startIndex,int length) {

		try {
			SqlSession sqlSession = SqlSessionUtil.getSqlSession();
			ProxyDelMapper proxyMapper = sqlSession.getMapper(ProxyDelMapper.class);
			HashMap<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.put("id", id);
			if (ValidateUtils.isValidatedIndex(startIndex,length)) {
				paramMap.put("startIndex", startIndex);
				paramMap.put("length", length);	
			}
			if (ValidateUtils.isValidatedDate(crTime,enTime)) {
				paramMap.put("startTime", crTime);
				paramMap.put("endTime", crTime);	
			}
			List<Proxy> cardRecord=proxyMapper.getProxys(paramMap);
			sqlSession.commit();
			sqlSession.close();
			return cardRecord;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	

	}

	@Override
	public int updateBonus(Proxy user) {
		try {
			SqlSession sqlSession = SqlSessionUtil.getSqlSession();
			ProxyDelMapper proxyMapper = sqlSession.getMapper(ProxyDelMapper.class);
			int result=proxyMapper.updateBonus(user);
			sqlSession.commit();
			sqlSession.close();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int exchangeBonus(Proxy user) {
		try {
			SqlSession sqlSession = SqlSessionUtil.getSqlSession();
			ProxyDelMapper proxyMapper = sqlSession.getMapper(ProxyDelMapper.class);
			int result=proxyMapper.exchangeBonus(user);
			sqlSession.commit();
			sqlSession.close();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public List<Proxy> getProxysByPioneerID(int pioneerId, int startIndex, int length) {
		try {
			SqlSession sqlSession = SqlSessionUtil.getSqlSession();
			ProxyDelMapper proxyMapper = sqlSession.getMapper(ProxyDelMapper.class);
			Map<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.put("pioneerId", pioneerId);
			if (ValidateUtils.isValidatedIndex(startIndex,length)) {
				paramMap.put("startIndex", startIndex);
				paramMap.put("length", length);	
			}
			List<Proxy> list=proxyMapper.getProxysByPioneerID(paramMap);
			sqlSession.commit();
			sqlSession.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getProxysCountByPioneerID(int pioneerId) {
		try {
			SqlSession sqlSession = SqlSessionUtil.getSqlSession();
			ProxyDelMapper proxyMapper = sqlSession.getMapper(ProxyDelMapper.class);
			int result=proxyMapper.getProxysCountByPioneerID(pioneerId);
			sqlSession.commit();
			sqlSession.close();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Integer> getProxyIdsByPioneerID(int pioneerPerson) {
		try {
			SqlSession	sqlSession = SqlSessionUtil.getSqlSession();
			ProxyDelMapper proxyMapper = sqlSession.getMapper(ProxyDelMapper.class);
			List<Integer> list=proxyMapper.getProxyIdsByPioneerID(pioneerPerson);
			sqlSession.commit();
			sqlSession.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Proxy> getProxysByRecommendID(int recommendPerson) {
		try {
			SqlSession	sqlSession = SqlSessionUtil.getSqlSession();
			ProxyDelMapper proxyMapper = sqlSession.getMapper(ProxyDelMapper.class);
			List<Proxy> list=proxyMapper.getProxysByRecommendID(recommendPerson);
			sqlSession.commit();
			sqlSession.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int clearFCardCount(int userId) {
		try {
			SqlSession sqlSession = SqlSessionUtil.getSqlSession();
			ProxyDelMapper proxyMapper = sqlSession.getMapper(ProxyDelMapper.class);
			int result=proxyMapper.clearFCardCount(userId);
			sqlSession.commit();
			sqlSession.close();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int removeProxy(int proxyId) {
		try {
			SqlSession sqlSession = SqlSessionUtil.getSqlSession();
			ProxyDelMapper proxyMapper = sqlSession.getMapper(ProxyDelMapper.class);
			int result=proxyMapper.deleteProxy(proxyId);
			sqlSession.commit();
			sqlSession.close();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}