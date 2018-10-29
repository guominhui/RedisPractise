package com.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.pojo.Person;
import com.utils.redis.CookieUtil;
import com.utils.redis.JsonUtil;
import com.utils.redis.RedisShardedPoolUtil;

public class SessionExpireFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		 HttpServletRequest request=(HttpServletRequest) arg0;
	     String login_token=CookieUtil.readLoginToken(request);
	     if(StringUtils.isNotEmpty(login_token)){
	    	 //判断login——token是否为空
	    	 String user_json_str=RedisShardedPoolUtil.get(login_token);
	    	 Person person=JsonUtil.string2Obj(user_json_str, Person.class);
	    	 if(person==null){
	    		 //如果User不为空，则重置redis时间
	    		 RedisShardedPoolUtil.expire(login_token,Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
	    	 }
	     }
	     
	     filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) {
		// TODO Auto-generated method stub
		
	}

}
