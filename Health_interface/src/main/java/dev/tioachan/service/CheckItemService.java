package dev.tioachan.service;

import com.github.pagehelper.Page;
import dev.tioachan.domain.CheckItem;
import dev.tioachan.entity.PageResult;
import dev.tioachan.entity.QueryPageBean;

public interface CheckItemService {
	void add(CheckItem checkItem);
	PageResult pageQuery(QueryPageBean queryPageBean);
	Page<CheckItem> selectByCondition(String queryString);

	void edit(CheckItem checkItem);
}
