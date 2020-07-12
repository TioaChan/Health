package dev.tioachan.dao;

import dev.tioachan.domain.CheckGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckGroupDao {
	List<CheckGroup> getCheckItem();

	void addCheckGroup(CheckGroup tempformData);

	void addCheckGroupCheckItems(@Param("groupId") Integer groupId,@Param("items") Integer[] items);
}
