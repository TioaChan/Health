package dev.tioachan.service;

import com.github.pagehelper.Page;
import dev.tioachan.domain.CheckItem;
import dev.tioachan.entity.PageResult;
import dev.tioachan.entity.QueryPageBean;

public interface CheckItemService {
	public void add(CheckItem checkItem);
	public PageResult pageQuery(QueryPageBean queryPageBean);
	public Page<CheckItem> selectByCondition(String queryString);
}
