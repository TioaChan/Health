package dev.tioachan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import dev.tioachan.dao.CheckGroupDao;
import dev.tioachan.domain.CheckGroup;
import dev.tioachan.domain.CheckItem;
import dev.tioachan.entity.PageResult;
import dev.tioachan.entity.QueryPageBean;
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

	@Override
	public void addCheckGroup(CheckGroup groupData, Integer[] checkItemIds) {
		checkGroupDao.addCheckGroup(groupData);
		Integer id = groupData.getId();
		checkGroupDao.addCheckGroupCheckItems(id,checkItemIds);
	}

	@Override
	public PageResult pageQuery(QueryPageBean queryPageBean) {
		//当前线程上下文里，存放分页的参数
		PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
		//执行SQL时，MYBATIS基于拦截器，看一下上文里有没有参数，如果有参数，拼合到sql形成分页
		//select count(*)   / select * from   limit
		Page<CheckItem> page = checkGroupDao.selectByCondition(queryPageBean.getQueryString());
		return new PageResult(page.getTotal(),page.getResult());
	}


//	@Override
//	public void addCheckGroup(CheckGroup tempformData, Integer[] checkitemIds) {

//	}
}
