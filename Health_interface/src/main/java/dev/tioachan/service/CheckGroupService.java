package dev.tioachan.service;

import dev.tioachan.domain.CheckGroup;

import java.util.List;

public interface CheckGroupService {
	List<CheckGroup> getCheckItem();

	void addCheckGroup(CheckGroup groupData, Integer[] checkItemIds);
}
