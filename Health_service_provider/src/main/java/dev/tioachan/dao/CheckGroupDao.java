package dev.tioachan.dao;

import dev.tioachan.domain.CheckGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckGroupDao {
	List<CheckGroup> getCheckItem();

}
