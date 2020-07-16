package dev.tioachan.service;

import dev.tioachan.domain.Setmeal;
import dev.tioachan.entity.PageResult;
import dev.tioachan.entity.QueryPageBean;

public interface SetmealService {
	PageResult pageQuery(QueryPageBean queryPageBean);

	void addCheckGroup(Setmeal tempformData, Integer[] checkitemIds);
}
