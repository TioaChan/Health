package dev.tioachan.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import dev.tioachan.dao.ReportDao;
import dev.tioachan.domain.OrderSetting;
import dev.tioachan.service.ReportService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportDao reportDao;

	@Override
	public List<Integer> countMemberByMonth(List<Date> months) throws Exception {

		List<Integer> counterList=new ArrayList<>();
		try {
			for (Date startDay : months) {
				Date endDay = DateUtils.addMonths(startDay, 1);
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
}
