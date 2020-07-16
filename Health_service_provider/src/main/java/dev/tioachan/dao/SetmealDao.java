package dev.tioachan.dao;

import com.github.pagehelper.Page;
import dev.tioachan.domain.CheckGroup;
import dev.tioachan.domain.CheckItem;
import dev.tioachan.domain.Setmeal;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetmealDao {
	List<Integer> getIdsByCheckGroupId(@Param("groupId") Integer id);

	Page<Setmeal> selectByCondition(String queryString);

	void addCheckGroup(Setmeal tempformData);

	void addSetmealCheckGroup(@Param("setmeal_id") Integer id, @Param("itemIds") Integer[] checkitemIds);
}
