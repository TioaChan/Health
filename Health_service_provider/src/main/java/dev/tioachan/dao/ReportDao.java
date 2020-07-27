package dev.tioachan.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface ReportDao {

	Integer counterMemberByMonth(@Param("startDay") Date startDay, @Param("endDay") Date endDay);

	List<Map<String, Object>> countSetmeal();

}
