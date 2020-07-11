package dev.tioachan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import dev.tioachan.dao.CheckItemDao;
import dev.tioachan.domain.CheckItem;
import dev.tioachan.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

	@Autowired
	private CheckItemDao checkItemDao;
	@Override
	public void add(CheckItem checkItem) {

			checkItemDao.add(checkItem);
	}
}
