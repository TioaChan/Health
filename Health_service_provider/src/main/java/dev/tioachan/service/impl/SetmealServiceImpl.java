package dev.tioachan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import dev.tioachan.dao.SetmealDao;
import dev.tioachan.domain.CheckItem;
import dev.tioachan.domain.Setmeal;
import dev.tioachan.entity.PageResult;
import dev.tioachan.entity.QueryPageBean;
import dev.tioachan.service.CheckItemService;
import dev.tioachan.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

	@Autowired
	private SetmealDao setmealDao;

	@Override
	public PageResult pageQuery(QueryPageBean queryPageBean) {
		//当前线程上下文里，存放分页的参数
		PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
		//执行SQL时，MYBATIS基于拦截器，看一下上文里有没有参数，如果有参数，拼合到sql形成分页
		//select count(*)   / select * from   limit
		Page<Setmeal> page = setmealDao.selectByCondition(queryPageBean.getQueryString());
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public void addCheckGroup(Setmeal tempformData, Integer[] checkitemIds) {
		setmealDao.addCheckGroup(tempformData);
		if (checkitemIds.length>0){
			Integer id = tempformData.getId();
			setmealDao.addSetmealCheckGroup(id,checkitemIds);
		}
	}
}
