package dev.tioachan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import dev.tioachan.constant.MessageConstant;
import dev.tioachan.domain.CheckItem;
import dev.tioachan.entity.PageResult;
import dev.tioachan.entity.QueryPageBean;
import dev.tioachan.entity.Result;
import dev.tioachan.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
