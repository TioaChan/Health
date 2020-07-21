package dev.tioachan.dao;

import com.github.pagehelper.Page;
import dev.tioachan.domain.CheckItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckItemDao {
	void add(CheckItem checkItem);

	Page<CheckItem> selectByCondition(String queryString);

	void edit(CheckItem checkItem);

	List<CheckItem> getAll();

	List<Integer> getIdsByCheckGroupId(@Param("groupId") Integer id);

	List<Integer> getGroupIdByCheckItemId(@Param("itemId")Integer itemId);

	void del(@Param("itemId") Integer itemId);

    List<CheckItem> findByCheckGroupId(@Param("id") Integer id);

}
