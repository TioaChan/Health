package dev.tioachan.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import dev.tioachan.dao.MemberDao;
import dev.tioachan.dao.OrderDao;
import dev.tioachan.dao.ReportDao;
import dev.tioachan.domain.OrderSetting;
import dev.tioachan.service.ReportService;
//import org.apache.commons.lang3.time.DateUtils;
import dev.tioachan.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;

@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private ReportDao reportDao;

	@Override
	public List<Integer> countMemberByMonth(List<Date> months) throws Exception {

		List<Integer> counterList=new ArrayList<>();
		try {
			for (Date startDay : months) {
				Date endDay = org.apache.commons.lang3.time.DateUtils.addMonths(startDay, 1);
				counterList.add(reportDao.counterMemberByMonth(startDay,endDay));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return counterList;
	}

	@Override
	public List<Map<String, Object>> countSetmeal() {
		return reportDao.countSetmeal();
	}

	@Override
	public Map<String, Object> getBusinessReportData() throws Exception {
		/**
		 * 获得运营统计数据
		 * Map数据格式：
		 {
		 * todayNewMember ‐> number
		 * totalMember ‐> number
		 * thisWeekNewMember ‐> number
		 * thisMonthNewMember ‐> number
		 * todayOrderNumber ‐> number
		 * todayVisitsNumber ‐> number
		 * thisWeekOrderNumber ‐> number
		 * thisWeekVisitsNumber ‐> number
		 * thisMonthOrderNumber ‐> number
		 * thisMonthVisitsNumber ‐> number
		 * hotSetmeals ‐> List<Setmeal>
		 }
		 */
		//报表日期
		String today = DateUtils.parseDate2String(DateUtils.getToday());
		//获得本周一日期
		String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
		//获得本月第一天日期
		String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());

		//本日新增会员数 regTime = #{date}
		Integer todayNewMember = memberDao.findMemberCountByDate(today);
		//总会员数
		Integer totalMember = memberDao.findMemberTotalCount();
		//本周新增会员数
		Integer thisWeekNewMember = memberDao.findMemberCountAfterDate(thisWeekMonday);
		//本月新增会员数
		Integer thisMonthNewMember = memberDao.findMemberCountAfterDate(firstDay4ThisMonth);

		//今日预约数
		Integer todayOrderNumber = orderDao.findOrderCountByDate(today);
		//本周预约数
		Integer thisWeekOrderNumber = orderDao.findOrderCountAfterDate(thisWeekMonday);
		//本月预约数
		Integer thisMonthOrderNumber = orderDao.findOrderCountAfterDate(firstDay4ThisMonth);
		//今日到诊数
		Integer todayVisitsNumber = orderDao.findVisitsCountByDate(today);
		//本周到诊数
		Integer thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate(thisWeekMonday);
		//本月到诊数
		Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(firstDay4ThisMonth);

		//热门套餐查询
		List<Map> hotSetmeal = orderDao.findHotSetmeal();

		Map<String,Object> result = new HashMap<>();
		result.put("reportDate",today);
		result.put("todayNewMember",todayNewMember);
		result.put("totalMember",totalMember);
		result.put("thisWeekNewMember",thisWeekNewMember);
		result.put("thisMonthNewMember",thisMonthNewMember);
		result.put("todayOrderNumber",todayOrderNumber);
		result.put("thisWeekOrderNumber",thisWeekOrderNumber);
		result.put("thisMonthOrderNumber",thisMonthOrderNumber);
		result.put("todayVisitsNumber",todayVisitsNumber);
		result.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
		result.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
		result.put("hotSetmeal",hotSetmeal);
		return result;
	}
}
