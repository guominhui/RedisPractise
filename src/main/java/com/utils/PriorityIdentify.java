package com.utils;

import javax.servlet.http.HttpServletResponse;

import com.pojo.MainProxy;
import com.pojo.Manager;
import com.pojo.Person;
import com.pojo.Proxy;

public class PriorityIdentify {

	public static boolean isManager(Person person){
        return person instanceof Manager;

    }
	public static boolean isManagerAndNotice(Person person,HttpServletResponse response){
		if (person instanceof Manager) {
			return true;
		}else{
			NoticeUtils.handleErrorNotice(response, "您无此权限", "index.do");
			return false;
		}
	}
	public static boolean isMainProxy(Person person){
        return person instanceof MainProxy;

    }
	public static boolean isMainProxyAndNotice(Person person,HttpServletResponse response){
		if (person instanceof MainProxy) {
			return true;
		}else{
			NoticeUtils.handleErrorNotice(response, "您无此权限", "playerMag.jsp");
			return false;
		}
	}
	public static boolean isManagerAndMainProxyWithNotice(Person person,HttpServletResponse response){
		if (person instanceof Manager||person instanceof MainProxy) {
			return true;
		}else{
			NoticeUtils.handleErrorNotice(response, "您无此权限", "playerMag.jsp");
			return false;
		}
	}
	public static boolean isProxy(Person person){
        return person instanceof Proxy;

    }
}
