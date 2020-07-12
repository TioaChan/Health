package dev.tioachan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import dev.tioachan.dao.CheckGroupDao;
import dev.tioachan.domain.CheckGroup;
import dev.tioachan.service.CheckGroupService;
import dev.tioachan.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
	@Autowired
	private CheckGroupDao checkGroupDao;

	@Override
	public List<CheckGroup> getCheckItem() {
		return checkGroupDao.getCheckItem();
	}
}
