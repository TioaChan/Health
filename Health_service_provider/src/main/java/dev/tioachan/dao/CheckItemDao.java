package dev.tioachan.dao;

import com.github.pagehelper.Page;
import dev.tioachan.domain.CheckItem;

public interface CheckItemDao {
	void add(CheckItem checkItem);

	Page<CheckItem> selectByCondition(String queryString);
}
