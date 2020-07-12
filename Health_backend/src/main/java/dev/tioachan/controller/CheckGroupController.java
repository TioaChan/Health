package dev.tioachan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import dev.tioachan.constant.MessageConstant;
import dev.tioachan.domain.CheckGroup;
import dev.tioachan.entity.Result;
import dev.tioachan.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

	@Reference
	private CheckGroupService checkGroupService;

	@RequestMapping("/getCheckItem")
	public Result getCheckItem(){
		List<CheckGroup> checkGroupList=null;
		try {
			checkGroupList=checkGroupService.getCheckItem();
		}catch (Exception e){
			e.printStackTrace();
			return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
		}
		return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkGroupList);
	}
}
