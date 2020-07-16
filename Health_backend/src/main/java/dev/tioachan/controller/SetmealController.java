package dev.tioachan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import dev.tioachan.entity.PageResult;
import dev.tioachan.entity.QueryPageBean;
import dev.tioachan.service.SetmealService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

	@Reference
	private SetmealService setmealService;

	@RequestMapping("/findPage")
	public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
		PageResult pageResult = setmealService.pageQuery(queryPageBean);
		return pageResult;
	}
}
