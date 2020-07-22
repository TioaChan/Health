package dev.tioachan.dao;

import com.github.pagehelper.Page;
import dev.tioachan.domain.CheckGroup;
import dev.tioachan.domain.CheckItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckGroupDao {

	void addCheckGroup(CheckGroup tempformData);

	void addCheckGroupCheckItems(@Param("groupId") Integer groupId,@Param("items") Integer[] items);

	void removeCheckGroupCheckItems(@Param("groupId")Integer id, @Param("items") Integer[] toArray);

	Page<CheckGroup> selectByCondition(String queryString);

	void edit(CheckGroup tempformData);

	void delGroup(Integer groupId);

	List<CheckGroup> getAll();

	List<CheckGroup> findBySetmealId(@Param("setmeal_id")Integer id);
}
