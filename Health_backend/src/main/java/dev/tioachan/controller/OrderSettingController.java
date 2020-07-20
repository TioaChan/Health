package dev.tioachan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import dev.tioachan.constant.MessageConstant;
import dev.tioachan.domain.OrderSetting;
import dev.tioachan.entity.Result;
import dev.tioachan.service.OrderSettingService;
import dev.tioachan.util.POIUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ordersetting")
@CrossOrigin("*")
public class OrderSettingController {

	@Reference
	private OrderSettingService orderSettingService;

	@RequestMapping("/upload")
	public Result upload(@RequestParam("excelFile") MultipartFile multipartFile){
		try {
			List<String[]> list = POIUtils.readExcel(multipartFile);//使用POI解析表格数据
			List<OrderSetting> data = new ArrayList<>();
			for (String[] strings : list) {
				String orderDate = strings[0];
				String number = strings[1];
				OrderSetting orderSetting = new OrderSetting(DateUtils.parseDate(orderDate,"yyyy/MM/dd"),Integer.parseInt(number));
				data.add(orderSetting);
			}
			//通过dubbo远程调用服务实现数据批量导入到数据库
			orderSettingService.add(data);
			return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			//文件解析失败
			return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
		}
	}

	@RequestMapping("/getOrderSettingByMonth")
	public Result getOrderSettingByMonth(@RequestParam("date") String month){
		try{
			List<OrderSetting> list = orderSettingService.getOrderSettingByMonth(month);
			//遍历OrderSetting
//			List<Map> data = new ArrayList<>();
			return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
		}catch (Exception e){
			e.printStackTrace();
			return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
		}
	}
}
