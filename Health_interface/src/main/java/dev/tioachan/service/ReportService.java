package dev.tioachan.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ReportService {
	List<Integer> countMemberByMonth(List<Date> months) throws Exception;

	List<Map<String, Object>> countSetmeal();

	Map<String,Object> getBusinessReportData () throws Exception ;

}
