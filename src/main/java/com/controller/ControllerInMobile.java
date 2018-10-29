package com.controller;



import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dao.disRoomRec.DisRoomRecDao;
import com.dao.manager.ManageDao;
import com.dictionary.OperateConstant;
import com.pojo.CardRecord;
import com.pojo.Customer;
import com.pojo.DisRoomRec;
import com.pojo.DistributionRecord;
import com.pojo.GameRecord;
import com.pojo.MainProxy;
import com.pojo.Manager;
import com.pojo.OperateRecord;
import com.pojo.Person;
import com.pojo.Proxy;
import com.service.customer.CustomerService;
import com.service.distribution.DistributionService;
import com.service.gameRecord.GameRecordService;
import com.service.mainProxy.MainProxyService;
import com.service.operateRec.OperateReService;
import com.service.proxy.ProxyService;
import com.service.record.CardRecordService;
import com.utils.ConfigUtil;
import com.utils.DateUtil;
import com.utils.NoticeUtils;
import com.utils.PriorityIdentify;
import com.utils.StringUtils;
import com.utils.redis.CookieUtil;
import com.utils.redis.JsonUtil;
import com.utils.redis.RedisShardedPoolUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class ControllerInMobile {

	@Autowired
	private MainProxyService mainProxyService;


	@Autowired
	private ProxyService proxyService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CardRecordService cardRecordService;

	@Autowired
	private DistributionService distributionService;
    
	@Autowired
	private GameRecordService gameRecordService;
	
	@Autowired
	private OperateReService operateReService;
	
	@Autowired
	private ManageDao manageDao;
	
	@Autowired
	private DisRoomRecDao disRoomRecDao;



	//================================================================================
	//-------------------------------处理移动端报文--------------------------------------
	//===============================================================================
	/**
	 * 分销记录---------------移动端
	 * @param type
	 * @param start_time
	 * @param end_time
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/distRecordInMobile")
	@ResponseBody
	public ModelAndView distRecordInMobile(@RequestParam int start_index,@RequestParam int end_index,HttpServletRequest  request,HttpServletResponse response) {
		Person person=(Person) request.getSession().getAttribute("user"); 
		int sellerId=person.getUserId();
		List<DistributionRecord> list=null;
		JSONObject jsonObject=new JSONObject();
		/*	Date startTime=new Date(start_time*1000);
		Date endTime=new Date(end_time*1000);;*/
		if (PriorityIdentify.isManager(person)) {
			return null;
		}else if (PriorityIdentify.isMainProxy(person)){
			return null;
			//买卡记录
		}else{
			list=distributionService.findDistributionByID(sellerId,null,null,start_index,end_index);
		}

		if (list!=null&&list.size()>0) {
			for(DistributionRecord cardRecord:list){
				cardRecord.setSellTimeInMb(cardRecord.getSellTime().getTime()/1000);
			}
		}
		//Collections.reverse(list);
		jsonObject.put("data", JSONArray.fromObject(list));
		if(StringUtils.isNotEmpty(jsonObject.get("data").toString())){
			jsonObject.put("status", 0);
		}	
		try {
			PrintWriter pw = response.getWriter();  
			pw.write(jsonObject.toString());  
			pw.flush();  
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return null;
	}
	/**
	 * 购卡记录---------------移动端
	 * @param type
	 * @param start_time
	 * @param end_time
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/buyCardInMobile")
	@ResponseBody
	public ModelAndView buyCardInMobile(@RequestParam String proxyIdStr,@RequestParam int start_index,@RequestParam int end_index,@RequestParam String startDateStr,@RequestParam String endDateStr,HttpServletRequest  request,HttpServletResponse response) {
		Person person=(Person) request.getSession().getAttribute("user"); 
		int sellerId=person.getUserId();
		List<CardRecord> list=new ArrayList<CardRecord>();
		JSONObject jsonObject=new JSONObject();
		/*	Date startTime=new Date(start_time*1000);
		Date endTime=new Date(end_time*1000);;*/

		//时间处理-----------------------------
		if (!StringUtils.isNotEmpty(startDateStr,endDateStr)) {
			return null;
		}
		Date startTime=null;
		Date endTime=null;
		if (StringUtils.isNotEmpty(startDateStr,endDateStr)) {
			String[] sDate=startDateStr.split("-");
			String[] eDate=endDateStr.split("-");
			if(sDate.length<=0){
				return null;
			}
			try {
				if ("开始日:".equals(startDateStr)){
					startTime=null;
				}else{
					startTime=new Date(Integer.parseInt(sDate[0])-1900, Integer.parseInt(sDate[1])-1, Integer.parseInt(sDate[2]),0,0,0);	
				}
				if ("结束日:".equals(endDateStr)){
					endTime=null;
				}else{
					endTime=new Date(Integer.parseInt(eDate[0])-1900, Integer.parseInt(eDate[1])-1, Integer.parseInt(eDate[2]),23,59,59);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return null;
			}
		}
		//时间处理-----------------------------
		int proxyId=0;
		if(StringUtils.isNotEmpty(proxyIdStr)&&StringUtils.isNumeric(proxyIdStr)){
			proxyId=Integer.parseInt(proxyIdStr);
		}
		if (PriorityIdentify.isManager(person)) {
			if(proxyId==0){
				list=cardRecordService.getAllCardRecord(startTime, endTime, start_index, end_index);
			}else{
				list=cardRecordService.findCardByBuyerIdandTime(proxyId,startTime, endTime,start_index, end_index);
			}

		}else{
			//买卡记录
			if(PriorityIdentify.isMainProxy(person)){
				if(proxyId==0){
					list=cardRecordService.findCardByBuyerIdandTime(sellerId,startTime, endTime,start_index,end_index);
				}else{
					list=cardRecordService.findCardByBuyerIdandTime(proxyId,startTime, endTime,start_index, end_index);
				}
			}else if(PriorityIdentify.isProxy(person)){
				list=cardRecordService.findCardByBuyerIdandTime(sellerId,startTime, endTime,start_index,end_index);
			}

			//ModelAndView modelAndView=new ModelAndView("cardRecord");
		}

		if (list!=null&&list.size()>0) {
			for(CardRecord cardRecord:list){
				cardRecord.setSellTimeInMb(cardRecord.getSellTime().getTime()/1000);
			}
		}
		//Collections.reverse(list);
		jsonObject.put("data", JSONArray.fromObject(list));
		if(StringUtils.isNotEmpty(jsonObject.get("data").toString())){
			jsonObject.put("status", 0);
		}	
		try {
			PrintWriter pw = response.getWriter();  
			pw.write(jsonObject.toString());  
			pw.flush();  
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return null;
	}
	/**
	 * 售卡记录-------------移动端
	 * @param type
	 * @param start_time
	 * @param end_time
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cardRecordInMobile")
	@ResponseBody
	public ModelAndView cardRecordInMobile(@RequestParam String proxyIdStr,@RequestParam int start_index,@RequestParam int end_index,@RequestParam String startDateStr,@RequestParam String endDateStr,HttpServletRequest  request,HttpServletResponse response) {
		Person person=(Person) request.getSession().getAttribute("user"); 
		int sellerId=person.getUserId();
		List<CardRecord> list=null;
		//Date startTime=new Date(start_time*1000);
		//Date endTime=new Date(end_time*1000);;
		//时间处理-----------------------------
		if (!StringUtils.isNotEmpty(startDateStr,endDateStr)) {
			return null;
		}
		Date startTime=null;
		Date endTime=null;
		if (StringUtils.isNotEmpty(startDateStr,endDateStr)) {
			String[] sDate=startDateStr.split("-");
			String[] eDate=endDateStr.split("-");
			if(sDate.length<=0){
				return null;
			}
			try {
				if ("开始日:".equals(startDateStr)){
					startTime=null;
				}else{
					startTime=new Date(Integer.parseInt(sDate[0])-1900, Integer.parseInt(sDate[1])-1, Integer.parseInt(sDate[2]),0,0,0);	
				}
				if ("结束日:".equals(endDateStr)){
					endTime=null;
				}else{
					endTime=new Date(Integer.parseInt(eDate[0])-1900, Integer.parseInt(eDate[1])-1, Integer.parseInt(eDate[2]),23,59,59);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return null;
			}
		}
		//时间处理-----------------------------
		int proxyId=0;
		if(StringUtils.isNotEmpty(proxyIdStr)&&StringUtils.isNumeric(proxyIdStr)){
			proxyId=Integer.parseInt(proxyIdStr);
		}
		if (PriorityIdentify.isManager(person)) {
			if(proxyId==0){
				list=cardRecordService.getAllCardRecord(startTime,endTime,start_index, end_index);	
			}else{
				list=cardRecordService.findCardBySellerIdandTime(proxyId,startTime,endTime,start_index, end_index);	
			}

		}else{

			//售卡记录
			if(PriorityIdentify.isMainProxy(person)){
				if(proxyId==0){
					list=cardRecordService.findCardBySellerIdandTime(sellerId,startTime,endTime, start_index, end_index);	
				}else{
					list=cardRecordService.findCardBySellerIdandTime(proxyId,startTime,endTime,start_index, end_index);	
				}
			}else if(PriorityIdentify.isProxy(person)){
				list=cardRecordService.findCardBySellerIdandTime(sellerId,startTime,endTime, start_index, end_index);
			}
		}
		if (list!=null&&list.size()>0) {
			for(CardRecord cardRecord:list){
				cardRecord.setSellTimeInMb(cardRecord.getSellTime().getTime()/1000);
			}
		}
		JSONObject jsonObject=new JSONObject();
		//Collections.reverse(list);
		jsonObject.put("data", JSONArray.fromObject(list));
		if(StringUtils.isNotEmpty(jsonObject.get("data").toString())){
			jsonObject.put("status", 0);
		}	
		//ModelAndView modelAndView=new ModelAndView("cardRecord");
		try {
			PrintWriter pw = response.getWriter();  
			pw.write(jsonObject.toString());  
			pw.flush();  
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return null;
	}
	/**
	 * 根据房卡查找游戏记录-------------移动端
	 * @param type
	 * @param start_time
	 * @param end_time
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/gameRecordInMobile")
	@ResponseBody
	public ModelAndView gameRecordInMobile(@RequestParam String roomIdStr,@RequestParam int start_index,@RequestParam int end_index,HttpServletRequest  request,HttpServletResponse response) {
		Person person=(Person) request.getSession().getAttribute("user"); 
		int sellerId=person.getUserId();
		List<GameRecord> list=null;
		int roomId=0;
		if(StringUtils.isNotEmpty(roomIdStr)&&StringUtils.isNumeric(roomIdStr)){
			roomId=Integer.parseInt(roomIdStr);
		}
//		if (list!=null&&list.size()>0) {
//			for(CardRecord cardRecord:list){
//				cardRecord.setSellTimeInMb(cardRecord.getSellTime().getTime()/1000);
//			}
//		}
		int roomIndex=0;
		roomIndex=start_index/10;
		list=gameRecordService.handGameRecordMsgWithRoomId(roomId,roomIndex);
		JSONObject jsonObject=new JSONObject();
		//Collections.reverse(list);
		int[] scoreList=new int[]{0,0,0,0,0,0,0};
		for(GameRecord gr:list){
			scoreList[0]+=gr.getScore1();
			scoreList[1]+=gr.getScore2();
			scoreList[2]+=gr.getScore3();
			scoreList[3]+=gr.getScore4();
			scoreList[4]+=gr.getScore5();
			scoreList[5]+=gr.getScore6();
			scoreList[6]+=gr.getScore7();
		}
		GameRecord gameRecord=new GameRecord();
		gameRecord.setScore1(scoreList[0]);
		gameRecord.setScore2(scoreList[1]);
		gameRecord.setScore3(scoreList[2]);
		gameRecord.setScore4(scoreList[3]);
		gameRecord.setScore5(scoreList[4]);
		gameRecord.setScore6(scoreList[5]);
		gameRecord.setScore7(scoreList[6]);
		if(list.size()>0){
			gameRecord.setNickName1(list.get(0).getNickName1()+"--总分--");
			gameRecord.setNickName2(list.get(0).getNickName2()+"--总分--");
			gameRecord.setNickName3(list.get(0).getNickName3()+"--总分--");
			gameRecord.setNickName4(list.get(0).getNickName4()+"--总分--");
			gameRecord.setNickName5(list.get(0).getNickName5()+"--总分--");
			gameRecord.setNickName6(list.get(0).getNickName6()+"--总分--");
			gameRecord.setNickName7(list.get(0).getNickName7()+"--总分--");
			list.add(gameRecord);
		}
		
		
		jsonObject.put("data", JSONArray.fromObject(list));
		if(StringUtils.isNotEmpty(jsonObject.get("data").toString())){
			jsonObject.put("status", 0);
		}	
		//ModelAndView modelAndView=new ModelAndView("cardRecord");
		try {
			PrintWriter pw = response.getWriter();  
			pw.write(jsonObject.toString());  
			pw.flush();  
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return null;
	}
	/**
	 * 根据房号查看解散信息-------------移动端
	 * @param type
	 * @param start_time
	 * @param end_time
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/gameDisRoomInfoInMobile")
	@ResponseBody
	public ModelAndView gameDisRoomInfoInMobile(@RequestParam String roomIdStr,HttpServletRequest  request,HttpServletResponse response) {
		Person person=(Person) request.getSession().getAttribute("user"); 
		List<DisRoomRec> list=null;
		int roomId=0;
		if(StringUtils.isNotEmpty(roomIdStr)&&StringUtils.isNumeric(roomIdStr)){
			roomId=Integer.parseInt(roomIdStr);
		}
		list=disRoomRecDao.findDistRoomRecByRoomNo(roomId);
		JSONObject jsonObject=new JSONObject();
		
		//处理List
		for (int i = 0; i < list.size(); i++) {
			DisRoomRec disRoomRec=list.get(i);
			disRoomRec.setDismissTypeStr(disRoomRec.getDismissTypeMsg(disRoomRec.getDismissType()));
		    disRoomRec.setRoomTypeStr(disRoomRec.getRoomTypeMsg(disRoomRec.getRoomType()));
		}
		
		jsonObject.put("data", JSONArray.fromObject(list));
		if(StringUtils.isNotEmpty(jsonObject.get("data").toString())){
			jsonObject.put("status", 0);
		}	
		//ModelAndView modelAndView=new ModelAndView("cardRecord");
		try {
			PrintWriter pw = response.getWriter();  
			pw.write(jsonObject.toString());  
			pw.flush();  
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return null;
	}
	
	
	/**
	 * 根据房卡查找游戏记录-------------移动端
	 * @param type
	 * @param start_time
	 * @param end_time
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/playerStateInMobile")
	@ResponseBody
	public ModelAndView playerDateInMobile(@RequestParam String playerIdStr,@RequestParam int start_index,@RequestParam int end_index,@RequestParam String startDateStr,@RequestParam String endDateStr,HttpServletRequest  request,HttpServletResponse response) {
		Person person=(Person) request.getSession().getAttribute("user"); 
		int sellerId=person.getUserId();
		List<GameRecord> list=null;
		int playerId=0;
		if(StringUtils.isNotEmpty(playerIdStr)&&StringUtils.isNumeric(playerIdStr)){
			playerId=Integer.parseInt(playerIdStr);
		}
		JSONObject jsonObject=new JSONObject();
		if(playerId==0){
			jsonObject.put("data", new ArrayList<GameRecord>());
			jsonObject.put("status", 0);
			try {
				PrintWriter pw = response.getWriter();  
				pw.write(jsonObject.toString());  
				pw.flush();  
				pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		
//		if (list!=null&&list.size()>0) {
//			for(CardRecord cardRecord:list){
//				cardRecord.setSellTimeInMb(cardRecord.getSellTime().getTime()/1000);
//			}
//		}
		
		//时间处理-----------------------------
				if (!StringUtils.isNotEmpty(startDateStr,endDateStr)) {
					return null;
				}
				Date startTime=null;
				Date endTime=null;
				if (StringUtils.isNotEmpty(startDateStr,endDateStr)) {
					String[] sDate=startDateStr.split("-");
					String[] eDate=endDateStr.split("-");
					if(sDate.length<=0){
						return null;
					}
					try {
						if ("开始日:".equals(startDateStr)){
							startTime=null;
						}else{
							startTime=new Date(Integer.parseInt(sDate[0])-1900, Integer.parseInt(sDate[1])-1, Integer.parseInt(sDate[2]),0,0,0);	
						}
						if ("结束日:".equals(endDateStr)){
							endTime=null;
						}else{
							endTime=new Date(Integer.parseInt(eDate[0])-1900, Integer.parseInt(eDate[1])-1, Integer.parseInt(eDate[2]),23,59,59);
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
						return null;
					}
				}
				//时间处理-----------------------------
				
				
		int roomIndex=0;
		roomIndex=start_index/10;
		list=gameRecordService.findGameRecordByPlayerId(playerId, startTime, endTime, roomIndex, 1);
		
		//Collections.reverse(list);
		int[] scoreList=new int[]{0,0,0,0,0,0,0};
		for(GameRecord gr:list){
			scoreList[0]+=gr.getScore1();
			scoreList[1]+=gr.getScore2();
			scoreList[2]+=gr.getScore3();
			scoreList[3]+=gr.getScore4();
			scoreList[4]+=gr.getScore5();
			scoreList[5]+=gr.getScore6();
			scoreList[6]+=gr.getScore7();
		}
		GameRecord gameRecord=new GameRecord();
		gameRecord.setScore1(scoreList[0]);
		gameRecord.setScore2(scoreList[1]);
		gameRecord.setScore3(scoreList[2]);
		gameRecord.setScore4(scoreList[3]);
		gameRecord.setScore5(scoreList[4]);
		gameRecord.setScore6(scoreList[5]);
		gameRecord.setScore7(scoreList[6]);
		if(list.size()>0){
			gameRecord.setNickName1(list.get(0).getNickName1()+"--总分--");
			gameRecord.setNickName2(list.get(0).getNickName2()+"--总分--");
			gameRecord.setNickName3(list.get(0).getNickName3()+"--总分--");
			gameRecord.setNickName4(list.get(0).getNickName4()+"--总分--");
			gameRecord.setNickName5(list.get(0).getNickName5()+"--总分--");
			gameRecord.setNickName6(list.get(0).getNickName6()+"--总分--");
			gameRecord.setNickName7(list.get(0).getNickName7()+"--总分--");
			list.add(gameRecord);
		}
		
		
		jsonObject.put("data", JSONArray.fromObject(list));
		if(StringUtils.isNotEmpty(jsonObject.get("data").toString())){
			jsonObject.put("status", 0);
		}	
		//ModelAndView modelAndView=new ModelAndView("cardRecord");
		try {
			PrintWriter pw = response.getWriter();  
			pw.write(jsonObject.toString());  
			pw.flush();  
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return null;
	}
	
	
	
	
	@RequestMapping("/playerStateScoreInMobile")
	@ResponseBody
	public ModelAndView playerDateAllScoreInMobile(@RequestParam String playerIdStr,@RequestParam String startDateStr,@RequestParam String endDateStr,HttpServletRequest  request,HttpServletResponse response) {
		Person person=(Person) request.getSession().getAttribute("user"); 
		int sellerId=person.getUserId();
		List<GameRecord> list=null;
		int playerId=0;
		if(StringUtils.isNotEmpty(playerIdStr)&&StringUtils.isNumeric(playerIdStr)){
			playerId=Integer.parseInt(playerIdStr);
		}
		JSONObject jsonObject=new JSONObject();
		if(playerId==0){
			jsonObject.put("data", new ArrayList<GameRecord>());
			jsonObject.put("status", 0);
			try {
				PrintWriter pw = response.getWriter();  
				pw.write(jsonObject.toString());  
				pw.flush();  
				pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		
//		if (list!=null&&list.size()>0) {
//			for(CardRecord cardRecord:list){
//				cardRecord.setSellTimeInMb(cardRecord.getSellTime().getTime()/1000);
//			}
//		}
		
		//时间处理-----------------------------
		int interDay=0;
				if (!StringUtils.isNotEmpty(startDateStr,endDateStr)) {
					return null;
				}
				Date startTime=null;
				Date endTime=null;
				if (StringUtils.isNotEmpty(startDateStr,endDateStr)) {
					String[] sDate=startDateStr.split("-");
					String[] eDate=endDateStr.split("-");
					if(sDate.length<=0){
						return null;
					}
					try {
						if ("开始日:".equals(startDateStr)){
							startTime=null;
						}else{
							startTime=new Date(Integer.parseInt(sDate[0])-1900, Integer.parseInt(sDate[1])-1, Integer.parseInt(sDate[2]),0,0,0);	
						}
						if ("结束日:".equals(endDateStr)){
							endTime=null;
						}else{
							endTime=new Date(Integer.parseInt(eDate[0])-1900, Integer.parseInt(eDate[1])-1, Integer.parseInt(eDate[2]),23,59,59);
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
						return null;
					}
					interDay=Integer.parseInt(eDate[2])-Integer.parseInt(sDate[2]);
				}
				//时间处理-----------------------------
				
				
				list=gameRecordService.findGameRecordByPlayerId(playerId, startTime, endTime, -1, 1);
				
				//Collections.reverse(list);
				int scoreAll=0;
				String nickName="";
				for(GameRecord gr:list){
					if (playerId==gr.getUid1()) {
						scoreAll+=gr.getScore1();
						nickName=gr.getNickName1();
					}else if(playerId==gr.getUid2()){
						scoreAll+=gr.getScore2();
						nickName=gr.getNickName2();
					}else if(playerId==gr.getUid3()){
						scoreAll+=gr.getScore3();
						nickName=gr.getNickName3();
					}else if(playerId==gr.getUid4()){
						scoreAll+=gr.getScore4();
						nickName=gr.getNickName4();
					}else if(playerId==gr.getUid5()){
						scoreAll+=gr.getScore5();
						nickName=gr.getNickName5();
					}else if(playerId==gr.getUid6()){
						scoreAll+=gr.getScore6();
						nickName=gr.getNickName6();
					}else if(playerId==gr.getUid7()){
						scoreAll+=gr.getScore7();
						nickName=gr.getNickName7();
					}
					
				}
				
				
				jsonObject.put("status", 0);
				if(interDay>7){
					jsonObject.put("success", -1);
				}else if(!StringUtils.isNotEmpty(nickName)&&scoreAll==0){
					jsonObject.put("success", -2);
				}else{
					jsonObject.put("score", scoreAll);
					jsonObject.put("nickName", nickName);	
				}
				
				
				//if(StringUtils.isNotEmpty(jsonObject.get("data").toString())){
					
					
				//}	
				//ModelAndView modelAndView=new ModelAndView("cardRecord");
				try {
					PrintWriter pw = response.getWriter();  
//					pw.write("<script>alert('添加失败，工号已经存在！');</script>");
//					pw.flush();
					pw.write(jsonObject.toString());  
					pw.flush();  
					pw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				return null;
	}
	
	/**
	 * 兑换信息-------------移动端
	 * @param type
	 * @param start_time
	 * @param end_time
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getExchangeBonusInfo")
	@ResponseBody
	public ModelAndView getExchangeBonusInfo(HttpServletRequest  request,HttpServletResponse response) {
		Person person=(Person) request.getSession().getAttribute("user"); 
		int sellerId=person.getUserId();
		if (person instanceof Proxy){
			Proxy proxy=proxyService.findProxyByID(person.getUserId());
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("bonus", proxy.getBonus());
			Map<Integer, Integer> map=ConfigUtil.getBonus_class_map();
			jsonObject.put("class1",ConfigUtil.getBonusPrice()[0]);
			jsonObject.put("class1Card",ConfigUtil.getBonusClassKey()[0]);
			jsonObject.put("class2",ConfigUtil.getBonusPrice()[1]);
			jsonObject.put("class2Card",ConfigUtil.getBonusClassKey()[1]);
			jsonObject.put("class3",ConfigUtil.getBonusPrice()[2]);
			jsonObject.put("class3Card",ConfigUtil.getBonusClassKey()[2]);
			jsonObject.put("status", 0);
			try {
				PrintWriter pw = response.getWriter();  
				pw.write(jsonObject.toString());  
				pw.flush();  
				pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}

		return null;
	}
	/**
	 * 获取近期售卡情况--------移动端
	 * @param operator
	 * @param uid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/cardstatsInMobile")
	public String cardstats(HttpServletRequest  request,HttpServletResponse response){
		Person person = (Person) request.getSession().getAttribute("user");
		JSONObject jsonObject=new JSONObject();
		Date todayStart=DateUtil.getDayBegin();
		Date todayEnd=DateUtil.getDayEnd();
		Date lastaDayStart=DateUtil.getBeginDayOfYesterday();
		Date lastDayEnd=DateUtil.getEndDayOfYesterDay();
		Date lastaWeekStart=DateUtil.getLastWeekStart();
		Date lastWeekEnd=DateUtil.getLastWeekEnd();
		List<CardRecord> todayList=cardRecordService.findCardBySellerIdandTime(person.getUserId(),todayStart, todayEnd);
		List<CardRecord> lastdayList=cardRecordService.findCardBySellerIdandTime(person.getUserId(),lastaDayStart, lastDayEnd);
		List<CardRecord> lastWeekList=cardRecordService.findCardBySellerIdandTime(person.getUserId(),lastaWeekStart, lastWeekEnd);
		int todayCount=0;	
		if (todayList!=null&&todayList.size()>0) {
			for(CardRecord cardRecord:todayList){
				todayCount=todayCount+cardRecord.getCardFCount()+cardRecord.getCardLCount();
			}	
		}

		int lastdayCount=0;	
		if (lastdayList!=null&&lastdayList.size()>0) {
			for(CardRecord cardRecord:lastdayList){
				lastdayCount=lastdayCount+cardRecord.getCardFCount()+cardRecord.getCardLCount();
			}	
		}

		int lastweekCount=0;	
		if (lastWeekList!=null&&lastWeekList.size()>0) {
			for(CardRecord cardRecord:lastWeekList){
				lastweekCount=lastweekCount+cardRecord.getCardFCount()+cardRecord.getCardLCount();
			}	
		}

		jsonObject.put("today", todayCount);
		jsonObject.put("yesterday", lastdayCount);
		jsonObject.put("last_week", lastweekCount);
		jsonObject.put("status", 0);
		try {
			PrintWriter pw = response.getWriter();  
			pw.write(jsonObject.toString());  
			pw.flush();  
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

		//response.setContentType("text/html;charset=UTF-8");  
		/*  try {
				PrintWriter pw = response.getWriter();  
				pw.write(responseString);  
				pw.flush();  
				pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			return "success";*/
		return null;
	}
	/***
	 * 修改密码---------------移动端
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/repassInMobile")
	@ResponseBody
	public ModelAndView repass(@RequestBody String msg,HttpServletRequest  request,HttpServletResponse response) {
		JSONObject requestJson=JSONObject.fromObject(msg);
		Person person = (Person) request.getSession().getAttribute("user");
		String password=requestJson.getString("ori_pass");
		String newPassword=requestJson.getString("new_pass");
		JSONObject jsonObject=new JSONObject();
		//	response.setContentType("text/html;charset=gb2312");
		PrintWriter out;
		if (person.getPassword().equals(password)) {
			person.setPassword(newPassword);
			int result=-1;
			if (person instanceof Manager) {
				result=manageDao.editPassword((Manager)person);
			}else if(person instanceof MainProxy){
				result=mainProxyService.editPassword((MainProxy) person);
			}else if(person instanceof Proxy){
				result=proxyService.editPassword((Proxy) person);
			}
			if (result>=0) {
				jsonObject.put("status", 0);
				//NoticeUtils.handleSuccessNotice(response,"editPassword.jsp");
			}
		}else{
			jsonObject.put("status", -1);
			//NoticeUtils.handleErrorNotice(response, "原密码错误!", "editPassword.jsp");
		}
		try {
			PrintWriter pw = response.getWriter();  
			pw.write(jsonObject.toString());  
			pw.flush();  
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return null;
	}
	/***
	 * 积分兑换房卡---------------移动端
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exchangeBonusInMobile")
	@ResponseBody
	public ModelAndView exchangeCardwithBonus(@RequestBody String msg,HttpServletRequest  request,HttpServletResponse response) {
		JSONObject requestJson=JSONObject.fromObject(msg);
		JSONObject jsonObject=new JSONObject();
		String exchangeString=requestJson.getString("exchangeClass");
		int exchangeValue=-1;
		if(StringUtils.isNotEmpty(exchangeString)){
			exchangeValue=Integer.parseInt(exchangeString);
		}
		Person person=(Person) request.getSession().getAttribute("user");
		if (person instanceof Proxy){
			Proxy proxy=(Proxy) person;	
			boolean result=proxyService.exchangeCardwithBonus(proxy.getUserId(), exchangeValue);
			if (result) {
				jsonObject.put("status", 0);
				proxy=proxyService.findProxyByID(proxy.getUserId());
				request.getSession().setAttribute("user", proxy);
				jsonObject.put("bonus", proxy.getBonus());
				//NoticeUtils.handleSuccessNotice(response,"editPassword.jsp");
			}else{
				jsonObject.put("status", -1);
			}
			try {
				PrintWriter pw = response.getWriter();  
				pw.write(jsonObject.toString());  
				pw.flush();  
				pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		return null;
	}
	/**
	 * 售卡统一处理结算操作---------移动端
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sellCardHandleInMobile")
	@ResponseBody
	public ModelAndView saveProxyAddCardInMobile(@RequestBody String  requestMsg
			,HttpServletRequest  request,HttpServletResponse response) {
		Person person = (Person) request.getSession().getAttribute("user");
		//String buyerIdString=request.getParameter("uid");
		String uid=(String) JSONObject.fromObject(requestMsg).get("uid");
		//要充值的房卡数
		int num= (int) JSONObject.fromObject(requestMsg).get("num2");
		//现金数
		int cash=(int) JSONObject.fromObject(requestMsg).get("cash");
		String buyerName="";
		JSONObject jsonObject=new JSONObject();
		//sellTypeString0售卖，1赠送
		//income入账金额
		int buyerId=-1;
		buyerId=Integer.parseInt(uid);
		//toTypeString0售卡给代理，1售卡给玩家,2给总代
		String memoString="";
		int sellTypeInCardRecord=-1;

		int userId=person.getUserId();
		int sellType=0;//统一为售卖
		if (cash>0) {
			sellType=0;
		}else{
			sellType=1;
		}
		int addCardCount=-1;
		int personType=-1;
		int income=cash;
		int toType=1;
		//判断toType值，并且拿到卖家名字------开始
		if (buyerId<=100) {
			//出售给管理员
			jsonObject.put("status", -1);
			NoticeUtils.returnMsgToMoile(response, jsonObject);
		}
		else if(buyerId>=101&&buyerId<=1000){
			toType=2;//出售给总代    
			MainProxy mainProxy=mainProxyService.getyMainProxyById(buyerId);
			if (mainProxy!=null) {
				buyerName=mainProxy.getUsername();	
			}else{
				jsonObject.put("status", -1);
				NoticeUtils.returnMsgToMoile(response, jsonObject);
				return null;
			}
		}else if(buyerId>=10000&&buyerId<100000){
			//出售给代理
			Proxy proxy=proxyService.findProxyByID(buyerId);
			if (proxy!=null) {
				buyerName=proxy.getUsername();	
			}else{
				jsonObject.put("status", -1);
				NoticeUtils.returnMsgToMoile(response, jsonObject);
				return null;
			} 
			toType=0;
		}else{
			toType=1;//2出售给总代，0出售给代理，1出售给玩家
			Customer customer=customerService.QueryCustomerById(buyerId);
			if (customer!=null) {
				buyerName=customer.getUsername();	
			}else{
				jsonObject.put("status", -1);
				NoticeUtils.returnMsgToMoile(response, jsonObject);
				return null;
			}
		}
		//判断toType值，并且拿到卖家名字------结束
		addCardCount=num;//要充值的房卡数
		int addCardCount2=0;
		int result=-1;
		String href=null;
		Date cardLTime=person.getCardLTime();
		if (person instanceof Manager) {
			person=manageDao.getManagerById(person.getUserId());
			if (toType!=1) {
				//检查售卖是否足够
				if (sellType==0) {
					if (person.getCardFCount()<num) {
						jsonObject.put("status", -1);
						NoticeUtils.returnMsgToMoile(response, jsonObject);
						return null;
					}
				}else{
					if (person.getCardLCount()<num) {
						jsonObject.put("status", -1);
						NoticeUtils.returnMsgToMoile(response, jsonObject);
						return null;
					}
				}
			}
			switch (toType) {
			case 0:
				sellTypeInCardRecord=1;
				//管理员送卡要单独拿出来写，因为管理员没有业务层
				result=proxyService.updateCardCount(person.getUserId(),person.getUsername(),buyerId,sellType,num,cardLTime);
				if (result>0) {
					if (sellType==0) {
						//proxyService.handleThreeClassBonus(buyerId,addCardCount);
						person.setCardFCount(person.getCardFCount()-num);
					}else{
						person.setCardLCount(person.getCardLCount()-num);
					}
				}
				break;
			case 1:
				sellTypeInCardRecord=2;
				//售卖给玩家特殊处理
				if (person.getCardLCount()<addCardCount) {
					addCardCount2=addCardCount-person.getCardLCount();
					//限时房卡不够的用永久房卡补
					addCardCount=person.getCardLCount();
				}
				if (person.getCardFCount()<addCardCount2) {
					jsonObject.put("status", -1);
					NoticeUtils.returnMsgToMoile(response, jsonObject);
					return null;
				}
				result=customerService.updateCardCount(buyerId, addCardCount);
				result=customerService.updateCardCount(buyerId, addCardCount2);
				if (result>0) {
					person.setCardLCount(person.getCardLCount()-addCardCount);
					person.setCardFCount(person.getCardFCount()-addCardCount2);
				}
				break;

			case 2:
				sellTypeInCardRecord=0;
				result=mainProxyService.updateCardCount(buyerId, sellType,num,cardLTime);
				if (result>0) {
					if (sellType==0) {
						person.setCardFCount(person.getCardFCount()-num);
					}else{
						person.setCardLCount(person.getCardLCount()-num);
					}
				}
				break;

			default:
				break;
			}
			if(result>0){
				person.setIncome(person.getIncome()+cash);
				result=manageDao.updateManager((Manager) person);
				request.getSession().setAttribute("user", person); 
			}else{
				jsonObject.put("status", -1);
				NoticeUtils.returnMsgToMoile(response, jsonObject);
				return null;
			}
		}
		//管理员售卡完毕---------------完毕
		//总代售卡开始---------------开始
		else if(person instanceof MainProxy){
			person=mainProxyService.getyMainProxyById(person.getUserId());
			href="proxy.do";
			if (toType==0) {
				sellTypeInCardRecord=3;
			}else if(toType==1){
				sellTypeInCardRecord=4;
			}else if(toType==2){
				sellTypeInCardRecord=7;
			}
			//玩家特殊处理---------开始//2出售给总代，0出售给代理，1出售给玩家
			if (toType==1) {
				if (person.getCardLCount()<addCardCount) {
					addCardCount2=addCardCount-person.getCardLCount();
					addCardCount=person.getCardLCount();
				}
				if (person.getCardFCount()<addCardCount2) {
					jsonObject.put("status", -1);
					NoticeUtils.returnMsgToMoile(response, jsonObject);
					return null;
				}
				result=mainProxyService.sellCardCount(userId, buyerId, 1, toType, addCardCount,income);
				result=mainProxyService.sellCardCount(userId, buyerId, 0, toType, addCardCount2,income);
				if (result>0) {
					person.setCardLCount(person.getCardLCount()-addCardCount);
					person.setCardFCount(person.getCardFCount()-addCardCount2);
				}else{
					jsonObject.put("status", -1);
					NoticeUtils.returnMsgToMoile(response, jsonObject);
					return null;
				}
				//玩家特殊处理---------结束
			}else if(toType==0){
				//检查售卖是否足够
				if (sellType==0) {
					if (person.getCardFCount()<num) {
						jsonObject.put("status", -1);
						NoticeUtils.returnMsgToMoile(response, jsonObject);
						return null;
					}
				}else{
					if (person.getCardLCount()<num) {
						jsonObject.put("status", -1);
						NoticeUtils.returnMsgToMoile(response, jsonObject);
						return null;
					}
				}
				result=proxyService.updateCardCount(person.getUserId(),person.getUsername(),buyerId,sellType,num,cardLTime);
				if (result>0) {
					if (sellType==0) {
						person.setCardFCount(person.getCardFCount()-num);
					}else{
						person.setCardLCount(person.getCardLCount()-num);
					}
				}
			}else if(toType==2){
				//检查售卖是否足够
				if (sellType==0) {
					if (person.getCardFCount()<num) {
						jsonObject.put("status", -1);
						NoticeUtils.returnMsgToMoile(response, jsonObject);
						return null;
					}
				}else{
					if (person.getCardLCount()<num) {
						jsonObject.put("status", -1);
						NoticeUtils.returnMsgToMoile(response, jsonObject);
						return null;
					}
				}
				result=mainProxyService.updateCardCount(buyerId, sellType,num,cardLTime);
				if (result>0) {
					if (sellType==0) {
						person.setCardFCount(person.getCardFCount()-num);
					}else{
						person.setCardLCount(person.getCardLCount()-num);
					}
				}
			} 
			if(result>0){
				mainProxyService.updateMainPorxy((MainProxy)person);
			}else{
				jsonObject.put("status", -1);
				NoticeUtils.returnMsgToMoile(response, jsonObject);
				return null;
			}
			request.getSession().setAttribute("user", person); 
		}else if(person instanceof Proxy){//代理售卡
			//2出售给总代，0出售给代理，1出售给玩家
			person=proxyService.findProxyByID(person.getUserId());
			if (toType==0) {
				sellTypeInCardRecord=5;
			}else{
				sellTypeInCardRecord=6;
			}
			if(toType==0){
				//检查售卖是否足够
				if (sellType==0) {
					if (person.getCardFCount()<num) {
						jsonObject.put("status", -1);
						NoticeUtils.returnMsgToMoile(response, jsonObject);
						return null;
					}
				}else{
					if (person.getCardLCount()<num) {
						jsonObject.put("status", -1);
						NoticeUtils.returnMsgToMoile(response, jsonObject);
						return null;
					}
				}
				result=proxyService.sellCardCount(userId, buyerId, sellType, toType, addCardCount,income);
				if(result<=0){
					jsonObject.put("status", -1);
					NoticeUtils.returnMsgToMoile(response, jsonObject);
					return null;
				}
				request.getSession().setAttribute("user", person);
			}else if (toType==1) {
				if (person.getCardLCount()<addCardCount) {
					addCardCount2=addCardCount-person.getCardLCount();
					addCardCount=person.getCardLCount();
				}
				if (person.getCardFCount()<addCardCount2) {
					jsonObject.put("status", -1);
					NoticeUtils.returnMsgToMoile(response, jsonObject);
					return null;
				}
				result=proxyService.sellCardCount(userId, buyerId, 1, toType, addCardCount,income);
				result=proxyService.sellCardCount(userId, buyerId, 0, toType, addCardCount2,income);	

				if (result>0) {
//					person.setCardLCount(person.getCardLCount()-addCardCount);
//					person.setCardFCount(person.getCardFCount()-addCardCount2);
//					proxyService.updateProxy((Proxy)person);
				}else{
					jsonObject.put("status", -1);
					NoticeUtils.returnMsgToMoile(response, jsonObject);
					return null;
				}
			}

		}
		String resultString;
		if(result>=0){
			resultString="成功";
			if (toType==1) {
				cardRecordService.addCardRecord(new CardRecord(person.getUserId(), person.getUsername(), sellTypeInCardRecord, new Date(System.currentTimeMillis()), income, 0, addCardCount, addCardCount2, buyerId, buyerName, memoString));
			}else{
				if (sellType==0) {
					cardRecordService.addCardRecord(new CardRecord(person.getUserId(), person.getUsername(), sellTypeInCardRecord, new Date(System.currentTimeMillis()), income, sellType, 0, num, buyerId, buyerName, memoString));
				}else{
					cardRecordService.addCardRecord(new CardRecord(person.getUserId(), person.getUsername(), sellTypeInCardRecord, new Date(System.currentTimeMillis()), income, sellType, num, 0, buyerId, buyerName, memoString));
				}
			}
			jsonObject.put("CardLCount", person.getCardLCount());
			jsonObject.put("CardFCount", person.getCardFCount());
			jsonObject.put("status",0);
		}else{
			resultString="失败";
			jsonObject.put("status",-1);
		}
		NoticeUtils.returnMsgToMoile(response, jsonObject);
		return null;
	}
	/**
	 * 撤卡操作（回归永久卡）---------移动端
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/retrieveCardCountInMobile")
	@ResponseBody
	public ModelAndView retrieveCardCountInMobile(@RequestBody String  requestMsg
			,HttpServletRequest  request,HttpServletResponse response) {
		Person person = (Person) request.getSession().getAttribute("user");
		//String buyerIdString=request.getParameter("uid");
		String uid=(String) JSONObject.fromObject(requestMsg).get("uid");
		//要充值的房卡数
		int retrieveCount= (int) JSONObject.fromObject(requestMsg).get("num2");
		//现金数
		JSONObject jsonObject=new JSONObject();
		
		String errorMsg=null;
		//白名单用户
		if(!Arrays.asList(ConfigUtil.priority_open_list).contains(person.getUserId()+"")){
			errorMsg="您无此权限";
			jsonObject.put("errorMsg", errorMsg);
			jsonObject.put("status", -1);
			NoticeUtils.returnMsgToMoile(response, jsonObject);
			return null;
			}
		/*if(person.getUserId()!=2){
			errorMsg="您无此权限";
			jsonObject.put("errorMsg", errorMsg);
			jsonObject.put("status", -1);
			NoticeUtils.returnMsgToMoile(response, jsonObject);
			return null;
		}*/
		
		//撤销卡数
		if(retrieveCount<=0){
			errorMsg="撤销卡数异常";
			jsonObject.put("errorMsg", errorMsg);
			jsonObject.put("status", -1);
			NoticeUtils.returnMsgToMoile(response, jsonObject);
			return null;
		}
	
		int beOpedUid=-1;
		beOpedUid=Integer.parseInt(uid);

		int sellType=0;//统一为售卖

		int result=-1;
		Date cardLTime=person.getCardLTime();
		//处理玩家
		Customer customer=customerService.QueryCustomerById(beOpedUid);
		if (customer.getCardCount()<retrieveCount){
			jsonObject.put("status", -1);
			errorMsg="撤销卡数异常";
			jsonObject.put("errorMsg", errorMsg);
			NoticeUtils.returnMsgToMoile(response, jsonObject);
			return null;
		}
		
		result=customerService.updateCardCount(beOpedUid, -retrieveCount);
		
		if (result<=0) {
			jsonObject.put("status", -1);
			errorMsg="撤销失败";
			jsonObject.put("errorMsg", errorMsg);
			NoticeUtils.returnMsgToMoile(response, jsonObject);
			return null;
		}
		if (person instanceof Manager) {
			person=manageDao.getManagerById(person.getUserId());
			person.setCardFCount(person.getCardFCount()+retrieveCount);
			result=manageDao.updateManager((Manager) person);
			request.getSession().setAttribute("user", person); 
		}else if(person instanceof MainProxy){
			person=mainProxyService.getyMainProxyById(person.getUserId());
			person.setCardFCount(person.getCardFCount()+retrieveCount);
			result=mainProxyService.updateCardCount(person.getUserId(), sellType,retrieveCount,cardLTime);
		}else if(person instanceof Proxy){
			person=proxyService.findProxyByID(person.getUserId());
			person.setCardFCount(person.getCardFCount()+retrieveCount);
			result=proxyService.updateCardCount(person.getUserId(),person.getUsername(),person.getUserId(),sellType,retrieveCount,cardLTime);
		}
	
			//玩家特殊处理---------开始//2出售给总代，0出售给代理，1出售给玩家
	
		if(result>=0){
			 operateReService.addOperate(new OperateRecord(OperateConstant.retrieveCard.getValue(), person.getUserId(), beOpedUid, retrieveCount, new Date(System.currentTimeMillis())), 0);
			jsonObject.put("CardLCount", person.getCardLCount());
			jsonObject.put("CardFCount", person.getCardFCount());
			jsonObject.put("status",0);
		}else{
			jsonObject.put("status",-1);
		}
		NoticeUtils.returnMsgToMoile(response, jsonObject);
		return null;
	}
	/**
	 * 登录功能----------移动端
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/loginMb", method = {RequestMethod.POST }) 
	@ResponseBody
	public  String  login_mobile(@RequestBody Person body,HttpServletRequest  request,HttpServletResponse response) {
		Person bb=body;
		System.out.println(bb.getUsername());
		String userIdString = body.getUsername();  
		String passwordPre = body.getPassword();
		String password = "";
		try {
			password=java.net.URLDecoder.decode(passwordPre, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			System.err.println("游戏后台密码解析异常");
			e1.printStackTrace();
		}

		int userId=-1;
		if (StringUtils.isNotEmpty(userIdString)) {
			try {
				userId=Integer.parseInt(userIdString);
			} catch (NumberFormatException e) {
				// NoticeUtils.DadInputError(response);
				e.printStackTrace();
				return null;
			}
		}
		int type=-1;
		String typeString="";
		Person person;

		if (userId<=100) {
			person=manageDao.getManagerById(userId);	
			typeString="管理员";
			type=0;
		}else if(userId>=101&&userId<=1000){
			person=mainProxyService.getyMainProxyById(userId);
			typeString="总代";
			type=1;
		}else{
			person=proxyService.findProxyByID(userId);
			typeString="代理";
			type=2;
		}
		int isCloseThreeLevelInstitution=-1;
		if(type==2){
			isCloseThreeLevelInstitution=ConfigUtil.three_level_institution_close;
		}
	
		

		//账号存在
		JSONObject jsonObject2=new JSONObject();
		jsonObject2.put("status",-1);
		if (person !=null) {
			if (person.getPassword().equals(password)) {
				//登陆成功，把user存入session域对象中  
				request.getSession().setAttribute("user", person); 
				request.getSession().setAttribute("userType",type); 
				HttpSession session=request.getSession();
				//save session to redis
				RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2String(person),24*60*60);
				//write cookie 
				CookieUtil.writeLoginToken(response, session.getId());
				//房间回访记录查询群贤
				if(Arrays.asList(ConfigUtil.priority_open_list).contains(userId+"")){
					jsonObject2.put("isOpenPriority", 1);
				}else{
					jsonObject2.put("isOpenPriority", -1);
				}
				//重定向到index.jsp  
				jsonObject2.put("uid", person.getUserId());
				jsonObject2.put("nickName", person.getUsername());
				jsonObject2.put("userType", type+1);
				jsonObject2.put("role", typeString);
				jsonObject2.put("flag", type==0?1:-1);
				jsonObject2.put("star",getTodatSellCount(person));
				jsonObject2.put("status",0);
				jsonObject2.put("isCloseThreeLevelInstitution",isCloseThreeLevelInstitution);
				//	jsonObject2.put("state","success");
				String responseString=jsonObject2.toString();

				try {
					PrintWriter pw = response.getWriter();  
					pw.write(responseString);  
					pw.flush();  
					pw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  

				/*	try {
						response.sendRedirect(request.getContextPath()+"/index.do");//index page is user info show
					} catch (IOException e) {
						e.printStackTrace();
					}  */
				return null;
			}else{

				try {
					PrintWriter pw = response.getWriter();  
					pw.write(jsonObject2.toString());  
					pw.flush();  
					pw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				//	NoticeUtils.handleErrorNotice(response, "密码错误，请重新确认！", "login_pc.jsp");
				//密码错误
				return null;
			}
		}else{
			try {
				PrintWriter pw = response.getWriter();  
				pw.write(jsonObject2.toString());  
				pw.flush();  
				pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			//账号不存在
			//NoticeUtils.handleErrorNotice(response, "用户不存在，请联系代理或者管理员进行注册！", "login.jsp");
			return null;
		}
	}
	/**
	 * 测试缓存
	 */
	@RequestMapping(value="/getUserInfo",method=RequestMethod.GET)
	@ResponseBody 
	public String getUserInfo(HttpServletRequest request){
		
		String loginToken=CookieUtil.readLoginToken(request);
		if(!StringUtils.isNotEmpty(loginToken)){
			return "";
		}
		String jsonUserStr=RedisShardedPoolUtil.get(loginToken);
		Person person=JsonUtil.string2Obj(jsonUserStr, Person.class);
		
		String result=JsonUtil.obj2String(person);
		return result;
	}
	
	/**
	 * 处理移动端查询玩家操作----------移动端
	 * 查找玩家
	 * @param operator
	 * @param uid
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/handleOperationInMobile")
	public String testRequestParam(@RequestParam(value="operator") String operator,@RequestParam(value="uid", required=false, defaultValue="") String uidString,HttpServletRequest  request,HttpServletResponse response){
		Person user = (Person) request.getSession().getAttribute("user");
		JSONObject jsonObject=new JSONObject();
		Person person=null;
		if (operator.equals("query")) {
			int uid=-1;
			if (StringUtils.isNotEmpty(uidString)) {
				try {
					uid=Integer.parseInt(uidString);
				} catch (NumberFormatException e) {
					jsonObject.put("status", -1);
					NoticeUtils.returnMsgToMoile(response, jsonObject);
					e.printStackTrace();

				}
			}else{
				jsonObject.put("data","");
			}	
			if (uid<=10) {
				if (PriorityIdentify.isManager(user)) {
					person=manageDao.getManagerById(uid);		
				}else{
					jsonObject.put("status", -2);
					NoticeUtils.returnMsgToMoile(response, jsonObject);
				}

			}else if(uid>=101&&uid<=1000){
				if (PriorityIdentify.isManager(user)||PriorityIdentify.isMainProxy(user)) {
					person=mainProxyService.getyMainProxyById(uid);
				}else{
					jsonObject.put("status", -2);
					NoticeUtils.returnMsgToMoile(response, jsonObject);
				}
			}else if(uid>=1001&&uid<100000){
				person=proxyService.findProxyByID(uid);
			}else{
				//玩家的特殊处理-----开始
				List<Customer> customerlist=new ArrayList<Customer>();
				Customer customer=customerService.QueryCustomerById(uid);
				if (customer!=null) {
					customerlist.add(customer);
					jsonObject.put("status", 0);
				}else{
					jsonObject.put("status", -1);
				}
				jsonObject.put("data", JSONArray.fromObject(customerlist).toString());
				NoticeUtils.returnMsgToMoile(response, jsonObject);
				return null;
				//玩家的特殊处理-----结束
			}
			List<Person> list=new ArrayList<Person>();
			if (person!=null) {
				list.add(person);
			}
			jsonObject.put("data", JSONArray.fromObject(list).toString());
			if(StringUtils.isNotEmpty(jsonObject.get("data").toString())){
				jsonObject.put("status", 0);
			}			
		}else if (operator.equals("get_console_info")) {
			if (PriorityIdentify.isManager(user)) {
				person=manageDao.getManagerById(user.getUserId());
			}
			if (PriorityIdentify.isMainProxy(user)) {
				person=mainProxyService.getyMainProxyById(user.getUserId());
				jsonObject.put("person_num",customerService.getRecommendPersonCount(person.getUserId()));
			}
			if (PriorityIdentify.isProxy(user)) {
				person=proxyService.findProxyByID(user.getUserId());
				jsonObject.put("bonus",((Proxy)person).getBonus());
				jsonObject.put("person_num",customerService.getRecommendPersonCount(person.getUserId()));
			}
			//获取当前根据时间得来的售卡数量
			//			Date todayStart=DateUtil.getDayBegin();
			//			Date todayEnd=DateUtil.getDayEnd();
			//			List<CardRecord> todayList=cardRecordService.findCardBySellerIdandTime(person.getUserId(),todayStart, todayEnd);
			//			int todayCount=0;	
			//			if (todayList!=null&&todayList.size()>0) {
			//				for(CardRecord cardRecord:todayList){
			//					todayCount=todayCount+cardRecord.getCardFCount()+cardRecord.getCardLCount();
			//				}	
			//			}https://192.168.1.185/svn/GMConcole
			Date todayStart=DateUtil.getDayBegin();
			Date todayEnd=DateUtil.getDayEnd();
			Date lastaDayStart=DateUtil.getBeginDayOfYesterday();
			Date lastDayEnd=DateUtil.getEndDayOfYesterDay();
			Date lastaWeekStart=DateUtil.getLastWeekStart();
			Date lastWeekEnd=DateUtil.getLastWeekEnd();
			List<CardRecord> todayList=cardRecordService.findCardBySellerIdandTime(person.getUserId(),todayStart, todayEnd);
			List<CardRecord> lastdayList=cardRecordService.findCardBySellerIdandTime(person.getUserId(),lastaDayStart, lastDayEnd);
			List<CardRecord> lastWeekList=cardRecordService.findCardBySellerIdandTime(person.getUserId(),lastaWeekStart, lastWeekEnd);
			int todayCount=0;	
			if (todayList!=null&&todayList.size()>0) {
				for(CardRecord cardRecord:todayList){
					todayCount=todayCount+cardRecord.getCardFCount()+cardRecord.getCardLCount();
				}	
			}

			int yesterdayCount=0;	
			if (lastdayList!=null&&lastdayList.size()>0) {
				for(CardRecord cardRecord:lastdayList){
					yesterdayCount=yesterdayCount+cardRecord.getCardFCount()+cardRecord.getCardLCount();
				}	
			}

			int lastweekCount=0;	
			if (lastWeekList!=null&&lastWeekList.size()>0) {
				for(CardRecord cardRecord:lastWeekList){
					lastweekCount=lastweekCount+cardRecord.getCardFCount()+cardRecord.getCardLCount();
				}	
			}

			jsonObject.put("todayCount", todayCount);
			jsonObject.put("yesterdayCount", yesterdayCount);
			jsonObject.put("lastweekCount", lastweekCount);
			//获取当前根据时间得来的售卡数量
			jsonObject.put("status", 0);
			jsonObject.put("name", person.getUsername());
			jsonObject.put("userId", person.getUserId());
			jsonObject.put("star",getTodatSellCount(person));
			//jsonObject.put("todayCount",todayCount);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (person.getCardLTime()==null) {
				jsonObject.put("time","暂无");
			}else{
				jsonObject.put("time",sdf.format(person.getCardLTime()));	
			}
			jsonObject.put("numof_cards_1",person.getCardLCount());
			jsonObject.put("numof_cards_2", person.getCardFCount());
		}else if(operator.equals("get_distribution_info")){
			List<DistributionRecord> list= distributionService.findDistributionByID(user.getUserId(),null,null,-1,-1);
			if(list==null||list.size()==0){
				jsonObject.put("status", -2);
				NoticeUtils.returnMsgToMoile(response, jsonObject);
				return null;
			}
			jsonObject.put("data", JSONArray.fromObject(list));
			jsonObject.put("status", 0);
		}
		NoticeUtils.returnMsgToMoile(response, jsonObject);

		//response.setContentType("text/html;charset=UTF-8");  
		/*  try {
				PrintWriter pw = response.getWriter();  
				pw.write(responseString);  
				pw.flush();  
				pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			return "success";*/
		return null;
	}
	@RequestMapping(value = "/logoutMb", method = {RequestMethod.POST }) 
	@ResponseBody
	public  String  logoutMb(@RequestBody String uid,HttpServletRequest  request,HttpServletResponse response) {
		JSONObject jsonObject=new JSONObject();
		request.getSession().setAttribute("user", null); 
		request.getSession().setAttribute("userType",-1); 
		jsonObject.put("status", 0);
		NoticeUtils.returnMsgToMoile(response, jsonObject);
		return null;
	}
	/**
	 * 添加代理----------移动端
	 * @param proxy
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addproxyMb", method = {RequestMethod.POST }) 
	@ResponseBody
	public  String  addproxy_mb(@RequestBody Proxy proxy,HttpServletRequest  request,HttpServletResponse response) {
		Person person = (Person) request.getSession().getAttribute("user");
		response.setContentType("text/html;charset=gb2312");
		PrintWriter out;
		JSONObject jsonObject2=new JSONObject();
		proxy.setCreateTime(new Date(System.currentTimeMillis()));
		proxy.setPioneerPerson(person.getUserId());
		if (PriorityIdentify.isMainProxy(person)||PriorityIdentify.isManager(person)) {


			int result=proxyService.addProxy(proxy);
			if (result>=1) {
				jsonObject2.put("status",0);
			}else{
				jsonObject2.put("status",result);
			}
		}else{
			jsonObject2.put("status",-1);
		}

		String responseString=jsonObject2.toString();

		try {
			PrintWriter pw = response.getWriter();  
			pw.write(responseString);  
			pw.flush();  
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return null;
	}
	/**
	 * 查看代理列表------------移动端
	 * 代理管理,拿到代理列表
	 * 目前修正，将方法拆开
	 * @return
	 */
	@RequestMapping("/proxyMb")
	public ModelAndView proxyManageMb(@RequestBody String  requestMsg,HttpServletRequest request,HttpServletResponse response){

		Person person = (Person) request.getSession().getAttribute("user");
		//String buyerIdString=request.getParameter("uid");
		int start_index=(int) JSONObject.fromObject(requestMsg).get("start_index");
		int end_index=(int) JSONObject.fromObject(requestMsg).get("end_index");
		PrintWriter out;
		JSONObject jsonObject=new JSONObject();
		//Date startTime=new Date(start_time*1000);
		//	Date endTime=new Date(end_time*1000);;
		ModelAndView model=new ModelAndView("proxy");
		List<Proxy> list=new ArrayList<Proxy>();
		if (PriorityIdentify.isManager(person)) {
			list=proxyService.getAllProxys(start_index, end_index);
		}else if(PriorityIdentify.isMainProxyAndNotice(person, response)){
			list=proxyService.getProxysByIndex(person.getUserId(), start_index, end_index);
			/*if (list!=null&&list.size()>0) {
				for(Proxy proxy:list){
					proxy.setPassword("");
				}
			}*/
		}
		//Collections.reverse(list);
		if (list!=null&&list.size()>0) {
			for(Proxy proxy:list){
				proxy.setCreateTimeInMb(proxy.getCreateTime().getTime()/1000);
				proxy.setContactName("");
				proxy.setContactPhone("");
				proxy.setPassword("");
				proxy.setAddress("");
			}
		}
		jsonObject.put("data", JSONArray.fromObject(list));
		jsonObject.put("status",0);
		try {
			PrintWriter pw = response.getWriter();  
			System.out.println(jsonObject.toString());
			pw.write(jsonObject.toString());  
			pw.flush();  
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return null;
	}
	/**
	 * 查看分销名单------------移动端
	 * 代理管理,拿到代理列表
	 * 目前修正，将方法拆开
	 * @return
	 */
	@RequestMapping("/distributionMb")
	public ModelAndView distributionListMb(@RequestBody String  requestMsg,HttpServletRequest request,HttpServletResponse response){

		Person person = (Person) request.getSession().getAttribute("user");

		int start_index=(int) JSONObject.fromObject(requestMsg).get("start_index");
		int end_index=(int) JSONObject.fromObject(requestMsg).get("end_index");
		PrintWriter out;
		JSONObject jsonObject=new JSONObject();
		List<Proxy> list=new ArrayList<Proxy>();
		List<Integer> firstClasslist=new ArrayList<Integer>();
		if (PriorityIdentify.isManager(person)) {
			return null;
		}else if(PriorityIdentify.isMainProxy(person)){
			return null;
			//list=proxyService.getProxysByIndex(person.getUserId(), start_index, end_index);
			/*if (list!=null&&list.size()>0) {
				for(Proxy proxy:list){
					proxy.setPassword("");
				}
			}*/
		}
		list=proxyService.getProxysByRecommendID(person.getUserId());
		if (list!=null&&list.size()>0) {
			for(Proxy proxy:list){
				proxy.setDistributionClass(1);
			}
		}
		firstClasslist=proxyService.getProxyIdsByRecommendID(person.getUserId());

		if (firstClasslist!=null&&firstClasslist.size()>0) {
			for (Integer firstClassProxyId : firstClasslist) {
				list.addAll(proxyService.getProxysByRecommendID(firstClassProxyId));
			}
		}
		if (start_index<list.size()) {
			if(start_index+end_index<list.size()){
				list=list.subList(start_index, start_index+end_index);	
			}else{
				list=list.subList(start_index,list.size());
			}
		}else{
			list=new ArrayList<Proxy>();
		}
		if (list!=null&&list.size()>0) {
			for(Proxy proxy:list){
				proxy.setCreateTimeInMb(proxy.getCreateTime().getTime()/1000);
				proxy.setContactName("");
				proxy.setContactPhone("");
				proxy.setPassword("");
				proxy.setAddress("");
			}
		}
		//只有代理有此功能
		//Collections.reverse(list);
		//	list=distributionService.findDistributionByID(person.getUserId());
		//		if (list!=null&&list.size()>0) {
		//			for(DistributionService distributionService:list){
		//				distributionService.setCreateTimeInMb(distributionService.getCreateTime().getTime()/1000);
		//				distributionService.setContactName("");
		//				distributionService.setContactPhone("");
		//				distributionService.setPassword("");
		//				distributionService.setAddress("");
		//			}
		//		}
		jsonObject.put("data", JSONArray.fromObject(list));
		jsonObject.put("status",0);
		try {
			PrintWriter pw = response.getWriter(); 
			System.out.println(jsonObject.toString());
			pw.write(jsonObject.toString());  
			pw.flush();  
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return null;
	}
	//logoutMb
	public int getTodatSellCount(Person person){
		Date todayStart=DateUtil.getDayBegin();
		Date todayEnd=DateUtil.getDayEnd();
		List<CardRecord> todayList=cardRecordService.findCardBySellerIdandTime(person.getUserId(),todayStart, todayEnd);
		int todayCount=0;	
		if (todayList!=null&&todayList.size()>0) {
			for(CardRecord cardRecord:todayList){
				todayCount=todayCount+cardRecord.getCardFCount()+cardRecord.getCardLCount();
			}	
		}

		return todayCount;
	}


}


