package dev.tioachan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import dev.tioachan.constant.MessageConstant;
import dev.tioachan.domain.CheckGroup;
import dev.tioachan.domain.CheckItem;
import dev.tioachan.entity.PageResult;
import dev.tioachan.entity.QueryPageBean;
import dev.tioachan.entity.Result;
import dev.tioachan.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
	@Reference
	private CheckItemService checkItemService;

	@RequestMapping("/add")
	public Result add(@RequestBody CheckItem checkItem){
		try {
			checkItemService.add(checkItem);
		}catch (Exception e){
			return new Result(false,MessageConstant.ADD_CHECKITEM_FAIL);
		}
		return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
	}

	@RequestMapping("/findPage")
	public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
		PageResult pageResult = checkItemService.pageQuery(queryPageBean);
		return pageResult;
	}

	@RequestMapping("/edit")
	public Result edit(@RequestBody CheckItem checkItem){
		try {
			checkItemService.edit(checkItem);
		}catch (Exception e){
			return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
		}
		return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
	}

	@RequestMapping("/delete")
	public Result delete(Integer itemId){
		try {
			checkItemService.delete(itemId);
		}catch (Exception e){
			return new Result(false,e.getMessage());
		}
		return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
	}

	@RequestMapping("/getAll")
	public Result getAll(){
		try {
			List<CheckItem> checkItemList=checkItemService.getAll();
			return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemList);
		}catch (Exception e){
			e.printStackTrace();
			return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
		}
	}

	@RequestMapping("/getIdsByCheckGroup")
	public Result getIdsByCheckGroup(Integer id){
		try {
			List<Integer> checkItemList=checkItemService.getIdsByCheckGroupId(id);
			return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemList);
		}catch (Exception e){
			e.printStackTrace();
			return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
		}
	}

}
