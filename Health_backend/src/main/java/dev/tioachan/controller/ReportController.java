package dev.tioachan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import dev.tioachan.constant.MessageConstant;
import dev.tioachan.entity.Result;
import dev.tioachan.service.ReportService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
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
			memberCount=reportService.counterMemberByMonth(months);
			Map<String,Object> map=new HashMap<>();
			map.put("months",monthsStr);
			map.put("memberCount",memberCount);

			return new Result(true,MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
		}
	}
}
