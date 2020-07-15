package dev.tioachan.dao;

import dev.tioachan.domain.CheckGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetmealDao {
	List<Integer> getIdsByCheckGroupId(@Param("groupId") Integer id);
}
