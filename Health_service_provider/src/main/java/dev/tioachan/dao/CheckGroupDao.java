package dev.tioachan.dao;

import com.github.pagehelper.Page;
import dev.tioachan.domain.CheckGroup;
import dev.tioachan.domain.CheckItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckGroupDao {
	List<CheckGroup> getCheckItem();

	void addCheckGroup(CheckGroup tempformData);

	void addCheckGroupCheckItems(@Param("groupId") Integer groupId,@Param("items") Integer[] items);

	Page<CheckGroup> selectByCondition(String queryString);
}
