package dev.tioachan.service;

import java.util.Date;
import java.util.List;

public interface ReportService {
	List<Integer> counterMemberByMonth(List<Date> months) throws Exception;
}
