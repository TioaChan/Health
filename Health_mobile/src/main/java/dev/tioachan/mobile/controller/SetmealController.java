package dev.tioachan.mobile.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import dev.tioachan.constant.MessageConstant;
import dev.tioachan.domain.Setmeal;
import dev.tioachan.entity.Result;
import dev.tioachan.service.SetmealService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
@CrossOrigin("*")
public class SetmealController {
	@Reference
	private SetmealService setmealService;
	//查询所有套餐
	@RequestMapping("/getAllSetmeal")
	public Result getAllSetmeal(){
		try{
			List<Setmeal> list = setmealService.findAll();
			return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,list);
		}catch (Exception e){
			e.printStackTrace();
			return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
		}
	}
	@RequestMapping("/findByIdNested")
	public Result findByIdNested(Integer id){
		try{
			Setmeal setmeal = setmealService.findById(id,true);
			return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
		}catch (Exception e){
			e.printStackTrace();
			return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
		}
	}

	@RequestMapping("/findById")
	public Result findById(Integer id){
		try{
			Setmeal setmeal = setmealService.findById(id,true);
			return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
		}catch (Exception e){
			e.printStackTrace();
			return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
		}
	}
}
