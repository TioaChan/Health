package dev.tioachan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import dev.tioachan.dao.CheckItemDao;
import dev.tioachan.domain.CheckItem;
import dev.tioachan.entity.PageResult;
import dev.tioachan.entity.QueryPageBean;
import dev.tioachan.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

	@Autowired
	private CheckItemDao checkItemDao;
	@Override
	public void add(CheckItem checkItem) {

			checkItemDao.add(checkItem);
	}

	@Override
	public PageResult pageQuery(QueryPageBean queryPageBean) {
		//当前线程上下文里，存放分页的参数
		PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
		//执行SQL时，MYBATIS基于拦截器，看一下上文里有没有参数，如果有参数，拼合到sql形成分页
		//select count(*)   / select * from   limit
		Page<CheckItem> page = checkItemDao.selectByCondition(queryPageBean.getQueryString());
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public Page<CheckItem> selectByCondition(String queryString) {
		return null;
	}

	@Override
	public void edit(CheckItem checkItem) {
		checkItemDao.edit(checkItem);
	}

	@Override
	public List<CheckItem> getAll() {
		return checkItemDao.getAll();
	}

	@Override
	public List<Integer> getIdsByCheckGroupId(Integer id) {
		return checkItemDao.getIdsByCheckGroupId(id);
	}
}
