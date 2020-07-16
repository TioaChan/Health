package dev.tioachan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import dev.tioachan.dao.CheckGroupDao;
import dev.tioachan.dao.CheckItemDao;
import dev.tioachan.dao.SetmealDao;
import dev.tioachan.domain.CheckGroup;
import dev.tioachan.domain.CheckItem;
import dev.tioachan.entity.PageResult;
import dev.tioachan.entity.QueryPageBean;
import dev.tioachan.entity.Result;
import dev.tioachan.service.CheckGroupService;
import dev.tioachan.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
	@Autowired
	private CheckGroupDao checkGroupDao;
	@Autowired
	private CheckItemDao checkItemDao;
	@Autowired
	private SetmealDao setmealDao;

	@Override
	public void addCheckGroup(CheckGroup groupData, Integer[] checkItemIds) {
		checkGroupDao.addCheckGroup(groupData);
		if (checkItemIds.length>0){
			Integer id = groupData.getId();
			checkGroupDao.addCheckGroupCheckItems(id,checkItemIds);
		}
	}

	@Override
	public PageResult pageQuery(QueryPageBean queryPageBean) {
		//当前线程上下文里，存放分页的参数
		PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
		//执行SQL时，MYBATIS基于拦截器，看一下上文里有没有参数，如果有参数，拼合到sql形成分页
		//select count(*)   / select * from   limit
		Page<CheckGroup> page = checkGroupDao.selectByCondition(queryPageBean.getQueryString());
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public void edit(CheckGroup tempformData, Integer[] checkitemIds) {
		//	originalItemids 为数据库中原有的组的项目的数组 假设原始数据为 [1,2,3,4]
		List<Integer> originalItemids = checkItemDao.getIdsByCheckGroupId(tempformData.getId());
		//	currentItemIds  为用户处理后的数据，tempList是其备份，假设处理后的数据为[3,4,5,6,7]
		List<Integer> currentItemIds=new ArrayList<>(Arrays.asList(checkitemIds));
		List<Integer> tempList = new ArrayList<>(Arrays.asList(checkitemIds));

		//处理数据.removeAll(原始数据)
		//[3,4,5,6,7].removeAll([1,2,3,4]) => [5,6,7] 表示为用户勾选的项目，需要在数据库中增加
		currentItemIds.removeAll(originalItemids);

		//原始数据.removeAll（处理数据）
		//此处，处理数据已被操作，使用处理数据的备份
		//[1,2,3,4].removeAll([3,4,5,6,7]) => [1,2] 表示为用户取消的项目，需要在数据库中删除
		originalItemids.removeAll(tempList);

		//所有改动同步到数据库
		checkGroupDao.edit(tempformData);

		if (currentItemIds.size()>0){
			checkGroupDao.addCheckGroupCheckItems(tempformData.getId(),currentItemIds.toArray(new Integer[currentItemIds.size()]));
		}
		if (originalItemids.size()>0){
			checkGroupDao.removeCheckGroupCheckItems(tempformData.getId(),originalItemids.toArray(new Integer[originalItemids.size()]));
		}
	}

	@Override
	public void delGroup(Integer groupId) {
		List<Integer> idsByCheckGroupId = checkItemDao.getIdsByCheckGroupId(groupId);
		List<Integer> idsByCheckGroupId1 = setmealDao.getIdsByCheckGroupId(groupId);
		if (idsByCheckGroupId.size()==0&&idsByCheckGroupId1.size()==0){
			checkGroupDao.delGroup(groupId);
		}else {
			throw new RuntimeException("存在已关联至本检查组的检查项或检查套餐");
		}
	}

	@Override
	public List<CheckGroup> getAll() {
		return checkGroupDao.getAll();
	}

}
