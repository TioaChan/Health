package dev.tioachan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import dev.tioachan.dao.CheckGroupDao;
import dev.tioachan.dao.CheckItemDao;
import dev.tioachan.dao.SetmealDao;
import dev.tioachan.domain.CheckGroup;
import dev.tioachan.domain.Setmeal;
import dev.tioachan.entity.PageResult;
import dev.tioachan.entity.QueryPageBean;
import dev.tioachan.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

	@Autowired
	private SetmealDao setmealDao;
	@Autowired
	private CheckGroupDao checkGroupDao;
	@Autowired
	private CheckItemDao checkItemDao;

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

	@Override
	public List<Setmeal> findAll() {
		return setmealDao.findAll();
	}

	@Override
	public Setmeal findById(Integer id, boolean isNested) {
		Setmeal setmeal = setmealDao.findById(id);
		if (isNested){
			List<CheckGroup> checkGroupBySetmealId = checkGroupDao.findBySetmealId(id);
			for (CheckGroup checkGroup : checkGroupBySetmealId) {
				checkGroup.setCheckItems(checkItemDao.findByCheckGroupId(checkGroup.getId()));
			}
			setmeal.setCheckGroups(checkGroupBySetmealId);
		}
		return setmeal;
	}

}
