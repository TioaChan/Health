package dev.tioachan.service;

import dev.tioachan.domain.Setmeal;
import dev.tioachan.entity.PageResult;
import dev.tioachan.entity.QueryPageBean;

import java.util.List;

public interface SetmealService {
	PageResult pageQuery(QueryPageBean queryPageBean);

	void addCheckGroup(Setmeal tempformData, Integer[] checkitemIds);

	List<Setmeal> findAll();

	Setmeal findById(int id);
}
