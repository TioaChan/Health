package dev.tioachan.dao;

import com.github.pagehelper.Page;
import dev.tioachan.domain.CheckItem;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckItemDao {
	void add(CheckItem checkItem);

	Page<CheckItem> selectByCondition(String queryString);

	void edit(CheckItem checkItem);
}
