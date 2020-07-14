package dev.tioachan.service;

import com.github.pagehelper.Page;
import dev.tioachan.domain.CheckItem;
import dev.tioachan.entity.PageResult;
import dev.tioachan.entity.QueryPageBean;

import java.util.List;

public interface CheckItemService {
	void add(CheckItem checkItem);
	PageResult pageQuery(QueryPageBean queryPageBean);
	Page<CheckItem> selectByCondition(String queryString);

	void edit(CheckItem checkItem);

	List<CheckItem> getAll();

	List<Integer> getIdsByCheckGroupId(Integer id);
}
