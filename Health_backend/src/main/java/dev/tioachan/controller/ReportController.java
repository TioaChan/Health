package dev.tioachan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import dev.tioachan.constant.MessageConstant;
import dev.tioachan.entity.Result;
import dev.tioachan.service.ReportService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
@CrossOrigin("*")
public class ReportController {

	@Reference
	private ReportService reportService;

	@RequestMapping("/getMemberReport")
	public Result getMemberReport(String startDate,String endDate){
		try {
			Date startMonth = DateUtils.parseDate(startDate, "yyyy-MM");
			Date endMonth = DateUtils.parseDate(endDate, "yyyy-MM");

//			Calendar.getInstance().
			//前端用月份数组
			List<String> monthsStr=new ArrayList<>();
			//查询用月份
			List<Date> months=new ArrayList<>();
			//按月份存放数量统计数字
			List<Integer> memberCount=new ArrayList<>();

//			//准备月份数据
			while (!DateUtils.isSameDay(startMonth,endMonth)){
				months.add(startMonth);
				monthsStr.add(new SimpleDateFormat("yyyy.MM").format(startMonth));
				startMonth=DateUtils.addMonths(startMonth,1);
			}
			months.add(endMonth);
			monthsStr.add(new SimpleDateFormat("yyyy.MM").format(endMonth));

			System.out.println(monthsStr);
			System.out.println(months);

			//查询
			memberCount=reportService.countMemberByMonth(months);
			Map<String,Object> map=new HashMap<>();
			map.put("months",monthsStr);
			map.put("memberCount",memberCount);

			return new Result(true,MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
		}
	}

	@RequestMapping("/getSetmealReport")
	public Result getSetmealReport(){
		/**
		 * 前端需要的数据结构：
		 * {
		 * 		setmealCount:[
		 * 						{"name":"套餐1","value":"1"},
		 * 						{"name":"套餐2","value":"2"},
		 * 						{"name":"套餐3","value":"3"},
		 * 						{"name":"套餐4","value":"4"},
		 * 					],
		 * 		setmealNames:["套餐1","套餐2","套餐3","套餐4"]
		 * }
		 *
		 *	1.一个 Map<String,Object> result
		 *	2. result.put( "setmealNames",new List<String> )   ===>setmealNames
		 *	3. result.put( "setmealCount",new List< Map<String,Object> setmeal > setmealList)   ===> setmealCount
		 *  		3.1  setmeal => setmeal.put("name","套餐1").put("value","3")
		 *  		3.2  	setmealList==> setmealList.add(setmeal)
		 * */

		//返回给前端的result
		Map<String,Object> result = new HashMap<>();
		//要放在result里的setmealCount
		List<Map<String,Object>> setmealCountResult = new ArrayList<>();
		//放进result的setmealNames
		List<String> setmealNamesResult = new ArrayList<>();

		result.put("setmealCount",setmealCountResult);
		result.put("setmealNames",setmealNamesResult);

		try{
			//来自数据库的setmealCount
			List<Map<String,Object>> setmealCount = reportService.countSetmeal();
			if(setmealCount != null){
				//遍历查询结果,用map接收
				for (Map<String, Object> map : setmealCount) {
					//如果套餐预约数量不为0
					String name = (String) map.get("name");//套餐名称
					int value = Integer.parseInt(map.get("value").toString());
					if(value> 0){
						//将名字放入setmealNamesResult
						setmealNamesResult.add(name);
						//将该map放入setmealCountResult
						setmealCountResult.add(map);
					}
				}
			}
			return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,result);
		}catch (Exception e){
			e.printStackTrace();
			return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
		}
	}
}
